package com.funkydonkies.geometrys.obstacles;

import com.funkydonkies.geometrys.Obstacle;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;

/**
 * This class represents the MovingBox obstacle.
 * @author SDumasy
 *
 */
public class KillerWhale extends Obstacle {
	
	private Geometry geom;
	private Box box;
	private boolean moveUp;
	private final RigidBodyControl phys;
	private static final float BOX_SPEED = 10f;

	/**
	 * The constructor of the class.
	 * @param width the width of the box
	 * @param height the height of the box 
	 * @param depth the height of the box
	 * @param node Node to attach the spatial to to draw in on the scene.
	 * @param x the x coordinate of the box
	 * @param y the y coordinate of the box
	 * @param z the z coordinate of the box
	 */
	public KillerWhale(final float width, final float height, final float depth,
			final float x, final float y, final float z, final Node node) {
		super(width, height, depth, x, y, z, node);
		box = new Box(width, height, depth);
		geom = new Geometry("closingBox", box);
		phys = new RigidBodyControl(0f);
		moveUp = true;
	}
	
	/**
	 * This method draws the box.
	 * @param mat the material of the box
	 * @param psySpace the physic space 
	 */
	@Override
	public final void draw(final Material mat, final PhysicsSpace psySpace) {
		geom.setMaterial(mat);
		geom.addControl(phys);
		psySpace.add(phys);
		phys.setPhysicsLocation(new Vector3f().add((float) getxCoord(), (float) getyCoord(), (float) getzCoord()));
		geom.setLocalTranslation(phys.getPhysicsLocation());
		super.getNode().attachChild(geom);
	}
	/**
	 * This method moves the box up and down.
	 * @param tpf float, time per frame; used for normalizing the speed of different speed machines
	 */
	public final void move(final float tpf) {
		final Vector3f vec = geom.getLocalTranslation();
		if (moveUp) {
			vec.setY(vec.getY() + (BOX_SPEED * tpf));
			phys.setPhysicsLocation(vec);
			geom.setLocalTranslation(phys.getPhysicsLocation());
			if (vec.getY() > getHeight()) {
				moveUp = false;
			}	
		} else {
			vec.setY(vec.getY() - (BOX_SPEED * tpf));
			phys.setPhysicsLocation(vec);
			geom.setLocalTranslation(phys.getPhysicsLocation());
			if (vec.getY() < -getHeight()) {
				moveUp = true;
			}
		}
	}
	
	/**
	 * This method returns the geometry.
	 * @return the geometry
	 */
	public final Geometry getGeometry() {
		return geom;
	}
}
