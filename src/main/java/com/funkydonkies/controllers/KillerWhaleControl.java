package com.funkydonkies.controllers;

import com.funkydonkies.gamestates.DifficultyState;
import com.funkydonkies.gamestates.PlayState;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.collision.PhysicsCollisionEvent;
import com.jme3.bullet.collision.PhysicsCollisionListener;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;


/**
 * This is a control to move floating spatials along the x- and y axis with a constant speed.
 */
public class KillerWhaleControl extends RigidBodyControl implements PhysicsCollisionListener{
	private double speed;
	private Vector3f initialLoc;
	private AppStateManager sm;
	private static final String BALL_NAME = "standardPenguin";
	private static final String OBSTACLE_NAME = "killerWhale";
	private float time;
	private boolean moveUp = true;

	
	/**
	 * The constructor of the control.	
	 * @param mass the mass of the spatial
	 * @param sp the constant speed for the spatial
	 * @param moveHor a boolean to check if the spatial moves horizontal or vertical
	 * @param moveUpRight a boolean to check if the spatial moves right or left
	 */
	public KillerWhaleControl(final float mass, final double sp, AppStateManager asm, Vector3f loci) {
		super(mass);
		sm = asm;
		this.speed = sp;
		initialLoc = loci;
		time = 0;
	}
	
	/**
	 * An initialize method for the controller.
	 */
	public final void init() {
		setKinematic(true);	
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
		if(spatial.getLocalTranslation().getY() < -500){
			destroy();
		}

	}
	
	public void destroy(){
		spatial.getParent().detachChild(spatial);
		spatial.removeControl(this);
	//	sm.getState(PlayState.class).getPhysicsSpace().remove(this);

	}
	
	/**
	 * This method moves the spatial in the desired direction.
	 */
	private void moveSpatial() {
		Vector3f loc;
		
		if (spatial != null && time > 1) {
			final Vector3f vec = spatial.getLocalTranslation();
			if(vec.getY() > 20){
				moveUp = false;
			}
			if(moveUp){
				loc = new Vector3f(vec.getX(), (float) (vec.getY() + speed), vec.getZ());
				spatial.setLocalTranslation(loc);
				this.setPhysicsLocation(loc);
			}else{
				loc = new Vector3f(vec.getX(), (float) (vec.getY() - speed), vec.getZ());
				spatial.setLocalTranslation(loc);
				this.setPhysicsLocation(loc);
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
		if (event.getNodeA() != null && event.getNodeB() != null){
			if (OBSTACLE_NAME.equals(event.getNodeA().getName()) 
					&& BALL_NAME.equals(event.getNodeB().getName())) {
				sm.getState(DifficultyState.class).resetDiff();
				sm.getState(PlayState.class).getRootNode().detachChild(event.getNodeB());
				((RigidBodyControl) event.getNodeB().getControl(0)).setEnabled(false);
				//sm.getState(PlayState.class).getPhysicsSpace().remove(this);
				//event.getNodeB().getParent().detachChild(event.getNodeB());
				//event.getNodeB().removeControl(this);
			} else if(BALL_NAME.equals(event.getNodeA().getName())
					&& OBSTACLE_NAME.equals(event.getNodeB().getName())) {
				sm.getState(DifficultyState.class).resetDiff();
				sm.getState(PlayState.class).getRootNode().detachChild(event.getNodeA());
				((RigidBodyControl) event.getNodeA().getControl(0)).setEnabled(false);
				//sm.getState(PlayState.class).getPhysicsSpace().remove(this);
				//event.getNodeA().getParent().detachChild(event.getNodeA());
				//event.getNodeA().removeControl(this);
			}
		}		
	}	

		
}
