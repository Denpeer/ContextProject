package com.funkydonkies.factories;

import com.funkydonkies.controllers.FishControl;
import com.funkydonkies.interfaces.FactoryInterface;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppStateManager;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.collision.shapes.SphereCollisionShape;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Spatial;

/**
 * This class represent the factory for the target.
 */
public class FishFactory implements FactoryInterface {

	private static final float RADIUS = 8;
	public static final String FISH_NAME = "fish";
	private static final String MODEL_PATH = "Models/TUNA.j3o";
	private static final String MATERIAL_PATH = "Common/MatDefs/Misc/Unshaded.j3md";
	private AppStateManager stateManager;
	private SimpleApplication app;
	private Spatial fish;
	
	/**
	 * The create method for a fish object.
	 * @param sManager jme AppStateManager for getting states
	 * @param appl jme SimpleApplication for getting rootNode or physicsSpace
	 * @return a fish object
	 */
	public Spatial makeObject(final AppStateManager sManager, final SimpleApplication appl) {
		stateManager = sManager;
		app = appl;
		final Spatial fish = makeFish();
		return fish;
	}
	
	/**
	 * Makes a new fish geometry and sets its material and control(s).
	 * @return new fish geometry instance
	 */
	public Spatial makeFish() {
		fish = makeSpatial();
		fish.setMaterial(getFishMaterial());
		final FishControl control = makeFishControl();
		fish.addControl(control);
		return fish;
	}
	
	/**
	 * This method makes the fish control.
	 * @return a fish control object
	 */
	public FishControl makeFishControl() {
		final CollisionShape colShape = 
				new SphereCollisionShape(RADIUS);	
		return new FishControl(colShape, stateManager);
	}
	
	/**
	 * This method makes a geometry.
	 * @param mesh the mesh of the fish
	 * @return a fish geometry
	 */
	public Spatial makeSpatial() {
		final Spatial f = app.getAssetManager().loadModel(MODEL_PATH);
		f.setName(FISH_NAME);
		return f;
	}
	
	/**
	 * This method makes all the required materials.
	 * @return a Material object
	 */
	public Material getFishMaterial() {
		final Material mat = new Material(app.getAssetManager(), MATERIAL_PATH);
		mat.setColor("Color", ColorRGBA.Red);
		return mat;
	}

}
