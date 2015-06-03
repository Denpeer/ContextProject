package com.funkydonkies.gamestates;

import com.funkydonkies.core.App;
import com.funkydonkies.exceptions.BadDynamicTypeException;
//import com.funkydonkies.factories.ObstacleFactory;
import com.funkydonkies.factories.PenguinFactory;
import com.funkydonkies.factories.TargetFactory;
import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.bullet.PhysicsSpace;

/**
 * This class takes care of spawning most gameObjects.
 * @author SDumasy
 *
 */
public class SpawnState extends AbstractAppState {
	private TargetFactory tarFac;
	//private ObstacleFactory obFac;
	private PenguinFactory pengFac;
	
	private float spawnBallTime;
	
	private App app;
	private PhysicsSpace phy;
	
	private float timeCount = 0;
	
	/**
	 * The initialize method of the state.
	 * @param appl the application
	 * @param sManager the appstate manager
	 */
	@Override
	public final void initialize(final AppStateManager sManager, final Application appl) {
		super.initialize(sManager, appl);
		if (appl instanceof App) {
			this.app = (App) appl;
		} else {
			throw new BadDynamicTypeException();
		}
		phy = PlayState.getPhysicsSpace();

		initFactories();
		tarFac.makeFish();
	}
	/**
	 * This method initializes every factory.
	 */
	public void initFactories() {
		tarFac = new TargetFactory(app.getAssetManager(), app.getRootNode(), phy);
		//obFac = new ObstacleFactory(app.getAssetManager(), app.getRootNode(), phy);
		pengFac = new PenguinFactory(app.getAssetManager(), app.getRootNode(), phy);
	}
	
	/**
	 * The update method of the state.
	 * @param tpf the time per frame
	 */
	@Override
	public final void update(final float tpf) {
		timeCount += tpf;
		if (timeCount > spawnBallTime) {
			timeCount = 0;
			pengFac.makeStandardPenguin();
		}
		updateDifficultyRatios();
		
	}
	
	/**
	 * This method updates the difficulty ratings needed for spawning.
	 */
	public final void updateDifficultyRatios() {
		spawnBallTime = DifficultyState.getSpawnBallTime();
	}

	

}
