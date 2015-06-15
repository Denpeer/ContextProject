package com.funkydonkies.controllers;

import com.funkydonkies.factories.PenguinFactory;
import com.funkydonkies.factories.SpearFactory;
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
 * Control class for the spear. Takes care of collisions between the fish and the spear.
 */
public class SpearControl extends MyAbstractGhostControl implements PhysicsCollisionListener {
	private static final float SPEED = 5;
	private float time = 0;

	private final float destroyXCoordinate = -100;

	private Vector3f initialLoc;

	private DifficultyState diffState;

	/**
	 * The constructor for the control.
	 * 
	 * @param shape
	 *            the collision shape of the spear
	 * @param sManager
	 *            the AppStateManager
	 * @param iLoc
	 *            the initial location of the spear
	 */
	public SpearControl(final CollisionShape shape, final AppStateManager sManager,
			final Vector3f iLoc) {
		super(shape);
		initialLoc = iLoc;
		diffState = sManager.getState(DifficultyState.class);
		sManager.getState(PlayState.class).getPhysicsSpace().add(this);
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
		spatial.setLocalTranslation(initialLoc);
		setPhysicsLocation(initialLoc);
	}

	/**
	 * The update method for the controller.
	 * 
	 * @param tpf
	 *            is the time per frame
	 */
	@Override
	public void update(final float tpf) {
		move();
		time += tpf;
		if (spatial.getLocalTranslation().getX() < destroyXCoordinate) {
			spatial.removeFromParent();
			setEnabled(false);
		}

	}

	/**
	 * This method moves the spatial in the desired direction.
	 */
	private void move() {
		Vector3f loc;
		if (spatial != null && time > 2) {
			final Vector3f vec = spatial.getLocalTranslation();
			loc = new Vector3f((float) (vec.getX() - SPEED), vec.getY(), vec.getZ());
			spatial.setLocalTranslation(loc);
			setPhysicsLocation(loc);
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
		if (checkCollision(event, SpearFactory.SPEAR_NAME, PenguinFactory.PENGUIN_NAME)) {
			diffState.resetDiff();
			destroy(event, PenguinFactory.PENGUIN_NAME);
		}
	}

}
