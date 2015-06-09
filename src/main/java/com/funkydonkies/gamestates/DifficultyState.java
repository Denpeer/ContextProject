package com.funkydonkies.gamestates;

import java.util.Observable;
import java.util.Observer;

import com.funkydonkies.combo.Combo;
import com.funkydonkies.core.App;
import com.funkydonkies.exceptions.BadDynamicTypeException;
import com.funkydonkies.powerups.InvertControlsPowerup;
import com.funkydonkies.powerups.SnowballPowerup;
import com.funkydonkies.powerups.SuperSizePowerup;
import com.funkydonkies.tiers.Tier1;
import com.funkydonkies.tiers.Tier2;
import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;

/**
 * This class controls the activation and deactivation of the different power ups.
 */
public class DifficultyState extends AbstractAppState implements Observer {
	private static final float TIER_ONE_ACTIVATION = 1;
	
	private float time = 0;
	private SuperSizePowerup superSize = null;
	private DisabledState activatedTier;
	private Tier1 tier1;
	private Tier2 tier2;
	private App app;

	private Combo combo;
	private int currCombo = 0;
	
	private InvertControlsPowerup invertControls;
	private SnowballPowerup snowBallPowerup;
	
	@Override
	public final void initialize(final AppStateManager sManager,
			final Application appl) {
		super.initialize(sManager, appl);
		if (appl instanceof App) {
			this.app = (App) appl;
		} else {
			throw new BadDynamicTypeException();
		}
		
		tier1 = new Tier1();
		sManager.attach(tier1);
		tier2 = new Tier2();
		sManager.attach(tier2);
		
		invertControls = new InvertControlsPowerup();
		sManager.attach(invertControls);
		
		snowBallPowerup = new SnowballPowerup();
		sManager.attach(snowBallPowerup);
		
		combo = new Combo(app.getGuiNode());
		combo.addObserver(this);
		combo.createCurrentComboText(app.getAssetManager());
		combo.createHighestComboText(app.getAssetManager());
	}
	
	@Override
	public void update(final float tpf) {
		super.update(tpf);
//		time += tpf;
//		if (time > 8) {
//			time = 0;
//			setTier2();
//		}
		
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

	public void update(Observable o, Object arg) {
		currCombo = combo.getCombo();
	}
	
	public void incDiff(){
		combo.incCombo();
	}
	
	public void resetDiff(){
		combo.resetCombo();
	}
	
	public void activateInvertControls() {
		invertControls.setEnabled(true);
	}
	
	public void activateSnowBallPowerup() {
		snowBallPowerup.setEnabled(true);
	}
}
