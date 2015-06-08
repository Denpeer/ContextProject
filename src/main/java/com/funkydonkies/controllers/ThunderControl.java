package com.funkydonkies.controllers;

import com.funkydonkies.factories.PenguinFactory;
import com.funkydonkies.factories.ThunderFactory;
import com.funkydonkies.gamestates.CurveState;
import com.funkydonkies.gamestates.DifficultyState;
import com.funkydonkies.gamestates.PlayState;
import com.jme3.app.state.AppStateManager;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.collision.PhysicsCollisionEvent;
import com.jme3.bullet.collision.PhysicsCollisionListener;
import com.jme3.bullet.collision.shapes.BoxCollisionShape;
import com.jme3.bullet.control.GhostControl;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;

public class ThunderControl extends GhostControl implements PhysicsCollisionListener {
	
	private float time = 0;
	
	private Vector3f loc;
	private Vector3f initLoc = new Vector3f(-200, 0, 0);
	private AppStateManager stateManager;
	
	private CurveState curveState;
	
	public ThunderControl(final AppStateManager sManager, final float xCoord) {
		super(new BoxCollisionShape(new Vector3f(5, 1000, 20)));
		loc = new Vector3f(xCoord, 0, 0);
		stateManager = sManager;
		curveState = stateManager.getState(CurveState.class);
	}
	
	@Override
	public void setPhysicsSpace(PhysicsSpace space) {
		super.setPhysicsSpace(space);
		space.addCollisionListener(this);
	}

	public void init() {
		spatial.setLocalTranslation(initLoc);
	}
	
	@Override
	public void update(final float tpf) {
		super.update(tpf);
		
		time += tpf;
		
		final float updateX = curveState.getHighestPointX();
		if (updateX > 0 && time > 2) {
			moveToX(updateX);
		}
		
		if (time > 3) {
			detach();
			spatial.removeControl(this);
			setEnabled(false);
		}
		
	}
	
	/**
	 * Detaches spatial from scene.
	 */
	public void detach() {
		spatial.getParent().detachChild(spatial);
	}
	
	/** Moves spatial to desired c location.
	 * @param updateX desired x location
	 */
	public void moveToX(final float updateX) {
		spatial.setLocalTranslation(updateX, spatial.getLocalTranslation().y, 
				spatial.getLocalTranslation().z);
	}

	@Override
	public void collision(final PhysicsCollisionEvent event) {
		System.out.println("collision");
		if (event.getNodeA() != null && event.getNodeB() != null) {
			if (ThunderFactory.THUNDER_NAME.equals(event.getNodeA().getName()) 
					&& PenguinFactory.PENGUIN_NAME.equals(event.getNodeB().getName())) {
				stateManager.getState(DifficultyState.class).resetDiff();
				stateManager.getState(PlayState.class).getRootNode().detachChild(event.getNodeB());
				((RigidBodyControl) event.getNodeB().getControl(0)).setEnabled(false);
			} else if (PenguinFactory.PENGUIN_NAME.equals(event.getNodeA().getName())
					&& ThunderFactory.THUNDER_NAME.equals(event.getNodeB().getName())) {
				stateManager.getState(DifficultyState.class).resetDiff();
				stateManager.getState(PlayState.class).getRootNode().detachChild(event.getNodeA());
				((RigidBodyControl) event.getNodeA().getControl(0)).setEnabled(false);
			}
		}
	}
	
	
}