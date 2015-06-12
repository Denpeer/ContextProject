package com.funkydonkies.factories;

import java.util.Random;

import com.funkydonkies.controllers.KillerWhaleControl;
import com.funkydonkies.controllers.WarningLineControl;
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
 *	Creates a Killer Whale and a Warning line for where it will spawn.
 */
public class KillerWhaleFactory implements FactoryInterface {
	
	public static final String WHALE_NAME = "killerWhale";
	public static final String WARNING_NAME = "warning line";
	
	private static final String COLOR = "Color";
	private static final String UNSHADED_MATERIAL_PATH = "Common/MatDefs/Misc/Unshaded.j3md";
	
	private static final float WARNING_LINE_ALPHA = 0.2f;
	
	private static final float WHALE_WIDTH = 50;
	private static final float WHALE_HEIGHT = 100; 
	private static final float WHALE_DEPTH = 1;
	
	private AppStateManager stateManager;
	private SimpleApplication app;
	
	@Override
	public Spatial makeObject(final AppStateManager sManager, final SimpleApplication appl) {
		stateManager = sManager;
		app = appl;
		
		final Random rand = new Random();
		final float x = rand.nextInt(320);
		final float y = -500;
		
		final Geometry whale = makeKillerWhale(x, y);
		final Geometry line = makeWarningLine(x); 

		final Node obstacleNode = new Node();
		obstacleNode.attachChild(whale);
		obstacleNode.attachChild(line);
		
		return obstacleNode;
	}
	
	/** Initializes KillerWhale Geometry and its Control(s).
	 * @param yCoord y point to spawn whale at
	 * @param xCoord random x point
	 * @return new Whale Geometry
	 */
	public Geometry makeKillerWhale(final float xCoord, final float yCoord) {
		final Mesh whaleMesh = new Box(WHALE_WIDTH, WHALE_HEIGHT, WHALE_DEPTH);
		final Geometry geom = new Geometry(WHALE_NAME, whaleMesh);
		geom.setMaterial(getKillerWhaleMaterial());
		
		final CollisionShape colShape = 
				new BoxCollisionShape(new Vector3f(WHALE_WIDTH, WHALE_HEIGHT, WHALE_DEPTH));
		
		final Vector3f loci = new Vector3f(xCoord, yCoord, 0);
		final KillerWhaleControl whaleControl = 
				new KillerWhaleControl(colShape, stateManager, loci);
		geom.addControl(whaleControl);
		
		return geom;
	}
	
	/** Initializes Warning Line Geometry and its Control(s).
	 * @param x random x point
	 * @return new Warning Line Geometry
	 */
	public Geometry makeWarningLine(final float x) {
		final Mesh warningLineMesh = new Box(WHALE_WIDTH, WHALE_HEIGHT, WHALE_DEPTH);
		final Geometry geom = new Geometry(WARNING_NAME, warningLineMesh);
		geom.setMaterial(getLineMaterial());
		geom.setQueueBucket(Bucket.Transparent);
		
		final WarningLineControl warningLineControl = new WarningLineControl(x, 0);
		geom.addControl(warningLineControl);
		return geom;
	}

	/** Loads Warning Line Material.
	 * @return the Warning Line's material
	 */
	public Material getLineMaterial() {
		final Material mat = new Material(app.getAssetManager(), UNSHADED_MATERIAL_PATH);
		mat.setColor(COLOR, new ColorRGBA(1, 0, 0, WARNING_LINE_ALPHA));
		mat.getAdditionalRenderState().setBlendMode(BlendMode.Alpha);
		return mat;
	}

	/** Loads Whale material.
	 * @return the Whale's material
	 */
	public Material getKillerWhaleMaterial() {
		final Material mat = new Material(app.getAssetManager(), UNSHADED_MATERIAL_PATH);
		mat.setColor(COLOR, ColorRGBA.Green);
		return mat;
	}

}
