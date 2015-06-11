package com.funkydonkies.controllers;

import com.funkydonkies.factories.FishFactory;
import com.funkydonkies.factories.PenguinFactory;
import com.funkydonkies.gamestates.CurveState;
import com.funkydonkies.gamestates.DifficultyState;
import com.funkydonkies.interfaces.MyAbstractGhostControl;
import com.jme3.app.state.AppStateManager;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.collision.PhysicsCollisionEvent;
import com.jme3.bullet.collision.PhysicsCollisionListener;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.math.Vector3f;
/**
 * Control class for the fish. Takes care of collisions between the fish and the penguins.
 */
public class FishControl extends MyAbstractGhostControl implements PhysicsCollisionListener {
	private static final Vector3f INITIAL_SPAWN_LOCATION = new Vector3f(50f, 30f, 1f);
	private static final float Y_PADDING = CurveState.POINTS_HEIGHT * 0.2f;
	private DifficultyState diffState;
	private boolean initialized = false;

	/**
	 * Constructor method for fish control.
	 * @param shape Collisionshape for the fish
	 * @param sm jme AppStateManager to get AppStates
	 */
	public FishControl(final CollisionShape shape, final AppStateManager sm) {
		super(shape);
		diffState = sm.getState(DifficultyState.class);
	}
	
	@Override
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
		if (!initialized) {
			space.addCollisionListener(this);
			initialized = true;
		}
		
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
