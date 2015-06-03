package com.funkydonkies.controllers;

import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;


/**
 * This is a control to move floating spatials along the x- and y axis with a constant speed.
 */
public class ConstantSpeedMoveControl extends RigidBodyControl {
	private double speed;
	private boolean moveUpOrRight;
	private boolean moveHorizontally;
	private final float initialXCoord = 60;
	private final float initialYCoord = 10;
	
	/**
	 * The constructor of the control.	
	 * @param mass the mass of the spatial
	 * @param sp the constant speed for the spatial
	 * @param moveHor a boolean to check if the spatial moves horizontal or vertical
	 * @param moveUpRight a boolean to check if the spatial moves right or left
	 */
	public ConstantSpeedMoveControl(final float mass, final double sp,
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
		final Vector3f loci = new Vector3f(initialXCoord, initialYCoord, 0);
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

		
}
