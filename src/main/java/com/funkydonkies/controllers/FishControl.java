package com.funkydonkies.controllers;

import com.funkydonkies.factories.FishFactory;
import com.funkydonkies.factories.PenguinFactory;
import com.funkydonkies.gamestates.CurveState;
import com.funkydonkies.gamestates.DifficultyState;
import com.funkydonkies.gamestates.PlayState;
import com.funkydonkies.interfaces.MyAbstractGhostControl;
import com.funkydonkies.powerups.SnowballPowerup;
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
	private DifficultyState diffState;

	/**
	 * Constructor method for fish control.
	 * @param shape Collisionshape for the fish
	 * @param sm jme AppStateManager to get AppStates
	 */
	public FishControl(final CollisionShape shape, final AppStateManager sManager) {
		super(shape);
		diffState = sManager.getState(DifficultyState.class);
		sManager.getState(PlayState.class).getPhysicsSpace().add(this);
	}
	
	@Override
	public void init() {
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
	 * Handles a collision between penguin and fish.
	 * Calls methods to increase the combo and respawn the fish.
	 * @param event PhysicsCollisionEvent containing information about the collision
	 */
	public void collision(final PhysicsCollisionEvent event) {
		if (checkCollision(event, FishFactory.FISH_NAME, PenguinFactory.PENGUIN_NAME)) {
			respawn();
			diffState.incDiff();
		}
		if (checkCollision(event, FishFactory.FISH_NAME, SnowballPowerup.SNOW_PENGUIN_NAME)) {
			respawn();
			diffState.incDiff();
		}

	}
}
