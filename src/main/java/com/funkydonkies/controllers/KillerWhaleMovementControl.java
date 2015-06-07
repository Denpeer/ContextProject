package com.funkydonkies.controllers;

import com.funkydonkies.appstates.ComboState;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.collision.PhysicsCollisionEvent;
import com.jme3.bullet.collision.PhysicsCollisionListener;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.GhostControl;

/**
 * KillerWhale Movement Control. Moves Whale in
 */
public class KillerWhaleMovementControl extends GhostControl implements PhysicsCollisionListener {

	private PhysicsSpace physicsSpace;
	private static final String BALL_NAME = "standardPenguin";
	private static final String TARGET_NAME = "fish";
	
	/**
	 * Constructor method for target control.
	 * @param shape Collisionshape for the target
	 */
	public KillerWhaleMovementControl(final CollisionShape shape) {
		super(shape);
	}
	
	
	/**
	 * Set the physics space and add this controller as tick listener.
	 * @param space takes a pre-defined jme3 physicsSpace
	 */
	@Override
	public void setPhysicsSpace(final PhysicsSpace space) {
		super.setPhysicsSpace(space);
		physicsSpace = space;
		space.addCollisionListener(this);
	}
	
	/**
	 * Removes the control from the physics space.
	 */
	public void delete() {
		space.removeCollisionListener(this);
	}

	public void collision(final PhysicsCollisionEvent event) {
		if (TARGET_NAME.equals(event.getNodeA().getName()) 
				&& BALL_NAME.equals(event.getNodeB().getName())
				|| BALL_NAME.equals(event.getNodeA().getName()) 
						&& TARGET_NAME.equals(event.getNodeB().getName())) {
			ComboState.incCombo();
//			respawn();
		}
	}
	
}