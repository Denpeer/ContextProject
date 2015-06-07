package com.funkydonkies.factories;

import java.util.Random;

import com.funkydonkies.controllers.KillerWhaleControl;
import com.funkydonkies.controllers.SpearControl;
import com.funkydonkies.controllers.WarningLineControl;
import com.funkydonkies.gamestates.PlayState;
import com.funkydonkies.geometries.WarningLine;
import com.funkydonkies.geometrys.obstacles.KillerWhale;
import com.funkydonkies.geometrys.obstacles.PolarBear;
import com.funkydonkies.geometrys.obstacles.Spear;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.material.Material;
import com.jme3.material.RenderState.BlendMode;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
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
	private Material spearMaterial;
	private Material warningLineMaterial;
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
	public Spear makeSpear() {
		final Mesh mesh = new Box(15, 2, 1);
		final Mesh mesh2 = new Box(1000, 2, 1);
		final Spear spear = new Spear("spear", mesh, rootNode, spearMaterial);
		Random rand = new Random();
		final float xCoord = 500;
		final float yCoord = rand.nextInt(100);
		final WarningLine line = new WarningLine(rootNode, warningLineMaterial, "wLine", 
				mesh2);
		WarningLineControl wLC = new WarningLineControl(rootNode, 0, yCoord);
		line.addControl(wLC);
		wLC.init();
		
		final Vector3f loci = new Vector3f(xCoord, yCoord, 0);
		final SpearControl cSMS = new SpearControl(1f, 6, asm, loci);
		spear.addControl(cSMS);
		physicSpace.add(cSMS);
		cSMS.init();	
		return spear;
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
	public KillerWhale makeKillerWhale() {
		final Mesh mesh = new Box(50, 200, 1);
		final KillerWhale kWhale = new KillerWhale("killerWhale", mesh, rootNode, killerWhaleMaterial, physicSpace);
		final KillerWhaleControl cSMS = new KillerWhaleControl(1f, 1, asm);
		kWhale.addControl(cSMS);
		physicSpace.add(cSMS);
		cSMS.init();
		return kWhale;
	}
	
	
	/**
	 * This method makes all the required materials.
	 */
	public void makeMaterials() {
		final String color = "Color";
		final String path = "Common/MatDefs/Misc/Unshaded.j3md";
		killerWhaleMaterial = new Material(assetManager, path);
		killerWhaleMaterial.setColor(color, ColorRGBA.Orange);
		
		polarBearMaterial = new Material(assetManager, path);
		polarBearMaterial.setColor(color, ColorRGBA.Orange);
		
		spearMaterial = new Material(assetManager, path);
		spearMaterial.setColor(color, ColorRGBA.Yellow);

		warningLineMaterial = new Material(assetManager, path);
		warningLineMaterial.setColor(color, new ColorRGBA(1, 0, 0, (float)0.4));
		warningLineMaterial.getAdditionalRenderState().setBlendMode(BlendMode.Alpha);
	}
}
