package com.funkydonkies.w4v3.obstacles;

import com.jme3.bullet.PhysicsSpace;
import com.jme3.material.Material;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;

/**
 * The target class.
 * @author SDumasy
 *
 */
public class Target extends Obstacle {
	private Geometry geom;
	private final Box box;
	
	/**
	 * This is the constructor of the Target Class.
	 * @param w the width of the target	
	 * @param h the height of the target
	 * @param d the depth of the target
	 */
	public Target(final double w, final double h, final double d) {
		super(w, h, d);
		box = new Box((float) w, (float) h, (float) d);
	}

	/**
	 * This method draws the target.
	 */
	@Override
	public void draw(final Material mat, final PhysicsSpace psySpace, final Node rootNode) {
		System.out.println("fdaf");
		geom = new Geometry("target", box);
		final float xTrans = 30f;
		final float zTrans = 0.5f;
		final float yTrans = 1f;
		geom.move(xTrans, yTrans, zTrans);
		geom.setMaterial(mat);
		rootNode.attachChild(geom);

	}

}
