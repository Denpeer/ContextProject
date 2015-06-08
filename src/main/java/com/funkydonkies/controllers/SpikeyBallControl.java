package com.funkydonkies.controllers;

import com.funkydonkies.curve.CustomCurveMesh;
import com.funkydonkies.gamestates.PlayState;
import com.jme3.app.state.AppStateManager;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.PhysicsTickListener;
import com.jme3.bullet.collision.PhysicsCollisionEvent;
import com.jme3.bullet.collision.PhysicsCollisionListener;
import com.jme3.bullet.collision.shapes.SphereCollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.math.Vector3f;

/**
 * Physics controller class extending from RigidBodyControl specified to
 * restrict the balls from moving over the z-axis.
 */
public class SpikeyBallControl extends RigidBodyControl implements
		PhysicsTickListener, PhysicsCollisionListener {
	private static final float MAX_DEVIANCE_ON_Z = 0.1f;
	private static final float MAX_ROTATIONAL_DEVIANCE = 0.1f;
	private static final String WHALE_NAME = "killerWhale";
	private static final String SPIKEY_BALL_NAME = "spikeyBall";
	private static final String PENGUIN_NAME = "standardPenguin";
	private static final String CURVE_NAME = "curve";
	private Vector3f initialSpawn;
	private Vector3f initialSpeed = new Vector3f(0, 1, 0);
	private final AppStateManager sm;

	/**
	 * Constructor for ball physics controller.
	 * 
	 * @param sphereCollisionShape
	 *            Collision shape used by the physics
	 * @param f
	 *            mass of the sphere
	 * @param sm
	 *            the AppStateManager
	 */
	public SpikeyBallControl(final SphereCollisionShape sphereCollisionShape,
			final AppStateManager stateManager, final float f) {
		super(sphereCollisionShape, f);
		this.sm = stateManager;
	}

	/**
	 * The initialize method for the control.
	 */
	public void init() {
		final int yOffSet = 5, xOffSet = 0;
		initialSpawn = new Vector3f(xOffSet,
				CustomCurveMesh.getLaunchPadHeight() + yOffSet, 0);
		setLocation(initialSpawn);
		setSpeed(initialSpeed);
	}

	/**
	 * Set the physics space and add this controller as tick listener.
	 * 
	 * @param space
	 *            takes a pre-defined jme3 physicsSpace
	 */
	@Override
	public void setPhysicsSpace(final PhysicsSpace space) {
		super.setPhysicsSpace(space);
		space.addTickListener(this);
		space.addCollisionListener(this);
	}

	/**
	 * Performed at each physics tick. z-axis.
	 * 
	 * @param space
	 *            The physics space
	 * @param tpf
	 *            time per frame in seconds (time since last frame) for
	 *            normalizing in faster computers
	 */
	public void physicsTick(final PhysicsSpace space, final float tpf) {
	}

	/**
	 * Performed before each physics tick. Sets the z location to 0 to restrict
	 * the object from moving on the z-axis.
	 * 
	 * @param space
	 *            The physics space
	 * @param tpf
	 *            time per frame in seconds (time since last frame) for
	 *            normalizing in faster computers
	 */
	public void prePhysicsTick(final PhysicsSpace space, final float tpf) {
		final Vector3f loc = getPhysicsLocation();
		final Vector3f angularvel = getAngularVelocity();
		// velocity.z = 0;
		if (Math.abs(loc.z) > MAX_DEVIANCE_ON_Z) {
			loc.z = 0;
			setPhysicsLocation(loc);
		}
		if (Math.abs(angularvel.x) > MAX_ROTATIONAL_DEVIANCE
				|| Math.abs(angularvel.y) > MAX_ROTATIONAL_DEVIANCE) {
			angularvel.y = 0;
			angularvel.x = 0;
			setAngularVelocity(angularvel);
		}
	}

	/**
	 * Sets the speed for the Ball by calling setLinVelocity on the physics.
	 * 
	 * @param vel
	 *            Vector3f, speed to set on the ball
	 */
	public void setSpeed(final Vector3f vel) {
		setLinearVelocity(vel);
	}

	/**
	 * Sets the ball´s location by calling setPhysicsLocation on its physics.
	 * 
	 * @param loc
	 *            Vector3f the new location
	 */
	public void setLocation(final Vector3f loc) {
		setPhysicsLocation(loc);
	}

	/**
	 * Listens for collisions. If the spikeyball collides (touches) with the
	 * penguin balls then remove the penguin balls
	 * 
	 * @param event
	 *            a PhysicsCollisionEvent which stores information about the
	 *            collision
	 */
	public void collision(final PhysicsCollisionEvent event) {
		if (event.getNodeA() != null && event.getNodeB() != null) {
			if (SPIKEY_BALL_NAME.equals(event.getNodeA().getName())
					&& PENGUIN_NAME.equals(event.getNodeB().getName())) {
				sm.getState(PlayState.class).getRootNode()
						.detachChild(event.getNodeB());
				((RigidBodyControl) event.getNodeB().getControl(0))
						.setEnabled(false);
			}
			if (PENGUIN_NAME.equals(event.getNodeA().getName())
					&& SPIKEY_BALL_NAME.equals(event.getNodeB().getName())) {
				sm.getState(PlayState.class).getRootNode()
						.detachChild(event.getNodeA());
				((RigidBodyControl) event.getNodeA().getControl(0))
						.setEnabled(false);
			}
		}
	}
}
