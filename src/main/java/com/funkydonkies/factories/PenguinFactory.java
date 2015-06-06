package com.funkydonkies.factories;

import com.funkydonkies.geometries.penguins.FatPenguin;
import com.funkydonkies.geometries.penguins.ShinyPenguin;
import com.funkydonkies.geometries.penguins.StandardPenguin;
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
	private Node rootNode;
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
	public PenguinFactory(final AssetManager assetM, final Node rootN, final PhysicsSpace phy) {
		this.rootNode = rootN;
		this.assetManager = assetM;	
		this.physicSpace = phy;
	}
	
	/**
	 * The create method to make fat penguins.
	 * @return a fatpenguin object
	 */
	public FatPenguin makeFatPenguin() {
		final Mesh mesh = new Box(10, 10, 10);
		final FatPenguin fatPeng = new FatPenguin("fatOne", mesh, rootNode, fatPenguinMaterial, physicSpace);
		return fatPeng;
	}
	
	/**
	 * The create method to make shiny penguins.
	 * @return a shinypenguin object
	 */
	public ShinyPenguin makeShinyPenguin() {
		final Mesh mesh = new Box(10, 10, 10);
		final ShinyPenguin shinyPeng = new ShinyPenguin("ShinyPenguinie", mesh, rootNode, shinyPenguinMaterial, physicSpace);
		return shinyPeng;
	}
	
	/**
	 * The create method to make standard penguins.
	 * @return a standard penguin object
	 */
	public StandardPenguin makeStandardPenguin() {
		final Mesh mesh = new Sphere(SAMPLES, SAMPLES, DEFAULT_RADIUS);
		final StandardPenguin standardPenguin = new StandardPenguin("standardPenguin", mesh, rootNode, standardPenguinMaterial, physicSpace, DEFAULT_RADIUS);
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
	
	/**
	 * Sets the material for the standard penguin.
	 * @param mat the material
	 */
	public void setStandardPenguinMaterial(Material mat){
		standardPenguinMaterial = mat;
	}
	
	/**
	 * Sets the material for the fap penguin.
	 * @param mat the material
	 */
	public void setFatPenguinMaterial(Material mat){
		fatPenguinMaterial = mat;
	}
	
	/**
	 * Sets the material for the shiny penguin.
	 * @param mat the material
	 */
	public void setShinyPenguinMaterial(Material mat){
		shinyPenguinMaterial = mat;
	}
	
}
