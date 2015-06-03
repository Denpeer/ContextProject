package com.funkydonkies.geometrys.targets;

import com.funkydonkies.controllers.ConstantSpeedMoveControl;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.material.Material;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;

public class Squid extends Geometry{
	
	public Squid(String name, Mesh mesh, Node rootNode, Material mat, PhysicsSpace phy){
		super(name, mesh);
		this.setMaterial(mat);
		rootNode.attachChild(this);
		ConstantSpeedMoveControl cSMS = new ConstantSpeedMoveControl(2.0f, 0.10,false,true);
		this.addControl(cSMS);
		phy.add(cSMS);
		cSMS.setKinematic(true);
	}
	
	
}
