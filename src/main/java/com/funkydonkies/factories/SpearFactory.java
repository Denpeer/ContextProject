package com.funkydonkies.factories;

import java.util.Random;

import com.funkydonkies.controllers.SpearControl;
import com.funkydonkies.controllers.WarningLineControl;
import com.funkydonkies.interfaces.FactoryInterface;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppStateManager;
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
	
	private static final float WARNING_WIDTH = 1000;
	private static final float SPEAR_WIDTH = 45;
	private static final float SPEAR_HEIGHT = 1;
	private static final float SPEAR_DEPTH = 1;
	
	public static final String SPEAR_NAME = "spear";
	public static final String WARNING_NAME = "warning line";
	private static final String COLOR = "Color";
	private static final String MATERIAL_PATH = "Common/MatDefs/Misc/Unshaded.j3md";
	private static final String MODEL_PATH = "Models/SPEAR.j3o";
	private static final float WARNING_LINE_ALPHA = 0.2f;
	
	private static final float SPEAR_SCALE = 0.4f;
	private static final int SPEAR_X_TRANSLATION = 45;

	
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
		final float x = 500;
		final float y = rand.nextInt(100);
		
		final Geometry line = makeWarningLine(y); 
		final Spatial spear = makeSpear(y, x);
		final Node obstacleNode = new Node();
		obstacleNode.attachChild(spear);
		obstacleNode.attachChild(line);
		obstacleNode.setLocalTranslation(SPEAR_X_TRANSLATION, 0, 0);
		
		return obstacleNode;
	}
	
	/**
	 * This method make and returns a spear geometry.
	 * @param y the initial y of the spear
	 * @param x the initial x of the spear
	 * @return a spear geometry
	 */
	public Spatial makeSpear(final float y, final float x) {
		
		final Spatial spear = makeSpatial();
		spear.setMaterial(getSpearMaterial());

		spear.addControl(makeSpearControl(x, y));
		
		return spear;
	}
	/**
	 * This method makes the control for the spear.
	 * @param x the initial x coordinate of the spear
	 * @param y the initial y coordinate of the spear
	 * @return a spear control   
	 */
	public SpearControl makeSpearControl(final float x, final float y) {
		
		final Vector3f loci = new Vector3f(x, y, 0);
		final CollisionShape colShape = 
				new BoxCollisionShape(new Vector3f(SPEAR_HEIGHT, SPEAR_WIDTH, SPEAR_DEPTH));
		return new SpearControl(colShape, stateManager, loci);
	}
	/**
	 * This method a warning line geometry.
	 * @param y the y coordinate of the warning line
	 * @return the warning line geometry
	 */
	public Geometry makeWarningLine(final float y) {
		final Mesh warningLineMesh = new Box(WARNING_WIDTH, SPEAR_HEIGHT, SPEAR_DEPTH);
		final Geometry geom = new Geometry(WARNING_NAME, warningLineMesh);
		geom.setMaterial(getLineMaterial());
		geom.setQueueBucket(Bucket.Transparent);
		
		final WarningLineControl warningLineControl = new WarningLineControl(0, y);
		geom.addControl(warningLineControl);
		return geom;
	}

	/**
	 * This method gets the material for the warningline.
	 * @return the line material
	 */
	public Material getLineMaterial() {
		final Material mat = new Material(app.getAssetManager(), MATERIAL_PATH);
		mat.setColor(COLOR, new ColorRGBA(1, 0, 0, WARNING_LINE_ALPHA));
		mat.getAdditionalRenderState().setBlendMode(BlendMode.Alpha);
		return mat;
	}

	/**
	 * This method gets the material for the spear.
	 * @return the spear material
	 */
	public Material getSpearMaterial() {
		final Material mat = new Material(app.getAssetManager(), MATERIAL_PATH);
		mat.setColor(COLOR, ColorRGBA.Green);
		return mat;
	}

	/**
	 * This method makes a geometry.
	 * @param mesh the mesh of the spear
	 * @return a spear geometry
	 */
	public Spatial makeSpatial() {
		Spatial spear = app.getAssetManager().loadModel(MODEL_PATH);
		spear.setName(SPEAR_NAME);
		spear.scale(SPEAR_SCALE);
		return spear;
	}
}
