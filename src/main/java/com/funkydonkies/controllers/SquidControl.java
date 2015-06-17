package com.funkydonkies.controllers;

import com.funkydonkies.factories.PenguinFactory;
import com.funkydonkies.factories.SquidFactory;
import com.funkydonkies.gamestates.DifficultyState;
import com.funkydonkies.gamestates.PlayState;
import com.funkydonkies.interfaces.MyAbstractGhostControl;
import com.funkydonkies.powerups.SnowballPowerup;
import com.funkydonkies.sounds.SoundState;
import com.funkydonkies.sounds.TargetCollisionSound;
import com.funkydonkies.sounds.TargetSpawnSound;
import com.jme3.app.state.AppStateManager;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.collision.PhysicsCollisionEvent;
import com.jme3.bullet.collision.PhysicsCollisionListener;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.math.Vector3f;

/**
 * Control class for the target. Takes care of collisions between the ball and
 * target.
 */
public class SquidControl extends MyAbstractGhostControl implements PhysicsCollisionListener {
	
	private static final float TIME_TO_MOVE_IN_ONE_DIRECTION = 4;
	private static final float STEP_SIZE = 0.5f;
	private AppStateManager stateManager;
	private DifficultyState diffState;

	private boolean moveRight = true;
	private float time = 0;

	/**
	 * Constructor method for target control.
	 * 
	 * @param shape
	 *            CollisionShape for the target
	 * @param sManager
	 *            jme AppstateManager to get AppStates
	 */
	public SquidControl(final CollisionShape shape, final AppStateManager sManager) {
		super(shape);
		diffState = sManager.getState(DifficultyState.class);
		stateManager = sManager;
	}
	
	@Override
	public void setPhysicsSpace(final PhysicsSpace space) {
		super.setPhysicsSpace(space);
		space.addCollisionListener(this);
	}

	@Override
	public void init() {
		stateManager.getState(SoundState.class).queueSound(new TargetSpawnSound());
		respawn();
		stateManager.getState(PlayState.class).getPhysicsSpace().add(this);
	}
	
	@Override
	public void update(final float tpf) {
		time += tpf;
		if (time > TIME_TO_MOVE_IN_ONE_DIRECTION) {
			time = 0;
			if (moveRight) {
				moveRight = false;
			} else {
				moveRight = true;
			}
		}
		if (moveRight) {
			final Vector3f vec = spatial.getLocalTranslation();
			final Vector3f loc = new Vector3f((float) (vec.getX() - STEP_SIZE), vec.getY(),
					vec.getZ());
			spatial.setLocalTranslation(loc);
			setPhysicsLocation(loc);
		} else {
			final Vector3f vec = spatial.getLocalTranslation();
			final Vector3f loc = new Vector3f((float) (vec.getX() + STEP_SIZE), vec.getY(),
					vec.getZ());
			spatial.setLocalTranslation(loc);
			setPhysicsLocation(loc);
		}
	}

	/**
	 * Handles a collision between penguin and squid. Calls methods to increase
	 * the combo and activate snow ball powerup.
	 * 
	 * @param event
	 *            PhysicsCollisionEvent containing information about the
	 *            collision
	 */
	public void collision(final PhysicsCollisionEvent event) {
		if (checkCollision(event, SquidFactory.SQUID_NAME, PenguinFactory.PENGUIN_NAME)) {
			stateManager.getState(SoundState.class).queueSound(new TargetCollisionSound());
			diffState.incDiff();
			diffState.activateSnowBallPowerup();
			destroy(event, SquidFactory.SQUID_NAME);
		}
		if (checkCollision(event, SquidFactory.SQUID_NAME, SnowballPowerup.SNOW_PENGUIN_NAME)) {
			stateManager.getState(SoundState.class).queueSound(new TargetCollisionSound());
			diffState.incDiff();
			diffState.activateSnowBallPowerup();
			destroy(event, SquidFactory.SQUID_NAME);
		}
	}

}
