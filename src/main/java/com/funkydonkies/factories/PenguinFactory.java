package com.funkydonkies.factories;

import com.funkydonkies.controllers.PenguinControl;
import com.funkydonkies.core.App;
import com.funkydonkies.gamestates.PlayState;
import com.funkydonkies.interfaces.FactoryInterface;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.bullet.collision.shapes.SphereCollisionShape;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Sphere;

/**
 * This class represent the factory for the target.
 */
public class PenguinFactory implements FactoryInterface {
	private static final int SAMPLES = 20;
	public static final float DEFAULT_RADIUS = 4f;
	public static final String STANDARD_PENGUIN_NAME = "penguin";

	private AppStateManager stateManager;
	private SimpleApplication app;

	/**
	 * The create method for a penguin object.
	 * 
	 * @param sManager
	 *            jme AppStateManager for getting states
	 * @param appl
	 *            jme SimpleApplication for getting rootNode or physicsSpace
	 * @return a penguin object
	 */
	public Spatial makeObject(final AppStateManager sManager, final SimpleApplication appl) {
		stateManager = sManager;
		app = appl;

		final Node node = makeNode();
		final Geometry penguin = makePenguin();
		
		node.attachChild(penguin);
		
		return node;
	}

	public Node makeNode() {
		final Node node = new Node(STANDARD_PENGUIN_NAME);
		
		final PenguinControl penguinControl = new PenguinControl(new SphereCollisionShape(
				DEFAULT_RADIUS), 1f);
		penguinControl.setRestitution(1);
		stateManager.getState(PlayState.class).getPhysicsSpace().add(penguinControl);
		
		node.addControl(penguinControl);
		
		return node;
	}

	/** 
	 * 
	 * @return newly created Penguin 
	 */
	public Geometry makePenguin() {
		final Mesh mesh = new Sphere(SAMPLES, SAMPLES, DEFAULT_RADIUS);
		final Geometry geom = new Geometry(STANDARD_PENGUIN_NAME, mesh);
		geom.setMaterial(getPenguinMaterial());

		return geom;
	}

	/**
	 * This method gets the material for the penguin.
	 * 
	 * @return the penguin material
	 */
	public Material getPenguinMaterial() {
		Material mat = new Material(app.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
		mat.setColor("Color", ColorRGBA.Orange);
		return mat;

	}

	/**
	 * This method gets the material for the snowball.
	 * 
	 * @param assetManager
	 *            jme AssetManager for loading models
	 * @return the snowball material
	 */
	public Material getSnowballMaterial(final AssetManager assetManager) {
		Material snowBallMaterial;
		snowBallMaterial = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
		snowBallMaterial.setColor("Color", ColorRGBA.White);
		return snowBallMaterial;
	}
}
