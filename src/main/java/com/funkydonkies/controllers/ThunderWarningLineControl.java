package com.funkydonkies.controllers;

import com.funkydonkies.gamestates.CurveState;
import com.jme3.app.state.AppStateManager;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.control.AbstractControl;

public class ThunderWarningLineControl extends AbstractControl {
	private float time = 0;

	private Vector3f initialSpawn;
	private CurveState curveState;
	
	public ThunderWarningLineControl(final AppStateManager sManager, float xOff, float yOff){
		initialSpawn = new Vector3f(xOff, yOff, 0);
		curveState = sManager.getState(CurveState.class);
	}
	
	/**
	 * The initialize method for the control.
	 */
	public void init() {
		spatial.setLocalTranslation(initialSpawn);
	}
	
	@Override
	protected void controlUpdate(final float tpf) {
		time += tpf;
		
		final float updateX = curveState.getHighestPointX();
		if (updateX > 0) {
			moveToX(updateX);
		}
		
		if (time > 2) {
			detach();
			spatial.removeControl(this);
		}
	}

	/** Moves spatial to desired c location.
	 * @param updateX desired x location
	 */
	public void moveToX(final float updateX) {
		spatial.setLocalTranslation(updateX, spatial.getLocalTranslation().y, 
				spatial.getLocalTranslation().z);
	}

	/**
	 * Detaches spatial from scene.
	 */
	public void detach() {
		spatial.getParent().detachChild(spatial);
	}

	@Override
	protected void controlRender(final RenderManager rm, final ViewPort vp) {
		// TODO Auto-generated method stub
		
	}

}
