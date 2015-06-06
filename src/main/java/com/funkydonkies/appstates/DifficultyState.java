package com.funkydonkies.appstates;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
/**
 * This state handles the difficulty of the game.
 * @author SDumasy
 *
 */
public class DifficultyState extends AbstractAppState {

	private static final float INITIAL_BALL_SPEED = 50;
	private static float ballSpeed = INITIAL_BALL_SPEED;
	private static final float MAX_BALL_SPEED = 250;
	
	private static final float INITIAL_SPAWN_BALL_TIME = 5;
	private static float spawnBallTime = INITIAL_SPAWN_BALL_TIME;
	private static float minSpawnBallTime = 1;
	private final float speedMultiplier = 20;
	
	@Override
	public final void initialize(final AppStateManager sManager, final Application appl) {
		super.initialize(sManager, appl);
	}
	
	@Override
	public void update(final float tpf) {
		ballSpeedDifficulty();
		ballSpawnDifficulty();
	}
	
	/**
	 * This method handles the difficulty by adjusting the ball speed.
	 */
	public void ballSpeedDifficulty() {
		if (ballSpeed < MAX_BALL_SPEED) {
			ballSpeed = INITIAL_BALL_SPEED + ComboState.getCombo() * speedMultiplier;
		}		
	}
	
	/**
	 * This method the difficult by spawning more balls.
	 */
	public void ballSpawnDifficulty() {
		if (spawnBallTime > minSpawnBallTime) {
			spawnBallTime = INITIAL_SPAWN_BALL_TIME - ComboState.getCombo();
		}	
	}
	
	/**
	 * The getter for the ballspeed.
	 * @return the speed of the ball
	 */
	public static final float getBallSpeed() {
		return ballSpeed;
	}
	
	/**
	 * The getter of the spawnBallTime.
	 * @return the time between the spawning of balls
	 */
	public static final float getSpawnBallTime() {
		return spawnBallTime;
	}
}
