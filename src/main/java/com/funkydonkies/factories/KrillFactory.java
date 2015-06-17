package com.funkydonkies.factories;

import com.funkydonkies.controllers.KrillControl;
import com.funkydonkies.interfaces.FactoryInterface;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppStateManager;
import com.jme3.bullet.collision.shapes.BoxCollisionShape;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.shape.Box;

/**
 * This class represent the factory for the target.
 * 
 * @author SDumasy
 *
 */
public class KrillFactory implements FactoryInterface {

	public static final String KRILL_NAME = "krill";

	private static final float KRILL_WIDTH = 3;
	private static final float KRILL_HEIGHT = 3;
	private static final float KRILL_DEPTH = 5;
	
	private static final String MATERIAL_PATH = "Common/MatDefs/Misc/Unshaded.j3md"; 

	private AppStateManager stateManager;
	private SimpleApplication app;

	@Override
	public Geometry makeObject(final AppStateManager sManager, final SimpleApplication appl) {
		stateManager = sManager;
		app = appl;

		final Geometry krill = makeKrill();

		return krill;
	}

	/**
	 * Instantiates krill geometry and adds its Control(s).
	 * 
	 * @return newly created Krill geometry
	 */
	public Geometry makeKrill() {
		final Mesh mesh = new Box(KRILL_WIDTH, KRILL_HEIGHT, KRILL_DEPTH);
		final Geometry geom = makeGeometry(mesh);
		geom.setMaterial(getkrillMaterial());

		final KrillControl tarCont = new KrillControl(new BoxCollisionShape(new Vector3f(
				KRILL_WIDTH, KRILL_HEIGHT, KRILL_DEPTH)), stateManager);
		geom.addControl(tarCont);

		return geom;
	}
	
	/**
	 * This method makes a geometry.
	 * @param mesh the mesh of the krill
	 * @return a krill geometry
	 */
	public Geometry makeGeometry(final Mesh mesh) {
		return new Geometry(KRILL_NAME, mesh);
	}


	/**
	 * This method makes all the required materials.
	 * 
	 * @return loaded material
	 */
	public Material getkrillMaterial() {
		final Material mat = new Material(app.getAssetManager(), MATERIAL_PATH);
		mat.setColor("Color", ColorRGBA.Magenta);
		return mat;
	}
}
