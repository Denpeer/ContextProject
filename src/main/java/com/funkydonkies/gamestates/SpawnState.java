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

/**
 * This class takes care of spawning most gameObjects.
 * @author SDumasy
 *
 */
public class SpawnState extends AbstractAppState {
	public static final float DEFAULT_BALL_SPAWN_TIME = 2;
	private TargetFactory tarFac;
	private ObstacleFactory obFac;
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
		spawnBallTime = DEFAULT_BALL_SPAWN_TIME;
		initFactories();
		tarFac.makeFish();
		obFac.makeKillerWhale();
	}
	/**
	 * This method initializes every factory.
	 */
	public void initFactories() {
		tarFac = new TargetFactory(app.getAssetManager(), app.getRootNode(), phy);
		obFac = new ObstacleFactory(app.getAssetManager(), app.getRootNode(), phy);
		pengFac = new PenguinFactory(app.getAssetManager(), app.getPenguinNode(), phy);
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
	}
	
	/**
	 * This method updates the difficulty ratings needed for spawning.
	 */
	public final void updateDifficultyRatios() {
		spawnBallTime = DifficultyState.getSpawnBallTime();
	}
	
	public void setBallSpawnTime(float newTime) {
		spawnBallTime = newTime;
	}

}
