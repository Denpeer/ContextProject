package com.funkydonkies.controllers;

import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import com.jme3.scene.control.AbstractControl;

public class WarningLineControl extends AbstractControl {
	private float time = 0;
	private Node rootN;
	private Vector3f initialSpawn;

	
	public WarningLineControl(Node rootNode, float xOff, float yOff){
		rootN = rootNode;
		initialSpawn = new Vector3f(xOff, yOff, 0);
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
		
		if (time > 1) {
			detach();
			spatial.removeControl(this);
		}
	}

	/**
	 * Detaches spatial from scene.
	 */
	public void detach() {
		spatial.getParent().detachChild(spatial);
	}

	@Override
	protected void controlRender(RenderManager rm, ViewPort vp) {
		// TODO Auto-generated method stub
		
	}

}
