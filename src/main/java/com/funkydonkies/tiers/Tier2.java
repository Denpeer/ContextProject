package com.funkydonkies.tiers;

import com.funkydonkies.gamestates.DisabledState;
import com.funkydonkies.powerups.SnowballPowerup;
import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;

/**
 * Tier 2 defines powerups to be enabled.
 */
public class Tier2 extends DisabledState {
	
	private SnowballPowerup snowBall;
	
	@Override
	public void initialize(final AppStateManager stateManager, final Application app) {
		super.initialize(stateManager, app);
		
		snowBall = new SnowballPowerup();
		stateManager.attach(snowBall);
	}
	
	@Override
	public void setEnabled(final boolean enabled) {
		super.setEnabled(enabled);
		if (enabled) {
			//invertControls.setEnabled(true);
			snowBall.setEnabled(true);
		} else {
			snowBall.setEnabled(false);
		}
	}
	
	@Override
	public void update(final float tpf) {
		super.update(tpf);
	}
	
}
