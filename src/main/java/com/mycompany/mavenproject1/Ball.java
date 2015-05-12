package com.mycompany.mavenproject1;

import com.jme3.asset.AssetManager;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.collision.shapes.SphereCollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Sphere;

/**
 * @author Jonathan
 *
 */
public class Ball {
	private Sphere sphere;
	private Geometry geom;
	private Material mat;
	private RigidBodyControl phy;
	
	public Ball(float radius, AssetManager assetManager){
		sphere = new Sphere(20, 20, radius);
		geom = new Geometry("ball", sphere);
		mat = new Material(assetManager,
				"Common/MatDefs/Misc/Unshaded.j3md");
		mat.setColor("Color", ColorRGBA.Blue);
		geom.setMaterial(mat);
		phy = new PhysicsController(new SphereCollisionShape(radius), radius * 2);
		phy.setRestitution(1);
	}
	
	public Ball(Sphere shape, Geometry geom, Material mat, RigidBodyControl physics) {
		sphere = shape;
		this.geom = geom;
		this.mat = mat;
		geom.setMaterial(mat);
		phy = physics;
	}
	
	/**
	 * For setting the color property of the ball
	 * TODO name may not be nessecary 
	 * @param name Name
	 * @param value
	 */
	public void setColor(String name, ColorRGBA value){
		mat.setColor(name, value);
	}
	
	public void setSpeed(Vector3f vel){
		phy.setLinearVelocity(vel);
	}
	
	public void setLocation(Vector3f loc){
		phy.setPhysicsLocation(loc);
	}
	
	public void spawn(Node node, PhysicsSpace space){
		if (phy != null)
			geom.addControl(phy);
		if (space != null)
			space.add(phy);
		node.attachChild(geom);
	}
	
	public void spawn(Node node, PhysicsSpace space, Vector3f location){
		if (phy != null)
			geom.addControl(phy);
		if (space != null)
			space.add(phy);
		node.attachChild(geom);
		setLocation(location);
	}
	
	public void spawn(Node node, PhysicsSpace space, Vector3f location, Vector3f speed){
		if (phy != null)
			geom.addControl(phy);
		if (space != null)
			space.add(phy);
		node.attachChild(geom);
		setLocation(location);
		setSpeed(speed);
	}
	
}
