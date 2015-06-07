package com.funkydonkies.factories;

import com.funkydonkies.controllers.FishControl;
import com.funkydonkies.gamestates.PlayState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.collision.shapes.BoxCollisionShape;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;

/**
 * This class represent the factory for the target.
 * @author SDumasy
 *
 */
public class TargetFactory {
	
	private AssetManager assetManager;
	private Node rootNode;
	private PhysicsSpace physicSpace;
	private AppStateManager asm;
	
	private Material fishMaterial;
	private Material krillMaterial;
	private Material squidMaterial;
	
	/**
	 * The constructor for the targetFactory.
	 * @param assetM the main assetManager
	 * @param rootN the main rootNode
	 * @param phy the main physicSpace
	 */
	public TargetFactory(final AppStateManager sm) {
		this.rootNode = sm.getState(PlayState.class).getRootNode();
		this.assetManager = sm.getApplication().getAssetManager();	
		this.physicSpace = sm.getState(PlayState.class).getPhysicsSpace();
		asm = sm;
		makeMaterials();
	}
	
	/**
	 * The create method for a fish object.
	 * @return a fish object
	 */
	public Geometry makeFish() {
		final float fishWidth = 5;
		final float fishHeight = 5;
		final float fishDepth = 5;
		final Mesh mesh = new Box(fishWidth, fishHeight, fishDepth);
		final Geometry fish = new Geometry("fish", mesh);
		fish.setMaterial(fishMaterial);
		rootNode.attachChild(fish);
		final FishControl tarCont = new FishControl(
				new BoxCollisionShape(new Vector3f(fishWidth, fishHeight, fishDepth)), asm);
		fish.addControl(tarCont);
		physicSpace.add(tarCont);
		tarCont.init();
		return fish;
	}
	
	
	/**
	 * This method makes all the required materials.
	 */
	public void makeMaterials() {
		final String color = "Color";
		final String path = "Common/MatDefs/Misc/Unshaded.j3md";
		fishMaterial = new Material(assetManager, path);
		fishMaterial.setColor(color, ColorRGBA.Blue);
		
		krillMaterial = new Material(assetManager, path);
		krillMaterial.setColor(color, ColorRGBA.Orange);
		
		squidMaterial = new Material(assetManager, path);
		squidMaterial.setColor(color, ColorRGBA.Cyan);
	}
}
