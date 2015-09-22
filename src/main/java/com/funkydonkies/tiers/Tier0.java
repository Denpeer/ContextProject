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
public class Tier0 extends Tier {
	private ArrayList<FactoryInterface> obstacleArray;
	private static final float TEXT_TIMER = 15;
	private float timer = 0;
	private String sentence = "The penguins like Fish!";
	IncreaseSpawnSpeedPowerup increaseSpawnSpeed;
	
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
		obstacleArray = new ArrayList<FactoryInterface>();
		increaseSpawnSpeed = IncreaseSpawnSpeedPowerup.getInstance(SpawnState.DEFAULT_BALL_SPAWN_TIME);
		stateManager.attach(increaseSpawnSpeed);
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
		if (!enabled) {
			clearText();
		}
	}

	@Override
	public void update(final float tpf) {
		super.update(tpf);
		timer += tpf;
		if (timer > TEXT_TIMER) {
			setText(sentence);
			timer = 0;
		}

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
