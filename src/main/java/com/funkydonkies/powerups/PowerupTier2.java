package com.funkydonkies.powerups;

import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;

public class PowerupTier2 extends AbstractPowerup {
	private static float INVERT_TIME = 5;

	private InvertControlsPowerup invertControls;
	
	@Override
	public void initialize(AppStateManager stateManager, Application app) {
		super.initialize(stateManager, app);
		
		invertControls = new InvertControlsPowerup();
		stateManager.attach(invertControls);
	}
	
	@Override
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
		if (enabled) {
			invertControls.setEnabled(true);
		} else {
			invertControls.setEnabled(false);
		}
	}
	
	@Override
	public void update(float tpf) {
		super.update(tpf);
	}
	
}
