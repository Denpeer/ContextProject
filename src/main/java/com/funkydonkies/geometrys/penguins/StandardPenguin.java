package com.funkydonkies.geometrys.penguins;


import com.funkydonkies.controllers.StandardPenguinControl;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.collision.shapes.SphereCollisionShape;
import com.jme3.material.Material;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;

/**
 * This class represent a standard penguin.
 */
public class StandardPenguin extends Geometry {
	
	/**
	 * The constructor of the class.
	 * @param name the name of the standard Penguin
	 * @param mesh the mesh of the standard Penguin
	 * @param mat the material of the standard Penguin
	 * @param rootNode the rootNode of the standard Penguin
	 * @param phy the physics space
	 * @param radius is the sphere radius of the penguin
	 */
	public StandardPenguin(final String name, final Mesh mesh, final Node rootNode, final Material mat, final PhysicsSpace phy, final float radius) {
		super(name, mesh);
		this.setMaterial(mat);
		rootNode.attachChild(this);
		final StandardPenguinControl pc = new StandardPenguinControl(new SphereCollisionShape(radius), 1f);
		this.addControl(pc);
		pc.setRestitution(1);
		phy.add(pc);
		pc.init();
	}
	
	
}
