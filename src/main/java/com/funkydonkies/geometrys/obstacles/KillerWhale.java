package com.funkydonkies.geometrys.obstacles;

import com.funkydonkies.controllers.KillerWhaleControl;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.material.Material;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;

/**
 * This class visualizes the killerWhale.
 */
public class KillerWhale extends Geometry {
	
	/**
	 * The constructor of the class.
	 * @param name the name of the shark
	 * @param mesh the mesh of the shark
	 * @param mat the material of the shark
	 * @param rootNode the rootNode of the shark
	 * @param p the physics space
	 */
	public KillerWhale(final String name, final Mesh mesh, final Node rootNode, final Material mat, final PhysicsSpace p) {
		super(name, mesh);
		this.setMaterial(mat);
		rootNode.attachChild(this);
		final KillerWhaleControl cSMS = new KillerWhaleControl(2.0f, 0.10, true, false);
		this.addControl(cSMS);
		p.add(cSMS);
		cSMS.init();

		
	}
}
