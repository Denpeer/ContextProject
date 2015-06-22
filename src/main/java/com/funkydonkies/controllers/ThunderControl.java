package com.funkydonkies.controllers;

import java.util.Random;

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
	private static final float WARNING_TIME = 2;

	private float time = 0;

	private DifficultyState diffState;

	private CurveState curveState;
	private AppStateManager stateManager;
	private float hitLocation;
	private Random randGen;

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
		randGen = new Random();
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
		if (updateX > 0 && time > WARNING_TIME) {
			moveToX(hitLocation);
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
		final boolean right_move = randGen.nextInt(2) % 2 == 0;
		final Vector3f currLoc = spatial.getLocalTranslation();
		if (right_move) {
			spatial.setLocalTranslation(updateX + randGen.nextFloat() * 2, currLoc.y,
					currLoc.z);
		} else {
			spatial.setLocalTranslation(updateX - randGen.nextFloat() * 2, currLoc.y,
					currLoc.z);
		}
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

	/**
	 * Sets the location for the thunder to hit.
	 * @param hitLoc float x-coordinate
	 */
	public void setHitLocation(float hitLoc) {
		hitLocation = hitLoc;
	}

}