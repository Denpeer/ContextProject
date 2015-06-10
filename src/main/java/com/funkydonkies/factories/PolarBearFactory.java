package com.funkydonkies.factories;

import java.util.Random;

import com.funkydonkies.controllers.PolarBearControl;
import com.funkydonkies.gamestates.PlayState;
import com.funkydonkies.interfaces.FactoryInterface;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.bullet.collision.shapes.BoxCollisionShape;
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
	private final float startRightXCoord = 500;
	private final float startLeftXCoord = -200;
	private final int maxFloatHeight = 20;
	private final int maxStopCoordinate = 280;
	public static final String POLARBEAR_NAME = "polar bear";

		
	/**
	 * The create method for a polar bear object.
	 * @param sManager jme AppStateManager for getting states
	 * @param appl jme SimpleApplication for getting rootNode or physicsSpace
	 * @return a polar bear object
	 */
	public Spatial makeObject(final AppStateManager sManager, final SimpleApplication appl) {	
		final Random rand = new Random();
		final float xCoord = getFloatSide(rand);
		final float yCoord = getFloatHeight(rand);
		final float stopX = getStopCoord(rand);
		final float bearWidth = 12;
		final float bearHeight = 20;
		final Mesh polarBearMesh = new Box(bearWidth, bearHeight, 1);

		final Geometry polarBear = new Geometry(POLARBEAR_NAME, polarBearMesh);
		polarBear.setMaterial(getPolarBearMaterial(appl.getAssetManager()));
		final Vector3f loci = new Vector3f(xCoord, yCoord, 0);
		final PolarBearControl polarBearControl = new PolarBearControl(
				new BoxCollisionShape(new Vector3f(bearWidth, bearHeight, 1)), stopX, 1, sManager, loci);
		polarBear.addControl(polarBearControl);
		sManager.getState(PlayState.class).getPhysicsSpace().add(polarBearControl);
		return polarBear;
	}
	
	/**
	 * This method get the stop coordinate op the polear bear.
	 * @param rand a random object
	 * @return  the stop coordinate
	 */
	public final float getStopCoord(final Random rand) {
		return rand.nextInt(maxStopCoordinate);
	}
	
	/**
	 * This method returns the float height of the polar bear.
	 * @param rand a random object.
	 * @return the float height of the bear
	 */
	public float getFloatHeight(final Random rand) {	
		return rand.nextInt(maxFloatHeight);
	}
	
	/**
	 * This method decides fro which side the polar bear floats in.
	 * @param rand a random object.
	 * @return the side from which the bear floats in
	 */
	public float getFloatSide(final Random rand) {
		if (rand.nextInt(2) == 1) {
			return startRightXCoord;
		} else {
			return startLeftXCoord;
		}
	}
	/**
	 * This method gets the polar bear material.
	 * @param assetManager jme AssetManager for loading models
	 * @return the polar bear material
	 */
	public Material getPolarBearMaterial(AssetManager assetManager) {
		Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
		mat.setColor("Color", ColorRGBA.Green);
		return mat;
	}

}
