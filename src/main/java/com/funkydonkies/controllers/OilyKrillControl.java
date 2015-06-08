package com.funkydonkies.controllers;

import com.funkydonkies.gamestates.CurveState;
import com.funkydonkies.gamestates.DifficultyState;
import com.funkydonkies.gamestates.PlayState;
import com.funkydonkies.powerups.OilSpillPowerup;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.collision.PhysicsCollisionEvent;
import com.jme3.bullet.collision.PhysicsCollisionListener;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.GhostControl;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.math.Vector3f;
/**
 * Control class for the target. Takes care of collisions between the ball and target.
 */
public class OilyKrillControl extends GhostControl implements PhysicsCollisionListener {
	private static final String BALL_NAME = "standardPenguin";
	private static final String TARGET_NAME = "oilyKrill";
	private static final Vector3f INITIAL_SPAWN_LOCATION = new Vector3f(130f, 90f, 1f);
	private static final float Y_PADDING = CurveState.POINTS_HEIGHT * 0.2f;
	private DifficultyState diffState;
	private AppStateManager sm;
	OilSpillPowerup osp;

	/**
	 * Constructor method for target control.
	 * @param shape Collisionshape for the target
	 */
	public OilyKrillControl(final CollisionShape shape, AppStateManager sm) {
		super(shape);
		this.sm = sm;
		diffState = sm.getState(DifficultyState.class);
		osp = new OilSpillPowerup();
		sm.attach(osp);
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
		space.remove(this);
		spatial.getParent().detachChild(spatial);
	}

	/**
	 * Handles a collision between ball and target.
	 * Calls methods to increase the combo and respawn the target.
	 * @param event PhysicsCollisionEvent containing information about the collision
	 */
	public void collision(final PhysicsCollisionEvent event) {
		if(event.getNodeA() != null && event.getNodeB() != null){
			if (TARGET_NAME.equals(event.getNodeA().getName()) 
					&& BALL_NAME.equals(event.getNodeB().getName())
					|| BALL_NAME.equals(event.getNodeA().getName()) 
							&& TARGET_NAME.equals(event.getNodeB().getName())) {
				osp.setEnabled(true);
				if (TARGET_NAME.equals(event.getNodeA())) {
					sm.getState(PlayState.class).getRootNode().detachChild(event.getNodeA());
					((RigidBodyControl) event.getNodeA().getControl(0)).setEnabled(false);
				} else if (TARGET_NAME.equals(event.getNodeB())) {
					sm.getState(PlayState.class).getRootNode().detachChild(event.getNodeB());
					((RigidBodyControl) event.getNodeB().getControl(0)).setEnabled(false);
				}
			}
		}
	}
}
