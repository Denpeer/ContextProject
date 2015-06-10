package com.funkydonkies.controllers;

import java.util.Random;

import com.funkydonkies.factories.PenguinFactory;
import com.funkydonkies.factories.YetiFactory;
import com.funkydonkies.gamestates.DifficultyState;
import com.jme3.app.state.AppStateManager;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.collision.PhysicsCollisionEvent;
import com.jme3.bullet.collision.PhysicsCollisionListener;
import com.jme3.bullet.collision.shapes.SphereCollisionShape;
import com.jme3.bullet.control.GhostControl;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;

/**
 * Control class fot the yeti. Takes care of collisions between the snow ball
 * and the penguins.
 * 
 * @author Olivier Dikken
 *
 */
public class YetiControl extends GhostControl implements PhysicsCollisionListener {
	private static final int RAND_X_MAX = 300;
	private static final int RAND_Y_MAX = 100;
	private static final int DEFAULT_DEPTH = -400;
	private static final int SPHERE_SIZE = 20;
	private static final int SNOW_BALL_Z_SPEED = 70;
	private Vector3f loc;
	private AppStateManager stateManager;
	private boolean initialized = false;
	private DifficultyState diffState;

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
		stateManager = sManager;
		diffState = stateManager.getState(DifficultyState.class);
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
		spatial.setLocalTranslation(loc);
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

	@Override
	public void update(final float tpf) {
		super.update(tpf);
		move(tpf);
	}

	/**
	 * Handles a collision between penguin and yeti. Calls methods to reset the
	 * combo and kill penguin.
	 * 
	 * @param event
	 *            PhysicsCollisionEvent containing information about the
	 *            collision
	 */
	public void collision(final PhysicsCollisionEvent event) {
		if (checkCollision(event, YetiFactory.YETI_NAME, PenguinFactory.STANDARD_PENGUIN_NAME)) {
			diffState.resetDiff();
			remove(event, PenguinFactory.STANDARD_PENGUIN_NAME);
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
	 * Move the snowball towards the screen (along z axis).
	 * 
	 * @param tpf
	 *            update time interval
	 */
	private void move(final float tpf) {
		spatial.setLocalTranslation(spatial.getLocalTranslation().x,
				spatial.getLocalTranslation().y, spatial.getLocalTranslation().z
						+ SNOW_BALL_Z_SPEED * tpf);
	}
}