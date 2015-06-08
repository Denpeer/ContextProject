package com.funkydonkies.factories;

import java.util.Random;

import com.funkydonkies.controllers.PolarBearControl;
import com.funkydonkies.controllers.WarningLineControl;
import com.funkydonkies.gamestates.PlayState;
import com.funkydonkies.interfaces.FactoryInterface;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.bullet.collision.shapes.BoxCollisionShape;
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

public class PolarBearFactory implements FactoryInterface {
	

	
	/** This method does not attach the spatial to the rootNode. Initializes PolarBear obstacles.
	 * @see com.funkydonkies.interfaces.FactoryInterface
	 * #makeObst(com.jme3.asset.AssetManager)
	 */
	public Spatial makeObject(final AppStateManager sManager, final SimpleApplication app) {	
		final Random rand = new Random();
		final float xCoord = getFloatSide(rand);
		final float yCoord = getFloatHeight(rand);
		final float stopX = getStopCoord(rand);
		final float bearWidth = 12;
		final float bearHeight = 20;
		final Mesh polarBearMesh = new Box(bearWidth, bearHeight, 1);
//		PolarBear pb = new PolarBear("la", polarBearMesh);

		Geometry polarBear = new Geometry("polarBear", polarBearMesh);
		polarBear.setMaterial(getPolarBearMaterial(app.getAssetManager()));
		final Vector3f loci = new Vector3f(xCoord, yCoord, 0);
		final PolarBearControl polarBearControl = new PolarBearControl(
				new BoxCollisionShape(new Vector3f(bearWidth, bearHeight, 1)), stopX, 1, sManager, loci);
		polarBear.addControl(polarBearControl);
		polarBearControl.init();
		sManager.getState(PlayState.class).getPhysicsSpace().add(polarBearControl);

		
		

		return polarBear;
	}
	
	public float getStopCoord(Random rand){
		return rand.nextInt(230) + 60;
	}
	
	public float getFloatHeight(Random rand){	
		return rand.nextInt(15);
	}
	
	public float getFloatSide(Random rand){
		if(rand.nextInt(2) == 1){
			return 500;
		}else{
			return -200;
		}
	}
	public Material getPolarBearMaterial(AssetManager assetManager) {
		Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
		mat.setColor("Color", ColorRGBA.Green);
		return mat;
	}

}
