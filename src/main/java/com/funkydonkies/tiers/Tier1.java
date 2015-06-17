package com.funkydonkies.tiers;

import com.funkydonkies.gamestates.DisabledState;
import com.funkydonkies.powerups.IncreaseSpawnSpeedPowerup;
import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;

/**
 * Tier 1 defines powerups to be enabled.
 */
public class Tier1 extends DisabledState {
	private IncreaseSpawnSpeedPowerup increaseSpawnSpeed;
	
	@Override
	public void initialize(final AppStateManager stateManager, final Application app) {
		super.initialize(stateManager, app);
		
		increaseSpawnSpeed = new IncreaseSpawnSpeedPowerup(1);
		stateManager.attach(increaseSpawnSpeed);
	}
	
	@Override
	public void setEnabled(final boolean enabled) {
		super.setEnabled(enabled);
		if (enabled) {
			enableIncreasedSpawnSpeed();
			
		} 
	}
	
	@Override
	public void update(final float tpf) {
		super.update(tpf);
	}
	
	
	/**
	 * Enables incresed speed.
	 */
	public void enableIncreasedSpawnSpeed() {
		increaseSpawnSpeed.setEnabled(true);
	}
}
