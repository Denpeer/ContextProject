package com.funkydonkies.factories;

import com.funkydonkies.controllers.PenguinControl;
import com.funkydonkies.interfaces.FactoryInterface;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.bullet.collision.shapes.SphereCollisionShape;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

/**
 * This class represent the factory for the target.
 */
public class PenguinFactory implements FactoryInterface {
	public static final String PENGUIN_NAME = "penguin";
	public static final float DEFAULT_RADIUS = 4f;

	private static final String COLOR = "Color";
	private static final String MATERIAL_PATH = "Common/MatDefs/Light/Lighting.j3md";
	private static final String MODEL_PATH = "Models/PENGUIN.j3o";

	// private static final String[] PenguinGeometry

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
		final Spatial penguin = makePenguin();

		node.attachChild(penguin);

		return node;
	}

	/**
	 * Instantiates a node for the penguin.
	 * 
	 * @return newly created penguin node
	 */
	public Node makeNode() {
		final Node node = makePengNode();

		final PenguinControl penguinControl = new PenguinControl(new SphereCollisionShape(
				DEFAULT_RADIUS), 1f, stateManager);
		penguinControl.setRestitution(1);

		node.addControl(penguinControl);

		return node;
	}

	/**
	 * 
	 * @return newly created Penguin
	 */
	public Spatial makePenguin() {
		final Spatial penguin = app.getAssetManager().loadModel(MODEL_PATH);
		penguin.setName(PENGUIN_NAME);
		return penguin;
	}

	/**
	 * This method makes a node.
	 * 
	 * @return a penguin node
	 */
	public Node makePengNode() {
		return new Node(PENGUIN_NAME);
	}

	/**
	 * This method gets the material for the snowball.
	 * 
	 * @param assetManager
	 *            jme AssetManager for loading models
	 * @return the snowball material
	 */
	public Material getSnowballMaterial(final AssetManager assetManager) {
		final Material snowBallMaterial = new Material(assetManager, MATERIAL_PATH);
		snowBallMaterial.setColor(COLOR, ColorRGBA.White);
		return snowBallMaterial;
	}
}
