package com.funkydonkies.factories;

import java.util.Random;

import com.funkydonkies.controllers.PolarBearControl;
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
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;

/**
 * This class represent the factory for the target.
 */
public class PolarBearFactory implements FactoryInterface {

	private static final float BEAR_WIDTH = 12;
	private static final float BEAR_HEIGHT = 20;
	private static final float BEAR_DEPTH = 20;

	private static final float START_RIGHT = 500;
	private static final float START_LEFT = -200;
	private static final int MAX_HEIGHT = 20;
	private static final int MAX_STOP = 280;

	public static final String COLOR = "Color";
	public static final String POLAR_BEAR_NAME = "polar bear";
	public static final String UNSHADED_MATERIAL_PATH = "Common/MatDefs/Misc/Unshaded.j3md";

	private AppStateManager stateManager;
	private SimpleApplication app;

	/**
	 * The create method for a polar bear object.
	 * 
	 * @param sManager
	 *            jme AppStateManager for getting states
	 * @param appl
	 *            jme SimpleApplication for getting rootNode or physicsSpace
	 * @return a polar bear object
	 */
	public Spatial makeObject(final AppStateManager sManager, final SimpleApplication appl) {
		stateManager = sManager;
		app = appl;

		final Geometry polarBear = makePolarBear();

		return polarBear;
	}

	/**
	 * Instantiates the polar bear's geometry and adds its Control(s).
	 * 
	 * @return newly created Polar bear geometry
	 */
	public Geometry makePolarBear() {
		final Mesh mesh = new Box(BEAR_WIDTH, BEAR_HEIGHT, BEAR_DEPTH);
		final Geometry geom = new Geometry(POLAR_BEAR_NAME, mesh);
		geom.setMaterial(getPolarBearMaterial());

		final float x = getFloatSide(new Random());
		final float y = getFloatHeight(new Random());
		final Vector3f initialLoc = new Vector3f(x, y, 0);

		final float stopX = getStopCoord(new Random());

		final CollisionShape colShape = new BoxCollisionShape(new Vector3f(BEAR_WIDTH, BEAR_HEIGHT,
				BEAR_DEPTH));

		final PolarBearControl polarBearControl = new PolarBearControl(colShape, stateManager,
				stopX, initialLoc);

		geom.addControl(polarBearControl);

		return geom;
	}

	/**
	 * This method get the stop coordinate op the polear bear.
	 * 
	 * @param rand
	 *            a random object
	 * @return the stop coordinate
	 */
	public final float getStopCoord(final Random rand) {
		return rand.nextInt(MAX_STOP);
	}

	/**
	 * This method returns the float height of the polar bear.
	 * 
	 * @param rand
	 *            a random object.
	 * @return the float height of the bear
	 */
	public float getFloatHeight(final Random rand) {
		return rand.nextInt(MAX_HEIGHT);
	}

	/**
	 * This method decides fro which side the polar bear floats in.
	 * 
	 * @param rand
	 *            a random object.
	 * @return the side from which the bear floats in
	 */
	public float getFloatSide(final Random rand) {
		if (rand.nextInt(2) == 1) {
			return START_RIGHT;
		} else {
			return START_LEFT;
		}
	}

	/**
	 * This method gets the polar bear material.
	 * 
	 * @return the polar bear material
	 */
	public Material getPolarBearMaterial() {
		final Material mat = new Material(app.getAssetManager(), UNSHADED_MATERIAL_PATH);
		mat.setColor(COLOR, ColorRGBA.Green);
		return mat;
	}

}
