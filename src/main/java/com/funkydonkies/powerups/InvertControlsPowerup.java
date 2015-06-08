package com.funkydonkies.powerups;

import com.funkydonkies.gamestates.CurveState;
import com.funkydonkies.gamestates.DisabledState;
import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;

public class InvertControlsPowerup extends DisabledState {
	private static float INVERT_TIME = 10;
	private CurveState curveState;
	private float timer = 0;
	
	@Override
	public void initialize(AppStateManager stateManager, Application app) {
		super.initialize(stateManager, app);
		curveState = stateManager.getState(CurveState.class);
	}
	
	@Override
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
		curveState.setInvertControlPoints(enabled);
		if (!enabled) {
			timer = 0;
		}
	}
	
	@Override
	public void update(float tpf) {
		super.update(tpf);
		timer += tpf;
		if (timer > INVERT_TIME) {
			timer = 0;
			setEnabled(false);
		}
	}
	
	
}
