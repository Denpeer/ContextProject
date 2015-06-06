package com.funkydonkies.factories;

import com.funkydonkies.controllers.KillerWhaleControl;
import com.funkydonkies.gamestates.PlayState;
import com.funkydonkies.geometrys.obstacles.KillerWhale;
import com.funkydonkies.geometrys.obstacles.PolarBear;
import com.funkydonkies.geometrys.obstacles.SeaLion;
import com.jme3.app.state.AppStateManager;
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
	private AppStateManager asm;
	
	/**
	 * The constructor for the obstacleFactory.
	 * @param assetM the AssetManager of the application
	 * @param rootN the main rootNode
	 * @param phy the main physicsLocation
	 */
	public ObstacleFactory(final AppStateManager sm) {
		this.rootNode = sm.getState(PlayState.class).getRootNode();
		this.assetManager = sm.getApplication().getAssetManager();	
		this.physicSpace = sm.getState(PlayState.class).getPhysicsSpace();
		asm = sm;
		makeMaterials();
	}
	
	/**
	 * The createMethod for the killerWhale.
	 * @return a killerWhale Object
	 */
	public KillerWhale makeKillerWhale() {
		final Mesh mesh = new Box(30, 6, 1);
		final KillerWhale kWhale = new KillerWhale("killerWhale", mesh, rootNode, killerWhaleMaterial);
		final KillerWhaleControl cSMS = new KillerWhaleControl(2.0f, 0.10, true, false, asm);
		kWhale.addControl(cSMS);
		physicSpace.add(cSMS);
		cSMS.init();	
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
		killerWhaleMaterial.setColor(color, ColorRGBA.Red);
		
		polarBearMaterial = new Material(assetManager, path);
		polarBearMaterial.setColor(color, ColorRGBA.Orange);
		
		seaLionMaterial = new Material(assetManager, path);
		seaLionMaterial.setColor(color, ColorRGBA.Cyan);
	}
}
