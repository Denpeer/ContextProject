package com.funkydonkies.controllers;

import com.funkydonkies.gamestates.ComboState;
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
	private boolean moveUpOrRight;
	private boolean moveHorizontally;
	private static final String BALL_NAME = "standardPenguin";
	private static final String OBSTACLE_NAME = "killerWhale";
	private static final float INITIAL_XCOORD = 300;
	private static final float INITIAL_YCOORD = 40;
	
	/**
	 * The constructor of the control.	
	 * @param mass the mass of the spatial
	 * @param sp the constant speed for the spatial
	 * @param moveHor a boolean to check if the spatial moves horizontal or vertical
	 * @param moveUpRight a boolean to check if the spatial moves right or left
	 */
	public KillerWhaleControl(final float mass, final double sp,
			final boolean moveHor, final boolean moveUpRight) {
		super(mass);
		this.speed = sp;
		moveHorizontally = moveHor;
		moveUpOrRight = moveUpRight;
	}
	
	/**
	 * An initialize method for the controller.
	 */
	public final void init() {
		final Vector3f loci = new Vector3f(INITIAL_XCOORD, INITIAL_YCOORD, 0);
		spatial.setLocalTranslation(loci);
		this.setPhysicsLocation(loci);
		setKinematic(true);
	}
	
	/**
	 * The update method for the contoller.
	 * @param tpf is the time per frame
	 */
	@Override
	public void update(final float tpf) {
		moveSpatial();
	}
	
	/**
	 * This method moves the spatial in the desired direction.
	 */
	private void moveSpatial() {
		Vector3f loc;
		if (spatial != null) {
			final Vector3f vec = spatial.getLocalTranslation();
			if (moveHorizontally && moveUpOrRight) {
				loc = new Vector3f((float) (vec.getX() + speed), vec.getY(), vec.getZ());
			} else if (moveHorizontally && !moveUpOrRight) {
				loc = new Vector3f((float) (vec.getX() - speed), vec.getY(), vec.getZ());
			} else if (!moveHorizontally && moveUpOrRight) {
				loc = new Vector3f(vec.getX(), (float) (vec.getY() + speed), vec.getZ());
			} else {
				loc = new Vector3f(vec.getX(), (float) (vec.getY() - speed), vec.getZ());
			}
			spatial.setLocalTranslation(loc);
			this.setPhysicsLocation(loc);
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
		if (OBSTACLE_NAME.equals(event.getNodeA().getName()) 
				&& BALL_NAME.equals(event.getNodeB().getName())
				|| BALL_NAME.equals(event.getNodeA().getName()) 
						&& OBSTACLE_NAME.equals(event.getNodeB().getName())) {
			ComboState.resetCombo();
		}
	}

		
}
