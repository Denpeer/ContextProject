package com.funkydonkies.obstacles;

import com.jme3.bullet.PhysicsSpace;
import com.jme3.material.Material;
import com.jme3.scene.Node;

/**
 * This class is the abstract class for obstacles.
 * @author SDumasy
 *
 */
public abstract class Obstacle {
	private float width;
	private float height;
	private float depth;
	private float xCoord;
	private float yCoord;
	private float zCoord;
	private Node node;
	
	/**
	 * The constructor of the obstacle class.
	 * @param w the width of the obstacle
	 * @param h the height of the obstacle
	 * @param d the depth of the obstacle
	 * @param n Node to attach the obstacle to the scene
	 * @param x the x coordinate of the obstacle
	 * @param y the y coordinate of the obstacle
	 * @param z the z coordinate of the obstacle
	 */
	public Obstacle(final float w, final float h, final float d, final float x,
			final float y, final float z, final Node n) {
		this.setWidth(w);
		this.setHeight(h);
		this.setDepth(d);
		this.setxCoord(x);
		this.setyCoord(y);
		this.setzCoord(z);
		node = n;
	}
		
	/**Accessor method for the node.
	 * 
	 * @return node
	 */
	public Node getNode() {
		return node;
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
	public final void setHeight(final float h) {
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
	public void setWidth(final float w) {
		this.width = w;
	}

	/** Get depth of the obstacle.
	 * @return current depth of obstacle
	 */
	public float getDepth() {
		return depth;
	}

	/** This method sets the depth of the obstacle.
	 * @param d desired depth of obstacle
	 */
	public void setDepth(final float d) {
		this.depth = d;
	}
	/**
	 * This method returns the z coordinate.
	 * @return the z coordinate
	 */
	public float getzCoord() {
		return zCoord;
	}
	/**
	 * This method sets the z coordinate.
	 * @param zCoor the z coordinate
	 */
	public void setzCoord(final float zCoor) {
		this.zCoord = zCoor;
	}

	/**
	 * This method returns the x coordinate.
	 * @return the x coordinate.
	 */
	public float getxCoord() {
		return xCoord;
	}

	/**
	 * This method sets the xCoordinate.
	 * @param xCoor the x coordinate 
	 */
	public void setxCoord(final float xCoor) {
		this.xCoord = xCoor;
	}
	/**
	 * This method returns the y coordinate.
	 * @return the y coordinate
	 */
	public double getyCoord() {
		return yCoord;
	}
	/**
	 * This method sets the y coordinate.
	 * @param yCoor the y coordinate
	 */
	public void setyCoord(final float yCoor) {
		this.yCoord = yCoor;
	}
}
