package com.funkydonkies.controllers;

import com.funkydonkies.gamestates.DifficultyState;
import com.funkydonkies.gamestates.PlayState;
import com.jme3.bullet.collision.PhysicsCollisionEvent;
import com.jme3.bullet.collision.PhysicsCollisionListener;
import com.jme3.bullet.collision.shapes.SphereCollisionShape;
import com.jme3.bullet.control.GhostControl;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.math.Vector3f;

public class YetiControl extends RigidBodyControl implements PhysicsCollisionListener {
	private Vector3f loc;
	private Vector3f speed;
	
	public YetiControl() {
		super(new SphereCollisionShape(10), 80);
		loc = new Vector3f(150, 0, -400);
		speed = new Vector3f(0, 50, 70);
	}

	public void init() {
		setPhysicsLocation(loc);
		setLinearVelocity(speed);
//		applyImpulse(speed, new Vector3f());
//		applyForce(speed, loc);
	}
	
	@Override
	public void update(float tpf) {
		super.update(tpf);
		
	}

	@Override
	public void collision(PhysicsCollisionEvent event) {
		if (event.getNodeA() != null && event.getNodeB() != null){
			if (OBSTACLE_NAME.equals(event.getNodeA().getName()) 
					&& BALL_NAME.equals(event.getNodeB().getName())) {
				sm.getState(DifficultyState.class).resetDiff();
				sm.getState(PlayState.class).getRootNode().detachChild(event.getNodeB());
				((RigidBodyControl) event.getNodeB().getControl(0)).setEnabled(false);
			} else if(BALL_NAME.equals(event.getNodeA().getName())
					&& OBSTACLE_NAME.equals(event.getNodeB().getName())) {
				sm.getState(DifficultyState.class).resetDiff();
				sm.getState(PlayState.class).getRootNode().detachChild(event.getNodeA());
				((RigidBodyControl) event.getNodeA().getControl(0)).setEnabled(false);
			}
		}
	}
	
	
}