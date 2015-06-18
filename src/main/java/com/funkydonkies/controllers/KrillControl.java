package com.funkydonkies.controllers;

import com.funkydonkies.factories.KrillFactory;
import com.funkydonkies.factories.PenguinFactory;
import com.funkydonkies.gamestates.DifficultyState;
import com.funkydonkies.gamestates.PlayState;
import com.funkydonkies.interfaces.MyAbstractGhostControl;
import com.funkydonkies.powerups.SnowballPowerup;
import com.funkydonkies.sounds.KrillCollisionSound;
import com.funkydonkies.sounds.PowerupVoiceSound;
import com.funkydonkies.sounds.SoundState;
import com.jme3.app.state.AppStateManager;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.collision.PhysicsCollisionEvent;
import com.jme3.bullet.collision.PhysicsCollisionListener;
import com.jme3.bullet.collision.shapes.CollisionShape;

/**
 * Control class for the target. Takes care of collisions between the ball and
 * target.
 */
public class KrillControl extends MyAbstractGhostControl implements PhysicsCollisionListener {

	private DifficultyState diffState;
	private AppStateManager stateManager;

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
		stateManager = sManager;
	}

	@Override
	public void init() {
		respawn();
		stateManager.getState(PlayState.class).getPhysicsSpace().add(this);
	}

	@Override
	public void setPhysicsSpace(final PhysicsSpace space) {
		super.setPhysicsSpace(space);
		space.addCollisionListener(this);
	}

	/**
	 * Handles a collision between ball and target. Calls methods to increase
	 * the combo and respawn the target.
	 * 
	 * @param event
	 *            PhysicsCollisionEvent containing information about the
	 *            collision
	 */
	public void collision(final PhysicsCollisionEvent event) {
		if (checkCollision(event, KrillFactory.KRILL_NAME, PenguinFactory.PENGUIN_NAME)) {
			stateManager.getState(SoundState.class).queueSound(new KrillCollisionSound());
			stateManager.getState(SoundState.class).queueSound(new PowerupVoiceSound());
			diffState.incDiff(2);
			diffState.activateInvertControls();
			destroy(event, PenguinFactory.PENGUIN_NAME);
		}
		if (checkCollision(event, KrillFactory.KRILL_NAME, SnowballPowerup.SNOW_PENGUIN_NAME)) {
			stateManager.getState(SoundState.class).queueSound(new KrillCollisionSound());
			stateManager.getState(SoundState.class).queueSound(new PowerupVoiceSound());
			diffState.incDiff(2);
			diffState.activateInvertControls();
			destroy(event, PenguinFactory.PENGUIN_NAME);
		}
	}
}
