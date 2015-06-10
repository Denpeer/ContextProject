package com.funkydonkies.controllers;

import com.funkydonkies.factories.KillerWhaleFactory;
import com.funkydonkies.factories.PenguinFactory;
import com.funkydonkies.gamestates.DifficultyState;
import com.funkydonkies.gamestates.PlayState;
import com.jme3.app.state.AppStateManager;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.collision.PhysicsCollisionEvent;
import com.jme3.bullet.collision.PhysicsCollisionListener;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.GhostControl;
import com.jme3.bullet.control.PhysicsControl;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.Control;


/**
 * This is a control to move floating spatials along the x- and y axis with a constant speed.
 */
public class KillerWhaleControl extends GhostControl implements PhysicsCollisionListener {
	private AppStateManager stateManager;

	private Vector3f initialLoc;

	private float time;
	
	private boolean moveUp = true;
	
	private static final float SPEED = 4;;
	private static final float STOP_HEIGHT = -30;
	private static final float DESTROY_HEIGHT = -500;
	
	/**
	 * The constructor of the control.
	 * @param colShape desired colShape
	 * @param sManager jme AppStateManager to get states
	 * @param iLoc initial location of the whale
	 * 
	 */
	public KillerWhaleControl(final CollisionShape colShape, final AppStateManager sManager, 
			final Vector3f iLoc) {
		super(colShape);
		stateManager = sManager;
		initialLoc = iLoc;
		time = 0;
	}
	
	@Override
	public void setSpatial(final Spatial spatial) {
		super.setSpatial(spatial);
		initLocation();
	}
	
	/**
	 * An initialize method for the controller.
	 */
	public final void initLocation() {
		stateManager.getState(PlayState.class).getPhysicsSpace().add(this);
		spatial.setLocalTranslation(initialLoc);
	}
	
	/**
	 * The update method for the contoller.
	 * @param tpf is the time per frame
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
	 * @param space takes a pre-defined jme3 physicsSpace
	 */
	@Override
	public void setPhysicsSpace(final PhysicsSpace space) {
		super.setPhysicsSpace(space);
		space.addCollisionListener(this);
	}
	
	/** 
	 * Removes the given spatial from the scene and disables its controls.
	 * 
	 * @param e event the Spatial to destroy participates in
	 * @param toDestroyName name of the Spatial to be destroyed
	 * @return true if the Spatial was found and destroyed
	 */
	public boolean destroy(final PhysicsCollisionEvent e, final String toDestroyName) {
		final Spatial toDestroy = getCollisionSpatial(e, toDestroyName);
		
		if (toDestroy != null) {
			toDestroy.removeFromParent();
			disableControls(toDestroy);
			return true;
		}
		return false;
	}

	/**
	 * Disables all the PhysicsControllers on the given spatial.
	 * @param s spatial to disable controls on
	 */
	public void disableControls(final Spatial s) {
		final int controlsAmount = s.getNumControls();
		
		for (int i = 0; i < controlsAmount; i++) {
			final Control tmp = s.getControl(i);
			if (tmp instanceof PhysicsControl) {
				((PhysicsControl) tmp).setEnabled(false);
			}
		}
	}

	/** 
	 * Get a collision Spatial by name.
	 * 
	 * @param e collision event
	 * @param name wanted node
	 * @return null if there is no such node
	 */
	public Spatial getCollisionSpatial(final PhysicsCollisionEvent e, final String name) {
		if (e.getNodeA().getName() == name) {
			return e.getNodeA();
		} else if (e.getNodeB().getName() == name) {
			return e.getNodeB();
		} else {
			return null;
		}
	}

	/**
	 * Handles a collision between penguin and killer whale.
	 * Destroys the penguin on collision.
	 * 
	 * @param event PhysicsCollisionEvent containing information about the collision
	 */
	public void collision(final PhysicsCollisionEvent event) {
		if (checkCollision(event, KillerWhaleFactory.WHALE_NAME, 
				PenguinFactory.STANDARD_PENGUIN_NAME)) {
			stateManager.getState(DifficultyState.class).resetDiff();
			destroy(event, PenguinFactory.STANDARD_PENGUIN_NAME);
		}
	}

	/** 
	 * Checks collision on an event between two Spatials c1 and c2.
	 * @param e PhysicsCollisionEvent to get the node names from
	 * @param c1 collidee 1
	 * @param c2 collidee 2
	 * @return result of collision check
	 */
	public boolean checkCollision(final PhysicsCollisionEvent e, final String c1, final String c2) {
		if (checkNull(e)) {
			return false;
		}
		
		final String nameA = e.getNodeA().getName();
		final String nameB = e.getNodeB().getName();
		
		return (c1.equals(nameA) && c2.equals(nameB)
				|| c2.equals(nameA) && c1.equals(nameB));
	}

	/** Checks whether the event has/is null.
	 * @param e event to check
	 * @return true when e has/iss null
	 */
	public boolean checkNull(final PhysicsCollisionEvent e) {
		return e == null || e.getNodeA() == null || e.getNodeB() == null;
	}
		
}
