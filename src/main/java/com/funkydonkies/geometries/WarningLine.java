package com.funkydonkies.geometries;

import com.jme3.material.Material;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;

public class WarningLine extends Geometry {
	public WarningLine(Node rootNode, Material mat, String name, Mesh mesh){
		super(name, mesh);
		this.setMaterial(mat);
		rootNode.attachChild(this);
	}
}
