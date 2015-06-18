package com.funkydonkies.tiers;

import java.util.ArrayList;

import com.funkydonkies.gamestates.SpawnState;
import com.funkydonkies.interfaces.FactoryInterface;
import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;

/**
 * This class represents the first difficulty tier.
 */
public class Tier1 extends Tier {
	private SpawnState spawnState;
	private ArrayList<FactoryInterface> obstacleArray;

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
		spawnState = stateManager.getState(SpawnState.class);
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
			setText("Tier 1: Activated!");
		}
	}

	/**
	 * Add obstacles to the spawn array.
	 */
	public void addObstacleArray() {
		obstacleArray.add(spawnState.getObstacles().get("SpearFactory"));
		obstacleArray.add(spawnState.getObstacles().get("PolarBearFactory"));
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
