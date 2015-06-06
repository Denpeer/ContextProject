package com.funkydonkies.geometries.targets;

import com.funkydonkies.controllers.KillerWhaleControl;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.material.Material;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;

/**
 * This class represents the squid target.
 * @author SDumasy
 *
 */
public class Squid extends Geometry {
	
	/**
	 * The constructor of the class.
	 * @param name the name of the squid target
	 * @param mesh the mesh of the squid
	 * @param mat the material of the squid
	 * @param rootNode the rootNode of the squid
	 * @param phy the physics space
	 */
	public Squid(final String name, final Mesh mesh, final Node rootNode, final Material mat, final PhysicsSpace phy) {
		super(name, mesh);
		this.setMaterial(mat);
		rootNode.attachChild(this);
		final KillerWhaleControl cSMS = new KillerWhaleControl(2.0f, 0.10, false, true);
		this.addControl(cSMS);
		phy.add(cSMS);
		cSMS.setKinematic(true);
	}
	
	
}
