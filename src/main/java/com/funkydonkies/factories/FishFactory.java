package com.funkydonkies.factories;

import com.funkydonkies.controllers.FishControl;
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
import com.jme3.scene.shape.Box;

/**
 * This class represent the factory for the target.
 * @author SDumasy
 *
 */
public class FishFactory implements FactoryInterface{

	/**
	 * The create method for a fish object.
	 * @return a fish object
	 */
	public Geometry makeObject(AppStateManager sManager, SimpleApplication app) {
		final float fishWidth = 5;
		final float fishHeight = 5;
		final float fishDepth = 5;
		final Mesh mesh = new Box(fishWidth, fishHeight, fishDepth);
		final Geometry fish = new Geometry("fish", mesh);
		fish.setMaterial(getFishMaterial(app.getAssetManager()));
		app.getRootNode().attachChild(fish);
		final FishControl tarCont = new FishControl(
				new BoxCollisionShape(new Vector3f(fishWidth, fishHeight, fishDepth)), sManager);
		fish.addControl(tarCont);
		sManager.getState(PlayState.class).getPhysicsSpace().add(tarCont);
		tarCont.init();
		return fish;
	}
	
	
	/**
	 * This method makes all the required materials.
	 */
	public Material getFishMaterial(AssetManager assetManager) {
		Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
		mat.setColor("Color", ColorRGBA.Green);
		return mat;
	}
}
