package com.funkydonkies.interfaces;

import com.jme3.bullet.collision.PhysicsCollisionEvent;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.PhysicsControl;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.Control;

/**
 * Abstract class for custom RigidBodyControl methods.
 */
public class MyAbstractRigidBodyControl extends RigidBodyControl {
	
	/** Constructor for the AbstractRigidBodyControl, takes a desired collision shape and mass.
	 * @param colShape desired collision shape
	 * @param mass desired mass
	 */
	public MyAbstractRigidBodyControl(final CollisionShape colShape, final float mass) {
		super(colShape, mass);
	}
	
	@Override
	public void setSpatial(final Spatial spatial) {
		super.setSpatial(spatial);
		init();
	}
	
	/**
	 * Called when the control has been added.
	 */
	public void init() {
		
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
	
}
