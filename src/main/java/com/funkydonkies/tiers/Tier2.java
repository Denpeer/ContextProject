package com.funkydonkies.tiers;

import com.funkydonkies.gamestates.DisabledState;
import com.funkydonkies.powerups.InvertControlsPowerup;
import com.funkydonkies.powerups.SnowballPowerup;
import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;

public class Tier2 extends DisabledState {
	private static float INVERT_TIME = 5;
	
	private SnowballPowerup snowBall;
	
	@Override
	public void initialize(AppStateManager stateManager, Application app) {
		super.initialize(stateManager, app);
		
		snowBall = new SnowballPowerup();
		stateManager.attach(snowBall);
	}
	
	@Override
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
		if (enabled) {
			//invertControls.setEnabled(true);
			snowBall.setEnabled(true);
		} else {
			snowBall.setEnabled(false);
		}
	}
	
	@Override
	public void update(float tpf) {
		super.update(tpf);
	}
	
}
