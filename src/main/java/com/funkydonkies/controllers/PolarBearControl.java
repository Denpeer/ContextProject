package com.funkydonkies.controllers;

import com.funkydonkies.factories.PenguinFactory;
import com.funkydonkies.factories.PolarBearFactory;
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
 * Control class for the polar bear. Takes care of collisions between the polar bear and the
 * penguins.
 */
public class PolarBearControl extends MyAbstractGhostControl implements PhysicsCollisionListener {

	private Vector3f initialLoc;

	private static final int DESTROY_TIME = 3;

	private boolean doneMoving = false;

	private static final float SPEED = 1;

	private float time = 0;
	private float stopCoord;

	private DifficultyState diffState;

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
	}
	
	/**
	 * Set the physics space and add this controller as tick listener.
	 */
	@Override
	public void setPhysicsSpace(final PhysicsSpace space) {
		super.setPhysicsSpace(space);
		space.addCollisionListener(this);
		space.add(this);
	}

	@Override
	public void init() {
		spatial.setLocalTranslation(initialLoc);
		setPhysicsLocation(initialLoc);
	}

	/**
	 * The update method for the contoller.
	 */
	@Override
	public void update(final float tpf) {
		moveSpatial();

		if (doneMoving) {
			time += tpf;
			if (time > DESTROY_TIME) {
				spatial.removeFromParent();
				setEnabled(false);
				doneMoving = false;
			}
		}
	}

	/**
	 * This method moves the spatial in the desired direction.
	 */
	private void moveSpatial() {
		Vector3f loc;
		final Vector3f vec = spatial.getLocalTranslation();
		if (spatial != null && initialLoc.getX() < stopCoord && vec.getX() < stopCoord) {
			loc = new Vector3f((float) (vec.getX() + SPEED), vec.getY(), vec.getZ());
			spatial.setLocalTranslation(loc);
			setPhysicsLocation(loc);
		} else if (spatial != null && initialLoc.getX() >= stopCoord && vec.getX() >= stopCoord) {
			loc = new Vector3f((float) (vec.getX() - SPEED), vec.getY(), vec.getZ());
			spatial.setLocalTranslation(loc);
			setPhysicsLocation(loc);
		} else {
			doneMoving = true;
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
		if (doneMoving) {
			if (checkCollision(event, PolarBearFactory.POLAR_BEAR_NAME,
					PenguinFactory.PENGUIN_NAME)) {
				diffState.resetDiff();
				destroy(event, PenguinFactory.PENGUIN_NAME);

			}
		}

	}

}
