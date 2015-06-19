package com.funkydonkies.controllers;

import com.funkydonkies.curve.CustomCurveMesh;
import com.funkydonkies.curve.SplineCurve;
import com.funkydonkies.factories.PenguinFactory;
import com.funkydonkies.gamestates.PlayState;
import com.funkydonkies.interfaces.MyAbstractRigidBodyControl;
import com.funkydonkies.sounds.PenguinSpawnSound;
import com.funkydonkies.sounds.SoundState;
import com.jme3.app.state.AppStateManager;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.PhysicsTickListener;
import com.jme3.bullet.collision.PhysicsCollisionEvent;
import com.jme3.bullet.collision.PhysicsCollisionListener;
import com.jme3.bullet.collision.shapes.SphereCollisionShape;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;

/**
 * Control class for the penguin. Takes care of collisions between the penguin and the curve.
 */
public class PenguinControl extends MyAbstractRigidBodyControl implements PhysicsTickListener,
		PhysicsCollisionListener {
	protected static final float MAX_DEVIANCE_ON_Z = 0.1f;
	protected static final float MAX_ROTATIONAL_DEVIANCE = 0.1f;
	
	private static final float PENGUIN_SPEED = 15f;
	private static final float Y_DESTROY_THRESHOLD = -10;
	private static final Vector3f INITIAL_SPEED = new Vector3f(50, 0, 0);
	
	private AppStateManager stateManager;
	private Vector3f initialSpawn;
	private float prevY = 0;
	private boolean touchingCurve = false;
	private boolean updateMeshRotation = true;
	private boolean goingDown = false;

	/**
	 * Constructor for ball physics controller.
	 * 
	 * @param sphereCollisionShape
	 *            Collision shape used by the physics
	 * @param mass
	 *            desired mass of the sphere
	 * @param sManager the AppStateManager of the game
	 */
	public PenguinControl(final SphereCollisionShape sphereCollisionShape, final float mass,
			final AppStateManager sManager) {
		super(sphereCollisionShape, mass);
		stateManager = sManager;
	}

	/**
	 * The initialize method for the control. called by super.setSpatial(spatial).
	 */
	public void init() {
		final int yOffSet = 5, xOffSet = -20;
		initialSpawn = new Vector3f(xOffSet, CustomCurveMesh.getLaunchPadHeight() + yOffSet, 0);
		stateManager.getState(PlayState.class).getPhysicsSpace().add(this);
		stateManager.getState(SoundState.class).queueSound(new PenguinSpawnSound());
		setPhysicsLocation(initialSpawn);
		setLinearVelocity(INITIAL_SPEED);
		prevY = this.getPhysicsLocation().getY();
	}

	/**
	 * Set the physics space and add a tick listener and collision listener to the controller.
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

	@Override
	public void physicsTick(final PhysicsSpace space, final float tpf) {
		if ((this.getPhysicsLocation().getY() - prevY) < -1) {
			goingDown = true;
		} else {
			goingDown = false;
		}
		prevY = this.getPhysicsLocation().getY();
		
		final Vector3f softGrav = new Vector3f(0,-20f,0);
		final Vector3f hardGrav = new Vector3f(0,-30f,0);
		if (goingDown) {
			this.setGravity(hardGrav);
		} else {
			this.setGravity(softGrav);
		}
	}

	/**
	 * Performed before each physics tick. Sets the z location to 0 to restrict the object from
	 * moving on the z-axis.
	 * @param space PhysicsSpace of the scene.
	 * @param tpf float time per frame.
	 */
	@Override
	public void prePhysicsTick(final PhysicsSpace space, final float tpf) {
		final Vector3f loc = this.getPhysicsLocation();
		final Vector3f angularvel = this.getAngularVelocity();

		if (Math.abs(loc.z) > MAX_DEVIANCE_ON_Z) {
			loc.z = 0;
			this.setPhysicsLocation(loc);
		}
		if (Math.abs(angularvel.x) > MAX_ROTATIONAL_DEVIANCE
				|| Math.abs(angularvel.y) > MAX_ROTATIONAL_DEVIANCE) {
			angularvel.y = 0;
			angularvel.x = 0;
			this.setAngularVelocity(angularvel);
		}
		if (touchingCurve) {
			final Vector3f velocity = getLinearVelocity();
			velocity.x = FastMath.interpolateLinear(tpf * 2 * 2, velocity.x, PENGUIN_SPEED);
			setLinearVelocity(velocity);
		}
		
	}

	@Override
	public void update(final float tpf) {
		super.update(tpf);
		final Vector3f direction = getLinearVelocity();
		if (updateMeshRotation) {
			spatial.lookAt(direction, new Vector3f(0, 1, 0));
		}
		if (getPhysicsLocation().y < Y_DESTROY_THRESHOLD) {
			destroy();
		}
		
	}
	
	/**
	 * Listens for collisions. If the ball collides (touches) with the curve and its speed is too
	 * low, increase it so that the ball can move uphill
	 * 
	 * @param event
	 *            a PhysicsCollisionEvent which stores information about the collision
	 */
	public void collision(final PhysicsCollisionEvent event) {
		if (checkCollision(event, SplineCurve.CURVE_NAME, PenguinFactory.PENGUIN_NAME)) {
			touchingCurve = true;
		} else {
			touchingCurve = false;
		}
	}
	
	/**
	 * Disables the rotation of the penguin independent from its collisionshape.
	 */
	public void disableMeshRotation() {
		updateMeshRotation = false;
	}

	
	@Override
	public void destroy() {
		super.destroy();
		stateManager.getState(PlayState.class).getPhysicsSpace().removeCollisionListener(this);
	}
	
}
