package com.funkydonkies.gamestates;

import com.funkydonkies.core.App;
import com.funkydonkies.exceptions.BadDynamicTypeException;
import com.funkydonkies.factories.ObstacleFactory;
import com.funkydonkies.factories.PenguinFactory;
import com.funkydonkies.factories.TargetFactory;
import com.funkydonkies.tiers.Tier1;
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
	public static final float DEFAULT_BALL_SPAWN_TIME = 10;
	private TargetFactory tarFac;
	private ObstacleFactory obFac;
	private PenguinFactory pengFac;
	
	private AppStateManager stManager;
	private float spawnBallTime;
	
	private App app;
	private PhysicsSpace phy;
	boolean bool = true;
	private float timeCount = 0;
	private float time = 0;
	
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
		stManager = sManager;
		phy = stManager.getState(PlayState.class).getPhysicsSpace();
		spawnBallTime = DEFAULT_BALL_SPAWN_TIME;
		initFactories();
		tarFac.makeFish();
	}
	/**
	 * This method initializes every factory.
	 */
	public void initFactories() {
		tarFac = new TargetFactory(stManager);
		obFac = new ObstacleFactory(stManager);
		pengFac = new PenguinFactory(stManager, app.getPenguinNode());
	}
	
	/**
	 * The update method of the state.
	 * @param tpf the time per frame
	 */
	@Override
	public final void update(final float tpf) {
		timeCount += tpf;
		time += tpf;
		if (timeCount > spawnBallTime) {
			timeCount = 0;
			pengFac.makeStandardPenguin();
		}
		if(time > 5){
			obFac.makeSpear();
			time = 0;
		}

	}
	
	public void setBallSpawnTime(float newTime) {
		spawnBallTime = newTime;
	}

}
