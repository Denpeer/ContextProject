package com.funkydonkies.controllers;

import com.funkydonkies.factories.PenguinFactory;
import com.funkydonkies.factories.PolarBearFactory;
import com.funkydonkies.gamestates.DifficultyState;
import com.funkydonkies.gamestates.PlayState;
import com.funkydonkies.interfaces.MyAbstractGhostControl;
import com.funkydonkies.sounds.ObstacleCollisionSound;
import com.funkydonkies.sounds.ObstacleSpawnSound;
import com.funkydonkies.sounds.SoundState;
import com.jme3.app.state.AppStateManager;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.collision.PhysicsCollisionEvent;
import com.jme3.bullet.collision.PhysicsCollisionListener;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial.CullHint;

/**
 * Control class for the polar bear. Takes care of collisions between the polar bear and the
 * penguins.
 */
public class PolarBearControl extends MyAbstractGhostControl implements PhysicsCollisionListener {

	private Vector3f initialLoc;

	private static final int DESTROY_TIME = 3;

	private boolean doneMoving = false;

	private static final float BLINKING_TIME = 0.5f;
	private static final float SPEED = 1.5f;

	private float blinkTime = 0;
	private float killTime = 0;
	private float time = 0;
	private float stopCoord;
	private AppStateManager stateManager;
	private DifficultyState diffState;


	/**
	 * The controller for the polar bear. Takes care of the collision between the polar bear and the
	 * penguin.
	 * 
	 * @param shape
	 *            the collision shape of the polar bear
	 * @param stopX
	 *            the coordinate the polar bear should stop moving
	 * @param sManager
	 *            the AppStateManager
	 * @param iLoc
	 *            the initial location of the polar bear
	 */
	public PolarBearControl(final CollisionShape shape, final AppStateManager sManager,
			final float stopX, final Vector3f iLoc) {
		super(shape);
		initialLoc = iLoc;
		stopCoord = stopX;
		diffState = sManager.getState(DifficultyState.class);
		stateManager = sManager;
	}

	/**
	 * Set the physics space and add this controller as tick listener.
	 * @param space PhysicsSpace of the scene.
	 */
	@Override
	public void setPhysicsSpace(final PhysicsSpace space) {
		super.setPhysicsSpace(space);
		space.addCollisionListener(this);
	}

	/**
	 * Initialized the control, eg. sets location and plays sound.
	 * @see com.funkydonkies.interfaces.MyAbstractGhostControl#init()
	 */
	@Override
	public void init() {
		stateManager.getState(SoundState.class).queueSound(new ObstacleSpawnSound());
		spatial.setLocalTranslation(initialLoc);
		setPhysicsLocation(initialLoc);
		stateManager.getState(PlayState.class).getPhysicsSpace().add(this);
	}

	/**
	 * The update method for the contoller.
	 * @param tpf float the time since last frame.
	 */
	@Override
	public void update(final float tpf) {
		moveSpatial();

		if (doneMoving) {
			if (spatial.getCullHint() == CullHint.Always) {
				spatial.setCullHint(CullHint.Dynamic);
			}
			time += tpf;
			if (time > DESTROY_TIME) {
				doneMoving = false;
				killBear(tpf);
			}
		} else {
			blink(tpf);
		}
	}

	/**
	 * Removes the bear after moving it down.
	 * @param tpf time per frame
	 */
	public void killBear(final float tpf) {
		killTime += tpf;
		final float scale = 5;
		spatial.move(0, -SPEED * tpf * scale, 0);
		blink(tpf);
		if (killTime > DESTROY_TIME + 1) {
			spatial.removeFromParent();
			setEnabled(false);
		}
	}

	/**
	 *  Makes the spatial blink.
	 *  @param tpf time per frame
	 */
	public void blink(final float tpf) {
		blinkTime += tpf;
		if (blinkTime > BLINKING_TIME) {
			blinkTime = 0;
			toggleCulling();
		}
	}

	/**
	 * Toggles visibilty of spatial.
	 */
	public void toggleCulling() {
		if (spatial.getCullHint() != CullHint.Always) {
			spatial.setCullHint(CullHint.Always);
		} else {
			spatial.setCullHint(CullHint.Dynamic);
		}
	}

	/**
	 * This method moves the spatial in the desired direction.
	 */
	private void moveSpatial() {
		Vector3f loc;
		final Vector3f vec = spatial.getLocalTranslation();
		if (initialLoc.getX() < stopCoord && vec.getX() < stopCoord) {
			loc = new Vector3f((float) (vec.getX() + SPEED), vec.getY(), vec.getZ());
			spatial.setLocalTranslation(loc);
			setPhysicsLocation(loc);
		} else if (initialLoc.getX() >= stopCoord && vec.getX() >= stopCoord) {
			loc = new Vector3f((float) (vec.getX() - SPEED), vec.getY(), vec.getZ());
			spatial.setLocalTranslation(loc);
			setPhysicsLocation(loc);
		} else {
			doneMoving = true;
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
		if (doneMoving
				&& checkCollision(event, PolarBearFactory.POLAR_BEAR_NAME,
						PenguinFactory.PENGUIN_NAME)) {
			stateManager.getState(SoundState.class).queueSound(new ObstacleCollisionSound());
			diffState.resetDiff();
			destroy(event, PenguinFactory.PENGUIN_NAME);
		}
	}
}
