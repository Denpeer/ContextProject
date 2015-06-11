package com.funkydonkies.controllers;

import com.funkydonkies.factories.KillerWhaleFactory;
import com.funkydonkies.factories.PenguinFactory;
import com.funkydonkies.gamestates.DifficultyState;
import com.funkydonkies.interfaces.MyAbstractGhostControl;
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
	private AppStateManager stateManager;

	private Vector3f initialLoc;

	private float time;

	private boolean moveUp = true;

	private static final float SPEED = 4;;
	private static final float STOP_HEIGHT = -30;
	private static final float DESTROY_HEIGHT = -500;

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
		stateManager = sManager;
		initialLoc = iLoc;
		time = 0;
	}

	/**
	 * An initialize method for the controller.
	 */
	public final void init() {
		spatial.setLocalTranslation(initialLoc);
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
		moveSpatial();
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
	private void moveSpatial() {
		Vector3f loc;

		if (spatial != null && time > 1) {
			final Vector3f vec = spatial.getLocalTranslation();
			if (vec.getY() > STOP_HEIGHT) {
				moveUp = false;
			}
			if (moveUp) {
				loc = new Vector3f(vec.getX(), (float) (vec.getY() + SPEED), vec.getZ());
				spatial.setLocalTranslation(loc);
			} else {
				loc = new Vector3f(vec.getX(), (float) (vec.getY() - SPEED), vec.getZ());
				spatial.setLocalTranslation(loc);
			}
		}

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
		space.add(this);
	}

	/**
	 * Handles a collision between penguin and killer whale. Destroys the penguin on collision.
	 * 
	 * @param event
	 *            PhysicsCollisionEvent containing information about the collision
	 */
	public void collision(final PhysicsCollisionEvent event) {
		if (checkCollision(event, KillerWhaleFactory.WHALE_NAME,
				PenguinFactory.STANDARD_PENGUIN_NAME)) {
			stateManager.getState(DifficultyState.class).resetDiff();
			destroy(event, PenguinFactory.STANDARD_PENGUIN_NAME);
		}
	}

}
