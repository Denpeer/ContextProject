package com.funkydonkies.controllers;

import com.funkydonkies.factories.FishFactory;
import com.funkydonkies.factories.PenguinFactory;
import com.funkydonkies.gamestates.CurveState;
import com.funkydonkies.gamestates.DifficultyState;
import com.jme3.app.state.AppStateManager;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.collision.PhysicsCollisionEvent;
import com.jme3.bullet.collision.PhysicsCollisionListener;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.GhostControl;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
/**
 * Control class for the fish. Takes care of collisions between the fish and the penguins.
 */
public class FishControl extends GhostControl implements PhysicsCollisionListener {
	private static final Vector3f INITIAL_SPAWN_LOCATION = new Vector3f(50f, 30f, 1f);
	private static final float Y_PADDING = CurveState.POINTS_HEIGHT * 0.2f;
	private DifficultyState diffState;
	private boolean initialized = false;

	/**
	 * Constructor method for fish control.
	 * @param shape Collisionshape for the fish
	 * @param sm jme AppstateManager to get AppStates
	 */
	public FishControl(final CollisionShape shape, final AppStateManager sm) {
		super(shape);
		diffState = sm.getState(DifficultyState.class);
	}
	
	/** 
	 * This Method calls initialization which should occur after the control has been added to the
	 * spatial. setSpatial(spatial) is called by addControl(control) in Spatial.
	 * @param spatial spatial this control should control
	 */
	@Override
	public void setSpatial(final Spatial spatial) {
		super.setSpatial(spatial);
		initLocation();
	}
	
	/**
	 * An initialize method for the controller.
	 */
	public void initLocation() {
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
		if (!initialized) {
			space.addCollisionListener(this);
			initialized = true;
		}
		
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
	 * Handles a collision between penguin and fish.
	 * Calls methods to increase the combo and respawn the fish.
	 * @param event PhysicsCollisionEvent containing information about the collision
	 */
	public void collision(final PhysicsCollisionEvent event) {
		
		if (checkCollision(event, FishFactory.FISH_NAME, PenguinFactory.STANDARD_PENGUIN_NAME)) {
			respawn();
			diffState.incDiff();
		}

	}
	
	/** 
	 * Checks collision on an event between two Spatials c1 and c2.
	 * @param e PhysicsCollisionEvent to get the node names from
	 * @param c1 collidee 1
	 * @param c2 collidee 2
	 * @return result of collision check
	 */
	public boolean checkCollision(final PhysicsCollisionEvent e, final String c1, final String c2) {
		if (checkNull(e)) {
			return false;
		}
		
		final String nameA = e.getNodeA().getName();
		final String nameB = e.getNodeB().getName();
		
		return (c1.equals(nameA) && c2.equals(nameB)
				|| c2.equals(nameA) && c1.equals(nameB));
	}

	/** Checks whether the event has/is null.
	 * @param e event to check
	 * @return true when e has/iss null
	 */
	public boolean checkNull(final PhysicsCollisionEvent e) {
		return e == null || e.getNodeA() == null || e.getNodeB() == null;
	}

	/**
	 * Respawn the fish at a reachable location.
	 * TODO make sure its reachable
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
