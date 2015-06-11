package com.funkydonkies.controllers;

import com.funkydonkies.gamestates.CurveState;
import com.jme3.app.state.AppStateManager;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;

/**
 * Control class for the warning line that warns for thunder.
 * 
 * @author Olivier Dikken
 *
 */
public class ThunderWarningLineControl extends AbstractControl {
	private float time = 0;

	private Vector3f initialSpawn;
	private CurveState curveState;

	/**
	 * Constructor method for warning line control. Sets the initialSpawn
	 * location.
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
	 * This Method calls initialization which should occur after the control has
	 * been added to the spatial. setSpatial(spatial) is called by
	 * addControl(control) in Spatial.
	 * 
	 * @param spatial
	 *            spatial this control should control
	 */
	@Override
	public void setSpatial(final Spatial spatial) {
		super.setSpatial(spatial);
		initLocation();
	}

	/**
	 * An initialize method for the controller.
	 */
	public void initLocation() {
		spatial.setLocalTranslation(initialSpawn);
	}

	@Override
	protected void controlUpdate(final float tpf) {
		time += tpf;

		final float updateX = curveState.getHighestPointX();
		if (updateX > 0) {
			moveToX(updateX);
		}

		if (time > 1) {
			detach();
			setEnabled(false);
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
	 * Detaches spatial from scene.
	 */
	public void detach() {
		spatial.getParent().detachChild(spatial);
	}

	@Override
	protected void controlRender(final RenderManager rm, final ViewPort vp) {
		// TODO Auto-generated method stub

	}
}
