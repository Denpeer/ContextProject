package com.funkydonkies.factories;

import com.funkydonkies.geometrys.penguins.FatPenguin;
import com.funkydonkies.geometrys.penguins.ShinyPenguin;
import com.funkydonkies.geometrys.penguins.StandardPenguin;
import com.jme3.asset.AssetManager;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Sphere;

public class PenguinFactory {
	private static final int SAMPLES = 20;
	private static final float DEFAULT_RADIUS = 4f;
	
	private AssetManager assetManager;
	private Node rootNode;
	private PhysicsSpace physicSpace;
	
	private Material fatPenguinMaterial;
	private Material ShinyPenguinMaterial;
	private Material StandardPenguinMaterial;
	
	public PenguinFactory(AssetManager assetM, Node rootN, PhysicsSpace phy) {
		this.rootNode = rootN;
		this.assetManager = assetM;	
		this.physicSpace = phy;
		makeMaterials();
	}
	
	public FatPenguin makeFatPenguin(){
		Mesh mesh = new Box(10,10,10);
		FatPenguin fatPeng = new FatPenguin("fatOne", mesh, rootNode, fatPenguinMaterial, physicSpace);
		return fatPeng;
	}
	
	public ShinyPenguin makeShinyPenguin(){
		Mesh mesh = new Box(10,10,10);
		ShinyPenguin shinyPeng = new ShinyPenguin("ShinyPenguinie", mesh, rootNode, ShinyPenguinMaterial, physicSpace);
		return shinyPeng;
	}
	
	public StandardPenguin makeStandardPenguin(){
		Mesh mesh = new Sphere(SAMPLES, SAMPLES, DEFAULT_RADIUS);
		StandardPenguin StandardPenguin = new StandardPenguin("standardPenguin", mesh, rootNode, StandardPenguinMaterial, physicSpace, DEFAULT_RADIUS);
		return StandardPenguin;
	}
	
	
	
	public void makeMaterials(){
		fatPenguinMaterial = new Material(assetManager,
				"Common/MatDefs/Misc/Unshaded.j3md");
		fatPenguinMaterial.setColor("Color", ColorRGBA.Blue);
		
		ShinyPenguinMaterial = new Material(assetManager,
				"Common/MatDefs/Misc/Unshaded.j3md");
		ShinyPenguinMaterial.setColor("Color", ColorRGBA.Orange);
		
		StandardPenguinMaterial = new Material(assetManager,
				"Common/MatDefs/Misc/Unshaded.j3md");
		StandardPenguinMaterial.setColor("Color", ColorRGBA.Cyan);
	}
}
