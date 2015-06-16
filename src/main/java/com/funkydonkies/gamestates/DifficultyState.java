package com.funkydonkies.gamestates;

import java.util.Vector;

import com.funkydonkies.combo.ComboDisplay;
import com.funkydonkies.core.App;
import com.funkydonkies.exceptions.BadDynamicTypeException;
import com.funkydonkies.interfaces.Observable;
import com.funkydonkies.interfaces.Observer;
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
 * 
 * This Class uses the observer pattern, it is an observable that notifies it observers when the 
 * combo count changes, so that GUI/HUD elements can be updated accordingly.
 * 
 */
/**
 * @author Jonathan
 *
 */
/**
 * @author Jonathan
 *
 */
/**
 * @author Jonathan
 *
 */
/**
 * @author Jonathan
 *
 */
public class DifficultyState extends AbstractAppState implements Observable {
	private static final float TIER_ONE_ACTIVATION = 1;

	private float time = 0;
	private SuperSizePowerup superSize = null;
	private DisabledState activatedTier;
	private Tier1 tier1;
	private Tier2 tier2;
	private App app;

	private ComboDisplay combo;
	private int currCombo = 0;
	private int highestCombo = 0;
	private boolean changed;

	private InvertControlsPowerup invertControls;
	private SnowballPowerup snowBallPowerup;

	private Vector obs = new Vector();;

	/**
	 * @see com.jme3.app.state.AbstractAppState#initialize(com.jme3.app.state.AppStateManager, com.jme3.app.Application)
	 */
	@Override
	public final void initialize(final AppStateManager sManager, final Application appl) {
		super.initialize(sManager, appl);
		if (appl instanceof App) {
			this.app = (App) appl;
		} else {
			throw new BadDynamicTypeException();
		}

		tier1 = makeTier1();
		sManager.attach(tier1);
		tier2 = makeTier2();
		sManager.attach(tier2);

		invertControls = makeInvertControlsPowerup();
		sManager.attach(invertControls);

		snowBallPowerup = makeSnowBallPowerup();
		sManager.attach(snowBallPowerup);

		combo = initComboDisplay();
	}

	/**
	 * Adds an observer to the observers vector, so that they can be updated.
	 * @param o Observer observer to be added.
	 */
	public void addObserver(Observer o) {
		if (o == null)
			throw new NullPointerException();
		if (!obs.contains(o)) {
			obs.addElement(o);
		}
	}

	/**
	 * Sets the changed boolean
	 */
	public void setChanged() {
		changed = true;
	}
	
	public Vector getObservers() {
		return obs;
	}

	/**
	 * Notifies all observers
	 * @see com.funkydonkies.interfaces.Observable#notifyObservers(java.lang.Object)
	 */
	public void notifyObservers(Object arg) {
		Object[] arrLocal;

		if (!changed)
			return;
		
		synchronized (this) {
			arrLocal = obs.toArray();
		}
		changed = false;

		for (int i = arrLocal.length - 1; i >= 0; i--)
			((Observer) arrLocal[i]).update((Observable) this, arg);
	}

	public Tier1 makeTier1() {
		return new Tier1();
	}

	public Tier2 makeTier2() {
		return new Tier2();
	}

	public InvertControlsPowerup makeInvertControlsPowerup() {
		return new InvertControlsPowerup();
	}

	public SnowballPowerup makeSnowBallPowerup() {
		return new SnowballPowerup();
	}

	/**
	 * Makes the combo display and calls its initialize method.
	 * @return combo ComboDisplay the instantiated combodisplay class.
	 */
	public ComboDisplay initComboDisplay() {
		ComboDisplay combo = makeCombo();
		combo.init();
		return combo;
	}

	
	/**
	 * Instantiates the comboDisplay class.
	 * @return new ComboDisplay
	 */
	public ComboDisplay makeCombo() {
		return new ComboDisplay(app);
	}

	/**
	 * @see com.jme3.app.state.AbstractAppState#update(float)
	 */
	@Override
	public void update(final float tpf) {
		super.update(tpf);
		// time += tpf;
		// if (time > 8) {
		// time = 0;
		// setTier2();
		// }
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

	/**
	 * Increments the combo counter and notifies the observers of the change.
	 */
	public void incDiff() {
		currCombo++;
		if (currCombo > highestCombo) {
			highestCombo = currCombo;
		}
		setChanged();
		notifyObservers(null);
	}

	/**
	 * Calls the incDiff method multiple times it increase the combo multiple times in one call.
	 * @param times int amount of combo to add.
	 */
	public void incDiff(final int times) {
		for (int i = 0; i < times; i++) {
			incDiff();
		}
	}

	/**
	 * Resets the current combo and notifies the observers of the change.
	 */
	public void resetDiff() {
		currCombo = 0;
		setChanged();
		notifyObservers(null);
	}
	
	/**
	 * Returns the current combo amount.
	 * @return currCombo the current combo;
	 */
	public int getCombo() {
		return currCombo;
	}
	
	/**
	 * Returns the current combo amount.
	 * @return currCombo the current combo;
	 */
	public int getMaxCombo() {
		return highestCombo;
	}

	/**
	 * Activates the invertControls powerup.
	 */
	public void activateInvertControls() {
		invertControls.setEnabled(true);
	}

	/**
	 * Activates the snowBallPowerup
	 */
	public void activateSnowBallPowerup() {
		snowBallPowerup.setEnabled(true);
	}
}
