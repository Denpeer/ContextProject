package com.funkydonkies.factories;

import com.funkydonkies.controllers.SquidControl;
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
public class SquidFactory implements FactoryInterface{

	/**
	 * The create method for a squid object.
	 * @return a squid object
	 */
	public Geometry makeObject(AppStateManager sManager, SimpleApplication app) {
		final float squidWidth = 7;
		final float squidHeight = 7;
		final float squidDepth = 5;
		final Mesh mesh = new Box(squidWidth, squidHeight, squidDepth);
		final Geometry squid = new Geometry("squid", mesh);
		squid.setMaterial(getsquidMaterial(app.getAssetManager()));
		final SquidControl tarCont = new SquidControl(
				new BoxCollisionShape(new Vector3f(squidWidth, squidHeight, squidDepth)), sManager);
		squid.addControl(tarCont);
		sManager.getState(PlayState.class).getPhysicsSpace().add(tarCont);
		tarCont.init();
		return squid;
	}
	
	
	/**
	 * This method makes all the required materials.
	 */
	public Material getsquidMaterial(AssetManager assetManager) {
		Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
		mat.setColor("Color", ColorRGBA.Yellow);
		return mat;
	}
}
