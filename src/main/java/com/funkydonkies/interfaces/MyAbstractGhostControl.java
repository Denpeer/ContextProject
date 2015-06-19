package com.funkydonkies.interfaces;

import java.util.Random;

import com.jme3.bullet.collision.PhysicsCollisionEvent;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.GhostControl;
import com.jme3.bullet.control.PhysicsControl;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.Control;

/**
 * Abstract class for custom GhostControl methods.
 */
public abstract class MyAbstractGhostControl extends GhostControl {
	private static final float PADDING = 20f;
	private static final int RANDOM_X = 280;
	private static final int RANDOM_Y = 80;

	/** Constructor for the AbstractGhostControl, takes a desired collision shape.
	 * @param colShape desired collision shape
	 */
	public MyAbstractGhostControl(final CollisionShape colShape) {
		super(colShape);
	}
	
	@Override
	public void setSpatial(final Spatial spatial) {
		super.setSpatial(spatial);
		init();
	}

	/**
	 * Called when the control has been added.
	 */
	public abstract void init();
	
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
		
		return c1.equals(nameA) && c2.equals(nameB)
				|| c2.equals(nameA) && c1.equals(nameB);
	}

	/** Checks whether the event has/is null.
	 * @param e event to check
	 * @return true when e has/iss null
	 */
	public boolean checkNull(final PhysicsCollisionEvent e) {
		return e == null || e.getNodeA() == null || e.getNodeB() == null;
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
			disablePhysicsControls(toDestroy);
			return true;
		}
		return false;
	}
	
	/**
	 * Destroys the object and disables control.
	 * @see com.jme3.bullet.objects.PhysicsGhostObject#destroy()
	 */
	@Override
	public void destroy() {
		super.destroy();
		spatial.removeFromParent();
		this.setEnabled(false);
	}

	/**
	 * Disables all the PhysicsControllers on the given spatial.
	 * @param s spatial to disable controls on
	 */
	public void disablePhysicsControls(final Spatial s) {
		final int controlsAmount = s.getNumControls();
		
		for (int i = 0; i < controlsAmount; i++) {
			final Control tmp = s.getControl(i);
			if (tmp instanceof PhysicsControl) {
				((PhysicsControl) tmp).setEnabled(false);
			}
		}
	}
	
	/**
	 * Respawn the fish at a reachable location.
	 * TODO make sure its reachable
	 */
	public void respawn() {
		final Random rand = new Random();
		final float x = (float) rand.nextInt(RANDOM_X) + PADDING;
		
		final float y = (float) rand.nextInt(RANDOM_Y) + PADDING;
		final Vector3f respawnlocation = new Vector3f(x, y, 1.5f);
		spatial.setLocalTranslation(respawnlocation);
		setPhysicsLocation(respawnlocation);
	}

	/** 
	 * Get a collision Spatial by name.
	 * 
	 * @param e collision event
	 * @param name wanted node
	 * @return null if there is no such node
	 */
	public Spatial getCollisionSpatial(final PhysicsCollisionEvent e, final String name) {
		if (e.getNodeA().getName().equals(name)) {
			return e.getNodeA();
		} else if (e.getNodeB().getName().equals(name)) {
			return e.getNodeB();
		} else {
			return null;
		}
	}
	
}
