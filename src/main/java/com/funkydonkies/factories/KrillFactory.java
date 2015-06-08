package com.funkydonkies.factories;

import com.funkydonkies.controllers.KrillControl;
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
public class KrillFactory implements FactoryInterface{

	/**
	 * The create method for a krill object.
	 * @return a krill object
	 */
	public Geometry makeObject(AppStateManager sManager, SimpleApplication app) {
		final float krillWidth = 3;
		final float krillHeight = 3;
		final float krillDepth = 5;
		final Mesh mesh = new Box(krillWidth, krillHeight, krillDepth);
		final Geometry krill = new Geometry("krill", mesh);
		krill.setMaterial(getkrillMaterial(app.getAssetManager()));
		app.getRootNode().attachChild(krill);
		final KrillControl tarCont = new KrillControl(
				new BoxCollisionShape(new Vector3f(krillWidth, krillHeight, krillDepth)), sManager);
		krill.addControl(tarCont);
		sManager.getState(PlayState.class).getPhysicsSpace().add(tarCont);
		tarCont.init();
		return krill;
	}
	
	
	/**
	 * This method makes all the required materials.
	 */
	public Material getkrillMaterial(AssetManager assetManager) {
		Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
		mat.setColor("Color", ColorRGBA.Magenta);
		return mat;
	}
}
