package com.funkydonkies.controllers;

import com.funkydonkies.gamestates.PlayState;
import com.funkydonkies.gamestates.PowerUpState;
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


/**
 * This is a control to move floating spatials along the x- and y axis with a constant speed.
 */
public class PolarBearControl extends GhostControl implements PhysicsCollisionListener{
	private double speed;
	private Vector3f initialLoc;
	private AppStateManager sm;
	private static final String BALL_NAME = "standardPenguin";
	private static final String OBSTACLE_NAME = "polarBear";
	private float time;
	private float stopCoord;
	private boolean doneMoving;

	
	/**
	 * The constructor of the control.	
	 * @param mass the mass of the spatial
	 * @param sp the constant speed for the spatial
	 * @param moveHor a boolean to check if the spatial moves horizontal or vertical
	 * @param moveUpRight a boolean to check if the spatial moves right or left
	 */
	public PolarBearControl(final CollisionShape shape, float stopX, final double sp, AppStateManager asm, Vector3f loci) {
		super(shape);
		sm = asm;
		this.speed = sp;
		initialLoc = loci;
		time = 0;
		stopCoord = stopX;
		doneMoving = false;
	}
	
	/**
	 * An initialize method for the controller.
	 */
	public final void init() {
		setPhysicsLocation(initialLoc);
		spatial.setLocalTranslation(initialLoc);
	}
	
	/**
	 * The update method for the contoller.
	 * @param tpf is the time per frame
	 */
	@Override
	public void update(final float tpf) {
		moveSpatial();
		
		if(doneMoving){
			space.addCollisionListener(this);
			time += tpf;
			if(time > 3){
				spatial.getParent().detachChild(spatial);
				this.setEnabled(false);
				spatial.removeControl(this);
				doneMoving = false;
			}
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
	 * This method moves the spatial in the desired direction.
	 */
	private void moveSpatial() {
		Vector3f loc;
		final Vector3f vec = spatial.getLocalTranslation();
		if (spatial != null && initialLoc.getX() < stopCoord && vec.getX() < stopCoord ) {
			loc = new Vector3f((float) (vec.getX() + speed), vec.getY(), vec.getZ());
			spatial.setLocalTranslation(loc);
			setPhysicsLocation(loc);

		}else if (spatial != null && initialLoc.getX() >= stopCoord && vec.getX() >= stopCoord ) {
			loc = new Vector3f((float) (vec.getX() - speed), vec.getY(), vec.getZ());
			spatial.setLocalTranslation(loc);	
			setPhysicsLocation(loc);
		}else{
			doneMoving = true;
		}

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
		if (doneMoving && event.getNodeA() != null && event.getNodeB() != null){
			if (OBSTACLE_NAME.equals(event.getNodeA().getName()) 
					&& BALL_NAME.equals(event.getNodeB().getName())) {
				sm.getState(PowerUpState.class).resetDiff();
				sm.getState(PlayState.class).getRootNode().detachChild(event.getNodeB());
				((RigidBodyControl) event.getNodeB().getControl(0)).setEnabled(false);
		
			} else if(BALL_NAME.equals(event.getNodeA().getName())
				&& OBSTACLE_NAME.equals(event.getNodeB().getName())) {
			sm.getState(PowerUpState.class).resetDiff();
			sm.getState(PlayState.class).getRootNode().detachChild(event.getNodeA());
			((RigidBodyControl) event.getNodeA().getControl(0)).setEnabled(false);
			}
		} 
	}	


}
