package com.funkydonkies.controllers;

import com.funkydonkies.factories.PenguinFactory;
import com.funkydonkies.factories.PolarBearFactory;
import com.funkydonkies.gamestates.DifficultyState;
import com.funkydonkies.interfaces.MyAbstractGhostControl;
import com.jme3.app.state.AppStateManager;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.collision.PhysicsCollisionEvent;
import com.jme3.bullet.collision.PhysicsCollisionListener;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;

/**
 * Control class for the polar bear. Takes care of collisions between the polar bear and the
 * penguins.
 */
public class PolarBearControl extends MyAbstractGhostControl implements PhysicsCollisionListener {

	private Vector3f initialLoc;

	private final int destroyTime = 3;

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

	@Override
	public void init() {
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
		moveSpatial();

		if (doneMoving) {
			space.addCollisionListener(this);
			time += tpf;
			if (time > destroyTime) {
				spatial.getParent().detachChild(spatial);
				this.setEnabled(false);
				spatial.removeControl(this);
				doneMoving = false;
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

		} else if (spatial != null && initialLoc.getX() >= stopCoord && vec.getX() >= stopCoord) {
			loc = new Vector3f((float) (vec.getX() - SPEED), vec.getY(), vec.getZ());
			spatial.setLocalTranslation(loc);
		} else {
			doneMoving = true;
		}

	}

	/**
	 * The renderer for the control.
	 * 
	 * @param rm
	 *            the renderManager
	 * @param vp
	 *            the viewPort
	 */
	protected void controlRender(final RenderManager rm, final ViewPort vp) {

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
					PenguinFactory.STANDARD_PENGUIN_NAME)) {
				diffState.resetDiff();
			}
		}

	}

}
