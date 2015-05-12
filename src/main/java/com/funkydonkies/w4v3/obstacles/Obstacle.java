package com.funkydonkies.w4v3.obstacles;

import com.jme3.scene.Geometry;

public abstract class Obstacle {
	private double width;
	private double height;
	private double depth;
	private Geometry geometry;
	
	public Obstacle(double width, double height, double depth){
		this.width = width;
		this.setHeight(height);
		this.depth = depth;
		this.geometry = geometry;
	}
	
	public void draw() {
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}
}
