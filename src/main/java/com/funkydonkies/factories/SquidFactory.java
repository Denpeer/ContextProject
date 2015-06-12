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
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.shape.Box;

/**
 * This class represent the factory for the target.
 * 
 * @author SDumasy
 *
 */
public class SquidFactory implements FactoryInterface {

	private static final float SQUID_WIDTH = 7;
	private static final float SQUID_HEIGHT = 7;
	private static final float SQUID_DEPTH = 5;
	public static final String SQUID_NAME = "squid";

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
	public Geometry makeObject(final AppStateManager sManager, final SimpleApplication appl) {
		stateManager = sManager;
		app = appl;

		final Geometry squid = makeSquid();
		return squid;
	}

	/**
	 * Makes a new squid geometry and sets its material and control(s).
	 * 
	 * @return new squid geometry instance
	 */
	public Geometry makeSquid() {
		final Mesh mesh = new Box(SQUID_WIDTH, SQUID_HEIGHT, SQUID_DEPTH);
		final Geometry geom = new Geometry(SQUID_NAME, mesh);
		geom.setMaterial(getSquidMaterial());
		
		final CollisionShape colShape = new BoxCollisionShape(new Vector3f(SQUID_WIDTH,
				SQUID_HEIGHT, SQUID_DEPTH));
		
		final SquidControl squidControl = new SquidControl(colShape, stateManager);
		geom.addControl(squidControl);
		
		return geom;
	}

	/**
	 * This method makes all the required materials.
	 * 
	 * @param assetManager
	 *            jme AssetManager for loading models
	 * @return a Material object
	 */
	public Material getSquidMaterial() {
		final Material mat = new Material(app.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
		mat.setColor("Color", ColorRGBA.Yellow);
		return mat;
	}
}
