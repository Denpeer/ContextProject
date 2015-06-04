package com.funkydonkies.geometrys.penguins;


import com.funkydonkies.controllers.KillerWhaleControl;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.material.Material;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;

/**
 * This class represents the shiny penguin class.
 * @author SDumasy
 *
 */
public class ShinyPenguin extends Geometry {
	
	/**
	 * The constructor of the class.
	 * @param name the name of the shiny Penguin
	 * @param mesh the mesh of the shiny Penguin
	 * @param mat the material of the shiny Penguin
	 * @param rootNode the rootNode of the shiny Penguin
	 * @param phy the physics space
	 */
	public ShinyPenguin(final String name, final Mesh mesh, final Node rootNode, final Material mat, final PhysicsSpace phy) {
		super(name, mesh);
		this.setMaterial(mat);
		rootNode.attachChild(this);
		final KillerWhaleControl cSMS = new KillerWhaleControl(2.0f, 0.10, false, true);
		this.addControl(cSMS);
		phy.add(cSMS);
		cSMS.setKinematic(true);
	}
	
	
}
