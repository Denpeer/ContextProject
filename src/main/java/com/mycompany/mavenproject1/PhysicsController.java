package com.mycompany.mavenproject1;

import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.PhysicsTickListener;
import com.jme3.bullet.collision.shapes.SphereCollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.math.Vector3f;

/**
 * Physics controller class extending from RigidBodyControl specified to 
 * restrict the balls from moving over the z-axis.
 */
public class PhysicsController extends RigidBodyControl implements
		PhysicsTickListener {
	
	/**
	 * Constructor for ball physics controller.
	 * @param sphereCollisionShape Collision shape used by the physics
	 * @param f Diameter of the sphere
	 */
	public PhysicsController(final SphereCollisionShape sphereCollisionShape, 
			final float f) {
		super(sphereCollisionShape, f);
	}
	
	/**
	 * Set the physics space and add this controller as tick listener.
	 */
	@Override
	public final void setPhysicsSpace(final PhysicsSpace space) {
		super.setPhysicsSpace(space);
		space.addTickListener(this);
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
	public final void prePhysicsTick(final PhysicsSpace space, 
			final float tpf) {
		//Vector3f velocity = getLinearVelocity();
		final Vector3f loc = getPhysicsLocation();
		//Vector3f angularvel = getAngularVelocity();
		//velocity.z = 0;
		loc.z = 0;
		//angularvel.y = 0;
		setPhysicsLocation(loc);
		//setLinearVelocity(velocity);
		//setAngularVelocity(angularvel);
	}

}
