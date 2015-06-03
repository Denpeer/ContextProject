package com.funkydonkies.geometrys.targets;

import com.funkydonkies.controllers.TargetControl;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.collision.shapes.BoxCollisionShape;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;

/**
 * This class represents the fish target.
 */
public class Fish extends Geometry {
	
	/**
	 * The constructor of the class.
	 * @param name the name of the fish
	 * @param mesh the mesh of the fish
	 * @param mat the material of the fish
	 * @param rootNode the rootNode of the fish
	 * @param phy the physics space
	 * @param dim the dimension of the fish target
	 */
	public Fish(final String name, final Mesh mesh, final Node rootNode, final Material mat, final PhysicsSpace phy, final Vector3f dim) {
		super(name, mesh);
		this.setMaterial(mat);
		rootNode.attachChild(this);
		final TargetControl tarCont = new TargetControl(
				new BoxCollisionShape(dim));
		
		this.addControl(tarCont);
		phy.add(tarCont);
		tarCont.init();
	}
	
	
}
