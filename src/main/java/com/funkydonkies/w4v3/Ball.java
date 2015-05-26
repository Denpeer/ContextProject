package com.funkydonkies.w4v3;

import com.funkydonkies.controllers.BallController;
import com.jme3.asset.AssetManager;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.collision.shapes.SphereCollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.Control;
import com.jme3.scene.shape.Sphere;

/**
 * @author Jonathan
 *
 */
public class Ball {
	private static final int SAMPLES = 20;
	private Sphere sphere;
	private Geometry geom;
	private Material mat;
	private RigidBodyControl phy;
	private static final float DEFAULT_RADIUS = 0.5f;
	private static final Vector3f DEFAULT_SPAWN_LOCATION = new Vector3f(10f, 15f, 0f);
	private static final Vector3f DEFAULT_INITIAL_SPEED = new Vector3f(5, -22, 0);
	
	/**
	 * Constructor for Ball, initializes its attributes itself with default settings.
	 * @param assetManager assetmanager to be used in the material creation
	 */
	public Ball(final AssetManager assetManager) {
		sphere = new Sphere(SAMPLES, SAMPLES, DEFAULT_RADIUS);
		geom = new Geometry("ball", sphere);
		mat = new Material(assetManager,
				"Common/MatDefs/Misc/Unshaded.j3md");
		mat.setColor("Color", ColorRGBA.Blue);
		geom.setMaterial(mat);
		phy = new BallController(new SphereCollisionShape(DEFAULT_RADIUS), 1f);
		phy.setRestitution(1);
	}
	
	/**
	 * Constructor for Ball, taking a shape, geometry, material and physics controller to create 
	 * the Ball.
	 * @param shape Sphere, the spherical shape to use. 
	 * @param geometry Geometry, the geometry
	 * @param material Material, the ball´s material
	 * @param physics RigidBodyControl, the controller for the ball´s physics
	 */
	public Ball(final Sphere shape, final Geometry geometry, final Material material
			, final RigidBodyControl physics) {
		sphere = shape;
		this.geom = geometry;
		this.mat = material;
		geom.setMaterial(mat);
		phy = physics;
	}
	
	/**
	 * For setting the color property of the ball.
	 * TODO name may not be neccesary, as it has not been different from ´Color´ yet.
	 * @param name String, name of the color.
	 * @param value ColorRGBA, the color value.
	 */
	public void setColor(final String name, final ColorRGBA value) {
		mat.setColor(name, value);
	}
	
	/**
	 * Sets the speed for the Ball by calling setLinVelocity on the physics.
	 * @param vel Vector3f, speed to set on the ball
	 */
	public void setSpeed(final Vector3f vel) {
		phy.setLinearVelocity(vel);
	}
	
	/**
	 * Sets the ball´s location by calling setPhysicsLocation on its physics.
	 * @param loc Vector3f the new location
	 */
	public void setLocation(final Vector3f loc) {
		phy.setPhysicsLocation(loc);
	}
	
	/**
	 * Spawns the ball by adding the physics and adding the spatial to the node.
	 * @param useDefaults used to choose to whether to use default spawn location and speed
	 * @param node Node, the node to which the spatial is attached
	 * @param space PhysicsSpace the physicsSpace to which to add the physics control
	 */
	public void spawn(final Node node, final PhysicsSpace space, final boolean useDefaults) {
		if (phy != null) {
			geom.addControl(phy);
		}
		if (space != null) {
			space.add(phy);
		}
		node.attachChild(geom);
		
		if (useDefaults) {
			setLocation(DEFAULT_SPAWN_LOCATION);
			setSpeed(DEFAULT_INITIAL_SPEED);
		}
	}
	
	/**
	 * Spawns the ball, and places it at a new location.
	 * @param node Node, the node to which the spatial is attached.
	 * @param space PhysicsSpace the physicsSpace to which to add the physics control.
	 * @param location Vector3f, location at which to spawn the ball.
	 */
	@Deprecated
	public void spawn(final Node node, final  PhysicsSpace space, final Vector3f location) {
		spawn(node, space, false);
		setLocation(location);
	}
	
	/**
	 * Spawns the ball, places it at a new location and gives it initial speed.
	 * @param node Node, the node to which the spatial is attached.
	 * @param space PhysicsSpace the physicsSpace to which to add the physics control.
	 * @param location Vector3f, location at which to spawn the ball.
	 * @param speed Vector3f, the ball's initial speed.
	 */
	@Deprecated
	public void spawn(final Node node, final PhysicsSpace space, final Vector3f location
			, final Vector3f speed) {
		spawn(node, space, false);
		setLocation(location);
		setSpeed(speed);
	}
	
	/**
	 * This method returns the geometry of the ball.
	 * @return the geometry of the ball
	 */
	public final Geometry getGeometry() {
		return geom;
	}
	
	public final Spatial resize(float size){
		return geom.scale(size);
	}
	
	public final void addControl(Control c) {
		geom.addControl(c);
	}
	
	public final Control getControl() {
		return phy;
	}

}
