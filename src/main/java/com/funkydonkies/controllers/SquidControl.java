package com.funkydonkies.controllers;

import com.funkydonkies.factories.PenguinFactory;
import com.funkydonkies.factories.SquidFactory;
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
 * Control class for the target. Takes care of collisions between the ball and
 * target.
 */
public class SquidControl extends GhostControl implements
		PhysicsCollisionListener {
	private static final Vector3f INITIAL_SPAWN_LOCATION = new Vector3f(250f,
			10f, 1f);
	private static final float Y_PADDING = CurveState.POINTS_HEIGHT * 0.2f;
	private boolean initialized = false;
	private DifficultyState diffState;

	private boolean moveRight = true;
	private float time = 0;

	/**
	 * Constructor method for target control.
	 * 
	 * @param shape
	 *            Collisionshape for the target
	 * @param sm
	 *            jme AppstateManager to get AppStates
	 */
	public SquidControl(final CollisionShape shape, final AppStateManager sm) {
		super(shape);
		diffState = sm.getState(DifficultyState.class);
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

	/**
	 * Set the physics space and add this controller as tick listener.
	 * 
	 * @param space
	 *            takes a pre-defined jme3 physicsSpace
	 */
	@Override
	public void setPhysicsSpace(final PhysicsSpace space) {
		super.setPhysicsSpace(space);
		if (!initialized) {
			space.addCollisionListener(this);
			initialized = true;
		}

	}

	/**
	 * Removes the control from the physics space.
	 */
	public void delete() {
		space.removeCollisionListener(this);
		space.remove(this);
		spatial.getParent().detachChild(spatial);
	}

	/**
	 * Handles a collision between penguin and squid. Calls methods to increase
	 * the combo and activate snow ball powerup.
	 * 
	 * @param event
	 *            PhysicsCollisionEvent containing information about the
	 *            collision
	 */
	public void collision(final PhysicsCollisionEvent event) {
		if (checkCollision(event, SquidFactory.SQUID_NAME,
				PenguinFactory.STANDARD_PENGUIN_NAME)) {
			respawn();
			diffState.incDiff();
			diffState.activateSnowBallPowerup();
			remove(event, SquidFactory.SQUID_NAME);
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
	public boolean checkCollision(final PhysicsCollisionEvent e,
			final String c1, final String c2) {
		if (checkNull(e)) {
			return false;
		}

		final String nameA = e.getNodeA().getName();
		final String nameB = e.getNodeB().getName();

		return (c1.equals(nameA) && c2.equals(nameB) || c2.equals(nameA)
				&& c1.equals(nameA));
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
			((GhostControl) event.getNodeA().getControl(SquidControl.class))
					.setEnabled(false);
		} else if (name.equals(event.getNodeB().getName())) {
			event.getNodeB().removeFromParent();
			((GhostControl) event.getNodeB().getControl(SquidControl.class))
					.setEnabled(false);
		}
	}

	/**
	 * updates the location of the squid powerup.
	 * 
	 * @param tpf
	 *            update interval
	 */
	public void update(final float tpf) {
		final double numHalf = 0.5;
		final int numFour = 4;
		time += tpf;
		if (time > numFour) {
			time = 0;
			if (moveRight) {
				moveRight = false;
			} else {
				moveRight = true;
			}
		}
		if (moveRight) {
			final Vector3f vec = spatial.getLocalTranslation();
			final Vector3f loc = new Vector3f((float) (vec.getX() - numHalf),
					vec.getY(), vec.getZ());
			spatial.setLocalTranslation(loc);
			this.setPhysicsLocation(loc);
		} else {
			final Vector3f vec = spatial.getLocalTranslation();
			final Vector3f loc = new Vector3f((float) (vec.getX() + numHalf),
					vec.getY(), vec.getZ());
			spatial.setLocalTranslation(loc);
			this.setPhysicsLocation(loc);
		}
	}

	/**
	 * Respawn the target at a reachable location. TODO make the spawn location
	 * random and make sure its reachable
	 */
	public void respawn() {
		final float x = (float) Math.random()
				* (CurveState.POINT_DISTANCE * CurveState.DEFAULT_CONTROL_POINTS_COUNT);

		final float y = (float) Math.random() * CurveState.POINTS_HEIGHT
				+ Y_PADDING;
		final Vector3f respawnlocation = new Vector3f(x, y, 1.5f);
		setPhysicsLocation(respawnlocation);
		spatial.setLocalTranslation(respawnlocation);
	}

}
