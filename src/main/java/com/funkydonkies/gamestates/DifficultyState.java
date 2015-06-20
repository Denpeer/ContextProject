package com.funkydonkies.gamestates;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import com.funkydonkies.combo.ComboDisplay;
import com.funkydonkies.core.App;
import com.funkydonkies.exceptions.BadDynamicTypeException;
import com.funkydonkies.interfaces.Observable;
import com.funkydonkies.interfaces.Observer;
import com.funkydonkies.powerups.InvertControlsPowerup;
import com.funkydonkies.powerups.SnowballPowerup;
import com.funkydonkies.sounds.ComboLostSound;
import com.funkydonkies.sounds.SoundState;
import com.funkydonkies.tiers.Tier;
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
public class DifficultyState extends AbstractAppState implements Observable {
	private final int tierBorder = 2;
	private App app;

	private int currCombo = 0;
	private int highestCombo = 0;
	private boolean changed;

	private InvertControlsPowerup invertControls;
	private SnowballPowerup snowBallPowerup;

	private SoundState soundState;
	private HashMap<String, Tier> tierMap;
	private int enabledTier;

	private Vector<Observer> obs = new Vector<Observer>();
	private AppStateManager stateManager;

	/**
	 * @param sManager
	 *            AppStateManager
	 * @param appl
	 *            Application
	 * @see com.jme3.app.state.AbstractAppState#initialize(com.jme3.app.state.AppStateManager,
	 *      com.jme3.app.Application)
	 */
	@Override
	public final void initialize(final AppStateManager sManager, final Application appl) {
		super.initialize(sManager, appl);
		if (appl instanceof App) {
			this.app = (App) appl;
		} else {
			throw new BadDynamicTypeException();
		}
		stateManager = sManager;
		attachTiers();

		invertControls = makeInvertControlsPowerup();
		sManager.attach(invertControls);

		snowBallPowerup = makeSnowBallPowerup();
		sManager.attach(snowBallPowerup);

		soundState = sManager.getState(SoundState.class);

		initComboDisplay();
	}

	/**
	 * This method attaches all the tiers to the state manager.
	 */
	public void attachTiers() {
		tierMap = stateManager.getState(SpawnState.class).getTiers();
		final Iterator<Tier> it = tierMap.values().iterator();
		while (it.hasNext()) {
			stateManager.attach(it.next());
		}
	}

	/**
	 * This method check if another tier needs to be enabled.
	 */
	public void checkTierenabling() {
		enabledTier = currCombo / tierBorder;
		final Iterator<String> it = tierMap.keySet().iterator();
		while (it.hasNext()) {
			final String name = it.next();
			if (Character.getNumericValue(name.charAt(name.length() - 1)) == enabledTier) {
				enableTier(name);
			}
		}
	}

	/**
	 * This method enables a tier and disables the other tiers.
	 * 
	 * @param name
	 *            the name of the tier
	 */
	public void enableTier(final String name) {
		final Iterator<Tier> it = tierMap.values().iterator();
		while (it.hasNext()) {
			final Tier tier = it.next();
			if (tier.equals(tierMap.get(name)) && !tier.isEnabled()) {
				tier.setEnabled(true);
			} else if (!tier.equals(tierMap.get(name))) {
				tier.setEnabled(false);
			}
		}
	}

	/**
	 * Adds an observer to the observers vector, so that they can be updated.
	 * 
	 * @param o
	 *            Observer observer to be added.
	 */
	public void addObserver(final Observer o) {
		if (o == null) {
			throw new NullPointerException();
		}
		if (!obs.contains(o)) {
			obs.addElement(o);
		}
	}

	/**
	 * Sets the changed boolean.
	 */
	public void setChanged() {
		changed = true;
	}

	/**
	 * Returns the observers vector containing all the observers.
	 * 
	 * @return obs Vector
	 */
	public Vector<Observer> getObservers() {
		return obs;
	}

	/**
	 * Notifies all observers.
	 * 
	 * @see com.funkydonkies.interfaces.Observable#notifyObservers(java.lang.Object)
	 * @param arg
	 *            the object
	 */
	@Override
	public void notifyObservers(final Object arg) {
		Object[] arrLocal;

		if (!changed) {
			return;
		}

		synchronized (this) {
			arrLocal = obs.toArray();
		}
		changed = false;

		for (int i = arrLocal.length - 1; i >= 0; i--) {
			((Observer) arrLocal[i]).update((Observable) this, arg);
		}
	}

	/**
	 * Returns a new InvertControlsPowerup, called in initialize.
	 * 
	 * @return new InvertControlsPowerup
	 */
	public InvertControlsPowerup makeInvertControlsPowerup() {
		return new InvertControlsPowerup();
	}

	/**
	 * Returns a new SnowballPowerup, called in initialize.
	 * 
	 * @return new SnowballPowerup
	 */
	public SnowballPowerup makeSnowBallPowerup() {
		return new SnowballPowerup();
	}

	/**
	 * Makes the combo display and calls its initialize method.
	 * 
	 * @return combo ComboDisplay the instantiated combodisplay class.
	 */
	public ComboDisplay initComboDisplay() {
		final ComboDisplay comb = makeCombo();
		comb.init();
		return comb;
	}

	/**
	 * Instantiates the comboDisplay class.
	 * 
	 * @return new ComboDisplay
	 */
	public ComboDisplay makeCombo() {
		return new ComboDisplay(app);
	}

	/**
	 * @param tpf
	 *            time since last frame
	 * @see com.jme3.app.state.AbstractAppState#update(float)
	 */
	@Override
	public void update(final float tpf) {
		super.update(tpf);
		checkTierenabling();
		// time += tpf;
		// if (time > 8) {
		// time = 0;
		// setTier2();
		// }
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
	 * 
	 * @param times
	 *            int amount of combo to add.
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
		soundState.queueSound(new ComboLostSound());
		currCombo = 0;
		setChanged();
		notifyObservers(null);
	}

	/**
	 * Returns the current combo amount.
	 * 
	 * @return currCombo the current combo;
	 */
	public int getCombo() {
		return currCombo;
	}

	/**
	 * Returns the current combo amount.
	 * 
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
	 * Activates the snowBallPowerup.
	 */
	public void activateSnowBallPowerup() {
		snowBallPowerup.setEnabled(true);
	}

	/**
	 * This method get the tier that should be enabled.
	 * 
	 * @return the enabled tier.
	 */
	public int getEnabledTier() {
		return enabledTier;
	}
}
