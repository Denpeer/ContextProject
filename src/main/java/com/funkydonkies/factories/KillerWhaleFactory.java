package com.funkydonkies.factories;

import java.util.Random;

import com.funkydonkies.controllers.KillerWhaleControl;
import com.funkydonkies.controllers.WarningLineControl;
import com.funkydonkies.interfaces.FactoryInterface;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.material.RenderState.BlendMode;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue.Bucket;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;

public class KillerWhaleFactory implements FactoryInterface {
	

	
	/** This method does not attach the spatial to the rootNode. Initializes whale obstacles.
	 * @see com.funkydonkies.interfaces.FactoryInterface
	 * #makeObst(com.jme3.asset.AssetManager)
	 */
	public Spatial makeObject(final AppStateManager sManager, final SimpleApplication app) {	
		final Random rand = new Random();
		final float xCoord = rand.nextInt(320);
		final float yCoord = -100;
		final float whaleYCoord = -500;
		
		final Geometry line = makeWarningLine(app, xCoord); 
		final Geometry whale = makeKillerWhale(app, whaleYCoord, xCoord, sManager);

		Node obstacleNode = new Node();
		obstacleNode.attachChild(whale);
		obstacleNode.attachChild(line);
		
		return obstacleNode;
	}
	
	public Geometry makeKillerWhale(SimpleApplication app, float yCoord, float xCoord, AppStateManager sManager){
		final Mesh whaleMesh = new Box(50, 200, 1);
		Geometry whale = new Geometry("killerWhale", whaleMesh);
		whale.setMaterial(getKillerWhaleMaterial(app.getAssetManager()));
		
		final Vector3f loci = new Vector3f(xCoord, yCoord, 0);
		final KillerWhaleControl whaleControl = new KillerWhaleControl(1f, 6, sManager, loci);
		whale.addControl(whaleControl);
		whaleControl.init();
		
		return whale;
	}
	public Geometry makeWarningLine(SimpleApplication app, float xCoord){
		final Mesh warningLineMesh = new Box(50, 200, 1);
		Geometry geom = new Geometry("warning line", warningLineMesh);
		geom.setMaterial(getLineMaterial(app.getAssetManager()));
		geom.setQueueBucket(Bucket.Transparent);
		
		WarningLineControl wLC = new WarningLineControl(xCoord, 0);
		geom.addControl(wLC);
		wLC.init();
		return geom;
	}

	public Material getLineMaterial(AssetManager assetManager) {
		Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
		mat.setColor("Color", new ColorRGBA(1, 0, 0, (float)0.2));
		mat.getAdditionalRenderState().setBlendMode(BlendMode.Alpha);
		return mat;
	}

	public Material getKillerWhaleMaterial(AssetManager assetManager) {
		Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
		mat.setColor("Color", ColorRGBA.Green);
		return mat;
	}

}
