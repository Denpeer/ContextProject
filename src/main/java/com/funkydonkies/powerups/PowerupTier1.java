package com.funkydonkies.powerups;

import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;

public class PowerupTier1 extends AbstractPowerup {
	private SuperSizePowerup superSize;
	private IncreaseSpawnSpeedPowerup increaseSpawnSpeed;
	
	@Override
	public void initialize(AppStateManager stateManager, Application app) {
		super.initialize(stateManager, app);
		
		superSize = new SuperSizePowerup();
		stateManager.attach(superSize);
		increaseSpawnSpeed = new IncreaseSpawnSpeedPowerup(1);
		stateManager.attach(increaseSpawnSpeed);
	}
	
	@Override
	public void setEnabled(final boolean enabled) {
		super.setEnabled(enabled);
		if (enabled) {
			enableSuperSize();
			enableIncreasedSpawnSpeed();
		} else {
			
		}
	}
	
	@Override
	public void update(final float tpf) {
		super.update(tpf);
	}
	
	
	public void enableSuperSize() {
		superSize.setEnabled(true);
	}
	
	public void enableIncreasedSpawnSpeed() {
		increaseSpawnSpeed.setEnabled(true);
	}
}
