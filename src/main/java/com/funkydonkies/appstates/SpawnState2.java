package com.funkydonkies.appstates;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.reflections.Reflections;

import com.funkydonkies.controllers.FishControl;
import com.funkydonkies.core.App;
import com.funkydonkies.exceptions.BadDynamicTypeException;
import com.funkydonkies.factories.ObstacleFactory;
import com.funkydonkies.factories.ObstacleFactoryInterface;
import com.funkydonkies.factories.PenguinFactory;
import com.funkydonkies.factories.TargetFactory;
import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.collision.shapes.BoxCollisionShape;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

/**
 * This class takes care of spawning most gameObjects.
 */
public class SpawnState2 extends AbstractAppState {
	private static final int SPAWN_OBSTACLE_TIME = 10;
	private static final String FACTORY_PACKAGE = "com.funkydonkies.factories"; 
	private TargetFactory tarFac;
	private ObstacleFactory obFac;
	private PenguinFactory pengFac;
	
	private int obstacleTimer;
	
	private float spawnBallTime;
	
	private App app;
	private PhysicsSpace phy;
	private AssetManager assetManager;
	private Node rootNode;
	
	private float timeCount = 0;
	
	private List<String> obstacleFactories = new ArrayList<String>();
	private ObstacleFactoryInterface obstacleFactory;
	
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
		assetManager = appl.getAssetManager();
		rootNode = app.getRootNode();

		initFactories();
		tarFac.makeFish();
		obFac.makeKillerWhale();
		
		initFactoryList();
	}
	
	/**
	 * Initialize list of factories.
	 */
	public void initFactoryList() {
		obstacleFactories.add("KillerWhaleFactory");
	}
	/**
	 * This method initializes every factory.
	 */
	public void initFactories() {
		tarFac = new TargetFactory(assetManager, rootNode, phy);
		tarFac.makeMaterials();
		obFac = new ObstacleFactory(assetManager, rootNode, phy);
		obFac.makeMaterials();
		pengFac = new PenguinFactory(assetManager, rootNode, phy);
		pengFac.makeMaterials();
	}
	
	/**
	 * The update method of the state.
	 * @param tpf the time per frame
	 */
	@Override
	public final void update(final float tpf) {
		timeCount += tpf;
		obstacleTimer += tpf * 100;
		if (timeCount > spawnBallTime) {
			timeCount = 0;
//			pengFac.makeStandardPenguin();
		}
		updateDifficultyRatios();
		
		if (obstacleTimer > SPAWN_OBSTACLE_TIME) {
			obstacleTimer = 0;
			obstacleFactory = getObstacleFactory();
			if (obstacleFactory != null) {
				spawnObstacle(obstacleFactory);
			}
		}
		
	}
	
	private void spawnObstacle(ObstacleFactoryInterface obstacleFactory) {
		final Spatial obstacle = obstacleFactory.makeObst(assetManager);
		if (obstacle != null) {
			rootNode.attachChild(obstacle);
			final FishControl c = new FishControl(new BoxCollisionShape(new Vector3f(20, 20, 20)));
			obstacle.addControl(c);
			phy.add(obstacle);
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

	/**
	 * This method updates the difficulty ratings needed for spawning.
	 */
	public final void updateDifficultyRatios() {
		spawnBallTime = DifficultyState.getSpawnBallTime();
	}

	

}
