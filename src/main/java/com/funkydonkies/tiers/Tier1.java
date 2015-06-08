package com.funkydonkies.tiers;

import com.funkydonkies.gamestates.DisabledState;
import com.funkydonkies.powerups.IncreaseSpawnSpeedPowerup;
import com.funkydonkies.powerups.SuperSizePowerup;
import com.funkydonkies.powerups.TsunamiPowerup;
import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;

public class Tier1 extends DisabledState {
	private IncreaseSpawnSpeedPowerup increaseSpawnSpeed;
	private TsunamiPowerup tsunami;
	
	@Override
	public void initialize(AppStateManager stateManager, Application app) {
		super.initialize(stateManager, app);
		
		increaseSpawnSpeed = new IncreaseSpawnSpeedPowerup(1);
		stateManager.attach(increaseSpawnSpeed);
		
		tsunami = new TsunamiPowerup();
		stateManager.attach(tsunami);
	}
	
	@Override
	public void setEnabled(final boolean enabled) {
		super.setEnabled(enabled);
		if (enabled) {
			enableIncreasedSpawnSpeed();
			tsunami.setEnabled(true);
		} else {
			
		}
	}
	
	@Override
	public void update(final float tpf) {
		super.update(tpf);
	}
	
	
	public void enableIncreasedSpawnSpeed() {
		increaseSpawnSpeed.setEnabled(true);
	}
}
