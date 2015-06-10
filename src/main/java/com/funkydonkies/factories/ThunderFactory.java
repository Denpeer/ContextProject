package com.funkydonkies.factories;

import java.util.Random;

import com.funkydonkies.controllers.ThunderControl;
import com.funkydonkies.controllers.ThunderWarningLineControl;
import com.funkydonkies.gamestates.PlayState;
import com.funkydonkies.interfaces.FactoryInterface;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppStateManager;
import com.jme3.bullet.collision.shapes.BoxCollisionShape;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.material.Material;
import com.jme3.material.RenderState.BlendMode;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue.Bucket;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;

/**
 * Creates the thunder and warning line for thunder.
 * 
 * @author Olivier Dikken
 *
 */
public class ThunderFactory implements FactoryInterface {

	private static final float THUNDER_WIDTH = 5;
	private static final float THUNDER_HEIGHT = 1000;
	private static final float THUNDER_DEPTH = 20;
	private static final float WARNING_LINE_WIDTH = 5;
	private static final float WARNING_LINE_HEIGHT = 200;
	private static final float WARNING_LINE_DEPTH = 1;

	private AppStateManager stateManager;
	private SimpleApplication app;

	private final int maxRand = 320;

	public static final String THUNDER_NAME = "thunder";
	public static final String WARNING_LINE_NAME = "warning line";
	private final String defaultMaterial = "default material";
	private final String color = "Color";

	@Override
	public Spatial makeObject(final AppStateManager sManager, final SimpleApplication appl) {
		stateManager = sManager;
		app = appl;

		final int randomInt = getRand(maxRand);

		final Node thunderNode = new Node();
		thunderNode.attachChild(makeThunder(randomInt));
		thunderNode.attachChild(makeWarningLine(randomInt));

		return thunderNode;
	}

	/**
	 * get random int in range.
	 * 
	 * @param max
	 *            range is from 0 to max
	 * @return the random number in range
	 */
	public int getRand(final int max) {
		final Random rand = new Random();
		final int numRand = rand.nextInt(max);
		return numRand;
	}

	/**
	 * Makes a new thunder geometry and sets its material and control(s).
	 * 
	 * @param xCoord
	 *            the coord passed on to the control
	 * @return new thunder geometry instance
	 */
	public Geometry makeThunder(final float xCoord) {
		final Box mesh = new Box(THUNDER_WIDTH, THUNDER_HEIGHT, THUNDER_DEPTH);
		final Geometry geom = new Geometry(THUNDER_NAME, mesh);
		geom.setMaterial(getThunderMaterial());

		final CollisionShape colShape = new BoxCollisionShape(new Vector3f(THUNDER_WIDTH,
				THUNDER_HEIGHT, THUNDER_DEPTH));

		final ThunderControl control = new ThunderControl(colShape, stateManager, xCoord);
		geom.addControl(control);
		stateManager.getState(PlayState.class).getPhysicsSpace().add(control);
		return geom;
	}

	/**
	 * Makes a new warning line geometry and sets its material and control(s).
	 * 
	 * @param xCoord
	 *            the coord passed on to the control
	 * @return new warning line geometry
	 */
	public Geometry makeWarningLine(final float xCoord) {
		final Mesh warningLineMesh = new Box(WARNING_LINE_WIDTH, WARNING_LINE_HEIGHT,
				WARNING_LINE_DEPTH);
		final Geometry geom = new Geometry(WARNING_LINE_NAME, warningLineMesh);
		geom.setMaterial(getWarningLineMaterial());
		geom.setQueueBucket(Bucket.Transparent);

		final CollisionShape colShape = new BoxCollisionShape(new Vector3f(WARNING_LINE_WIDTH,
				WARNING_LINE_HEIGHT, WARNING_LINE_DEPTH));

		final ThunderWarningLineControl wLC = new ThunderWarningLineControl(stateManager, xCoord, 0);
		geom.addControl(wLC);
		stateManager.getState(PlayState.class).getPhysicsSpace().add(wLC);
		return geom;
	}

	/**
	 * The method makes the required material.
	 * 
	 * @return the thunder material
	 */
	public Material getThunderMaterial() {
		Material thunderMaterial;
		thunderMaterial = ((Material) app.getRootNode().getUserData(defaultMaterial)).clone();
		thunderMaterial.setColor(color, ColorRGBA.Pink);
		return thunderMaterial;
	}

	/**
	 * The method makes the required materials.
	 * 
	 * @return the warning line material
	 */
	public Material getWarningLineMaterial() {
		Material warningLineMaterial;
		final float colorAlpha = 0.2f;
		warningLineMaterial = ((Material) app.getRootNode().getUserData(defaultMaterial)).clone();
		warningLineMaterial.setColor(color, new ColorRGBA(1, 0, 0, (float) colorAlpha));
		warningLineMaterial.getAdditionalRenderState().setBlendMode(BlendMode.Alpha);
		return warningLineMaterial;
	}

}
