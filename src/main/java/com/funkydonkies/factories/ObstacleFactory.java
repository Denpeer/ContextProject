package com.funkydonkies.factories;

import com.funkydonkies.geometrys.obstacles.KillerWhale;
import com.funkydonkies.geometrys.obstacles.PolarBear;
import com.funkydonkies.geometrys.obstacles.SeaLion;
import com.jme3.asset.AssetManager;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;

public class ObstacleFactory {
	
	private AssetManager assetManager;
	private Node rootNode;
	private PhysicsSpace physicSpace;
	
	private Material killerWhaleMaterial;
	private Material polarBearMaterial;
	private Material seaLionMaterial;
	
	public ObstacleFactory(AssetManager assetM, Node rootN, PhysicsSpace phy) {
		this.rootNode = rootN;
		this.assetManager = assetM;	
		this.physicSpace = phy;
		makeMaterials();
	}
	
	public KillerWhale makeKillerWhale(){
		Mesh mesh = new Box(10,10,10);
		KillerWhale kWhale = new KillerWhale("fishie", mesh, rootNode, killerWhaleMaterial, physicSpace);
		return kWhale;
	}
	
	public PolarBear makePolarBear(){
		Mesh mesh = new Box(10,10,10);
		PolarBear pBear = new PolarBear("polarBear", mesh, rootNode, polarBearMaterial, physicSpace);
		return pBear;
	}
	
	public SeaLion makeSeaLion(){
		Mesh mesh = new Box(10,10,10);
		SeaLion sLion = new SeaLion("seaLion", mesh, rootNode, seaLionMaterial, physicSpace);
		return sLion;
	}
	
	
	
	public void makeMaterials(){
		killerWhaleMaterial = new Material(assetManager,
				"Common/MatDefs/Misc/Unshaded.j3md");
		killerWhaleMaterial.setColor("Color", ColorRGBA.Blue);
		
		polarBearMaterial = new Material(assetManager,
				"Common/MatDefs/Misc/Unshaded.j3md");
		polarBearMaterial.setColor("Color", ColorRGBA.Orange);
		
		seaLionMaterial = new Material(assetManager,
				"Common/MatDefs/Misc/Unshaded.j3md");
		seaLionMaterial.setColor("Color", ColorRGBA.Cyan);
	}
}
