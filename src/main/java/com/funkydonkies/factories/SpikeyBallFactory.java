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
 * This class represent the factory for penguins.
 * 
 * @author SDumasy
 *
 */
public class SpikeyBallFactory implements FactoryInterface {
	private static final int SAMPLES = 20;
	private static final float DEFAULT_RADIUS = 10f;

	/**
	 * The create method to make standard penguins.
	 * 
	 * @param sManager
	 *            the stateManager
	 * @param app
	 *            the application
	 * @return a standard penguin object
	 */
	public Spatial makeObject(final AppStateManager sManager,
			final SimpleApplication app) {
		final Mesh mesh = new Sphere(SAMPLES, SAMPLES, DEFAULT_RADIUS);
		final Geometry spikeyBall = new Geometry("spikeyBall", mesh);
		spikeyBall.setMaterial(getSpikeyBallMaterial(app.getAssetManager()));
		final SpikeyBallControl pc = new SpikeyBallControl(
				new SphereCollisionShape(DEFAULT_RADIUS), sManager, 10f);
		spikeyBall.addControl(pc);
		pc.setRestitution(1);
		sManager.getState(PlayState.class).getPhysicsSpace().add(pc);
		pc.init();
		return spikeyBall;
	}

	/**
	 * This method makes all the required materials.
	 * 
	 * @param assetManager
	 *            the AssetManager
	 * @return the material
	 */
	public Material getSpikeyBallMaterial(final AssetManager assetManager) {
		final Material mat = new Material(assetManager,
				"Common/MatDefs/Misc/Unshaded.j3md");
		mat.setColor("Color", ColorRGBA.Gray);
		return mat;
	}
}
