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
public class Tier4 extends Tier {
	private static final float TIER4_SPAWN_SPEED = 4f;
	private IncreaseSpawnSpeedPowerup increaseSpawnSpeed;
	private SpawnState spawnState;
	private ArrayList<FactoryInterface> obstacleArray;
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
		spawnState = sManager.getState(SpawnState.class);
		obstacleArray = new ArrayList<FactoryInterface>();
		addObstacleArray();
	}

	/**
	 * This method is performed when this class is enabled.
	 * 
	 * @param enabled
	 *            boolean for enabling / disabling
	 */
	@Override
	public void setEnabled(final boolean enabled) {
		super.setEnabled(enabled);
		if (enabled) {
			setText("Tier 4: Grease Lightning!");
			increaseSpawnSpeed = IncreaseSpawnSpeedPowerup.getInstance(TIER4_SPAWN_SPEED);
			increaseSpawnSpeed.setEnabled(true);
			sManager.getState(SoundState.class).queueSound(new ComboNewLevelSound());
			sManager.getState(SoundState.class).queueSound(new ComboNewLevelVoiceSound());
		} else {
			clearText();
			//if (increaseSpawnSpeed != null && increaseSpawnSpeed.isEnabled())
				//increaseSpawnSpeed.setEnabled(false);
		}
	}

	/**
	 * Add obstacles to the spawn array.
	 */
	public void addObstacleArray() {
		obstacleArray.add(spawnState.getObstacles().get("ThunderFactory"));
	}

	/**
	 * The getter for the obstacle array.
	 * 
	 * @return obstacleArray
	 */
	@Override
	public ArrayList<FactoryInterface> getObstacleArray() {
		return obstacleArray;
	}

}
