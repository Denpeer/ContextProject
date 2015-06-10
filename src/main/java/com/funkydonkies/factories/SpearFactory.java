package com.funkydonkies.factories;

import java.util.Random;

import com.funkydonkies.controllers.SpearControl;
import com.funkydonkies.controllers.WarningLineControl;
import com.funkydonkies.interfaces.FactoryInterface;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.bullet.collision.shapes.BoxCollisionShape;
import com.jme3.bullet.collision.shapes.CollisionShape;
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

/**
 * This class represent the factory for the target.
 */
public class SpearFactory implements FactoryInterface {
	
	private static final float SPEAR_WIDTH = 15;
	private static final float SPEAR_HEIGHT = 2;
	private static final float SPEAR_DEPTH = 1;
	
	public static final String SPEAR_NAME = "spear";
	
	private AppStateManager stateManager;
	private SimpleApplication app;
	
	/**
	 * The create method for a spear object.
	 * @param sManager jme AppStateManager for getting states
	 * @param appl jme SimpleApplication for getting rootNode or physicsSpace
	 * @return a spear object
	 */
	public Spatial makeObject(final AppStateManager sManager, final SimpleApplication appl) {
		app = appl;
		stateManager = sManager;
		
		final Random rand = new Random();
		final float xCoord = 500;
		final float yCoord = rand.nextInt(100);
		
		final Geometry line = makeWarningLine(yCoord); 
		final Geometry spear = makeSpear(yCoord, xCoord);
			
		final Node obstacleNode = new Node();
		obstacleNode.attachChild(spear);
		obstacleNode.attachChild(line);
		
		return obstacleNode;
	}
	
	/**
	 * This method make and returns a spear geometry.
	 * @param yCoord the initial yCoord of the spear
	 * @param xCoord the initial xCoord of the spear
	 * @return a spear geometry
	 */
	public Geometry makeSpear(final float yCoord, final float xCoord) {
		
		final Mesh spearMesh = new Box(SPEAR_WIDTH, SPEAR_HEIGHT, SPEAR_DEPTH);
		final Geometry spear = new Geometry(SPEAR_NAME, spearMesh);
		spear.setMaterial(getSpearMaterial(app.getAssetManager()));
		
		final Vector3f loci = new Vector3f(xCoord, yCoord, 0);
		final CollisionShape colShape = 
				new BoxCollisionShape(new Vector3f(SPEAR_WIDTH, SPEAR_HEIGHT, SPEAR_DEPTH));
		final SpearControl spearControl = new SpearControl(colShape, 6, stateManager, loci);
		spear.addControl(spearControl);
		
		return spear;
	}
	/**
	 * This method a warning line geometry.
	 * @param yCoord the y coordinate of the warning line
	 * @return the warning line geometry
	 */
	public Geometry makeWarningLine(final float yCoord) {
		final Mesh warningLineMesh = new Box(1000, 2, 1);
		final Geometry geom = new Geometry("warning line", warningLineMesh);
		geom.setMaterial(getLineMaterial(app.getAssetManager()));
		geom.setQueueBucket(Bucket.Transparent);
		
		final WarningLineControl wLC = new WarningLineControl(0, yCoord);
		geom.addControl(wLC);
		wLC.init();
		return geom;
	}

	/**
	 * This method gets the material for the warningline.
	 * @param assetManager jme AssetManager for loading models
	 * @return the line material
	 */
	public Material getLineMaterial(final AssetManager assetManager) {
		final Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
		mat.setColor("Color", new ColorRGBA(1, 0, 0, (float) 0.2));
		mat.getAdditionalRenderState().setBlendMode(BlendMode.Alpha);
		return mat;
	}

	/**
	 * This method gets the material for the spear.
	 * @param assetManager jme AssetManager for loading models
	 * @return the spear material
	 */
	public Material getSpearMaterial(AssetManager assetManager) {
		Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
		mat.setColor("Color", ColorRGBA.Green);
		return mat;
	}

}
