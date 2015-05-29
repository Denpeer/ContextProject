package com.funkydonkies.obstacles;

import com.funkydonkies.controllers.SplineCurveController;
import com.funkydonkies.controllers.TargetControl;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.collision.shapes.BoxCollisionShape;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;

/**
 * The target class.
 */
public class Target extends Obstacle {
	private static final Vector3f INITIAL_SPAWN_LOCATION = new Vector3f(50f, 10f, 1f);
	private Geometry geom;
	private final Box box;
	private TargetControl control;
//	private static final String UNSHADED_MATERIAL_PATH = "Common/MatDefs/Misc/Unshaded.j3md";
	
	/**
	 * This is the constructor of the Target Class.
	 * @param w the width of the target	
	 * @param h the height of the target
	 * @param d the depth of the target
	 * @param x the x coordinate of the box
	 * @param y the y coordinate of the box
	 * @param z the z coordinate of the box
	 * @param node Node to attach the target to the scene
	 */
	public Target(final float w, final float h, final float d,
			final float x, final float y, final float z, final Node node) {
		super(w, h, d, x, y, z, node);
		box = new Box(w, h, d);
		control = new TargetControl(
				new BoxCollisionShape(new Vector3f((float) w, (float) h, (float) d)), this);
//		mat = new Material(assetManager, UNSHADED_MATERIAL_PATH);
		geom = new Geometry("target", box);
//		geom.setMaterial(mat);
		
	}

	/**
	 * This method draws the target.
	 * @param m Material needed because of the abstract class, not used
	 * @param phySpace physics space to attach control to
	 */
	@Override
	public void draw(final Material m, final PhysicsSpace phySpace) {
		geom.setMaterial(m);
		geom.setLocalTranslation(INITIAL_SPAWN_LOCATION);
		geom.addControl(control);
		phySpace.add(control);
		super.getNode().attachChild(geom);
		respawn();
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
		final float x = (float) Math.random() * (SplineCurveController.POINT_DISTANCE 
				* SplineCurveController.getAmountOfControlPoints());
		
		final float y = (float) Math.random() * SplineCurveController.POINTS_HEIGHT + 20;
//		final Vector3f respawnlocation = new Vector3f(40f, -2f, 1.5f);
		final Vector3f respawnlocation = new Vector3f(x, y, 1.5f);
		control.setPhysicsLocation(respawnlocation);
		geom.setLocalTranslation(respawnlocation);
	}
	
	/**
	 * Returns the targets current location.
	 * Obstacles getter methods do not return the up to date location after a respawn.
	 * @return the up to date current location of the target
	 */
	public Vector3f getLocation() {
		return geom.getLocalTranslation();
	}
	
	/**
	 * Returns the Targetcontrol of the target.
	 * @return control TargetControl, the controll that is controlling this target
	 */
	public TargetControl getControl() {
		return control;
	}
}
