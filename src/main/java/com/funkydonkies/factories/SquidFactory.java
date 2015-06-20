package com.funkydonkies.factories;

import com.funkydonkies.controllers.SquidControl;
import com.funkydonkies.interfaces.FactoryInterface;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppStateManager;
import com.jme3.bullet.collision.shapes.BoxCollisionShape;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;

/**
 * This class represent the factory for the target.
 * 
 * @author SDumasy
 *
 */
public class SquidFactory implements FactoryInterface {

	private static final float SQUID_WIDTH = 12;
	private static final float SQUID_HEIGHT = 12;
	private static final float SQUID_DEPTH = 5;
	public static final String SQUID_NAME = "squid";

	private static final String MATERIAL_PATH = "Common/MatDefs/Misc/Unshaded.j3md";
	private static final String MODEL_PATH = "Models/KRILL.j3o";

	private AppStateManager stateManager;
	private SimpleApplication app;

	/**
	 * The create method for a squid object.
	 * 
	 * @param sManager
	 *            jme AppStateManager for getting states
	 * @param appl
	 *            jme SimpleApplication for getting rootNode or physicsSpace
	 * @return a squid object
	 */
	public Spatial makeObject(final AppStateManager sManager, final SimpleApplication appl) {
		stateManager = sManager;
		app = appl;

		final Spatial squid = makeSquid();
		return squid;
	}

	/**
	 * Makes a new squid geometry and sets its material and control(s).
	 * 
	 * @return new squid geometry instance
	 */
	public Spatial makeSquid() {
		final Spatial squid = makeSpatial();
		squid.setMaterial(getSquidMaterial());
		final CollisionShape colShape = new BoxCollisionShape(new Vector3f(SQUID_WIDTH,
				SQUID_HEIGHT, SQUID_DEPTH));

		final SquidControl squidControl = new SquidControl(colShape, stateManager);
		squid.addControl(squidControl);

		return squid;
	}

	/**
	 * This method makes all the required materials.
	 * 
	 * @return a Material object
	 */
	public Material getSquidMaterial() {
		final Material mat = new Material(app.getAssetManager(), MATERIAL_PATH);
		mat.setColor("Color", ColorRGBA.Magenta);
		return mat;
	}

	/**
	 * This method makes a geometry.
	 * 
	 * @return a krill geometry
	 */
	public Spatial makeSpatial() {
		final Spatial squid = app.getAssetManager().loadModel(MODEL_PATH);
		squid.setName(SQUID_NAME);
		return squid;
	}
}
