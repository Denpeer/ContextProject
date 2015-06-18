package com.funkydonkies.controllers;

import com.funkydonkies.curve.CustomCurveMesh;
import com.funkydonkies.factories.PenguinFactory;
import com.funkydonkies.factories.SpikeyBallFactory;
import com.funkydonkies.gamestates.DifficultyState;
import com.funkydonkies.gamestates.PlayState;
import com.funkydonkies.interfaces.MyAbstractRigidBodyControl;
import com.funkydonkies.sounds.DangerAboveSound;
import com.funkydonkies.sounds.ObstacleCollisionSound;
import com.funkydonkies.sounds.SoundState;
import com.jme3.app.state.AppStateManager;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.PhysicsTickListener;
import com.jme3.bullet.collision.PhysicsCollisionEvent;
import com.jme3.bullet.collision.PhysicsCollisionListener;
import com.jme3.bullet.collision.shapes.SphereCollisionShape;
import com.jme3.math.Vector3f;

/**
 * Control class for the spikey ball. Takes care of collisions between the fish and the penguins.
 */
public class SpikeyBallControl extends MyAbstractRigidBodyControl implements PhysicsTickListener,
		PhysicsCollisionListener {
	private static final float MAX_DEVIANCE_ON_Z = 0.1f;
	private static final float MAX_ROTATIONAL_DEVIANCE = 0.1f;
	private AppStateManager stateManager;
	private Vector3f initialSpawn;
	private Vector3f initialSpeed = new Vector3f(0, 0, 0);
	private final DifficultyState diffState;

	/**
	 * Constructor for ball physics controller.
	 * 
	 * @param sphereCollisionShape
	 *            Collision shape used by the physics
	 * @param sManager
	 *            the AppStateManager
	 * @param mass
	 *            mass of the sphere
	 */
	public SpikeyBallControl(final SphereCollisionShape sphereCollisionShape,
			final AppStateManager sManager, final float mass) {
		super(sphereCollisionShape, mass);
		diffState = sManager.getState(DifficultyState.class);
		stateManager = sManager;
	}
	
	@Override
	public void setPhysicsSpace(final PhysicsSpace space) {
		super.setPhysicsSpace(space);
		space.addTickListener(this);
		space.addCollisionListener(this);
	}

	@Override
	public void init() {
		stateManager.getState(SoundState.class).queueSound(new DangerAboveSound());
		final int yOffSet = 50, xOffSet = 100;
		initialSpawn = new Vector3f(xOffSet, CustomCurveMesh.getLaunchPadHeight() + yOffSet, 0);
		setPhysicsLocation(initialSpawn);
		setLinearVelocity(initialSpeed);
		stateManager.getState(PlayState.class).getPhysicsSpace().add(this);
	}

	/**
	 * Performed at each physics tick. z-axis.
	 * 
	 * @param space
	 *            The physics space
	 * @param tpf
	 *            time per frame in seconds (time since last frame) for normalizing in faster
	 *            computers
	 */
	public void physicsTick(final PhysicsSpace space, final float tpf) {
	}

	/**
	 * Performed before each physics tick. Sets the z location to 0 to restrict the object from
	 * moving on the z-axis.
	 * 
	 * @param space
	 *            The physics space
	 * @param tpf
	 *            time per frame in seconds (time since last frame) for normalizing in faster
	 *            computers
	 */
	public void prePhysicsTick(final PhysicsSpace space, final float tpf) {
		final Vector3f loc = getPhysicsLocation();
		final Vector3f angularvel = getAngularVelocity();
		if (Math.abs(loc.z) > MAX_DEVIANCE_ON_Z) {
			loc.z = 0;
			setPhysicsLocation(loc);
		}
		if (Math.abs(angularvel.x) > MAX_ROTATIONAL_DEVIANCE
				|| Math.abs(angularvel.y) > MAX_ROTATIONAL_DEVIANCE) {
			angularvel.y = 0;
			angularvel.x = 0;
			setAngularVelocity(angularvel);
		}
	}

	/**
	 * Listens for collisions. If the spikeyball collides (touches) with the penguin balls then
	 * remove the penguin balls
	 * 
	 * @param event
	 *            a PhysicsCollisionEvent which stores information about the collision
	 */
	public void collision(final PhysicsCollisionEvent event) {
		if (checkCollision(event, SpikeyBallFactory.SPIKEYBALL_NAME,
				PenguinFactory.PENGUIN_NAME)) {
			stateManager.getState(SoundState.class).queueSound(new ObstacleCollisionSound());
			diffState.resetDiff();
			destroy(event, PenguinFactory.PENGUIN_NAME);
		}
	}
}
