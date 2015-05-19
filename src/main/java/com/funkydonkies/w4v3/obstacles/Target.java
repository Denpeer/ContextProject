package com.funkydonkies.w4v3.obstacles;

import com.funkydonkies.w4v3.Combo;
import com.funkydonkies.w4v3.TargetControl;
import com.jme3.asset.AssetManager;
import com.jme3.bounding.BoundingBox;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.collision.shapes.BoxCollisionShape;
import com.jme3.collision.CollisionResults;
import com.jme3.material.Material;
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
	private TargetControl control;
	private Material mat;
	private static final String UNSHADED_MATERIAL_PATH = "Common/MatDefs/Misc/Unshaded.j3md";
	
	/**
	 * This is the constructor of the Target Class.
	 * @param w the width of the target	
	 * @param h the height of the target
	 * @param d the depth of the target
	 */
	public Target(final double w, final double h, final double d, Node node, 
			AssetManager assetManager, Combo combo) {
		super(w, h, d, node);
		box = new Box((float) w, (float) h, (float) d);
//		box = new Sphere
		control = new TargetControl(
				new BoxCollisionShape(new Vector3f((float)w,(float)h,(float)d)), this, combo);
		mat = new Material(assetManager, UNSHADED_MATERIAL_PATH);
		geom = new Geometry("target", box);
		geom.setMaterial(mat);
		
	}

	/**
	 * This method draws the target.
	 */
	@Override
	public void draw(Material mat, final PhysicsSpace psySpace) {
		final float xTrans = 30f;
		final float zTrans = 0.5f;
		final float yTrans = 1f;
//		final Vector3f vec = new Vector3f(xTrans, yTrans, zTrans);
		geom.setLocalTranslation(xTrans, yTrans, zTrans);

		geom.addControl(control);
		psySpace.add(control);
		node.attachChild(geom);
	}
	
	public void destroy(){
//		control.delete();
//		geom.removeControl(control);
		node.detachChild(geom);
	}
	
	public void respawn(){
		float x = (float) Math.random() * 50;
		float y = (float) Math.random() * 50;
		x = 40;
		y = -2;
		geom.setLocalTranslation(x, y, 1.5f);
//		control.setPhysicsLocation(new Vector3f(x, y, 1.5f));
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
