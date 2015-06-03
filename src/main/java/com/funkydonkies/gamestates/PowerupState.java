package com.funkydonkies.gamestates;

import powerups.SuperSizePowerup;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;

/**
 * This class controls the activation and deactivation of the different power ups.
 */
public class PowerupState extends AbstractAppState {
	private float time = 0;

	private SuperSizePowerup superSize = null;
	
	@Override
	public final void initialize(final AppStateManager sManager,
			final Application appl) {
		super.initialize(sManager, appl);
		
		superSize = new SuperSizePowerup();
		sManager.attach(superSize);
	}
	
	@Override
	public void update(final float tpf) {
		super.update(tpf);
		time += tpf;
		if (time > 5) {
			time = 0;
			enableSuperSize();
		}
		
	}
	
	/**
	 * Enables the super size powerup by calling setEnabled(true) on it.
	 */
	public void enableSuperSize() {
		superSize.setEnabled(true);
	}
	
	/**
	 * Disables the super size powerup by calling setEnabled(true) on it.
	 */
	public void disableSuperSize() {
		superSize.setEnabled(false);
	}
	
	
}
