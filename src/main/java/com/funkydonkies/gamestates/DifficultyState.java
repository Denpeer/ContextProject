package com.funkydonkies.gamestates;

import com.funkydonkies.core.App;
import com.funkydonkies.exceptions.BadDynamicTypeException;
import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;

public class DifficultyState extends AbstractAppState{
	private App app;
	private AppStateManager stateManager;

	private float initialBallSpeed = 50;
	private static float ballSpeed = 50;
	private static float maxBallSpeed = 250;
	
	private float InitialspawnBallTime = 5f;
	private static float spawnBallTime = 5f;
	private static float minSpawnBallTime = 1;
	
	@Override
	public final void initialize(final AppStateManager sManager, final Application appl) {
		super.initialize(sManager, appl);
		stateManager = sManager;
		if (appl instanceof App) {
			this.app = (App) appl;
		} else {
			throw new BadDynamicTypeException();
		}
	}
	
	@Override
	public void update(float tpf){
		ballSpeedDifficulty();
		ballSpawnDifficulty();
	}
	
	public void ballSpeedDifficulty(){
		if(ballSpeed < maxBallSpeed){
			ballSpeed = initialBallSpeed + ComboState.getCombo() * 20;
		}		
	}
	
	public void ballSpawnDifficulty(){
		if(spawnBallTime > minSpawnBallTime){
			spawnBallTime = InitialspawnBallTime - ComboState.getCombo();
		}	
	}
	
	public static final float getBallSpeed(){
		return ballSpeed;
	}
	
	public static final float getSpawnBallTime(){
		return spawnBallTime;
	}
}
