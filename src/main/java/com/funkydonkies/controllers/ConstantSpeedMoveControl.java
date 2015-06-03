package com.funkydonkies.controllers;

import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;


/**
 * This is a control to move spatials along the x- and y axis with a constant speed.
 */
public class ConstantSpeedMoveControl extends RigidBodyControl{
	private double speed;
	private boolean moveUpOrRight;
	private boolean moveHorizontally;
	Boolean bool;
	
	public ConstantSpeedMoveControl(float mass, double sp, boolean moveHor, boolean moveUpRight){
		super(mass);
		this.speed = sp;
		moveHorizontally = moveHor;
		moveUpOrRight = moveUpRight;
		bool = true;
	}
	
	@Override
	public void update(float tpf) {
		moveSpatial();
	}
	
	private void moveSpatial(){
		Vector3f loc;
		if(bool){
			Vector3f loci = new Vector3f(60,0,0);
			spatial.setLocalTranslation(loci);
			this.setPhysicsLocation(loci);
			bool = false;
		}
		if(spatial != null){
			Vector3f vec = spatial.getLocalTranslation();
			if(moveHorizontally && moveUpOrRight){
				loc = new Vector3f((float) (vec.getX() + speed), vec.getY(), vec.getZ());
			}else if(moveHorizontally && !moveUpOrRight){
				loc = new Vector3f((float) (vec.getX() - speed), vec.getY(), vec.getZ());
			}else if(!moveHorizontally && moveUpOrRight){
				loc = new Vector3f(vec.getX(), (float) (vec.getY() + speed), vec.getZ());
			}else{
				loc = new Vector3f(vec.getX(),(float) (vec.getY() - speed), vec.getZ());
			}
			spatial.setLocalTranslation(loc);
			this.setPhysicsLocation(loc);
		}

	}


	protected void controlRender(RenderManager rm, ViewPort vp) {
		// TODO Auto-generated method stub
		
	}

		
}
