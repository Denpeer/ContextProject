package com.funkydonkies.powerups;

import com.funkydonkies.gamestates.DisabledState;
import com.funkydonkies.gamestates.SpawnState;
import com.funkydonkies.sounds.PowerupSpeedEndSound;
import com.funkydonkies.sounds.PowerupSpeedSound;
import com.funkydonkies.sounds.SoundState;
import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;

/**
 * This powerup increases the game difficulty by increasing the time between new penguin spawns.
 *
 */
public class IncreaseSpawnSpeedPowerup extends DisabledState {
	private float speedMultiplier = 0;
	
	private AppStateManager sManager;
	private SpawnState spawnState;
	
	/**
	 * @param multiplier change speed multiplier
	 */
	public IncreaseSpawnSpeedPowerup(final float multiplier) {
		speedMultiplier = multiplier;
	}
	
	@Override
	public void initialize(final AppStateManager stateManager, final Application app) {
		super.initialize(stateManager, app);
		sManager = stateManager;
		spawnState = sManager.getState(SpawnState.class);
	}
	
	/**
	 * Enables or disables the powerup by setting the ballspawntime variable on spawnstate.
	 * @see com.funkydonkies.gamestates.DisabledState#setEnabled(boolean)
	 */
	@Override
	public void setEnabled(final boolean enabled) {
		super.setEnabled(enabled);
		if (enabled) {
			spawnState.setBallSpawnTime(speedMultiplier);
			sManager.getState(SoundState.class).queueSound(new PowerupSpeedSound());
		} else {
			spawnState.setBallSpawnTime(SpawnState.DEFAULT_BALL_SPAWN_TIME);
			sManager.getState(SoundState.class).queueSound(new PowerupSpeedEndSound());
		}
	}
}
