package com.funkydonkies.controllers;

import com.jme3.app.state.AppStateManager;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.PhysicsTickListener;
import com.jme3.bullet.collision.PhysicsCollisionListener;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.collision.shapes.SphereCollisionShape;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Sphere;

/**
 * Control for turning penguins into invincible snowballs.
 */
public class GrowingSnowballControl extends PenguinControl implements PhysicsCollisionListener,
		PhysicsTickListener {

	public static final String SNOW_BALL_NAME = "snowball";
	private static final float SCALE_UP_FACTOR = 0.15f;
	private static final float SCALE_TIME = 0.25f;
	private static final float MAX_RADIUS = 10f;
	private static final float SCALE_OFFSET = 0.5f; // Dont touch, should be 1/2
	private float timer = 0;

	/**
	 * Constructor for GrowingSnowBallControl.
	 * 
	 * @param sphereCollisionShape
	 *            the Snow Ball's collisionShape (should be based on the mesh)
	 * @param mass
	 *            the Snow Ball's desired mass
	 * @param sManager
	 *            jme AppStateManager for getting states
	 */
	public GrowingSnowballControl(final SphereCollisionShape sphereCollisionShape,
			final float mass, final AppStateManager sManager) {
		super(sphereCollisionShape, mass, sManager);
	}

	@Override
	public void setPhysicsSpace(final PhysicsSpace space) {
		super.setPhysicsSpace(space);
		space.addCollisionListener(this);
		space.addTickListener(this);
		space.add(this);
	}

	@Override
	public void update(final float tpf) {
		super.update(tpf);
		scaleSnowBall(tpf);
	}

	@Override
	public void init() {
		disableMeshRotation();
		// do nothing
	}

	/**
	 * Scales Snow Ball every THRESHOLD seconds.
	 * 
	 * @param tpf
	 *            elapsed time per frame
	 */
	public void scaleSnowBall(final float tpf) {
		timer += tpf;
		if (timer > SCALE_TIME) {
			final CollisionShape s = getCollisionShape();
			final float radius = ((SphereCollisionShape) s).getRadius();
			timer = 0;
			if (radius < MAX_RADIUS) {
				final Spatial snowBall = ((Node) spatial).getChild(SNOW_BALL_NAME);
//				float r = ((Sphere) ((Geometry) snowBall).getMesh()).radius;
				((Geometry) snowBall).setMesh(new Sphere(30, 30, radius + SCALE_UP_FACTOR));
				setCollisionShape(new SphereCollisionShape(radius + SCALE_UP_FACTOR));
			}
		}
	}

	@Override
	public void physicsTick(final PhysicsSpace space, final float tpf) {
		super.physicsTick(space, tpf);
	}

	/**
	 * Performed before each physics tick. Sets the z location to 0 to restrict the object from
	 * moving on the z-axis.
	 * 
	 * @param space
	 *            The physics space
	 * @param tpf
	 *            time per frame in seconds (time since last frame) for normalizing in faster
	 *            computers
	 */
	public void prePhysicsTick(final PhysicsSpace space, final float tpf) {
		super.prePhysicsTick(space, tpf);
	}
}
