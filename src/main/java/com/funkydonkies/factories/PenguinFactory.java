package com.funkydonkies.factories;

import com.funkydonkies.controllers.StandardPenguinControl;
import com.funkydonkies.gamestates.PlayState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.collision.shapes.SphereCollisionShape;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Sphere;

/**
 * This class represent the factory for penguins.
 * @author SDumasy
 *
 */
public class PenguinFactory {
	private static final int SAMPLES = 20;
	private static final float DEFAULT_RADIUS = 4f;
	
	private AssetManager assetManager;
	private Node penguinNode;
	private PhysicsSpace physicSpace;

	private Material standardPenguinMaterial;
	
	/**
	 * The constructor of the penguin factory.
	 * @param assetM the main AssetManager
	 * @param rootN the main rootNode
	 * @param phy the main physic location
	 */
	public PenguinFactory(final AppStateManager sm, Node n) {
		this.penguinNode = n;
		this.assetManager = sm.getApplication().getAssetManager();	
		this.physicSpace = sm.getState(PlayState.class).getPhysicsSpace();
		makeMaterials();
	}
	
	/**
	 * The create method to make standard penguins.
	 * @return a standard penguin object
	 */
	public Geometry makeStandardPenguin() {
		final Mesh mesh = new Sphere(SAMPLES, SAMPLES, DEFAULT_RADIUS);
		final Geometry standardPenguin = new Geometry("standardPenguin", mesh);
		standardPenguin.setMaterial(standardPenguinMaterial);
		final StandardPenguinControl pc = new StandardPenguinControl(new SphereCollisionShape(DEFAULT_RADIUS), 1f);
		standardPenguin.addControl(pc);
		pc.setRestitution(1);
		physicSpace.add(pc);
		pc.init();
		penguinNode.attachChild(standardPenguin);
		return standardPenguin;
	}
	
	
	/**
	 * This method makes all the required materials.
	 */
	public void makeMaterials() {
		final String path = "Common/MatDefs/Misc/Unshaded.j3md";
		final String color = "Color";
		
		standardPenguinMaterial = new Material(assetManager, path);
		standardPenguinMaterial.setColor(color, ColorRGBA.Cyan);
	}
}
