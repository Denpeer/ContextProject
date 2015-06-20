package com.funkydonkies.powerups;

import com.funkydonkies.gamestates.CurveState;
import com.funkydonkies.gamestates.DisabledState;
import com.funkydonkies.sounds.PowerupInverseEndSound;
import com.funkydonkies.sounds.PowerupInverseSound;
import com.funkydonkies.sounds.SoundState;
import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;

/**
 * Changes the controls for INVERT_TIME seconds.
 */
public class InvertControlsPowerup extends DisabledState {
	
	private static final float INVERT_TIME = 10;
	private static final String COLOR = "Diffuse";
	private CurveState curveState;
	private float timer = 0;
	private AppStateManager sManager;
	
	@Override
	public void initialize(final AppStateManager stateManager, final Application app) {
		super.initialize(stateManager, app);
		curveState = stateManager.getState(CurveState.class);
		sManager = stateManager;
	}
	
	@Override
	public void setEnabled(final boolean enabled) {
		super.setEnabled(enabled);
		curveState.setInvertControlPoints(enabled);
		if (enabled) {
			curveState.getSplineCurve().getGeometry().getMaterial().setColor(COLOR, CurveState.INVERTED_COLOR);
			sManager.getState(SoundState.class).queueSound(new PowerupInverseSound());
		} else {
			timer = 0;
			curveState.getSplineCurve().getGeometry().getMaterial().setColor(COLOR, CurveState.DEFAULT_COLOR);
		}
	}
	
	@Override
	public void update(final float tpf) {
		super.update(tpf);
		timer += tpf;
		if (timer > INVERT_TIME) {
			timer = 0;
			setEnabled(false);
			sManager.getState(SoundState.class).queueSound(new PowerupInverseEndSound());
		}
	}
}
