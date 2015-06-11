package com.funkydonkies.controllers;

import com.funkydonkies.curve.SplineCurve;
import com.funkydonkies.factories.PenguinFactory;
import com.funkydonkies.geometrys.penguins.Snowball;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.PhysicsTickListener;
import com.jme3.bullet.collision.PhysicsCollisionEvent;
import com.jme3.bullet.collision.PhysicsCollisionListener;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.collision.shapes.SphereCollisionShape;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

/**
 * Control for turning penguins into invincible snowballs.
 */
public class GrowingSnowballControl extends PenguinControl implements PhysicsCollisionListener,
		PhysicsTickListener {
	private static final String SNOW_BALL_NAME = "snowball";
	private static final float SCALE_UP_FACTOR = 0.3f;
	private static final float THRESHOLD = 0.5f;
	private static final float SCALE_OFFSET = 0.5f;
	private float timer = 0;
	private boolean canScale = false;

	/**
	 * Constructor for GrowingSnowBallControl.
	 * 
	 * @param sphereCollisionShape
	 *            the Snow Ball's collisionShape (should be based on the mesh)
	 * @param mass
	 *            the Snow Ball's desired mass
	 */
	public GrowingSnowballControl(final SphereCollisionShape sphereCollisionShape, final float mass) {
		super(sphereCollisionShape, mass);

	}

	@Override
	public void setPhysicsSpace(final PhysicsSpace space) {
		super.setPhysicsSpace(space);
		space.addCollisionListener(this);
		space.addTickListener(this);
	}

	@Override
	public void update(final float tpf) {
		super.update(tpf);
		scaleSnowBall(tpf);
	}

	/**
	 * Scales Snow Ball every THRESHOLD seconds.
	 * 
	 * @param tpf
	 *            elapsed time per frame
	 */
	public void scaleSnowBall(final float tpf) {
		timer += tpf;
		if (timer > THRESHOLD) {
			timer = 0;
			canScale = true;
			final Spatial snowBall = ((Node) spatial).getChild(SNOW_BALL_NAME);
			((Snowball) snowBall).setRadius(((Snowball) snowBall).getRadius() + SCALE_UP_FACTOR);

			final Vector3f loc = snowBall.getLocalTranslation();
			loc.x = loc.x - SCALE_OFFSET * SCALE_UP_FACTOR;
			loc.y = loc.y - SCALE_OFFSET * SCALE_UP_FACTOR;

			final CollisionShape s = getCollisionShape();
			final float radius = ((SphereCollisionShape) s).getRadius();
			setCollisionShape(new SphereCollisionShape(radius + SCALE_UP_FACTOR * SCALE_OFFSET));
			snowBall.setLocalTranslation(loc);
		}
	}

	/**
	 * Scales the Snow Balls to normal.
	 */
	public void scaleBack() {
		spatial.scale(1 / spatial.getWorldScale().x);
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
		final Vector3f a = getAngularVelocity();
		a.x = 0;
		a.y = 0;
		setAngularVelocity(a);
	}

	@Override
	public void collision(final PhysicsCollisionEvent event) {
		if (checkCollision(event, PenguinFactory.STANDARD_PENGUIN_NAME, SplineCurve.CURVE_NAME)) {
			if (canScale) {
				canScale = false;
			}
		}
	}

}
