package com.funkydonkies.controllers;

import com.funkydonkies.appstates.ComboState;
import com.funkydonkies.appstates.CurveState;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.collision.PhysicsCollisionEvent;
import com.jme3.bullet.collision.PhysicsCollisionListener;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.GhostControl;
import com.jme3.math.Vector3f;
/**
 * Control class for the target. Takes care of collisions between the ball and target.
 */
public class FishControl extends GhostControl implements PhysicsCollisionListener {
	private static final String BALL_NAME = "standardPenguin";
	private static final String TARGET_NAME = "fish";
	public static final Vector3f INITIAL_SPAWN_LOCATION = new Vector3f(50f, 30f, 1f);
	private static final float Y_PADDING = CurveState.POINTS_HEIGHT * 0.2f;

	/**
	 * Constructor method for target control.
	 * @param shape Collisionshape for the target
	 */
	public FishControl(final CollisionShape shape) {
		super(shape);
	}
	
	/**
	 * An initialize method for the controller.
	 */
	public void init() {
		setPhysicsLocation(INITIAL_SPAWN_LOCATION);
		spatial.setLocalTranslation(INITIAL_SPAWN_LOCATION);
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
		if (//TARGET_NAME.equals(event.getNodeA().getName()) && 
				BALL_NAME.equals(event.getNodeB().getName())
				|| BALL_NAME.equals(event.getNodeA().getName())) {
						//&& TARGET_NAME.equals(event.getNodeB().getName())) {
			ComboState.incCombo();
			respawn();
		}
	}
	
	/**
	 * Respawn the target at a reachable location.
	 * TODO make the spawn location random and make sure its reachable
	 */
	public void respawn() {
		final float x = (float) Math.random() * (CurveState.POINT_DISTANCE 
				* CurveState.DEFAULT_CONTROL_POINTS_COUNT);
		
		final float y = (float) Math.random() * CurveState.POINTS_HEIGHT + Y_PADDING;
		final Vector3f respawnlocation = new Vector3f(x, y, 1.5f);
		setPhysicsLocation(respawnlocation);
		spatial.setLocalTranslation(respawnlocation);
	}

}
