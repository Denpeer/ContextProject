package com.funkydonkies.gamestates;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

import org.reflections.Reflections;

import com.funkydonkies.core.App;
import com.funkydonkies.exceptions.BadDynamicTypeException;
import com.funkydonkies.factories.KrillFactory;
import com.funkydonkies.factories.SquidFactory;
import com.funkydonkies.interfaces.FactoryInterface;
import com.funkydonkies.tiers.Tier;
import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

/**
 * This class takes care of spawning most gameObjects.
 * 
 * @author SDumasy
 *
 */
public class SpawnState extends AbstractAppState {
	public static final float DEFAULT_BALL_SPAWN_TIME = 10;
	public static final float OBSTACLE_SPAWN_TIME = 8;
	public static final float SPECIAL_FISH_SPAWN_TIME = 60;
	private static final String FACTORY_PACKAGE = "com.funkydonkies.factories";
	private static final String TIER_PACKAGE = "com.funkydonkies.tiers";
	private HashMap<String, FactoryInterface> facHm;
	private HashMap<String, Tier> tierMap;

	private AppStateManager stateManager;
	private float spawnBallTime;
	private ArrayList<FactoryInterface> obstacleList;

	private App app;
	private float timeCount = 0;
	private float specialFishTimer = 0;
	private float obstacleTimer = 0;

	private Random rand;

	/**
	 * The initialize method of the state.
	 * 
	 * @param appl
	 *            the application
	 * @param sManager
	 *            the appstate manager
	 */
	@Override
	public final void initialize(final AppStateManager sManager, final Application appl) {
		super.initialize(sManager, appl);
		if (appl instanceof App) {
			this.app = (App) appl;
		} else {
			throw new BadDynamicTypeException();
		}
		fillObstacleFactoriesMap();
		fillTiersMap();
		obstacleList = new ArrayList<FactoryInterface>();
		stateManager = sManager;
		spawnBallTime = DEFAULT_BALL_SPAWN_TIME;
		spawn(facHm.get("FishFactory"), app.getSceneNode());
		rand = new Random();
	}

	/**
	 * The update method of the state.
	 * 
	 * @param tpf
	 *            the time per frame
	 */
	@Override
	public final void update(final float tpf) {
		timeCount += tpf;
		specialFishTimer += tpf;
		obstacleTimer += tpf;

		if (obstacleTimer > OBSTACLE_SPAWN_TIME) {
			obstacleTimer = 0;
			chooseObstacleToSpawn();
		}

		if (timeCount > spawnBallTime) {
			timeCount = 0;
			spawn(facHm.get("PenguinFactory"), app.getPenguinNode());
		}
		if (specialFishTimer > SPECIAL_FISH_SPAWN_TIME) {
			specialFishTimer = 0;
			chooseTargetToSpawn();
		}
	}

	/**
	 * This method chooses and spawn an obstacle depending on the tier.
	 */
	public void chooseObstacleToSpawn() {
		final Iterator<Tier> it = tierMap.values().iterator();
		while (it.hasNext()) {
			final Tier tier = it.next();
			if (tier.isEnabled()) {
				if (!tier.getObstacleArray().isEmpty()
						&& !obstacleList.contains(tier.getObstacleArray().get(0))) {
					obstacleList.addAll(tier.getObstacleArray());
				}
				if (!obstacleList.isEmpty()) {
					final int rInt = rand.nextInt(obstacleList.size());
					spawn(obstacleList.get(rInt), app.getSceneNode());
				}
			}
		}
		if (stateManager.getState(DifficultyState.class).getEnabledTier() == 0) {
			obstacleList.clear();
			spawnBallTime = DEFAULT_BALL_SPAWN_TIME;
		}
	}

	/**
	 * This method chooses and spawns a special target.
	 */
	public void chooseTargetToSpawn() {
		final int i = rand.nextInt(2);
		switch (i) {
		case 0:
			if (app.getSceneNode().getChild(KrillFactory.KRILL_NAME) == null) {
				spawn(facHm.get("KrillFactory"), app.getSceneNode());
			}
			break;
		case 1:
			if (app.getSceneNode().getChild(SquidFactory.SQUID_NAME) == null) {
				spawn(facHm.get("SquidFactory"), app.getSceneNode());
			}
			break;
		default:
			break;
		}

	}

	/**
	 * Spawn an object.
	 * 
	 * @param obstacleFactory
	 *            the obstaclefactory.
	 * @param nodeToAttach
	 *            the node where the objects get attached to
	 */
	public void spawn(final FactoryInterface obstacleFactory, final Node nodeToAttach) {
		final Spatial obstacle = obstacleFactory.makeObject(stateManager, app);
		if (obstacle != null) {
			nodeToAttach.attachChild(obstacle);
		}
	}

	/**
	 * 
	 */
	public void fillObstacleFactoriesMap() {
		facHm = new HashMap<String, FactoryInterface>();
		final Reflections reflections = new Reflections(FACTORY_PACKAGE);
		final Set<Class<? extends FactoryInterface>> classes = reflections
				.getSubTypesOf(FactoryInterface.class);

		for (Class<? extends FactoryInterface> c : classes) {
			try {

				facHm.put(c.getSimpleName(), (FactoryInterface) c.newInstance());
			} catch (final InstantiationException e) {
				e.printStackTrace();
			} catch (final IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * This method creates a map containing every tier.
	 */
	public void fillTiersMap() {
		tierMap = new HashMap<String, Tier>();
		final Reflections reflections = new Reflections(TIER_PACKAGE);
		final Set<Class<? extends Tier>> classes = reflections.getSubTypesOf(Tier.class);

		for (Class<? extends Tier> c : classes) {
			try {
				tierMap.put(c.getSimpleName(), (Tier) c.newInstance());
			} catch (final InstantiationException e) {
				e.printStackTrace();
			} catch (final IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * This method sets the spawntime of the penguins.
	 * 
	 * @param newTime
	 *            the new spawntime of the penguins
	 */
	public void setBallSpawnTime(final float newTime) {
		spawnBallTime = newTime;
	}

	/**
	 * This method creates a default material.
	 * 
	 * @param appl
	 *            the simple application
	 */

	/**
	 * The getter for the tiermap.
	 * 
	 * @return the hashmap containing the tiers.
	 */
	public HashMap<String, Tier> getTiers() {
		return tierMap;
	}

	/**
	 * The getter for the obstacles.
	 * 
	 * @return the obstacle map
	 */
	public HashMap<String, FactoryInterface> getObstacles() {
		return facHm;
	}

}
