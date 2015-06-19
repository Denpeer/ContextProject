package com.funkydonkies.controllers;

import com.funkydonkies.factories.PenguinFactory;
import com.funkydonkies.factories.ThunderFactory;
import com.funkydonkies.gamestates.CurveState;
import com.funkydonkies.gamestates.DifficultyState;
import com.funkydonkies.gamestates.PlayState;
import com.funkydonkies.interfaces.MyAbstractGhostControl;
import com.funkydonkies.sounds.ObstacleCollisionSound;
import com.funkydonkies.sounds.SoundState;
import com.funkydonkies.sounds.ThunderHitSound;
import com.jme3.app.state.AppStateManager;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.collision.PhysicsCollisionEvent;
import com.jme3.bullet.collision.PhysicsCollisionListener;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.math.Vector3f;

/**
 * Control class for the thunder. Takes care of collisions between the thunder and the penguins.
 * 
 */
public class ThunderControl extends MyAbstractGhostControl implements PhysicsCollisionListener {
	
	private static final Vector3f INITIAL_SPAWN_LOCATION = new Vector3f(-200, 0, 0);
	private static final float TIME_TO_LIVE = 3;

	private float time = 0;

	private DifficultyState diffState;

	private CurveState curveState;
	private AppStateManager stateManager;
	/**
	 * Constructor method for ThunderControl.
	 * 
	 * @param shape
	 *            Collisionshape for thunder
	 * @param sManager
	 *            stateManager
	 * @param xCoord
	 *            spawn location x
	 */
	public ThunderControl(final CollisionShape shape, final AppStateManager sManager,
			final float xCoord) {
		super(shape);
		new Vector3f(xCoord, 0, 0);
		diffState = sManager.getState(DifficultyState.class);
		curveState = sManager.getState(CurveState.class);
		stateManager = sManager;
	}

	@Override
	public void init() {
		spatial.setLocalTranslation(INITIAL_SPAWN_LOCATION);
		stateManager.getState(PlayState.class).getPhysicsSpace().add(this);

	}

	@Override
	public void setPhysicsSpace(final PhysicsSpace space) {
		super.setPhysicsSpace(space);
		space.addCollisionListener(this);
	}

	@Override
	public void update(final float tpf) {
		super.update(tpf);

		time += tpf;

		final float updateX = curveState.getHighestPointX();
		if (updateX > 0 && time > 2) {
			moveToX(updateX);
		}

		if (time > TIME_TO_LIVE) {
			spatial.removeFromParent();
			setEnabled(false);
		}

	}

	/**
	 * Moves spatial to desired c location.
	 * 
	 * @param updateX
	 *            desired x location
	 */
	public void moveToX(final float updateX) {
		spatial.setLocalTranslation(updateX, spatial.getLocalTranslation().y,
				spatial.getLocalTranslation().z);
	}
	
	/**
	 * Handles a collision between penguin and thunder. Kill penguin and reset difficulty.
	 * 
	 * @param event
	 *            PhysicsCollisionEvent containing information about the collision
	 */
	public void collision(final PhysicsCollisionEvent event) {

		if (checkCollision(event, ThunderFactory.THUNDER_NAME, PenguinFactory.PENGUIN_NAME)) {
			stateManager.getState(SoundState.class).queueSound(new ThunderHitSound());
			stateManager.getState(SoundState.class).queueSound(new ObstacleCollisionSound());
			diffState.resetDiff();
			destroy(event, PenguinFactory.PENGUIN_NAME);
		}
	}

}