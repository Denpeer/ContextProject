package com.funkydonkies.controllers;

import com.funkydonkies.curve.CustomCurveMesh;
import com.funkydonkies.factories.PenguinFactory;
import com.funkydonkies.factories.SpikeyBallFactory;
import com.funkydonkies.gamestates.DifficultyState;
import com.funkydonkies.interfaces.MyAbstractRigidBodyControl;
import com.jme3.app.state.AppStateManager;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.PhysicsTickListener;
import com.jme3.bullet.collision.PhysicsCollisionEvent;
import com.jme3.bullet.collision.PhysicsCollisionListener;
import com.jme3.bullet.collision.shapes.SphereCollisionShape;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;

/**
 * Control class for the spikey ball. Takes care of collisions between the fish and the penguins.
 */
public class SpikeyBallControl extends MyAbstractRigidBodyControl implements PhysicsTickListener,
		PhysicsCollisionListener {
	private static final float MAX_DEVIANCE_ON_Z = 0.1f;
	private static final float MAX_ROTATIONAL_DEVIANCE = 0.1f;
	private Vector3f initialSpawn;
	private Vector3f initialSpeed = new Vector3f(0, 0, 0);
	private final AppStateManager stateManager;

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
		this.stateManager = sManager;
	}

	/**
	 * This Method calls initialization which should occur after the control has been added to the
	 * spatial. setSpatial(spatial) is called by addControl(control) in Spatial.
	 * 
	 * @param spatial
	 *            spatial this control should control
	 */
	@Override
	public void setSpatial(final Spatial spatial) {
		super.setSpatial(spatial);
		init();
	}

	/**
	 * The initialize method for the control.
	 */
	public void init() {
		final int yOffSet = 50, xOffSet = 100;
		initialSpawn = new Vector3f(xOffSet, CustomCurveMesh.getLaunchPadHeight() + yOffSet, 0);
		setLocation(initialSpawn);
		setSpeed(initialSpeed);
	}

	/**
	 * Set the physics space and add this controller as tick listener.
	 * 
	 * @param space
	 *            takes a pre-defined jme3 physicsSpace
	 */
	@Override
	public void setPhysicsSpace(final PhysicsSpace space) {
		super.setPhysicsSpace(space);
		space.addTickListener(this);
		space.addCollisionListener(this);
		space.add(this);
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
	 * Sets the speed for the Ball by calling setLinVelocity on the physics.
	 * 
	 * @param vel
	 *            Vector3f, speed to set on the ball
	 */
	public void setSpeed(final Vector3f vel) {
		setLinearVelocity(vel);
	}

	/**
	 * Sets the ball´s location by calling setPhysicsLocation on its physics.
	 * 
	 * @param loc
	 *            Vector3f the new location
	 */
	public void setLocation(final Vector3f loc) {
		setPhysicsLocation(loc);
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
				PenguinFactory.STANDARD_PENGUIN_NAME)) {
			stateManager.getState(DifficultyState.class).resetDiff();
			destroy(event, PenguinFactory.STANDARD_PENGUIN_NAME);
		}
	}
}
