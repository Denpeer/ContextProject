package com.funkydonkies.factories;

import com.funkydonkies.controllers.YetiControl;
import com.funkydonkies.interfaces.FactoryInterface;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppStateManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.shape.Sphere;

/**
 * This class is the factory for the yeti and its snow ball.
 * 
 * @author Olivier Dikken
 *
 */
public class YetiFactory implements FactoryInterface {
	private static final int YETI_WIDTH = 40;
	private static final int YETI_HEIGHT = 40;
	private static final int YETI_DEPTH = 20;
	public static final String YETI_NAME = "yeti_snowball";

	private AppStateManager stateManager;
	private SimpleApplication app;

	/**
	 * The create method for a yeti object.
	 * 
	 * @param sManager
	 *            jme AppStateManager for getting states
	 * @param appl
	 *            jme SimpleApplication for getting rootNode or physicsSpace
	 * @return a fish object
	 */
	public Geometry makeObject(final AppStateManager sManager, final SimpleApplication appl) {
		stateManager = sManager;
		app = appl;

		final Geometry yeti = makeYetiSnowBall();

		return yeti;
	}

	/**
	 * Makes a new yeti snow ball.
	 * 
	 * @return new yeti snow ball instance
	 */
	public Geometry makeYetiSnowBall() {
		final Sphere mesh = new Sphere(YETI_WIDTH, YETI_HEIGHT, YETI_DEPTH);
		final Geometry geom = makeGeometry(mesh);
		geom.setMaterial(getYetiSnowBallMaterial());

		final YetiControl control = new YetiControl(stateManager);
		geom.addControl(control);
		
		return geom;
	}

	/**
	 * This method makes all the required materials.
	 * 
	 * @return a Material object
	 */
	public Material getYetiSnowBallMaterial() {
		Material snowMaterial;
		snowMaterial = ((Material) app.getRootNode().getUserData("default material")).clone();
		snowMaterial.setColor("Color", ColorRGBA.White);
		return snowMaterial;
	}
	
	/**
	 * This method makes a geometry.
	 * @param mesh the mesh of the krill
	 * @return a krill geometry
	 */
	public Geometry makeGeometry(final Mesh mesh) {
		return new Geometry(YETI_NAME, mesh);
	}

}
