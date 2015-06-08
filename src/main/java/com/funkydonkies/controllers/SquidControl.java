package com.funkydonkies.controllers;

import com.funkydonkies.factories.PenguinFactory;
import com.funkydonkies.gamestates.CurveState;
import com.funkydonkies.gamestates.DifficultyState;
import com.jme3.app.state.AppStateManager;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.collision.PhysicsCollisionEvent;
import com.jme3.bullet.collision.PhysicsCollisionListener;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.GhostControl;
import com.jme3.math.Vector3f;

/**
 * Control class for the target. Takes care of collisions between the ball and target.
 */
public class SquidControl extends GhostControl implements PhysicsCollisionListener {
	private static final String TARGET_NAME = "squid";
	private static final Vector3f INITIAL_SPAWN_LOCATION = new Vector3f(250f, 10f, 1f);
	private static final float Y_PADDING = CurveState.POINTS_HEIGHT * 0.2f;
	private DifficultyState diffState;
	private boolean moveRight = true;
	private float time = 0;

	/**
	 * Constructor method for target control.
	 * 
	 * @param shape
	 *            Collisionshape for the target
	 */
	public SquidControl(final CollisionShape shape, AppStateManager sm) {
		super(shape);
		diffState = sm.getState(DifficultyState.class);
	}

	/**
	 * An initialize method for the controller.
	 */
	public void init() {
		setPhysicsLocation(INITIAL_SPAWN_LOCATION);
		spatial.setLocalTranslation(INITIAL_SPAWN_LOCATION);
		respawn();
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
		space.addCollisionListener(this);
	}

	/**
	 * Removes the control from the physics space.
	 */
	public void delete() {
		space.removeCollisionListener(this);
		space.remove(this);
		spatial.getParent().detachChild(spatial);
	}

	public void update(float tpf) {
		time += tpf;
		if (time > 4) {
			time = 0;
			if (moveRight) {
				moveRight = false;
			} else {
				moveRight = true;
			}
		}
		if (moveRight) {
			final Vector3f vec = spatial.getLocalTranslation();
			Vector3f loc = new Vector3f((float) (vec.getX() - 0.5), vec.getY(), vec.getZ());
			spatial.setLocalTranslation(loc);
			this.setPhysicsLocation(loc);
		} else {
			final Vector3f vec = spatial.getLocalTranslation();
			Vector3f loc = new Vector3f((float) (vec.getX() + 0.5), vec.getY(), vec.getZ());
			spatial.setLocalTranslation(loc);
			this.setPhysicsLocation(loc);
		}

	}

	/**
	 * Handles a collision between ball and target. Calls methods to increase the combo and respawn
	 * the target.
	 * 
	 * @param event
	 *            PhysicsCollisionEvent containing information about the collision
	 */
	public void collision(final PhysicsCollisionEvent event) {
		if (event.getNodeA() != null && event.getNodeB() != null) {
			if (TARGET_NAME.equals(event.getNodeA().getName())
					&& PenguinFactory.STANDARD_PENGUIN_NAME.equals(event.getNodeB().getName())) {
				diffState.incDiff();
				diffState.incDiff();
				event.getNodeA().removeFromParent();
				((GhostControl) event.getNodeA().getControl(SquidControl.class)).setEnabled(false);
				diffState.activateSnowBallPowerup();
			} else if (PenguinFactory.STANDARD_PENGUIN_NAME.equals(event.getNodeA().getName())
					&& TARGET_NAME.equals(event.getNodeB().getName())) {
				diffState.incDiff();
				diffState.incDiff();
				event.getNodeB().removeFromParent();
				((GhostControl) event.getNodeB().getControl(SquidControl.class)).setEnabled(false);
				diffState.activateSnowBallPowerup();
			}
		}

	}

	/**
	 * Respawn the target at a reachable location. TODO make the spawn location random and make sure
	 * its reachable
	 */
	public void respawn() {
		final float x = (float) Math.random()
				* (CurveState.POINT_DISTANCE * CurveState.DEFAULT_CONTROL_POINTS_COUNT);

		final float y = (float) Math.random() * CurveState.POINTS_HEIGHT + Y_PADDING;
		final Vector3f respawnlocation = new Vector3f(x, y, 1.5f);
		setPhysicsLocation(respawnlocation);
		spatial.setLocalTranslation(respawnlocation);
	}

}
