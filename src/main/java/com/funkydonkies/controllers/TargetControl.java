package com.funkydonkies.controllers;

import com.funkydonkies.obstacles.Target;
import com.funkydonkies.w4v3.Combo;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.collision.PhysicsCollisionEvent;
import com.jme3.bullet.collision.PhysicsCollisionListener;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.GhostControl;
/**
 * Control class for the target. Takes care of collisions between the ball and target.
 */
public class TargetControl extends GhostControl implements PhysicsCollisionListener {
	private static final String BALL_NAME = "ball";
	private static final String TARGET_NAME = "target";
	private Target target;
	private Combo combo;

	/**
	 * Constructor method for target control.
	 * @param shape Collisionshape for the target
	 * @param t Target the target to control
	 */
	public TargetControl(final CollisionShape shape, final Target t) {
		super(shape);
		target = t;
	}
	
	/**
	 * Set the physics space and add this controller as tick listener.
	 * @param space takes a pre-defined jme3 physicsSpace
	 */
	@Override
	public void setPhysicsSpace(final PhysicsSpace space) {
		super.setPhysicsSpace(space);
		space.addCollisionListener(this);
	}
	
	/**
	 * Removes the control from the physics space.
	 */
	public void delete() {
		space.removeCollisionListener(this);
	}

	/**
	 * Handles a collision between ball and target.
	 * Calls methods to increase the combo and respawn the target.
	 * @param event PhysicsCollisionEvent containing information about the collision
	 */
	public void collision(final PhysicsCollisionEvent event) {
		if (TARGET_NAME.equals(event.getNodeA().getName()) 
				&& BALL_NAME.equals(event.getNodeB().getName())
				|| BALL_NAME.equals(event.getNodeA().getName()) 
						&& TARGET_NAME.equals(event.getNodeB().getName())) {
			if (combo != null) {
				combo.incCombo();
			}
			target.respawn();
		}
	}
	
	/**
	 * Sets the combo for the controller so that it can update the combo counter.
	 * @param c Combo 
	 */
	public void setCombo(final Combo c) {
		combo = c;
	}

}
