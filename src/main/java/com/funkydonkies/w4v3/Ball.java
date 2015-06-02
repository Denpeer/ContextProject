package com.funkydonkies.w4v3;

import com.funkydonkies.controllers.PhysicsController;
import com.jme3.bullet.collision.shapes.SphereCollisionShape;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Sphere;

/**
 * @author Jonathan
 *
 */
public class Ball extends Geometry {
	private static final int SAMPLES = 20;
	private static final Sphere sphere = new Sphere(SAMPLES, SAMPLES, 4f);
	private PhysicsController phy;
	private static final float DEFAULT_RADIUS = 4f;
	public static final Vector3f DEFAULT_SPAWN_LOCATION = new Vector3f(25f, 130f, 0f);
	public static final Vector3f DEFAULT_INITIAL_SPEED = new Vector3f(10, -22, 0);
	
	/**
	 * Constructor for Ball, initializes and adds a physics control.
	 * @param name String name of the spatial
	 */
	public Ball(final String name) {
		super(name, sphere);
		phy = initControl();
		phy.setRestitution(1);
		addControl(phy);
	}
	
	public PhysicsController initControl() {
		return new PhysicsController(new SphereCollisionShape(DEFAULT_RADIUS), 1f);
	}
	
	/**
	 * Returns the ball's coontroller.
	 * @return phy PhysicsController
	 */
	public PhysicsController getControl() {
		return phy;
	}
	
	/**
	 * Sets the speed for the Ball by calling setLinVelocity on the physics.
	 * @param vel Vector3f, speed to set on the ball
	 */
	public void setSpeed(final Vector3f vel) {
		phy.setLinearVelocity(vel);
	}
	
	/**
	 * Sets the ball´s location by calling setPhysicsLocation on its physics.
	 * @param loc Vector3f the new location
	 */
	public void setLocation(final Vector3f loc) {
		phy.setPhysicsLocation(loc);
	}
}
