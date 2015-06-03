package com.funkydonkies.factories;

import com.funkydonkies.geometrys.targets.Fish;
import com.funkydonkies.geometrys.targets.Krill;
import com.funkydonkies.geometrys.targets.Squid;
import com.jme3.asset.AssetManager;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
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
	
	private Material fishMaterial;
	private Material krillMaterial;
	private Material squidMaterial;
	
	/**
	 * The constructor for the targetFactory.
	 * @param assetM the main assetManager
	 * @param rootN the main rootNode
	 * @param phy the main physicSpace
	 */
	public TargetFactory(final AssetManager assetM, final Node rootN, final PhysicsSpace phy) {
		this.rootNode = rootN;
		this.assetManager = assetM;	
		this.physicSpace = phy;
		makeMaterials();
	}
	
	/**
	 * The create method for a fish object.
	 * @return a fish object
	 */
	public Fish makeFish() {
		final float fishWidth = 5;
		final float fishHeight = 5;
		final float fishDepth = 5;
		final Vector3f dimensions = new Vector3f(fishWidth, fishHeight, fishDepth);
		final Mesh mesh = new Box(fishWidth, fishHeight, fishDepth);
		final Fish fish = new Fish("fish", mesh, rootNode, fishMaterial, physicSpace, dimensions);
		return fish;
	}
	
	/**
	 * The create method for a krill object.
	 * @return a krill object
	 */
	public Krill makeKrill() {
		final Mesh mesh = new Box(10, 10, 10);
		final Krill kril = new Krill("krillie", mesh, rootNode, krillMaterial, physicSpace);
		return kril;
	}
	
	/**
	 * The create method for a squid object.
	 * @return a squid object
	 */
	public Squid makeSquid() {
		final Mesh mesh = new Box(10, 10, 10);
		final Squid squid = new Squid("squiddie", mesh, rootNode, squidMaterial, physicSpace);
		return squid;
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
