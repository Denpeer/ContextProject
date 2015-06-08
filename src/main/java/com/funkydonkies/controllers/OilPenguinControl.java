package com.funkydonkies.controllers;

import com.funkydonkies.curve.CustomCurveMesh;
import com.funkydonkies.factories.PenguinFactory;
import com.funkydonkies.powerups.OilSpillPowerup;
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
public class OilPenguinControl extends StandardPenguinControl implements
		PhysicsTickListener, PhysicsCollisionListener {
	
	private static final String CURVE_NAME = "curve";
	
	/**
	 * call the super.
	 * @param sphereCollisionShape
	 * @param f
	 */
	public OilPenguinControl(SphereCollisionShape sphereCollisionShape, float f) {
		super(sphereCollisionShape, f);
	}

	/**
	 * Listens for collisions. If the ball collides (touches) with the curve and
	 * its speed is too low, increase it so that the ball can move uphill
	 * 
	 * @param event
	 *            a PhysicsCollisionEvent which stores information about the
	 *            collision
	 */
	public void collision(final PhysicsCollisionEvent event) {
		if (event.getNodeA() != null && event.getNodeB() != null) {
			if (PenguinFactory.STANDARD_PENGUIN_NAME.equals(event.getNodeA().getName())
					&& CURVE_NAME.equals(event.getNodeB().getName())) {
				final Vector3f velocity = getLinearVelocity();
				if (velocity.x <= 1) {
					velocity.x = 2;
					setLinearVelocity(velocity);
				}
				if (getPhysicsLocation().x > OilSpillPowerup
						.getOilStartX()
						&& getPhysicsLocation().x < OilSpillPowerup
								.getOilEndX()) {
					if (velocity.x >= 2) {
						velocity.x = 2;
					}
					setLinearVelocity(velocity);
				}
			}
		}
	}
}
