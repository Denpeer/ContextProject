package com.funkydonkies.gamestates;

import java.util.Observable;
import java.util.Observer;

import com.funkydonkies.powerups.AbstractPowerup;
import com.funkydonkies.powerups.PowerupTier1;
import com.funkydonkies.powerups.PowerupTier2;
import com.funkydonkies.powerups.SuperSizePowerup;
import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;

/**
 * This class controls the activation and deactivation of the different power ups.
 */
public class PowerupState extends AbstractAppState implements Observer {
	private static final float TIER_ONE_ACTIVATION = 5;
	
	private float time = 0;
	private SuperSizePowerup superSize = null;
	private AbstractPowerup activatedTier;
	private PowerupTier1 tier1;
	private PowerupTier2 tier2;

	private Combo combo;
	private int currCombo = 0;
	
	@Override
	public final void initialize(final AppStateManager sManager,
			final Application appl) {
		super.initialize(sManager, appl);
		tier1 = new PowerupTier1();
		sManager.attach(tier1);
		tier2 = new PowerupTier2();
		sManager.attach(tier2);
		
		combo = sManager.getState(PlayState.class).getCombo();
		combo.addObserver(this);
	}
	
	@Override
	public void update(final float tpf) {
		super.update(tpf);
		time += tpf;
		if (time > 5) {
			time = 0;
			setTier2();
		}
		
	}
	public void disableAllPowerups() {
		
	}
	
	public void setTier1() {
		if (!tier1.isEnabled()) {
			tier1.setEnabled(true);
		}
	}
	
	public void setTier2() {
		if (tier1.isEnabled()) {
			tier1.setEnabled(false);
		}
		if (!tier2.isEnabled()) {
			tier2.setEnabled(true);
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		currCombo = combo.getCombo();
	}
}
