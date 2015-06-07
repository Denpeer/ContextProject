package com.funkydonkies.gamestates;

import java.util.Set;

import org.reflections.Reflections;

import com.funkydonkies.core.App;
import com.funkydonkies.exceptions.BadDynamicTypeException;
import com.funkydonkies.factories.PenguinFactory;
import com.funkydonkies.factories.TargetFactory;
import com.funkydonkies.interfaces.ObstacleFactoryInterface;
import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.material.Material;
import com.jme3.scene.Spatial;

/**
 * This class takes care of spawning most gameObjects.
 * @author SDumasy
 *
 */
public class SpawnState extends AbstractAppState {
	public static final float DEFAULT_BALL_SPAWN_TIME = 10;
	private static final String FACTORY_PACKAGE = "com.funkydonkies.factories";
	private static final String UNSHADED_MATERIAL_PATH = "Common/MatDefs/Misc/Unshaded.j3md";
	private TargetFactory tarFac;
	private ObstacleFactoryInterface obstacleFactory;
	private PenguinFactory pengFac;
	
	private AppStateManager stManager;
	private AssetManager assetManager;
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
		assetManager = app.getAssetManager();
		phy = stManager.getState(PlayState.class).getPhysicsSpace();
		spawnBallTime = DEFAULT_BALL_SPAWN_TIME;
		initFactories();
		tarFac.makeFish();
		
		initRootNodeMat(app);
	}
	
	/**
	 * This method initializes every factory.
	 */
	public void initFactories() {
		tarFac = new TargetFactory(stManager);
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
		final int spawnSpear = 5;
		if (time > spawnSpear) {
			obstacleFactory = getObstacleFactory();
			if (obstacleFactory != null) {
				spawnObstacle(obstacleFactory);
			}
		}

	}
	
	private void spawnObstacle(ObstacleFactoryInterface obstacleFactory) {
		final Spatial obstacle = obstacleFactory.makeObst(stManager, app);
		if (obstacle != null) {
			app.getRootNode().attachChild(obstacle);
		}
	}
	
	public ObstacleFactoryInterface getObstacleFactory() {
		final Reflections reflections = new Reflections(FACTORY_PACKAGE);
		final Set<Class<? extends ObstacleFactoryInterface>> classes = 
				reflections.getSubTypesOf(ObstacleFactoryInterface.class);
	    for (Class c : classes) {
	        try {
	        	System.out.println(c.getSimpleName());
				return (ObstacleFactoryInterface) c.newInstance();
			} catch (final InstantiationException e) {
				e.printStackTrace();
			} catch (final IllegalAccessException e) {
				e.printStackTrace();
			}
	    }
		return null;
	}
	
	public void setBallSpawnTime(float newTime) {
		spawnBallTime = newTime;
	}

	public void initRootNodeMat(final App appl) {
		final Material mat = new Material(appl.getAssetManager(), UNSHADED_MATERIAL_PATH);
		appl.getRootNode().setUserData("default material", mat);
	}
}
