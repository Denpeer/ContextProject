package com.funkydonkies.geometrys.penguins;


import com.funkydonkies.controllers.GrowingSnowballControl;
import com.funkydonkies.controllers.StandardPenguinControl;
import com.funkydonkies.factories.PenguinFactory;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.collision.shapes.SphereCollisionShape;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Sphere;

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
	public StandardPenguin(final String name, final Mesh mesh, final Node rootNode, final Material mat, final PhysicsSpace phy, final float radius, Material snow) {
		super(name, mesh);
		this.setMaterial(mat);
		rootNode.attachChild(this);
		final StandardPenguinControl pc = new StandardPenguinControl(new SphereCollisionShape(radius), 1f);
//		GrowingSnowballControl snowBallControl = new GrowingSnowballControl(
//				new SphereCollisionShape(radius+1), 20f);
////		
//		Sphere shape = new Sphere(20, 20, 1);
//		Geometry snowball = new Geometry("snowball", shape);
//		snowball.setMaterial(snow);
//		rootNode.attachChild(snowball);
////		
		rootNode.addControl(pc);
//		rootNode.addControl(snowBallControl);
//		snowBallControl.setEnabled(false);
		pc.setRestitution(1);
		phy.add(pc);
		pc.init();
	}
	
	
}
