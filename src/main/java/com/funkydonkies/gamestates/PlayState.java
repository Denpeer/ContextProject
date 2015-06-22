package com.funkydonkies.gamestates;

import com.funkydonkies.core.App;
import com.funkydonkies.exceptions.BadDynamicTypeException;
import com.funkydonkies.sounds.SoundState;
import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;

/**
 * The AppState that controls the basic aspects of the game, it is responsible for initializing the
 * game.
 */
public class PlayState extends AbstractAppState {
	public static final Vector3f GRAVITY = new Vector3f(0f, -9.81f, 0f);
	public static final Vector3f CAM_LOCATION = new Vector3f(160, 25, 200);

	private static final String MATERIAL_PATH = "Common/MatDefs/Light/Lighting.j3md";

	private App app;

	private BulletAppState bulletAppState;

	private GameInputState gameInputState;
	private CurveState curveState;
	private CameraState cameraState;
	private SoundState soundState;
	private GameBackgroundMusicState gameSoundState;
	private SpawnState spawnState;
	private DifficultyState difficultyState;
	private AppStateManager stateManage;
	private SceneState sceneState;

	/**
	 * Initializes the basic components of the game.
	 * 
	 * @param stateManager
	 *            AppStateManager, the statemanager of the application, passed by the
	 *            SimpleApplication
	 * @param appl
	 *            Application: The main Application
	 * @see com.jme3.app.state.AbstractAppState#initialize(com.jme3.app.state.AppStateManager,
	 *      com.jme3.app.Application)
	 */
	public void initialize(final AppStateManager stateManager, final Application appl) {
		super.initialize(stateManager, appl);
		if (appl instanceof App) {
			this.app = (App) appl;
		} else {
			throw new BadDynamicTypeException();
		}
		stateManage = stateManager;

		app.getFlyByCamera().setEnabled(false);
		app.getCamera().setLocation(CAM_LOCATION);

		handleBulletAppState();
		initStates();
		initRootNodeMat(app);
	}

	/**
	 * This method initializes the states.
	 */
	public void initStates() {
		cameraState = new CameraState();
		stateManage.attach(cameraState);

		gameInputState = new GameInputState();
		stateManage.attach(gameInputState);

		curveState = new CurveState();
		stateManage.attach(curveState);

		soundState = new SoundState();
		stateManage.attach(soundState);

		sceneState = new SceneState();
		stateManage.attach(sceneState);

		gameSoundState = new GameBackgroundMusicState();
		stateManage.attach(gameSoundState);

		spawnState = new SpawnState();
		stateManage.attach(spawnState);

		difficultyState = new DifficultyState();
		stateManage.attach(difficultyState);

	}

	/**
	 * This method handles bulletAppState.
	 */
	public void handleBulletAppState() {
		bulletAppState = makeBulletAppState();
		stateManage.attach(bulletAppState);
		bulletAppState.setDebugEnabled(false);
		bulletAppState.getPhysicsSpace().setGravity(GRAVITY);
	}

	/**
	 * Instantiates the BulletAppState.
	 * 
	 * @return new BulletAppState
	 */
	public BulletAppState makeBulletAppState() {
		return new BulletAppState();
	}

	/**
	 * Returns the physicsSpace of the application, taken from bulletAppState.
	 * 
	 * @return PhysicsSpace
	 */
	public PhysicsSpace getPhysicsSpace() {
		return bulletAppState.getPhysicsSpace();
	}

	/**
	 * Adds the default material to the rootnode as user data.
	 * 
	 * @param appl
	 *            App to get the assetManager.
	 */
	public void initRootNodeMat(final App appl) {
		final Material mat = new Material(appl.getAssetManager(), MATERIAL_PATH);
		mat.setBoolean("UseMaterialColors", true);
		mat.setColor("Diffuse", ColorRGBA.randomColor());
		mat.setColor("Specular", ColorRGBA.randomColor());
		final float scalar = 0.5f;
		mat.setColor("Ambient", ColorRGBA.White.mult(scalar));
		final float shininess = 64f;
		mat.setFloat("Shininess", shininess);
		mat.setColor("GlowColor", ColorRGBA.Black);
		appl.getRootNode().setUserData("default material", mat);
	}
}
