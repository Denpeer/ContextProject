package com.funkydonkies.factories;

import java.util.Random;

import com.funkydonkies.controllers.PolarBearControl;
import com.funkydonkies.interfaces.FactoryInterface;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppStateManager;
import com.jme3.bullet.collision.shapes.BoxCollisionShape;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;

/**
 * This class represent the factory for the polar bear.
 */
public class PolarBearFactory implements FactoryInterface {

	private static final float BEAR_WIDTH = 9;
	private static final float BEAR_HEIGHT = 25;
	private static final float BEAR_DEPTH = 18;

	private static final float START_RIGHT = 500;
	private static final float START_LEFT = -200;
	private static final int INITIAL_HEIGHT = -26;
	private static final int MAX_STOP = 210;
	private static final int MINIMAL_X = 70;

	public static final String COLOR = "Color";
	public static final String POLAR_BEAR_NAME = "polar bear";
	public static final String MATERIAL_PATH = "Common/MatDefs/Misc/Unshaded.j3md";
	private static final String MODEL_PATH = "Models/POLARBEAR.j3o";

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

		final Spatial polarBear = makePolarBear();

		return polarBear;
	}

	/**
	 * Instantiates the polar bear's geometry and adds its Control(s).
	 * 
	 * @return newly created Polar bear geometry
	 */
	public Spatial makePolarBear() {
		final Spatial geom = makeSpatial();
		geom.addControl(makePolarBearControl());

		return geom;
	}

	/**
	 * This method makes a polar bear control.
	 * 
	 * @return a polar bear control
	 */
	public PolarBearControl makePolarBearControl() {
		final Random rand = new Random();
		final int leftRight = rand.nextInt(2);
		final float x = getFloatSide(leftRight);
		final CollisionShape colShape = new BoxCollisionShape(new Vector3f(BEAR_WIDTH, BEAR_HEIGHT,
				BEAR_DEPTH));
		final Vector3f initialLoc = new Vector3f(x, INITIAL_HEIGHT, 0);

		final float stopX = getStopCoord(new Random());
		return new PolarBearControl(colShape, stateManager, stopX, initialLoc);
	}

	/**
	 * This method makes a geometry.
	 * 
	 * @return a polar bear geometry
	 */
	public Spatial makeSpatial() {
		final Spatial bear = app.getAssetManager().loadModel(MODEL_PATH);
		bear.setName(POLAR_BEAR_NAME);
		return bear;
	}

	/**
	 * This method get the stop coordinate op the polear bear.
	 * 
	 * @param rand
	 *            a random object
	 * @return the stop coordinate
	 */
	public final float getStopCoord(final Random rand) {
		return rand.nextInt(MAX_STOP) + MINIMAL_X;
	}

	/**
	 * This method decides fro which side the polar bear floats in.
	 * 
	 * @param i
	 *            the integer to check if the bear floats left or right.
	 * @return the side from which the bear floats in
	 */
	public float getFloatSide(final int i) {
		if (i == 1) {
			return START_RIGHT;
		} else {
			return START_LEFT;
		}
	}

}
