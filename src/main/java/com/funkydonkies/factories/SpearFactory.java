package com.funkydonkies.factories;

import java.util.Random;

import com.funkydonkies.controllers.SpearControl;
import com.funkydonkies.controllers.WarningLineControl;
import com.funkydonkies.gamestates.PlayState;
import com.funkydonkies.interfaces.ObstacleFactoryInterface;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;

public class SpearFactory implements ObstacleFactoryInterface {
	
	private Material defMaterial;
	
	/** This method does not attach the spatial to the rootNode. Initializes Spear obstacles.
	 * @see com.funkydonkies.interfaces.ObstacleFactoryInterface
	 * #makeObst(com.jme3.asset.AssetManager)
	 */
	public Spatial makeObst(final AppStateManager sManager, final SimpleApplication app) {
		defMaterial = app.getRootNode().getUserData("default material");
		
		final Mesh spearMesh = new Box(15, 2, 1);
		final Mesh warningLineMesh = new Box(1000, 2, 1);
		final Geometry spear = new Geometry("spear", spearMesh);
		spear.setMaterial(getSpearMaterial(app.getAssetManager()));
		
		final Random rand = new Random();
		final float xCoord = 500;
		final float yCoord = rand.nextInt(100);
		
		Geometry line = new Geometry("warning line", warningLineMesh);
		line.setMaterial(getLineMaterial(app.getAssetManager()));
		
		WarningLineControl wLC = new WarningLineControl(app.getRootNode(), 0, yCoord);
		line.addControl(wLC);
		wLC.init();
		
		final Vector3f loci = new Vector3f(xCoord, yCoord, 0);
		final SpearControl spearControl = new SpearControl(1f, 6, sManager, loci);
		spear.addControl(spearControl);
		spearControl.init();
		
		Node obstacleNode = new Node();
		obstacleNode.attachChild(spear);
		obstacleNode.attachChild(line);
		
		sManager.getState(PlayState.class).getPhysicsSpace().add(spearControl);
		
		return obstacleNode;
	}

	public Material getLineMaterial(final AssetManager assetManager) {
		Material mat = defMaterial.clone();
		mat.setColor("Color", ColorRGBA.Yellow);
		return mat;
	}

	public Material getSpearMaterial(final AssetManager assetManager) {
		Material mat = defMaterial.clone();
		mat.setColor("Color", ColorRGBA.Red);
		return mat;
	}

}
