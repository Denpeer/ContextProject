package com.funkydonkies.controllers;

import com.funkydonkies.curve.CustomCurveMesh;
import com.funkydonkies.factories.PenguinFactory;
import com.funkydonkies.interfaces.MyAbstractRigidBodyControl;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.PhysicsTickListener;
import com.jme3.bullet.collision.PhysicsCollisionEvent;
import com.jme3.bullet.collision.PhysicsCollisionListener;
import com.jme3.bullet.collision.shapes.SphereCollisionShape;
import com.jme3.math.Vector3f;

/**
 * Control class for the penguin. Takes care of collisions between the penguin and the curve.
 */
public class PenguinControl extends MyAbstractRigidBodyControl implements PhysicsTickListener,
		PhysicsCollisionListener {
	protected static final float MAX_DEVIANCE_ON_Z = 0.1f;
	protected static final float MAX_ROTATIONAL_DEVIANCE = 0.1f;

	private static final String CURVE_NAME = "curve";
	private static final Vector3f INITIAL_SPEED = new Vector3f(50, 0, 0);

	private Vector3f initialSpawn;

	/**
	 * Constructor for ball physics controller.
	 * 
	 * @param sphereCollisionShape
	 *            Collision shape used by the physics
	 * @param mass
	 *            desired mass of the sphere
	 */
	public PenguinControl(final SphereCollisionShape sphereCollisionShape, final float mass) {
		super(sphereCollisionShape, mass);
	}

	/**
	 * The initialize method for the control. called by super.setSpatial(spatial).
	 */
	public void init() {
		final int yOffSet = 5, xOffSet = -20;
		initialSpawn = new Vector3f(xOffSet, CustomCurveMesh.getLaunchPadHeight() + yOffSet, 0);
		setPhysicsLocation(initialSpawn);
		setLinearVelocity(INITIAL_SPEED);
	}

	/**
	 * Set the physics space and add a tick listener and collision listener to the controller.
	 * 
	 * @param space
	 *            takes a pre-defined jme3 physicsSpace
	 */
	@Override
	public void setPhysicsSpace(final PhysicsSpace space) {
		super.setPhysicsSpace(space);
		space.addTickListener(this);
		space.addCollisionListener(this);

		
	}

	@Override
	public void physicsTick(final PhysicsSpace space, final float tpf) {
	}

	/**
	 * Performed before each physics tick. Sets the z location to 0 to restrict the object from
	 * moving on the z-axis.
	 */
	@Override
	public void prePhysicsTick(final PhysicsSpace space, final float tpf) {
		final Vector3f loc = this.getPhysicsLocation();
		final Vector3f angularvel = this.getAngularVelocity();

		// velocity.z = 0;
		if (Math.abs(loc.z) > MAX_DEVIANCE_ON_Z) {
			loc.z = 0;
			this.setPhysicsLocation(loc);
		}
		if (Math.abs(angularvel.x) > MAX_ROTATIONAL_DEVIANCE
				|| Math.abs(angularvel.y) > MAX_ROTATIONAL_DEVIANCE) {
			angularvel.y = 0;
			angularvel.x = 0;
			this.setAngularVelocity(angularvel);
		}
	}

	/**
	 * Listens for collisions. If the ball collides (touches) with the curve and its speed is too
	 * low, increase it so that the ball can move uphill
	 * 
	 * @param event
	 *            a PhysicsCollisionEvent which stores information about the collision
	 */
	public void collision(final PhysicsCollisionEvent event) {
		if (checkCollision(event, CURVE_NAME, PenguinFactory.PENGUIN_NAME)) {
			final Vector3f velocity = getLinearVelocity();
			if (velocity.x <= 1) {
				velocity.x = 2;
				setLinearVelocity(velocity);
			}
		}
	}

}
