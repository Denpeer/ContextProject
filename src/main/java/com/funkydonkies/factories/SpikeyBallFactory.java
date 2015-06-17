package com.funkydonkies.factories;

import com.funkydonkies.controllers.SpikeyBallControl;
import com.funkydonkies.interfaces.FactoryInterface;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppStateManager;
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
	private SimpleApplication app;
	private AppStateManager stateManager;

	/**
	 * The create method for a spikey ball object.
	 * @param sManager jme AppStateManager for getting states
	 * @param appl jme SimpleApplication for getting rootNode or physicsSpace
	 * @return a spikey ball object
	 */
	public Spatial makeObject(final AppStateManager sManager,
			final SimpleApplication appl) {
		app = appl;
		stateManager = sManager;
		final Mesh mesh = new Sphere(SAMPLES, SAMPLES, DEFAULT_RADIUS);
		final Geometry spikeyBall = new Geometry(SPIKEYBALL_NAME, mesh);
		spikeyBall.setMaterial(getSpikeyBallMaterial());

		spikeyBall.addControl(makeSpikeyBallControl());

		return spikeyBall;
	}
	
	/**
	 * This method make a spikeyball Control.
	 * @return a spikeybal control.
	 */
	public SpikeyBallControl makeSpikeyBallControl() {
		final SpikeyBallControl pc = new SpikeyBallControl(
				new SphereCollisionShape(DEFAULT_RADIUS), stateManager, 10f);
		pc.setRestitution(1);
		return pc;
	}
	
	/**
	 * This method gets the material for the spikey ball.
	 * @return the spikey ball material
	 */
	public Material getSpikeyBallMaterial() {
		final Material mat = new Material(app.getAssetManager(),
				"Common/MatDefs/Misc/Unshaded.j3md");
		mat.setColor("Color", ColorRGBA.Gray);
		return mat;
	}
}
