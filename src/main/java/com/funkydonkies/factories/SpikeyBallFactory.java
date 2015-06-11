package com.funkydonkies.factories;

import com.funkydonkies.controllers.SpikeyBallControl;
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
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Sphere;

/**
 * This class represent the factory for the target.
 */
public class SpikeyBallFactory implements FactoryInterface {
	private static final int SAMPLES = 20;
	private static final float DEFAULT_RADIUS = 10f;
	public static final String SPIKEYBALL_NAME = "spikeyball";

	/**
	 * The create method for a spikey ball object.
	 * @param sManager jme AppStateManager for getting states
	 * @param appl jme SimpleApplication for getting rootNode or physicsSpace
	 * @return a spikey ball object
	 */
	public Spatial makeObject(final AppStateManager sManager,
			final SimpleApplication appl) {
		final Mesh mesh = new Sphere(SAMPLES, SAMPLES, DEFAULT_RADIUS);
		final Geometry spikeyBall = new Geometry("spikeyBall", mesh);
		spikeyBall.setMaterial(getSpikeyBallMaterial(appl.getAssetManager()));
		final SpikeyBallControl pc = new SpikeyBallControl(
				new SphereCollisionShape(DEFAULT_RADIUS), sManager, 10f);
		spikeyBall.addControl(pc);
		pc.setRestitution(1);
		return spikeyBall;
	}

	/**
	 * This method gets the material for the spikey ball.
	 * @param assetManager jme AssetManager for loading models
	 * @return the spikey ball material
	 */
	public Material getSpikeyBallMaterial(final AssetManager assetManager) {
		final Material mat = new Material(assetManager,
				"Common/MatDefs/Misc/Unshaded.j3md");
		mat.setColor("Color", ColorRGBA.Gray);
		return mat;
	}
}
