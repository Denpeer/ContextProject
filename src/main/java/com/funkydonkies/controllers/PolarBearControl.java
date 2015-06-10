package com.funkydonkies.controllers;

import com.funkydonkies.factories.PolarBearFactory;
import com.funkydonkies.gamestates.DifficultyState;
import com.jme3.app.state.AppStateManager;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.collision.PhysicsCollisionEvent;
import com.jme3.bullet.collision.PhysicsCollisionListener;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.GhostControl;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Spatial;

/**
 * Control class for the polar bear. Takes care of collisions between the polar bear and the penguins.
 */
public class PolarBearControl extends GhostControl implements PhysicsCollisionListener {
	private double speed;
	private Vector3f initialLoc;
	private AppStateManager stateManager;
	private static final String BALL_NAME = "standardPenguin";
	private final int destroyTime = 3;
	private float time;
	private float stopCoord;
	private boolean doneMoving;

	/**
	 * The controller for the polar bear. Takes care of the collision between the polar bear and the penguin.
	 * @param shape the collision shape of the polar bear
	 * @param stopX the coordinate the polar bear should stop moving
	 * @param sp the moving speed of the polar bear
	 * @param stateManageranager the AppStateManager
	 * @param loci the initial location of the polar bear
	 */
	public PolarBearControl(final CollisionShape shape, final float stopX, final double sp,
			final AppStateManager stateManageranager, final Vector3f loci) {
		super(shape);
		stateManager = stateManageranager;
		this.speed = sp;
		initialLoc = loci;
		time = 0;
		stopCoord = stopX;
		doneMoving = false;
	}
	
	/** 
	 * This Method calls initialization which should occur after the control has been added to the
	 * spatial. setSpatial(spatial) is called by addControl(control) in Spatial.
	 * @param spatial spatial this control should control
	 */
	@Override
	public void setSpatial(final Spatial spatial) {
		super.setSpatial(spatial);
		initLocation();
	}

	/**
	 * An initialize method for the controller so the location.
	 */
	public final void initLocation() {
		setPhysicsLocation(initialLoc);
		spatial.setLocalTranslation(initialLoc);
	}

	/**
	 * The update method for the contoller.
	 * @param tpf is the time per frame
	 */
	@Override
	public void update(final float tpf) {
		moveSpatial();

		if (doneMoving) {
			space.addCollisionListener(this);
			time += tpf;
			if (time > destroyTime) {
				spatial.getParent().detachChild(spatial);
				this.setEnabled(false);
				spatial.removeControl(this);
				doneMoving = false;
			}
		}
	}

	/**
	 * Set the physics space and add this controller as tick listener.
	 * @param space takes a pre-defined jme3 physicsSpace
	 */
	@Override
	public void setPhysicsSpace(final PhysicsSpace space) {
		super.setPhysicsSpace(space);
		space.addCollisionListener(this);
	}

	/**
	 * This method moves the spatial in the desired direction.
	 */
	private void moveSpatial() {
		Vector3f loc;
		final Vector3f vec = spatial.getLocalTranslation();
		if (spatial != null && initialLoc.getX() < stopCoord && vec.getX() < stopCoord) {
			loc = new Vector3f((float) (vec.getX() + speed), vec.getY(), vec.getZ());
			spatial.setLocalTranslation(loc);
			setPhysicsLocation(loc);

		} else if (spatial != null && initialLoc.getX() >= stopCoord && vec.getX() >= stopCoord) {
			loc = new Vector3f((float) (vec.getX() - speed), vec.getY(), vec.getZ());
			spatial.setLocalTranslation(loc);
			setPhysicsLocation(loc);
		} else {
			doneMoving = true;
		}

	}

	/**
	 * The renderer for the control.
	 * @param rm the renderManager
	 * @param vp the viewPort
	 */
	protected void controlRender(final RenderManager rm, final ViewPort vp) {
		// TODO Auto-generated method stub

	}

	/**
	 * Handles a collision between ball and target. Calls methods to increase the combo and respawn
	 * the target.
	 * @param event PhysicsCollisionEvent containing information about the collision
	 */
	public void collision(final PhysicsCollisionEvent event) {
		if (doneMoving && event.getNodeA() != null && event.getNodeB() != null) {
			if (PolarBearFactory.POLARBEAR_NAME.equals(event.getNodeA().getName())
					&& BALL_NAME.equals(event.getNodeB().getName())) {
				stateManager.getState(DifficultyState.class).resetDiff();
				event.getNodeB().removeFromParent();
				((RigidBodyControl) event.getNodeB().getControl(PenguinControl.class))
						.setEnabled(false);

			} else if (BALL_NAME.equals(event.getNodeA().getName())
					&& PolarBearFactory.POLARBEAR_NAME.equals(event.getNodeB().getName())) {
				stateManager.getState(DifficultyState.class).resetDiff();
				event.getNodeA().removeFromParent();
				((RigidBodyControl) event.getNodeA().getControl(PenguinControl.class))
						.setEnabled(false);
			}
		}
	}
	
}
