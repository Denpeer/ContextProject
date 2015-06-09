package com.funkydonkies.factories;

import java.util.Random;

import com.funkydonkies.controllers.ThunderControl;
import com.funkydonkies.controllers.ThunderWarningLineControl;
import com.funkydonkies.gamestates.PlayState;
import com.funkydonkies.interfaces.FactoryInterface;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppStateManager;
import com.jme3.material.Material;
import com.jme3.material.RenderState.BlendMode;
import com.jme3.math.ColorRGBA;
import com.jme3.renderer.queue.RenderQueue.Bucket;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;

public class ThunderFactory implements FactoryInterface {
	
	public static final String THUNDER_NAME = "thunder";
	private Material thunderMaterial;
	private Material warningLineMaterial;
	
	@Override
	public Spatial makeObject(final AppStateManager sManager, final SimpleApplication app) {
		final Random rand = new Random();
		final float xCoord = rand.nextInt(320);
		
		initMaterials(app);
		
		final Geometry thunderGeom = makeThunder(sManager, xCoord);
		final Geometry warningLineGeom = makeWarningLine(sManager, app, xCoord);
		
		final Node node = new Node();
		node.attachChild(thunderGeom);
		node.attachChild(warningLineGeom);
		
		return node;
	}
	
	public Geometry makeThunder(final AppStateManager sManager, final float xCoord) {
		final Box mesh = new Box(5, 1000, 20);
		final Geometry geom = new Geometry(THUNDER_NAME, mesh);
		geom.setMaterial(thunderMaterial);
		
		final ThunderControl control = new ThunderControl(sManager, xCoord);
		geom.addControl(control);
		sManager.getState(PlayState.class).getPhysicsSpace().add(control);
		control.init();
		
		return geom;
	}
	
	public Geometry makeWarningLine(final AppStateManager sManager, final SimpleApplication app, 
			float xCoord){
		final Mesh warningLineMesh = new Box(5, 200, 1);
		final Geometry geom = new Geometry("warning line", warningLineMesh);
		geom.setMaterial(warningLineMaterial);
		geom.setQueueBucket(Bucket.Transparent);
		
		final ThunderWarningLineControl wLC = new ThunderWarningLineControl(sManager, xCoord, 0);
		geom.addControl(wLC);
		wLC.init();
		return geom;
	}
	
	public void initMaterials(SimpleApplication app) {
		thunderMaterial = ((Material) app.getRootNode().getUserData("default material")).clone();
		thunderMaterial.setColor("Color", ColorRGBA.Pink);
		
		warningLineMaterial = ((Material) app.getRootNode().getUserData("default material")).clone();
		warningLineMaterial.setColor("Color", new ColorRGBA(1, 0, 0, (float)0.2));
		warningLineMaterial.getAdditionalRenderState().setBlendMode(BlendMode.Alpha);
	}
	
	

}
