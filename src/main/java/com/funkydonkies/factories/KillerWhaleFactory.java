package com.funkydonkies.factories;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;

/**
 *	Factory that makes Whales. Instantiated by SpawnState.
 */
public class KillerWhaleFactory implements ObstacleFactoryInterface {
	
	private static final String MATERIAL_PATH = "Common/MatDefs/Light/Lighting.j3md";
	
	/** Makes new obstacle.
	 * @see com.funkydonkies.factories.ObstacleFactoryInterface
	 * 								#makeObst(com.jme3.asset.AssetManager)
	 * @param assetManager passed jMonkey assetManager
	 * @return new Obstacle
	 */
	public Spatial makeObst(final AssetManager assetManager) {
		final Mesh mesh = new Box(30, 6, 1);
		final Geometry geom = new Geometry("KillerWhale", mesh);
		geom.setMaterial(getMaterial(assetManager));
		return geom;
	}

	/** Init material.
	 * @param assetManager passed assetManager internally
	 * @return geom material
	 */
	private Material getMaterial(final AssetManager assetManager) {
		final Material mat = new Material(assetManager, MATERIAL_PATH);
		mat.setBoolean("UseMaterialColors", true);
		mat.setColor("Ambient", ColorRGBA.Blue);
		mat.setColor("Diffuse", ColorRGBA.Blue);
		return mat;
	}

}
