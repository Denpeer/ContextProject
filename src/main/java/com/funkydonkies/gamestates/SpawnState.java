package com.funkydonkies.gamestates;

import java.util.HashMap;
import java.util.Random;
import java.util.Set;

import org.reflections.Reflections;

import com.funkydonkies.core.App;
import com.funkydonkies.exceptions.BadDynamicTypeException;
import com.funkydonkies.interfaces.FactoryInterface;
import com.funkydonkies.powerups.OilSpillPowerup;
import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.material.Material;
import com.jme3.scene.Spatial;

/**
 * This class takes care of spawning most gameObjects.
 * 
 * @author SDumasy
 *
 */
public class SpawnState extends AbstractAppState {
	public static final float DEFAULT_BALL_SPAWN_TIME = 2;
	public static final float OBSTACLE_SPAWN_TIME = 5;
	private static final String FACTORY_PACKAGE = "com.funkydonkies.factories";
	private static final String UNSHADED_MATERIAL_PATH = "Common/MatDefs/Misc/Unshaded.j3md";
	private HashMap<String, FactoryInterface> facHm;

	private AppStateManager stManager;
	private float spawnBallTime;

	private FactoryInterface penguin;
	private FactoryInterface spear;
	private FactoryInterface fish;
	private FactoryInterface krill;
	private FactoryInterface squid;
	private FactoryInterface killerWhale;
	private FactoryInterface spikeyBall;
	private FactoryInterface polarBear;
	private FactoryInterface yeti;
	private FactoryInterface oilyKrill;

	private App app;
	boolean bool = true;
	private float timeCount = 0;
	private float time = 0;

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
	public final void initialize(final AppStateManager sManager,
			final Application appl) {
		super.initialize(sManager, appl);
		if (appl instanceof App) {
			this.app = (App) appl;
		} else {
			throw new BadDynamicTypeException();
		}
		stManager = sManager;
		spawnBallTime = DEFAULT_BALL_SPAWN_TIME;
		initFactories();
		setSpawnAbleObjects();
		spawn(fish);
		initRootNodeMat(app);
		rand = new Random();
	}

	/**
	 * This method initializes every factory.
	 */
	public void initFactories() {
		fillObstacleFactoriesMap();
	}

	public void setSpawnAbleObjects() {
		penguin = facHm.get("PenguinFactory");
		spear = facHm.get("SpearFactory");
		fish = facHm.get("FishFactory");
		krill = facHm.get("KrillFactory");
		squid = facHm.get("SquidFactory");
		killerWhale = facHm.get("KillerWhaleFactory");
		yeti = facHm.get("YetiFactory");
		spikeyBall = facHm.get("SpikeyBallFactory");
		polarBear = facHm.get("PolarBearFactory");
		oilyKrill = facHm.get("OilyKrillFactory");
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
		time += tpf;
		if (timeCount > spawnBallTime) {
			timeCount = 0;
			spawnPenguin(penguin);
		}
		if (time > OBSTACLE_SPAWN_TIME) {
			time = 0;
			// int i = rand.nextInt(6);
			int i = 5;
			switch (i) {
			case 0:
				spawn(spear);
				break;
			case 1:
				spawn(killerWhale);
				break;
			case 2:
				spawn(yeti);
				break;
			case 3:
				spawn(spikeyBall);
				break;
			case 4:
				spawn(polarBear);
				break;
			case 5:
				spawn(oilyKrill);
				break;
			default:
				break;
			}
		}
	}

	private void spawnPenguin(FactoryInterface penguinFactory) {
		final Spatial penguin = penguinFactory.makeObject(stManager, app);
		if (penguin != null) {
			app.getPenguinNode().attachChild(penguin);
		}
	}

	private void spawn(FactoryInterface obstacleFactory) {
		final Spatial obstacle = obstacleFactory.makeObject(stManager, app);
		if (obstacle != null) {
			app.getRootNode().attachChild(obstacle);
		}
	}

	public void fillObstacleFactoriesMap() {
		facHm = new HashMap<String, FactoryInterface>();
		final Reflections reflections = new Reflections(FACTORY_PACKAGE);
		final Set<Class<? extends FactoryInterface>> classes = reflections
				.getSubTypesOf(FactoryInterface.class);
		for (Class c : classes) {
			try {
				facHm.put(c.getSimpleName(), (FactoryInterface) c.newInstance());
			} catch (final InstantiationException e) {
				e.printStackTrace();
			} catch (final IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}

	public void setBallSpawnTime(float newTime) {
		spawnBallTime = newTime;
	}

	public void initRootNodeMat(final App appl) {
		final Material mat = new Material(appl.getAssetManager(),
				UNSHADED_MATERIAL_PATH);
		appl.getRootNode().setUserData("default material", mat);
	}
}
