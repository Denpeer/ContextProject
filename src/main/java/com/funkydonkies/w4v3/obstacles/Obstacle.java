package com.funkydonkies.w4v3.obstacles;

import com.jme3.bullet.PhysicsSpace;
import com.jme3.material.Material;
import com.jme3.scene.Geometry;
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
	 * @param width the width of the obstacle
	 * @param height the height of the obstacle
	 * @param depth the depth of the obstacle
	 */
	public Obstacle(final double width, final double height, final double depth) {
		this.width = width;
		this.setHeight(height);
		this.depth = depth;
	}
	
	/**
	 * The draw method every obstacle needs.
	 * @param mat the material
	 * @param psySpace the physic space
	 * @param rootNode the rootNode
	 */
	public void draw(final Material mat, final PhysicsSpace psySpace, final Node rootNode) {
	}
	
	/**
	 * This method return the height.
	 * @return the height of the obstacle
	 */
	public final double getHeight() {
		return height;
	}
	/**
	 * This method sets the height of the obstacle.
	 * @param height is the height
	 */
	public final void setHeight(final double height) {
		this.height = height;
	}
}
