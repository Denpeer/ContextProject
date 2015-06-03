package com.funkydonkies.geometrys.penguins;


import com.funkydonkies.controllers.ConstantSpeedMoveControl;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.material.Material;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;

/**
 * This class represent the fat penguin.
 */
public class FatPenguin extends Geometry {
	
	/**
	 * The constructor of the class.
	 * @param name the name of the FatPenguin
	 * @param mesh the mesh of the FatPenguin
	 * @param mat the material of the FatPenguin
	 * @param rootNode the rootNode of the FatPenguin
	 * @param phy the physics space
	 */
	public FatPenguin(final String name, final Mesh mesh, final Node rootNode, final Material mat, final PhysicsSpace phy) {
		super(name, mesh);
		this.setMaterial(mat);
		rootNode.attachChild(this);
		final ConstantSpeedMoveControl cSMS = new ConstantSpeedMoveControl(2.0f, 0.10, false, true);
		this.addControl(cSMS);
		phy.add(cSMS);
		cSMS.setKinematic(true);
	}
	
	
}
