package com.funkydonkies.factories;

import com.funkydonkies.controllers.KrillControl;
import com.funkydonkies.interfaces.FactoryInterface;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppStateManager;
import com.jme3.bullet.collision.shapes.BoxCollisionShape;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;

/**
 * This class represent the factory for the krill.
 * 
 * @author SDumasy
 *
 */
public class KrillFactory implements FactoryInterface {

	public static final String KRILL_NAME = "krill";

	private static final float KRILL_WIDTH = 10;
	private static final float KRILL_HEIGHT = 10;
	private static final float KRILL_DEPTH = 5;

	private static final String MATERIAL_PATH = "Common/MatDefs/Misc/Unshaded.j3md";

	public static final String MODEL_PATH = "Models/SQUID.j3o";

	private AppStateManager stateManager;
	private SimpleApplication app;

	@Override
	public Spatial makeObject(final AppStateManager sManager, final SimpleApplication appl) {
		stateManager = sManager;
		app = appl;

		final Spatial krill = makeKrill();
		return krill;
	}

	/**
	 * Instantiates krill spatial and adds its Control(s).
	 * 
	 * @return newly created Krill geometry
	 */
	public Spatial makeKrill() {
		final Spatial krill = makeSpatial();
		krill.setMaterial(getkrillMaterial());
		final KrillControl tarCont = new KrillControl(new BoxCollisionShape(new Vector3f(
				KRILL_WIDTH, KRILL_HEIGHT, KRILL_DEPTH)), stateManager);
		krill.addControl(tarCont);

		return krill;
	}

	/**
	 * This method makes a spatial.
	 * 
	 * @return a krill geometry
	 */
	public Spatial makeSpatial() {
		final Spatial krill = app.getAssetManager().loadModel(MODEL_PATH);
		krill.setName(KRILL_NAME);
		return krill;
	}

	/**
	 * This method makes all the required materials.
	 * 
	 * @return loaded material
	 */
	public Material getkrillMaterial() {
		final Material mat = new Material(app.getAssetManager(), MATERIAL_PATH);
		mat.setColor("Color", ColorRGBA.Yellow);
		return mat;
	}
}
