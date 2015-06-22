package com.funkydonkies.controllers;

import com.funkydonkies.factories.KillerWhaleFactory;
import com.funkydonkies.factories.PenguinFactory;
import com.funkydonkies.gamestates.DifficultyState;
import com.funkydonkies.gamestates.PlayState;
import com.funkydonkies.interfaces.MyAbstractGhostControl;
import com.funkydonkies.sounds.ObstacleCollisionSound;
import com.funkydonkies.sounds.SoundState;
import com.funkydonkies.sounds.WhaleSpawnSound;
import com.jme3.app.state.AppStateManager;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.collision.PhysicsCollisionEvent;
import com.jme3.bullet.collision.PhysicsCollisionListener;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.math.Vector3f;

/**
 * This is a control to move floating spatials along the x- and y axis with a constant speed.
 */
public class KillerWhaleControl extends MyAbstractGhostControl implements PhysicsCollisionListener {

	private Vector3f initialLoc;

	private float time = 0;;

	private boolean moveUp = true;

	private DifficultyState diffState;

	private static final float SPEED = 80;
	private static final float WHALE_SPAWN_TIME = 1;
	private static final float STOP_HEIGHT = -30;
	private static final float DESTROY_HEIGHT = -500;
	private AppStateManager stateManager;

	/**
	 * The constructor of the control.
	 * 
	 * @param colShape
	 *            desired colShape
	 * @param sManager
	 *            jme AppStateManager to get states
	 * @param iLoc
	 *            initial location of the whale
	 * 
	 */
	public KillerWhaleControl(final CollisionShape colShape, final AppStateManager sManager,
			final Vector3f iLoc) {
		super(colShape);
		initialLoc = iLoc;
		stateManager = sManager;
		diffState = sManager.getState(DifficultyState.class);
	}

	/**
	 * Set the physics space and add this controller as tick listener.
	 * 
	 * @param space
	 *            takes a pre-defined jme3 physicsSpace
	 */
	@Override
	public void setPhysicsSpace(final PhysicsSpace space) {
		super.setPhysicsSpace(space);
		space.addCollisionListener(this);
	}

	/**
	 * An initialize method for the controller.
	 */
	public final void init() {
		stateManager.getState(SoundState.class).queueSound(new WhaleSpawnSound());
		spatial.setLocalTranslation(initialLoc);
		setPhysicsLocation(initialLoc);
		stateManager.getState(PlayState.class).getPhysicsSpace().add(this);
	}

	/**
	 * The update method for the contoller.
	 * 
	 * @param tpf
	 *            is the time per frame
	 */
	@Override
	public void update(final float tpf) {
		time += tpf;
		moveSpatial(tpf);
		removeCheck();
	}

	/**
	 * Checks whether the whale should be removed.
	 */
	public void removeCheck() {
		if (spatial.getLocalTranslation().getY() < DESTROY_HEIGHT) {
			spatial.removeFromParent();
			setEnabled(false);
		}
	}

	/**
	 * This method moves the spatial in the desired direction.
	 */
	private void moveSpatial(float tpf) {
		Vector3f loc;

		if (spatial != null && time > WHALE_SPAWN_TIME) {
			final Vector3f vec = spatial.getLocalTranslation();
			if (vec.getY() > STOP_HEIGHT) {
				moveUp = false;
			}
			if (moveUp) {
				loc = new Vector3f(vec.getX(), (float) (vec.getY() + SPEED * tpf), vec.getZ());
				spatial.setLocalTranslation(loc);
				setPhysicsLocation(loc);
			} else {
				loc = new Vector3f(vec.getX(), (float) (vec.getY() - SPEED * tpf), vec.getZ());
				spatial.setLocalTranslation(loc);
				setPhysicsLocation(loc);
			}
		}

	}

	/**
	 * Handles a collision between penguin and killer whale. Destroys the penguin on collision.
	 * 
	 * @param event
	 *            PhysicsCollisionEvent containing information about the collision
	 */
	public void collision(final PhysicsCollisionEvent event) {
		if (checkCollision(event, KillerWhaleFactory.WHALE_NAME, PenguinFactory.PENGUIN_NAME)) {
			stateManager.getState(SoundState.class).queueSound(new ObstacleCollisionSound());
			diffState.resetDiff();
			destroy(event, PenguinFactory.PENGUIN_NAME);
		}
	}

}
