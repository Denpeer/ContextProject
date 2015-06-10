package com.funkydonkies.controllers;

import com.funkydonkies.factories.PenguinFactory;
import com.funkydonkies.factories.SpearFactory;
import com.funkydonkies.gamestates.DifficultyState;
import com.funkydonkies.gamestates.PlayState;
import com.jme3.app.state.AppStateManager;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.collision.PhysicsCollisionEvent;
import com.jme3.bullet.collision.PhysicsCollisionListener;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.GhostControl;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Spatial;


/**
 * Control class for the spear. Takes care of collisions between the fish and the spear.
 */
public class SpearControl extends GhostControl implements PhysicsCollisionListener {
	private double speed;
	private Vector3f initialLoc;
	private AppStateManager sm;
	private float time;
	private final float destroyXCoordinate = -100;

	
	/**
	 * The constructor for the control.
	 * @param shape the collision shape of the spear
	 * @param sp the speed of the spear
	 * @param sManager the AppStateManager
	 * @param loci the initial location of the spear
	 */
	public SpearControl(final CollisionShape shape, final double sp, final AppStateManager sManager, final Vector3f loci) {
		super(shape);
		sm = sManager;
		this.speed = sp;
		initialLoc = loci;
		time = 0;
	}
	
	/** 
	 * This Method calls initialization which should occur after the control has been added to the
	 * spatial. setSpatial(spatial) is called by addControl(control) in Spatial.
	 * @param spatial spatial this control should control
	 */
	@Override
	public void setSpatial(final Spatial spatial) {
		super.setSpatial(spatial);
		initLocation();
	}
	
	/**
	 * An initialize method for the controller.
	 */
	public final void initLocation() {
		sm.getState(PlayState.class).getPhysicsSpace().add(this);
		spatial.setLocalTranslation(initialLoc);
		this.setPhysicsLocation(initialLoc);
	}
	
	/**
	 * The update method for the contoller.
	 * @param tpf is the time per frame
	 */
	@Override
	public void update(final float tpf) {
		moveSpatial();
		time += tpf;
		if (spatial.getLocalTranslation().getX() < destroyXCoordinate) {
			this.destroy();
		}

	}
	
	/**
	 * This method moves the spatial in the desired direction.
	 */
	private void moveSpatial() {
		Vector3f loc;
		if (spatial != null && time > 1) {
			final Vector3f vec = spatial.getLocalTranslation();
			loc = new Vector3f((float) (vec.getX() - speed), vec.getY(), vec.getZ());
			spatial.setLocalTranslation(loc);
			this.setPhysicsLocation(loc);
		}

	}
	
	/**
	 * Set the physics space and add this controller as tick listener.
	 * @param space takes a pre-defined jme3 physicsSpace
	 */
	@Override
	public void setPhysicsSpace(final PhysicsSpace space) {
		super.setPhysicsSpace(space);
		space.addCollisionListener(this);
	}
	/**
	 * The renderer for the control.
	 * @param rm the renderManager 
	 * @param vp the viewPort
	 */
	protected void controlRender(final RenderManager rm, final ViewPort vp) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Handles a collision between ball and target.
	 * Calls methods to increase the combo and respawn the target.
	 * @param event PhysicsCollisionEvent containing information about the collision
	 */
	public void collision(final PhysicsCollisionEvent event) {
		if (event.getNodeA() != null && event.getNodeB() != null) {
			if (SpearFactory.SPEAR_NAME.equals(event.getNodeA().getName()) 
					&& PenguinFactory.STANDARD_PENGUIN_NAME.equals(event.getNodeB().getName())) {
				sm.getState(DifficultyState.class).resetDiff();
				event.getNodeB().removeFromParent();
				((RigidBodyControl) event.getNodeB().getControl(PenguinControl.class)).setEnabled(false);
			} else if (PenguinFactory.STANDARD_PENGUIN_NAME.equals(event.getNodeA().getName())
					&& SpearFactory.SPEAR_NAME.equals(event.getNodeB().getName())) {
				sm.getState(DifficultyState.class).resetDiff();
				event.getNodeA().removeFromParent();
				((RigidBodyControl) event.getNodeA().getControl(PenguinControl.class)).setEnabled(false);
			}
		}		
	}	

		
}
