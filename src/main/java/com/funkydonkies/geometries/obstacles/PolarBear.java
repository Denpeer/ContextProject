package com.funkydonkies.geometries.obstacles;

import com.funkydonkies.controllers.KillerWhaleControl;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.material.Material;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;

/**
 * This class visualizes the Polar bear.
 */
public class PolarBear extends Geometry {
	
	/**
	 * The constructor of the class.
	 * @param name the name of the polar bear
	 * @param mesh the mesh of the polar bear
	 * @param mat the material of the polar bear
	 * @param rootNode the rootNode of the polar bear
	 * @param p the physics space
	 */
	public PolarBear(final String name, final Mesh mesh, final Node rootNode, final Material mat, final PhysicsSpace p) {
		super(name, mesh);
		this.setMaterial(mat);
		rootNode.attachChild(this);
		final KillerWhaleControl cSMS = new KillerWhaleControl(2.0f, 0.10, false, true);
		this.addControl(cSMS);
		p.add(cSMS);
		cSMS.setKinematic(true);

		
	}
 
}
