package com.funkydonkies.controllers;

import com.funkydonkies.factories.KrillFactory;
import com.funkydonkies.factories.PenguinFactory;
import com.funkydonkies.gamestates.DifficultyState;
import com.funkydonkies.gamestates.PlayState;
import com.funkydonkies.interfaces.MyAbstractGhostControl;
import com.jme3.app.state.AppStateManager;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.collision.PhysicsCollisionEvent;
import com.jme3.bullet.collision.PhysicsCollisionListener;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.math.Vector3f;

/**
 * Control class for the target. Takes care of collisions between the ball and target.
 */
public class KrillControl extends MyAbstractGhostControl implements PhysicsCollisionListener {

	private static final Vector3f INITIAL_SPAWN_LOCATION = new Vector3f(130f, 90f, 1f);

	private DifficultyState diffState;

	/**
	 * Constructor method for target control.
	 * 
	 * @param colShape
	 *            Collisionshape for the target
	 * @param sManager
	 *            jme AppStateManager for getting states
	 */
	public KrillControl(final CollisionShape colShape, final AppStateManager sManager) {
		super(colShape);
		diffState = sManager.getState(DifficultyState.class);
		sManager.getState(PlayState.class).getPhysicsSpace().add(this);
	}

	@Override
	public void init() {
		spatial.setLocalTranslation(INITIAL_SPAWN_LOCATION);
		setPhysicsLocation(INITIAL_SPAWN_LOCATION);
	}

	@Override
	public void setPhysicsSpace(final PhysicsSpace space) {
		super.setPhysicsSpace(space);
		space.addCollisionListener(this);
	}

	/**
	 * Handles a collision between ball and target. Calls methods to increase the combo and respawn
	 * the target.
	 * 
	 * @param event
	 *            PhysicsCollisionEvent containing information about the collision
	 */
	public void collision(final PhysicsCollisionEvent event) {
		if (checkCollision(event, KrillFactory.KRILL_NAME, PenguinFactory.PENGUIN_NAME)) {
			diffState.incDiff(2);
			diffState.activateInvertControls();
			destroy(event, PenguinFactory.PENGUIN_NAME);
		}

	}

}
