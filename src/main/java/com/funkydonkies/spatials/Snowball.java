package com.funkydonkies.spatials;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Quad;
import com.jme3.texture.Texture;
import com.jme3.texture.Texture2D;
import com.jme3.texture.plugins.AWTLoader;

/**
 * Spatial that uses java awt graphics to draw a circle on the screen.
 * Code taken from http://hub.jmonkeyengine.org/t/drawing-simple-circle-using-java-2d/25885
 * @see http://hub.jmonkeyengine.org/t/drawing-simple-circle-using-java-2d/25885
 */
public class Snowball extends Node {
	private float radius = 5;
	private int angle = 360;
	private int borderAngle = 360;
	private Color color = Color.BLACK;
	private Color fillColor = null;
	private AssetManager assetManager;
	private final Texture texture = new Texture2D();
	private float borderWidth;
	private int heightResolution = 64;
	private int widthResolution = 64;
	private Material material;
	private Geometry geometry;	
	
	/**
	 * 
	 * @param assetManager
	 * @param radius radius of the circle
	 * @param borderWidth width of the border
	 * @param color  fill color
	 * @param borderAngle and of border displayed
	 * @param fillColor filled color
	 * @param angle and gle of the filled color
	 */
	public Snowball(AssetManager assetManager, float radius, float borderWidth, Color color, int borderAngle, Color fillColor, int angle) {
	    this.assetManager = assetManager;
	    this.radius = radius;
	    this.color = color;
	    this.fillColor = fillColor;
	    this.borderWidth = borderWidth;
	    this.borderAngle = borderAngle;
	    this.angle = angle;
	    material = new Material(this.assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
	    material.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
	    initSpatial();
	    updateSpatial();
	    setName("snowball");
	}

	private void initSpatial() {
	    generateImage();
	    Quad q = new Quad(10, 10);
	    geometry = new Geometry("sball", q);
	    geometry.rotate(0, 0, 0);
	    geometry.setMaterial(material);
	    this.attachChild(geometry);
	}

	/**
	 * Updates the geometrys scale when the radius is modified.
	 */
	private void updateSpatial() {
	    geometry.setLocalScale(radius / 10f, radius / 10f, radius / 10f);
	}
	
	/**
	 * Returns the circles radius.
	 * @return
	 */
	public float getRadius() {
		return radius;
	}

	/**
	 * Uses java 2D graphics to draw the circle.
	 */
	public void generateImage() {
	    BufferedImage image = new BufferedImage(widthResolution, heightResolution, BufferedImage.TYPE_INT_ARGB);
	    Graphics2D g = image.createGraphics();
	    g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	    if (fillColor != null) {
	        g.setColor(fillColor);
	        g.fillArc((int) borderWidth / 2, (int) borderWidth / 2, (int) (widthResolution - 1 - borderWidth), (int) (heightResolution - borderWidth), 180+(angle/2), -angle);
	    }
	    g.setStroke(new BasicStroke(borderWidth));
	    g.setColor(color);
	    g.drawArc((int) borderWidth / 2, (int) borderWidth / 2, (int) (widthResolution - 1 - borderWidth), (int) (heightResolution - borderWidth), 180 +(borderAngle/2), -borderAngle);

	    AWTLoader awtLoader = new AWTLoader();
	    texture.setImage(awtLoader.load(image, false));
	    material.setTexture("ColorMap", texture);
	}


	/**
	 * Sets the circles radius.
	 * @param radius float the radius
	 */
	public void setRadius(float radius) {
	    this.radius = radius;
	    updateSpatial();
	}

	/**
	 * Sets the fill color of the circle.
	 * @param fillColor Color the color.
	 */
	public void setFillColor(Color fillColor) {
	    this.fillColor = fillColor;
	}

	/**
	 * Setsthe border color of the circle.
	 * @param color Color
	 */
	public void setColor(Color color) {
	    this.color = color;
	}

	
	/**
	 * Sets the border with.
	 * @param borderWidth float.
	 */
	public void setBorderWidth(float borderWidth) {
	    this.borderWidth = borderWidth;
	}

	/**
	 * Sets the resolution.
	 * @param resolution int.
	 */
	public void setResolution(int resolution) {
	    this.heightResolution = resolution;
	    this.widthResolution = resolution;
	}
}

