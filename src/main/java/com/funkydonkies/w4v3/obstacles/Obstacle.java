package com.funkydonkies.w4v3.obstacles;

import com.jme3.bullet.PhysicsSpace;
import com.jme3.material.Material;
import com.jme3.scene.Node;

/**
 * This class is the abstract class for obstacles.
 * @author SDumasy
 *
 */
public abstract class Obstacle {
	private double width;
	private double height;
	private double depth;
	
	/**
	 * The constructor of the obstacle class.
	 * @param w the width of the obstacle
	 * @param h the height of the obstacle
	 * @param d the depth of the obstacle
	 */
	public Obstacle(final double w, final double h, final double d) {
		this.width = w;
		this.setHeight(h);
		this.depth = d;
	}
	
	/**
	 * The draw method that every obstacle needs.
	 * @param mat the material
	 * @param psySpace the physic space
	 * @param rootNode the rootNode
	 */
	public abstract void draw(final Material mat, final PhysicsSpace psySpace, final Node rootNode);
	
	/**
	 * This method return the height.
	 * @return the height of the obstacle
	 */
	public final double getHeight() {
		return height;
	}
	/**
	 * This method sets the height of the obstacle.
	 * @param h is the height
	 */
	public final void setHeight(final double h) {
		this.height = h;
	}
}
