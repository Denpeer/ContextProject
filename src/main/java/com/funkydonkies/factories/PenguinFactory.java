package com.funkydonkies.factories;

import com.funkydonkies.controllers.StandardPenguinControl;
import com.funkydonkies.gamestates.PlayState;
import com.funkydonkies.interfaces.FactoryInterface;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.collision.shapes.SphereCollisionShape;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Sphere;

/**
 * This class represent the factory for penguins.
 * @author SDumasy
 *
 */
public class PenguinFactory implements FactoryInterface{
	private static final int SAMPLES = 20;
	private static final float DEFAULT_RADIUS = 4f;

	
	/**
	 * The create method to make standard penguins.
	 * @return a standard penguin object
	 */
	public Spatial makeObject(AppStateManager sManager, SimpleApplication app) {
		final Mesh mesh = new Sphere(SAMPLES, SAMPLES, DEFAULT_RADIUS);
		final Geometry standardPenguin = new Geometry("standardPenguin", mesh);
		standardPenguin.setMaterial(getPenguinMaterial(app.getAssetManager()));
		final StandardPenguinControl pc = new StandardPenguinControl(new SphereCollisionShape(DEFAULT_RADIUS), 1f);
		standardPenguin.addControl(pc);
		pc.setRestitution(1);
		sManager.getState(PlayState.class).getPhysicsSpace().add(pc);
		pc.init();
		return standardPenguin;
	}
	
	
	/**
	 * This method makes all the required materials.
	 */
	public Material getPenguinMaterial(AssetManager assetManager) {
		Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
		mat.setColor("Color", ColorRGBA.Orange);
		return mat;
	}
}
