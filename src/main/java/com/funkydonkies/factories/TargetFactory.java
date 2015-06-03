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

public class TargetFactory {
	
	private AssetManager assetManager;
	private Node rootNode;
	private PhysicsSpace physicSpace;
	
	private Material fishMaterial;
	private Material krillMaterial;
	private Material squidMaterial;
	
	public TargetFactory(AssetManager assetM, Node rootN, PhysicsSpace phy) {
		this.rootNode = rootN;
		this.assetManager = assetM;	
		this.physicSpace = phy;
		makeMaterials();
	}
	
	public Fish makeFish(){
		float fishWidth = 5;
		float fishHeight = 5;
		float fishDepth = 5;
		Vector3f dimensions = new Vector3f(fishWidth, fishHeight, fishDepth);
		Mesh mesh = new Box(fishWidth, fishHeight, fishDepth);
		Fish fish = new Fish("fish", mesh, rootNode, fishMaterial, physicSpace, dimensions);
		return fish;
	}
	
	public Krill makeKrill(){
		Mesh mesh = new Box(10,10,10);
		Krill kril = new Krill("krillie", mesh, rootNode, krillMaterial, physicSpace);
		return kril;
	}
	
	public Squid makeSquid(){
		Mesh mesh = new Box(10,10,10);
		Squid squid = new Squid("squiddie", mesh, rootNode, squidMaterial, physicSpace);
		return squid;
	}
	
	
	
	public void makeMaterials(){
		fishMaterial = new Material(assetManager,
				"Common/MatDefs/Misc/Unshaded.j3md");
		fishMaterial.setColor("Color", ColorRGBA.Blue);
		
		krillMaterial = new Material(assetManager,
				"Common/MatDefs/Misc/Unshaded.j3md");
		krillMaterial.setColor("Color", ColorRGBA.Orange);
		
		squidMaterial = new Material(assetManager,
				"Common/MatDefs/Misc/Unshaded.j3md");
		squidMaterial.setColor("Color", ColorRGBA.Cyan);
	}
}
