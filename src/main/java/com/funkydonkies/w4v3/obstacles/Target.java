package com.funkydonkies.w4v3.obstacles;

import com.funkydonkies.w4v3.Combo;
import com.funkydonkies.w4v3.TargetControl;
import com.jme3.asset.AssetManager;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.collision.shapes.BoxCollisionShape;
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
	private static final Vector3f INITIAL_SPAWN_LOCATION = new Vector3f(30f, 0.5f, 1f);
	private Geometry geom;
	private final Box box;
	private TargetControl control;
	private Material mat;
	private static final String UNSHADED_MATERIAL_PATH = "Common/MatDefs/Misc/Unshaded.j3md";
	
	/**
	 * This is the constructor of the Target Class.
	 * @param w the width of the target	
	 * @param h the height of the target
	 * @param d the depth of the target
	 * @param x the x coordinate of the box
	 * @param y the y coordinate of the box
	 * @param z the z coordinate of the box
	 * @param node Node to attach the target to the scene
	 * @param assetManager assetmanager to create the material
	 * @param combo Combo to create target control
	 */
	public Target(final double w, final double h, final double d,
			final double x, final double y, final double z, final Node node, 
			final AssetManager assetManager, final Combo combo) {
		super(w, h, d, x, y, z, node);
		box = new Box((float) w, (float) h, (float) d);
		control = new TargetControl(
				new BoxCollisionShape(new Vector3f((float) w, (float) h, (float) d)), this, combo);
		mat = new Material(assetManager, UNSHADED_MATERIAL_PATH);
		geom = new Geometry("target", box);
		geom.setMaterial(mat);
		
	}

	/**
	 * This method draws the target.
	 * @param m Material needed because of the abstract class, not used
	 * @param phySpace physics space to attach control to
	 */
	@Override
	public void draw(final Material m, final PhysicsSpace phySpace) {
		geom.setLocalTranslation(INITIAL_SPAWN_LOCATION);

		geom.addControl(control);
		phySpace.add(control);
		super.getNode().attachChild(geom);
	}
	
	/**
	 * Removes the target from the scene.
	 * DOES NOT REMOVE THE CONTROL FROM THE GAME (yet)
	 */
	public void destroy() {
//		control.delete();
//		geom.removeControl(control);
		super.getNode().detachChild(geom);
	}
	
	/**
	 * Respawn the target at a reachable location.
	 * TODO make the spawn location random and make sure its reachable
	 */
	public void respawn() {
		float x = (float) Math.random() * 200;
		float y = (float) Math.random() * 200;
		final Vector3f respawnlocation = new Vector3f(x, y, 1.5f);
		geom.setLocalTranslation(respawnlocation);
	}
}
