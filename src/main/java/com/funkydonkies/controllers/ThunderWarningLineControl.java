package com.funkydonkies.controllers;

import com.funkydonkies.gamestates.CurveState;
import com.funkydonkies.interfaces.MyAbstractControl;
import com.jme3.app.state.AppStateManager;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;

/**
 * Control class for the warning line that warns for thunder.
 * 
 */
public class ThunderWarningLineControl extends MyAbstractControl {

	private static final float REMOVE_WARNING = 2;

	private float time = 0;

	private Vector3f initialSpawn;
	private CurveState curveState;

	private boolean setHitLoc = false;
	private ThunderControl linkedControl;

	/**
	 * Constructor method for warning line control. Sets the initialSpawn location.
	 * 
	 * @param sManager
	 *            the stateManager
	 * @param xOff
	 *            the y offset of the spawn location
	 * @param yOff
	 *            the x offset of the spawn location
	 */
	public ThunderWarningLineControl(final AppStateManager sManager, final float xOff,
			final float yOff) {
		initialSpawn = new Vector3f(xOff, yOff, 0);
		curveState = sManager.getState(CurveState.class);
	}

	/**
	 * This Method calls initialization which should occur after the control has been added to the
	 * spatial. setSpatial(spatial) is called by addControl(control) in Spatial.
	 * 
	 * @param spatial
	 *            the spatial to set.
	 */
	@Override
	public void setSpatial(final Spatial spatial) {
		super.setSpatial(spatial);
		init();
	}

	@Override
	public void init() {
		spatial.setLocalTranslation(initialSpawn);
	}

	@Override
	protected void controlUpdate(final float tpf) {
		time += tpf;

		final float updateX = curveState.getHighestPointX();
		if (updateX > 0 && !setHitLoc) {
			moveToX(updateX);
		}

		if (time > 1) {
			if (linkedControl != null && !setHitLoc) {
				linkedControl.setHitLocation(updateX);
				setHitLoc = true;
			}
			if (time > REMOVE_WARNING) {
				spatial.removeFromParent();
				setEnabled(false);
			}
		}
	}

	/**
	 * Moves spatial to desired c location.
	 * 
	 * @param updateX
	 *            desired x location
	 */
	public void moveToX(final float updateX) {
		spatial.setLocalTranslation(updateX, spatial.getLocalTranslation().y,
				spatial.getLocalTranslation().z);
	}

	/**
	 * Links an Thundercontrol to this warning line so that its hit location can be set.
	 * @param control the Thundercontrol
	 */
	public void setLinkedControl(final ThunderControl control) {
		linkedControl = control;
	}

}
