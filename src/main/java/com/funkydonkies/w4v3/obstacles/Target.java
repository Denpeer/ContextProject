package com.funkydonkies.w4v3.obstacles;

import com.jme3.bounding.BoundingBox;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.collision.CollisionResults;
import com.jme3.material.Material;
import com.jme3.math.Transform;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;

/**
 * The target class.
 * @author SDumasy
 *
 */
public class Target extends Obstacle {
	private Geometry geom;
	private final Box box;
	private BoundingBox bBox;
	
	/**
	 * This is the constructor of the Target Class.
	 * @param w the width of the target	
	 * @param h the height of the target
	 * @param d the depth of the target
	 * @param x the x coordinate of the box
	 * @param y the y coordinate of the box
	 * @param z the z coordinate of the box
	 */
	public Target(final double w, final double h, final double d,
			final double x, final double y, final double z) {
		super(w, h, d, x, y, z);
		box = new Box((float) w, (float) h, (float) d);
		
	}

	/**
	 * This method draws the target.
	 * @param mat the material
	 * @param psySpace the physic space
	 * @param rootNode the node where everything gets attached to
	 */
	@Override
	public void draw(final Material mat, final PhysicsSpace psySpace, final Node rootNode) {
		geom = new Geometry("target", box);
		final float xTrans = 30f;
		final float zTrans = 0.5f;
		final float yTrans = 1f;
		final Vector3f vec = new Vector3f(xTrans, yTrans, zTrans);
		final Transform trans = new Transform(vec);
		geom.setLocalTranslation(xTrans, yTrans, zTrans);
		geom.setMaterial(mat);
		bBox = (BoundingBox) geom.getModelBound();
		bBox = (BoundingBox) bBox.transform(trans);
		rootNode.attachChild(geom);
	}
	
	/**
	 * This method checks if something collides with the target.
	 * @param rootNode the node for the root
	 */
	public void collides(final Node rootNode) {
		
		for (int i = 0; i < rootNode.getChildren().size(); i++) {
			if (("ball").equals(rootNode.getChild(i).getName())) {
				final CollisionResults results = new CollisionResults();
				rootNode.getChild(i).collideWith(bBox, results);
				if (results.size() > 0) {
					rootNode.detachChild(geom);
				}
			}
		}
	}

}
