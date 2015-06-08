package com.funkydonkies.controllers;

import com.funkydonkies.factories.PenguinFactory;
import com.funkydonkies.factories.YetiFactory;
import com.funkydonkies.gamestates.DifficultyState;
import com.funkydonkies.gamestates.PlayState;
import com.jme3.app.state.AppStateManager;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.collision.PhysicsCollisionEvent;
import com.jme3.bullet.collision.PhysicsCollisionListener;
import com.jme3.bullet.collision.shapes.SphereCollisionShape;
import com.jme3.bullet.control.GhostControl;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;

public class YetiControl extends GhostControl implements PhysicsCollisionListener {
	private Vector3f loc;
	private Vector3f speed;
	private AppStateManager sManager;
	
	public YetiControl(AppStateManager asm) {
		super(new SphereCollisionShape(20));
		loc = new Vector3f(150, 10, -400);
		speed = new Vector3f(0, 50, 70);
		sManager = asm;
	}
	
	@Override
	public void setPhysicsSpace(PhysicsSpace space) {
		// TODO Auto-generated method stub
		super.setPhysicsSpace(space);
		space.addCollisionListener(this);
	}

	public void init() {
		spatial.setLocalTranslation(loc);
//		applyImpulse(speed, new Vector3f());
//		applyForce(speed, loc);
	}
	
	@Override
	public void update(float tpf) {
		super.update(tpf);
		move(tpf);
		if (getPhysicsLocation().z >= 0) {
			Material mat = ((Geometry) spatial).getMaterial();
		}
	}

		
	private void move(float tpf) {
		spatial.setLocalTranslation(spatial.getLocalTranslation().x, 
				spatial.getLocalTranslation().y, spatial.getLocalTranslation().z + 70 * tpf);
	}
	
	@Override
	public void collision(PhysicsCollisionEvent event) {
		System.out.println("collision");
		if (event.getNodeA() != null && event.getNodeB() != null){
			if (YetiFactory.YETI_NAME.equals(event.getNodeA().getName()) 
					&& PenguinFactory.PENGUIN_NAME.equals(event.getNodeB().getName())) {
				sManager.getState(DifficultyState.class).resetDiff();
				sManager.getState(PlayState.class).getRootNode().detachChild(event.getNodeB());
				((RigidBodyControl) event.getNodeB().getControl(0)).setEnabled(false);
			} else if(PenguinFactory.PENGUIN_NAME.equals(event.getNodeA().getName())
					&& YetiFactory.YETI_NAME.equals(event.getNodeB().getName())) {
				sManager.getState(DifficultyState.class).resetDiff();
				sManager.getState(PlayState.class).getRootNode().detachChild(event.getNodeA());
				((RigidBodyControl) event.getNodeA().getControl(0)).setEnabled(false);
			}
		}
	}
	
	
}