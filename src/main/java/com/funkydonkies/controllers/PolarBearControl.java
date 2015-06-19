package com.funkydonkies.controllers;

import java.util.Random;

import com.funkydonkies.factories.PenguinFactory;
import com.funkydonkies.factories.PolarBearFactory;
import com.funkydonkies.gamestates.DifficultyState;
import com.funkydonkies.gamestates.PlayState;
import com.funkydonkies.interfaces.MyAbstractGhostControl;
import com.funkydonkies.sounds.BearHitSound;
import com.funkydonkies.sounds.ObstacleCollisionSound;
import com.funkydonkies.sounds.SoundState;
import com.jme3.app.state.AppStateManager;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.collision.PhysicsCollisionEvent;
import com.jme3.bullet.collision.PhysicsCollisionListener;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.math.Vector3f;

/**
 * Control class for the polar bear. Takes care of collisions between the polar bear and the
 * penguins.
 */
public class PolarBearControl extends MyAbstractGhostControl implements PhysicsCollisionListener {

	private static final int MAX_HEIGHT = 20;
	private static final int DESTROY_TIME = 5;
	private static final float SPEED = 20f;
	private static final float UP_SPEED = 10f;
	private static final float REMOVE_TIME = 4.2f;

	private Vector3f initialLoc;

	private boolean doneMoving = false;

	private float killTime = 0;
	private float time = 0;
	private float stopCoord;
	private AppStateManager stateManager;
	private DifficultyState diffState;
	
	private int height;

	/**
	 * The controller for the polar bear. Takes care of the collision between the polar bear and the
	 * penguin.
	 * 
	 * @param shape
	 *            the collision shape of the polar bear
	 * @param stopX
	 *            the coordinate the polar bear should stop moving
	 * @param sManager
	 *            the AppStateManager
	 * @param iLoc
	 *            the initial location of the polar bear
	 */
	public PolarBearControl(final CollisionShape shape, final AppStateManager sManager,
			final float stopX, final Vector3f iLoc) {
		super(shape);
		initialLoc = iLoc;
		stopCoord = stopX;
		diffState = sManager.getState(DifficultyState.class);
		stateManager = sManager;
	}

	/**
	 * Set the physics space and add this controller as tick listener.
	 * @param space PhysicsSpace of the scene.
	 */
	@Override
	public void setPhysicsSpace(final PhysicsSpace space) {
		super.setPhysicsSpace(space);
		space.addCollisionListener(this);
	}

	/**
	 * Initialized the control, eg. sets location and plays sound.
	 * @see com.funkydonkies.interfaces.MyAbstractGhostControl#init()
	 */
	@Override
	public void init() {
		spatial.setLocalTranslation(initialLoc);
		setPhysicsLocation(initialLoc);
		stateManager.getState(PlayState.class).getPhysicsSpace().add(this);
		height = new Random().nextInt(MAX_HEIGHT);
	}

	/**
	 * The update method for the contoller.
	 * @param tpf float the time since last frame.
	 */
	@Override
	public void update(final float tpf) {
		moveSpatial(doneMoving, tpf);

		if (doneMoving) {
			time += tpf;
			if (time > DESTROY_TIME) {
				doneMoving = false;
				killBear(tpf);
			}
		}
	}
	
	/**
	 * Removes the bear after moving it down.
	 * @param tpf time per frame
	 */
	public void killBear(final float tpf) {
		killTime += tpf;
		Vector3f loc = getPhysicsLocation();
		loc.y -= UP_SPEED * tpf;
		spatial.setLocalTranslation(loc);
		setPhysicsLocation(loc);
		if (killTime > REMOVE_TIME) {
			spatial.removeFromParent();
			setEnabled(false);
		}
	}

	/**
	 * This method moves the spatial in the desired direction.
	 */
	private void moveSpatial(boolean moveUp, float tpf) {
		final Vector3f vec = spatial.getLocalTranslation();
		if (moveUp) {
			if (vec.y < height) {
				vec.y += UP_SPEED * tpf;
				spatial.setLocalTranslation(vec);
				setPhysicsLocation(vec);
			}
		} else {
			Vector3f loc;
			if (initialLoc.getX() < stopCoord && vec.getX() < stopCoord) {
				loc = new Vector3f((float) (vec.getX() + SPEED * tpf), vec.getY(), vec.getZ());
				spatial.setLocalTranslation(loc);
				setPhysicsLocation(loc);
			} else if (initialLoc.getX() >= stopCoord && vec.getX() >= stopCoord) {
				loc = new Vector3f((float) (vec.getX() - SPEED * tpf), vec.getY(), vec.getZ());
				spatial.setLocalTranslation(loc);
				setPhysicsLocation(loc);
			} else {
				doneMoving = true;
			}			
		}
	}

	/**
	 * Handles a collision between ball and target. Calls methods to increase the combo and respawn
	 * the target.
	 * 
	 * @param event
	 *            PhysicsCollisionEvent containing information about the collision
	 */
	public void collision(final PhysicsCollisionEvent event) {
		if (checkCollision(event, PolarBearFactory.POLAR_BEAR_NAME,
						PenguinFactory.PENGUIN_NAME)) {
				stateManager.getState(SoundState.class).queueSound(new BearHitSound());
			stateManager.getState(SoundState.class).queueSound(new ObstacleCollisionSound());
			diffState.resetDiff();
			destroy(event, PenguinFactory.PENGUIN_NAME);
		}
	}
}
