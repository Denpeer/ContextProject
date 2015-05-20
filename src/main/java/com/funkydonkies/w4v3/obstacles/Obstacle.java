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
	protected Node node;
	
	/**
	 * The constructor of the obstacle class.
	 * @param w the width of the obstacle
	 * @param h the height of the obstacle
	 * @param d the depth of the obstacle
	 * @param n Node to attach the obstacle to the scene
	 */
	public Obstacle(final double w, final double h, final double d, final Node n) {
		this.setWidth(w);
		this.setHeight(h);
		this.setDepth(d);
		node = n;
	}
	
	/**
	 * The draw method that every obstacle needs.
	 * @param mat the material
	 * @param psySpace the physic space
	 */
	public abstract void draw(final Material mat, final PhysicsSpace psySpace);
	
	/**
	 * This method returns the height of the obstacle.
	 * @return current height of obstacle
	 */
	public final double getHeight() {
		return height;
	}
	
	/**
	 * This method sets the height of the obstacle.
	 * @param h desired height of obstacle
	 */
	public final void setHeight(final double h) {
		this.height = h;
	}
	
	
	/** Get width of the obstacle.
	 * @return current width of the obstacle
	 */
	public double getWidth() {
		return width;
	}

	/** This method sets the width of the obstacle.
	 * @param w desired width of obstacle
	 */
	public void setWidth(final double w) {
		this.width = w;
	}

	/** Get depth of the obstacle.
	 * @return current depth of obstacle
	 */
	public double getDepth() {
		return depth;
	}

	/** This method sets the depth of the obstacle.
	 * @param d desired depth of obstacle
	 */
	public void setDepth(final double d) {
		this.depth = d;
	}
}
