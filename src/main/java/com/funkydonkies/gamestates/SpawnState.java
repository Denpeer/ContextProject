package com.funkydonkies.gamestates;

import com.funkydonkies.core.App;
import com.funkydonkies.exceptions.BadDynamicTypeException;
import com.funkydonkies.factories.ObstacleFactory;
import com.funkydonkies.factories.PenguinFactory;
import com.funkydonkies.factories.TargetFactory;
import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.bullet.PhysicsSpace;


public class SpawnState extends AbstractAppState{
	private TargetFactory tarFac;
	private ObstacleFactory obFac;
	private PenguinFactory pengFac;
	
	private App app;
	private AppStateManager stateManager;
	private PhysicsSpace phy;
	
	private ComboState comboState;
	private float InitialspawnBallTime = 5f;
	private float spawnBallTime = 5f;
	private float timeCount = 0;
	
	@Override
	public final void initialize(final AppStateManager sManager, final Application appl) {
		super.initialize(sManager, appl);
		stateManager = sManager;
		if (appl instanceof App) {
			this.app = (App) appl;
		} else {
			throw new BadDynamicTypeException();
		}
		phy = PlayState.getPhysicsSpace();
		comboState = new ComboState();
		stateManager.attach(comboState);
		initFactories();
		tarFac.makeFish();
	}
	/**
	 * This method initializes every factory.
	 */
	public void initFactories(){
		tarFac = new TargetFactory(app.getAssetManager(), app.getRootNode(), phy);
		obFac = new ObstacleFactory(app.getAssetManager(), app.getRootNode(), phy);
		pengFac = new PenguinFactory(app.getAssetManager(), app.getRootNode(), phy);
	}
	
	@Override
	public final void update(float tpf){
		timeCount += tpf;
		if (timeCount > spawnBallTime) {
			timeCount = 0;
			pengFac.makeStandardPenguin();
		}
		updateDifficultyRatios();
		
	}
	
	public final void updateDifficultyRatios(){
		if(spawnBallTime > 1){
			spawnBallTime = InitialspawnBallTime - ComboState.getCombo();
		}
	}

	

}
