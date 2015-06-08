package com.funkydonkies.factories;

import com.funkydonkies.controllers.YetiControl;
import com.funkydonkies.gamestates.PlayState;
import com.funkydonkies.interfaces.FactoryInterface;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppStateManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Sphere;

public class YetiFactory implements FactoryInterface {
	public static final String YETI_NAME = "yeti_snowball";
	private Material snowMaterial;
	
	@Override
	public Spatial makeObject(AppStateManager sManager, SimpleApplication app) {
		snowMaterial = ((Material) app.getRootNode().getUserData("default material")).clone();
		snowMaterial.setColor("Color", ColorRGBA.White);
		
		Sphere mesh = new Sphere(40, 40, 20);
		Geometry snowBall = new Geometry(YETI_NAME, mesh);
		snowBall.setMaterial(snowMaterial);
		
		YetiControl control = new YetiControl(sManager);
		snowBall.addControl(control);
		sManager.getState(PlayState.class).getPhysicsSpace().add(control);
		control.init();
		return snowBall;
	}
}
