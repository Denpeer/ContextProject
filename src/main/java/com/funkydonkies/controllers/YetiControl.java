package com.funkydonkies.controllers;

import java.util.Random;

import com.funkydonkies.factories.PenguinFactory;
import com.funkydonkies.factories.YetiFactory;
import com.funkydonkies.gamestates.DifficultyState;
import com.funkydonkies.gamestates.PlayState;
import com.funkydonkies.interfaces.MyAbstractGhostControl;
import com.funkydonkies.sounds.ObstacleCollisionSound;
import com.funkydonkies.sounds.ObstacleSpawnSound;
import com.funkydonkies.sounds.SnowballWaveCollisionSound;
import com.funkydonkies.sounds.SoundState;
import com.jme3.app.state.AppStateManager;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.collision.PhysicsCollisionEvent;
import com.jme3.bullet.collision.PhysicsCollisionListener;
import com.jme3.bullet.collision.shapes.SphereCollisionShape;
import com.jme3.math.Vector3f;

/**
 * Control class fot the yeti. Takes care of collisions between the snow ball and the penguins.
 * 
 */
public class YetiControl extends MyAbstractGhostControl implements PhysicsCollisionListener {
	private static final int RAND_X_MAX = 300;
	private static final int RAND_Y_MAX = 100;
	private static final int DEFAULT_DEPTH = -400;
	private static final int SPHERE_SIZE = 20;
	private static final int SNOW_BALL_Z_SPEED = 70;
	private Vector3f loc;
	private DifficultyState diffState;
	private AppStateManager stateManager;
	/**
	 * Constructor method for yeti control.
	 * 
	 * @param sManager
	 *            jme AppStateManager
	 */
	public YetiControl(final AppStateManager sManager) {
		super(new SphereCollisionShape(SPHERE_SIZE));
		final Random rand = new Random();

		loc = new Vector3f(rand.nextInt(RAND_X_MAX), rand.nextInt(RAND_Y_MAX), DEFAULT_DEPTH);
		diffState = sManager.getState(DifficultyState.class);
		stateManager = sManager;
	}

	@Override
	public void init() {
		stateManager.getState(SoundState.class).queueSound(new ObstacleSpawnSound());
		spatial.setLocalTranslation(loc);
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
		move(tpf);
	}

	/**
	 * Handles a collision between penguin and yeti. Calls methods to reset the combo and kill
	 * penguin.
	 * 
	 * @param event
	 *            PhysicsCollisionEvent containing information about the collision
	 */
	public void collision(final PhysicsCollisionEvent event) {
		if (checkCollision(event, YetiFactory.YETI_NAME, PenguinFactory.PENGUIN_NAME)) {
			stateManager.getState(SoundState.class).queueSound(new SnowballWaveCollisionSound());
			diffState.resetDiff();
			destroy(event, PenguinFactory.PENGUIN_NAME);
		}
	}

	/**
	 * Move the snowball towards the screen (along z axis).
	 * 
	 * @param tpf
	 *            update time interval
	 */
	private void move(final float tpf) {
		stateManager.getState(SoundState.class).queueSound(new ObstacleCollisionSound());
		spatial.setLocalTranslation(spatial.getLocalTranslation().x,
				spatial.getLocalTranslation().y, spatial.getLocalTranslation().z
						+ SNOW_BALL_Z_SPEED * tpf);
	}
}