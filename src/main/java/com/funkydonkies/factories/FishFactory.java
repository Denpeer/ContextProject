package com.funkydonkies.factories;

import com.funkydonkies.controllers.FishControl;
import com.funkydonkies.gamestates.PlayState;
import com.funkydonkies.interfaces.FactoryInterface;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
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
 */
public class FishFactory implements FactoryInterface {

	private static final float FISH_WIDTH = 5;
	private static final float FISH_HEIGHT = 5;
	private static final float FISH_DEPTH = 5;
	public static final String FISH_NAME = "fish";
	
	private AppStateManager stateManager;
	private SimpleApplication app;
	
	/**
	 * The create method for a fish object.
	 * @param sManager jme AppStateManager for getting states
	 * @param appl jme SimpleApplication for getting rootNode or physicsSpace
	 * @return a fish object
	 */
	public Geometry makeObject(final AppStateManager sManager, final SimpleApplication appl) {
		stateManager = sManager;
		app = appl;
		
		final Geometry fish = makeFish();
		
		return fish;
	}
	
	/**
	 * Makes a new fish geometry and sets its material and control(s).
	 * @return new fish geometry instance
	 */
	public Geometry makeFish() {
		final Mesh mesh = new Box(FISH_WIDTH, FISH_HEIGHT, FISH_DEPTH);
		final Geometry geom = makeGeometry(mesh);
		geom.setMaterial(getFishMaterial(app.getAssetManager()));
		final FishControl control = makeFishControl();
		geom.addControl(control);
		
		return geom;
	}
	
	/**
	 * This method makes the fish control.
	 * @return a fish control object
	 */
	public FishControl makeFishControl() {
		final CollisionShape colShape = 
				new BoxCollisionShape(new Vector3f(FISH_WIDTH, FISH_HEIGHT, FISH_DEPTH));	
		return new FishControl(colShape, stateManager);
	}
	
	/**
	 * This method makes a geometry.
	 * @param mesh the mesh of the fish
	 * @return a fish geometry
	 */
	public final Geometry makeGeometry(final Mesh mesh) {
		return new Geometry(FISH_NAME, mesh);
	}
	
	/**
	 * This method makes all the required materials.
	 * @param assetManager jme AssetManager for loading models
	 * @return a Material object
	 */
	public Material getFishMaterial(final AssetManager assetManager) {
		final Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
		mat.setColor("Color", ColorRGBA.Green);
		return mat;
	}

}
