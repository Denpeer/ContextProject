package com.funkydonkies.controllers;

import com.funkydonkies.factories.PenguinFactory;
import com.funkydonkies.factories.ThunderFactory;
import com.funkydonkies.gamestates.CurveState;
import com.funkydonkies.gamestates.DifficultyState;
import com.jme3.app.state.AppStateManager;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.collision.PhysicsCollisionEvent;
import com.jme3.bullet.collision.PhysicsCollisionListener;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.GhostControl;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;

/**
 * Control class fot the thunder. Takes care of collisions between the thunder
 * and the penguins.
 * 
 * @author Olivier Dikken
 *
 */
public class ThunderControl extends GhostControl implements PhysicsCollisionListener {
	private static final Vector3f INITIAL_SPAWN_LOCATION = new Vector3f(-200, 0, 0);
	private boolean initialized = false;

	private float time = 0;
	private final float ttl = 3; // time to live

	private AppStateManager stateManager;

	private CurveState curveState;

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
		stateManager = sManager;
		curveState = stateManager.getState(CurveState.class);
	}

	/**
	 * This Method calls initialization which should occur after the control has
	 * been added to the spatial. setSpatial(spatial) is called by
	 * addControl(control) in Spatial.
	 * 
	 * @param spatial
	 *            spatial this control should control
	 */
	@Override
	public void setSpatial(final Spatial spatial) {
		super.setSpatial(spatial);
		initLocation();
	}

	/**
	 * An initialize method for the controller.
	 */
	public void initLocation() {
		setPhysicsLocation(INITIAL_SPAWN_LOCATION);
		spatial.setLocalTranslation(INITIAL_SPAWN_LOCATION);
	}

	@Override
	public void setPhysicsSpace(final PhysicsSpace space) {
		super.setPhysicsSpace(space);
		if (!initialized) {
			space.addCollisionListener(this);
			initialized = true;
		}
	}

	/**
	 * Handles a collision between penguin and thunder. Kill penguin and reset
	 * difficulty.
	 * 
	 * @param event
	 *            PhysicsCollisionEvent containing information about the
	 *            collision
	 */
	public void collision(final PhysicsCollisionEvent event) {

		if (checkCollision(event, ThunderFactory.THUNDER_NAME, PenguinFactory.STANDARD_PENGUIN_NAME)) {
			stateManager.getState(DifficultyState.class).resetDiff();
			remove(event, PenguinFactory.STANDARD_PENGUIN_NAME);
		}
	}

	/**
	 * remove this instance from parent and disable controller.
	 * 
	 * @param event
	 *            collision event
	 * @param name
	 *            of the node to be removed
	 */
	public void remove(final PhysicsCollisionEvent event, final String name) {
		if (name.equals(event.getNodeA().getName())) {
			event.getNodeA().removeFromParent();
			((GhostControl) event.getNodeA().getControl(SquidControl.class)).setEnabled(false);
		} else if (name.equals(event.getNodeB().getName())) {
			event.getNodeB().removeFromParent();
			((GhostControl) event.getNodeB().getControl(SquidControl.class)).setEnabled(false);
		}
	}

	/**
	 * Checks collision on an event between two Spatials c1 and c2.
	 * 
	 * @param e
	 *            PhysicsCollisionEvent to get the node names from
	 * @param c1
	 *            collidee 1
	 * @param c2
	 *            collidee 2
	 * @return result of collision check
	 */
	public boolean checkCollision(final PhysicsCollisionEvent e, final String c1, final String c2) {
		if (checkNull(e)) {
			return false;
		}

		final String nameA = e.getNodeA().getName();
		final String nameB = e.getNodeB().getName();

		return (c1.equals(nameA) && c2.equals(nameB) || c2.equals(nameA) && c1.equals(nameB));
	}

	/**
	 * Checks whether the event has/is null.
	 * 
	 * @param e
	 *            event to check
	 * @return true when e has/iss null
	 */
	public boolean checkNull(final PhysicsCollisionEvent e) {
		return e == null || e.getNodeA() == null || e.getNodeB() == null;
	}

	@Override
	public void update(final float tpf) {
		super.update(tpf);

		time += tpf;

		final float updateX = curveState.getHighestPointX();
		if (updateX > 0 && time > 2) {
			moveToX(updateX);
		}

		if (time > ttl) {
			spatial.removeFromParent();
			setEnabled(false);
		}

	}

	/**
	 * Detaches spatial from scene.
	 */
	public void detach() {
		spatial.getParent().detachChild(spatial);
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

}