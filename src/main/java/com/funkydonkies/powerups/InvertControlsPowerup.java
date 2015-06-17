package com.funkydonkies.powerups;

import com.funkydonkies.gamestates.CurveState;
import com.funkydonkies.gamestates.DisabledState;
import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;

/**
 * Changes the controls for INVERT_TIME seconds.
 */
public class InvertControlsPowerup extends DisabledState {
	
	private static final float INVERT_TIME = 10;
	private CurveState curveState;
	private float timer = 0;
	
	@Override
	public void initialize(final AppStateManager stateManager, final Application app) {
		super.initialize(stateManager, app);
		curveState = stateManager.getState(CurveState.class);
	}
	
	@Override
	public void setEnabled(final boolean enabled) {
		super.setEnabled(enabled);
		curveState.setInvertControlPoints(enabled);
		if (!enabled) {
			timer = 0;
		}
	}
	
	@Override
	public void update(final float tpf) {
		super.update(tpf);
		timer += tpf;
		if (timer > INVERT_TIME) {
			timer = 0;
			setEnabled(false);
		}
	}
}
