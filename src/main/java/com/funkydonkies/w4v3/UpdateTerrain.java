package com.funkydonkies.w4v3;

import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.PhysicsTickListener;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.terrain.geomipmap.TerrainQuad;

/**
 * Physics controller class extending from RigidBodyControl specified to 
 * restrict the balls from moving over the z-axis.
 */
public class UpdateTerrain implements PhysicsTickListener {
	
	private TerrainQuad terrain;
	
	/**
	 * Constructor for UpdateTerrain, adds Listener to physicsSpace.
	 * @param sphereCollisionShape Collision shape used by the physics
	 * @param f Diameter of the sphere
	 */
	public UpdateTerrain(final PhysicsSpace space, final TerrainQuad t) {
		space.addTickListener(this);
		this.terrain = t;
	}
	

	/**
	 * Performed at each physics tick.
	 * @param space The physics space 
	 * @param tpf time per frame in seconds (time since last frame) 
	 * 	for normalizing in faster computers 
	 */
	public void physicsTick(final PhysicsSpace space, final float tpf) {
		
	}
	
	/**
	 * Performed before each physics tick.
	 * Updates the collisionShape of the terrain.
	 * @param space The physics space 
	 * @param tpf time per frame in seconds (time since last frame) 
	 * 	for normalizing in faster computers 
	 */
	public void prePhysicsTick(final PhysicsSpace space, 
			final float tpf) {
        CollisionShape colShape = CollisionShapeFactory.createDynamicMeshShape(terrain); 
//        CollisionShape colShape = new HeightfieldCollisionShape(new float[1]);
//        HullCollisionShape colShape = new HullCollisionShape(terrain);
        terrain.getControl(RigidBodyControl.class).setCollisionShape(colShape);
	}

}
