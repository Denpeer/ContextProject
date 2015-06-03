package com.funkydonkies.geometrys.penguins;


import com.funkydonkies.controllers.ConstantSpeedMoveControl;
import com.funkydonkies.controllers.StandardPenguinController;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.collision.shapes.SphereCollisionShape;
import com.jme3.material.Material;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;

public class StandardPenguin extends Geometry{
	
	public StandardPenguin(String name, Mesh mesh, Node rootNode, Material mat, PhysicsSpace phy, float radius){
		super(name, mesh);
		this.setMaterial(mat);
		rootNode.attachChild(this);
		StandardPenguinController pc = new StandardPenguinController(new SphereCollisionShape(radius), 1f);
		this.addControl(pc);
		pc.setRestitution(10);
		phy.add(pc);
		pc.init();
	}
	
	
}
