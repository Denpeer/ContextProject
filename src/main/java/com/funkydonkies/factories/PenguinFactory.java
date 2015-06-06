package com.funkydonkies.factories;

import com.funkydonkies.gamestates.PlayState;
import com.funkydonkies.geometrys.penguins.FatPenguin;
import com.funkydonkies.geometrys.penguins.ShinyPenguin;
import com.funkydonkies.geometrys.penguins.StandardPenguin;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
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
	
	private Material fatPenguinMaterial;
	private Material shinyPenguinMaterial;
	private Material standardPenguinMaterial;
	
	/**
	 * The constructor of the penguin factory.
	 * @param assetM the main AssetManager
	 * @param rootN the main rootNode
	 * @param phy the main physic location
	 */
	public PenguinFactory(final AppStateManager sm) {
		this.penguinNode = sm.getState(PlayState.class).getRootNode();
		this.assetManager = sm.getApplication().getAssetManager();	
		this.physicSpace = sm.getState(PlayState.class).getPhysicsSpace();
		makeMaterials();
	}
	
	/**
	 * The create method to make fat penguins.
	 * @return a fatpenguin object
	 */
	public FatPenguin makeFatPenguin() {
		final Mesh mesh = new Box(10, 10, 10);
		final FatPenguin fatPeng = new FatPenguin("fatOne", mesh, penguinNode, fatPenguinMaterial, physicSpace);
		return fatPeng;
	}
	
	/**
	 * The create method to make shiny penguins.
	 * @return a shinypenguin object
	 */
	public ShinyPenguin makeShinyPenguin() {
		final Mesh mesh = new Box(10, 10, 10);
		final ShinyPenguin shinyPeng = new ShinyPenguin("ShinyPenguinie", mesh, penguinNode, shinyPenguinMaterial, physicSpace);
		return shinyPeng;
	}
	
	/**
	 * The create method to make standard penguins.
	 * @return a standard penguin object
	 */
	public StandardPenguin makeStandardPenguin() {
		final Mesh mesh = new Sphere(SAMPLES, SAMPLES, DEFAULT_RADIUS);
		final StandardPenguin standardPenguin = new StandardPenguin("standardPenguin", mesh, penguinNode, standardPenguinMaterial, physicSpace, DEFAULT_RADIUS);
		return standardPenguin;
	}
	
	
	/**
	 * This method makes all the required materials.
	 */
	public void makeMaterials() {
		final String path = "Common/MatDefs/Misc/Unshaded.j3md";
		final String color = "Color";
		fatPenguinMaterial = new Material(assetManager, path);
		fatPenguinMaterial.setColor(color, ColorRGBA.Blue);
		
		shinyPenguinMaterial = new Material(assetManager, path);
		shinyPenguinMaterial.setColor(color, ColorRGBA.Orange);
		
		standardPenguinMaterial = new Material(assetManager, path);
		standardPenguinMaterial.setColor(color, ColorRGBA.Cyan);
	}
}
