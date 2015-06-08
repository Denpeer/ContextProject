package com.funkydonkies.controllers;

import com.funkydonkies.curve.CustomCurveMesh;
import com.funkydonkies.factories.PenguinFactory;
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
public class StandardPenguinControl extends RigidBodyControl implements
		PhysicsTickListener, PhysicsCollisionListener {
	protected static final float MAX_DEVIANCE_ON_Z = 0.1f;
	protected static final float MAX_ROTATIONAL_DEVIANCE = 0.1f;
	private static final String OBSTACLE_NAME = "killerWhale";
	private static final String PENGUIN_NAME = PenguinFactory.STANDARD_PENGUIN_NAME;
	private static final String CURVE_NAME = "curve";
	private Vector3f initialSpawn;
	private Vector3f initialSpeed  = new Vector3f(50, 0, 0);
	
	/**
	 * Constructor for ball physics controller.
	 * @param sphereCollisionShape Collision shape used by the physics
	 * @param f mass of the sphere
	 */
	public StandardPenguinControl(final SphereCollisionShape sphereCollisionShape, 
			final float f) {
		super(sphereCollisionShape, f);
	}
	/**
	 * The initialize method for the control.
	 */
	public void init() {
		final int yOffSet = 5, xOffSet = -20;
		initialSpawn = new Vector3f(xOffSet, CustomCurveMesh.getLaunchPadHeight() + yOffSet, 0);
		setLocation(initialSpawn);
		setSpeed(initialSpeed);
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
		final Vector3f loc = this.getPhysicsLocation();
		final Vector3f angularvel = this.getAngularVelocity();
		
		//velocity.z = 0;
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
	 * Sets the speed for the Ball by calling setLinVelocity on the physics.
	 * @param vel Vector3f, speed to set on the ball
	 */
	public void setSpeed(final Vector3f vel) {
		setLinearVelocity(vel);
	}
	
	/**
	 * Sets the ball´s location by calling setPhysicsLocation on its physics.
	 * @param loc Vector3f the new location
	 */
	public void setLocation(final Vector3f loc) {
		setPhysicsLocation(loc);
	}
//	/**
//	 * This method listens to the penguin collisions.
//	 * @param event a PhysicsCollisionEvent which stores information about the collision
//	 */
//	public void collision(final PhysicsCollisionEvent event) {
//		curveCollision(event);
////		whaleCollision(event);
//	}

	/**
	 * Listens for collisions. If the ball collides (touches) with the curve and its speed is too 
	 * low, increase it so that the ball can move uphill
	 * @param event a PhysicsCollisionEvent which stores information about the collision
	 */
	public void collision(final PhysicsCollisionEvent event) {
		if(event.getNodeA() != null && event.getNodeB() != null){
			if (PENGUIN_NAME.equals(event.getNodeA().getName()) 
					&& CURVE_NAME.equals(event.getNodeB().getName())) {
				final Vector3f velocity = getLinearVelocity();
				if (velocity.x <= 1) {
					velocity.x = 2;
					setLinearVelocity(velocity);
				}
			}
		}
	}
}
