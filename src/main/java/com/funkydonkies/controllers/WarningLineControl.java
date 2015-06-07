package com.funkydonkies.controllers;

import com.funkydonkies.curve.CustomCurveMesh;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import com.jme3.scene.control.AbstractControl;

public class WarningLineControl extends AbstractControl {
	float time = 0;
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
	protected void controlUpdate(float tpf) {
		time += tpf;
		
		if(time > 1){
			System.out.println("da");
			rootN.detachChild(spatial);
			spatial.removeControl(this);
		}
	}

	@Override
	protected void controlRender(RenderManager rm, ViewPort vp) {
		// TODO Auto-generated method stub
		
	}
	
}
