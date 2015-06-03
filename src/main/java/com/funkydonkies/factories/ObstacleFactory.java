package com.funkydonkies.factories;

import com.funkydonkies.geometrys.obstacles.KillerWhale;
import com.funkydonkies.geometrys.obstacles.PolarBear;
import com.funkydonkies.geometrys.obstacles.SeaLion;
import com.jme3.asset.AssetManager;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;

/**
 * This is the factory for obstacles.
 * @author SDumasy
 *
 */
public class ObstacleFactory {
	
	private AssetManager assetManager;
	private Node rootNode;
	private PhysicsSpace physicSpace;
	
	private Material killerWhaleMaterial;
	private Material polarBearMaterial;
	private Material seaLionMaterial;
	
	/**
	 * The constructor for the obstacleFactory.
	 * @param assetM the AssetManager of the application
	 * @param rootN the main rootNode
	 * @param phy the main physicsLocation
	 */
	public ObstacleFactory(final AssetManager assetM, final Node rootN, final PhysicsSpace phy) {
		this.rootNode = rootN;
		this.assetManager = assetM;	
		this.physicSpace = phy;
		makeMaterials();
	}
	
	/**
	 * The createMethod for the killerWhale.
	 * @return a killerWhale Object
	 */
	public KillerWhale makeKillerWhale() {
		final Mesh mesh = new Box(1, 1, 1);
		final KillerWhale kWhale = new KillerWhale("fishie", mesh, rootNode, killerWhaleMaterial, physicSpace);
		return kWhale;
	}
	
	/**
	 * The createMethod for the polar bear.
	 * @return a killerWhale Object
	 */
	public PolarBear makePolarBear() {
		final Mesh mesh = new Box(1, 1, 1);
		final PolarBear pBear = new PolarBear("polarBear", mesh, rootNode, polarBearMaterial, physicSpace);
		return pBear;
	}
	/**
	 * The createMethod for the seaLion.
	 * @return a killerWhale Object
	 */
	public SeaLion makeSeaLion() {
		final Mesh mesh = new Box(1, 1, 1);
		final SeaLion sLion = new SeaLion("seaLion", mesh, rootNode, seaLionMaterial, physicSpace);
		return sLion;
	}
	
	
	/**
	 * This method makes all the required materials.
	 */
	public void makeMaterials() {
		final String color = "Color";
		final String path = "Common/MatDefs/Misc/Unshaded.j3md";
		killerWhaleMaterial = new Material(assetManager,
				path);
		killerWhaleMaterial.setColor(color, ColorRGBA.Blue);
		
		polarBearMaterial = new Material(assetManager, path);
		polarBearMaterial.setColor(color, ColorRGBA.Orange);
		
		seaLionMaterial = new Material(assetManager, path);
		seaLionMaterial.setColor(color, ColorRGBA.Cyan);
	}
}