package com.funkydonkies.tiers;

import java.util.ArrayList;

import com.funkydonkies.gamestates.SpawnState;
import com.funkydonkies.interfaces.FactoryInterface;
import com.funkydonkies.powerups.IncreaseSpawnSpeedPowerup;
import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;

/**
 * This class represents the first difficulty tier.
 */
public class Tier3 extends Tier {
	private IncreaseSpawnSpeedPowerup increaseSpawnSpeed;
	private SpawnState spawnState;
	private ArrayList<FactoryInterface> obstacleArray;
	private final float spawnSpeedTime = 3f;

	/**
	 * The initialize method.
	 */
	@Override
	public void initialize(final AppStateManager stateManager, final Application app) {
		super.initialize(stateManager, app);
		increaseSpawnSpeed = new IncreaseSpawnSpeedPowerup(spawnSpeedTime);
		stateManager.attach(increaseSpawnSpeed);
		spawnState = stateManager.getState(SpawnState.class);
		obstacleArray = new ArrayList<FactoryInterface>();
		addObstacleArray();
	}
	
	@Override
	public void setEnabled(final boolean enabled) {
		super.setEnabled(enabled);
		if (enabled) {
			enableIncreasedSpawnSpeed();
			setText("Tier 3: Activated!");
		} 
	}

	/**
	 * Add obstacles to the spawn array.
	 */
	public void addObstacleArray() {
		obstacleArray.add(spawnState.getObstacles().get("KillerWhaleFactory"));
	}
	
	/**
	 * Enable the increase spawn speed power up.
	 */
	public void enableIncreasedSpawnSpeed() {
		increaseSpawnSpeed.setEnabled(true);
	}
	/**
	 * The getter for the obstacle array.
	 */
	@Override
	public ArrayList<FactoryInterface> getObstacleArray() {
		return obstacleArray;
	}

}
