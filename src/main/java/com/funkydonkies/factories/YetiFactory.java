package com.funkydonkies.factories;

import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppStateManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Sphere;

public class YetiFactory implements ObstacleFactoryInterface {
	private Material snowMaterial;
	
	@Override
	public Spatial makeObst(AppStateManager sManager, SimpleApplication app) {
		snowMaterial = app.getRootNode().getUserData("default material");
		snowMaterial.setColor("Color", ColorRGBA.White);
		
		Sphere mesh = new Sphere(40, 40, 1);
		Geometry snowBall = new Geometry("yeti_snowball", mesh);
		snowBall.setMaterial(snowMaterial);
		
	}

}
