package com.funkydonkies.tiers;

import java.util.ArrayList;

import com.funkydonkies.gamestates.SpawnState;
import com.funkydonkies.interfaces.FactoryInterface;
import com.funkydonkies.powerups.IncreaseSpawnSpeedPowerup;
import com.funkydonkies.sounds.ComboNewLevelSound;
import com.funkydonkies.sounds.ComboNewLevelVoiceSound;
import com.funkydonkies.sounds.SoundState;
import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;

/**
 * This class represents the first difficulty tier.
 */
public class Tier3 extends Tier {
	private IncreaseSpawnSpeedPowerup increaseSpawnSpeed;
	private SpawnState spawnState;
	private ArrayList<FactoryInterface> obstacleArray;
	private static final float NEW_SPAWN_SPEED = 3f;
	private AppStateManager sManager;

	/**
	 * The initialize method.
	 * 
	 * @param stateManager
	 *            AppStateManager
	 * @param app
	 *            Application The initialize method.
	 */
	@Override
	public void initialize(final AppStateManager stateManager, final Application app) {
		super.initialize(stateManager, app);
		sManager = stateManager;
		increaseSpawnSpeed = getIncreasedSpawnSpeed();
		sManager.attach(increaseSpawnSpeed);
		spawnState = sManager.getState(SpawnState.class);
		obstacleArray = new ArrayList<FactoryInterface>();
		addObstacleArray();
	}

	@Override
	public void setEnabled(final boolean enabled) {
		super.setEnabled(enabled);
		if (enabled) {
			setIncreasedSpawnSpeedEnabled(true);
			setText("Tier 3: mind your step!");
			sManager.getState(SoundState.class).queueSound(new ComboNewLevelSound());
			sManager.getState(SoundState.class).queueSound(new ComboNewLevelVoiceSound());
		} else {
			clearText();
			if (increaseSpawnSpeed != null && increaseSpawnSpeed.isEnabled()) {
				setIncreasedSpawnSpeedEnabled(false);
			}
		}
	}

	/**
	 * get the increase spawn speed powerup.
	 * 
	 * @return the increased spawn speed power up.
	 */
	public IncreaseSpawnSpeedPowerup getIncreasedSpawnSpeed() {
		return new IncreaseSpawnSpeedPowerup(NEW_SPAWN_SPEED);
	}

	/**
	 * Add obstacles to the spawn array.
	 */
	public void addObstacleArray() {
		obstacleArray.add(spawnState.getObstacles().get("KillerWhaleFactory"));
	}

	/**
	 * Enable the increase spawn speed power up.
	 * @param enabled desired state
	 */
	public void setIncreasedSpawnSpeedEnabled(final boolean enabled) {
		increaseSpawnSpeed.setEnabled(enabled);
	}

	/**
	 * The getter for the obstacle array.
	 * @return obstacleArray
	 */
	@Override
	public ArrayList<FactoryInterface> getObstacleArray() {
		return obstacleArray;
	}

}
