package com.funkydonkies.geometrys.targets;

import com.funkydonkies.controllers.TargetControl;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.collision.shapes.BoxCollisionShape;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;

public class Fish extends Geometry{
	
	public Fish(String name, Mesh mesh, Node rootNode, Material mat, PhysicsSpace phy, Vector3f dim){
		super(name, mesh);
		this.setMaterial(mat);
		rootNode.attachChild(this);
		TargetControl tarCont= new TargetControl(
				new BoxCollisionShape(dim));
		
		this.addControl(tarCont);
		phy.add(tarCont);
		tarCont.init();
	}
	
	
}
