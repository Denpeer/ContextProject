package com.funkydonkies.controllers;

import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.PhysicsTickListener;
import com.jme3.bullet.collision.PhysicsCollisionEvent;
import com.jme3.bullet.collision.PhysicsCollisionListener;
import com.jme3.bullet.collision.shapes.SphereCollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.math.Vector3f;

/**
 * Physics controller class extending from RigidBodyControl specified to 
 * restrict the balls from moving over the z-axis.
 */
public class BallController extends RigidBodyControl implements
		PhysicsTickListener, PhysicsCollisionListener {
	private static final float MAX_DEVIANCE_ON_Z = 0.1f;
	private static final float MAX_ROTATIONAL_DEVIANCE = 0.1f;
	
	/**
	 * Constructor for ball physics controller.
	 * @param sphereCollisionShape Collision shape used by the physics
	 * @param f mass of the sphere
	 */
	public BallController(final SphereCollisionShape sphereCollisionShape, 
			final float f) {
		super(sphereCollisionShape, f);
	}
	
	/**
	 * Set the physics space and add this controller as tick listener.
	 * @param space takes a pre-defined jme3 physicsSpace
	 */
	@Override
	public void setPhysicsSpace(final PhysicsSpace space) {
		super.setPhysicsSpace(space);
		space.addTickListener(this);
		space.addCollisionListener(this);
	}

	/**
	 * Performed at each physics tick.
	 * z-axis.
	 * @param space The physics space 
	 * @param tpf time per frame in seconds (time since last frame) 
	 * 	for normalizing in faster computers 
	 */
	public void physicsTick(final PhysicsSpace space, final float tpf) {
	}
	
	/**
	 * Performed before each physics tick.
	 * Sets the z location to 0 to restrict the object from moving on the 
	 * z-axis.
	 * @param space The physics space 
	 * @param tpf time per frame in seconds (time since last frame) 
	 * 	for normalizing in faster computers 
	 */
	public void prePhysicsTick(final PhysicsSpace space, 
			final float tpf) {
		final Vector3f loc = getPhysicsLocation();
		final Vector3f angularvel = getAngularVelocity();
		//velocity.z = 0;
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
	 * Listens for collisions. If the ball collides (touches) with the curve and its speed is too 
	 * low, increase it so that the ball can move uphill
	 * @param event a PhysicsCollisionEvent which stores information about the collision
	 */
	public void collision(final PhysicsCollisionEvent event) {
		if ("ball".equals(event.getNodeA().getName()) 
				&& "curve".equals(event.getNodeB().getName())) {
			final Vector3f velocity = getLinearVelocity();
			if (velocity.x <= 1) {
				velocity.x = 2;
				setLinearVelocity(velocity);
			}
		}
	}
}