package com.funkydonkies.appstates;

import com.funkydonkies.core.App;
import com.funkydonkies.exceptions.BadDynamicTypeException;
import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.light.DirectionalLight;
import com.jme3.math.Vector3f;

/**
 * The AppState that controls the basic aspects of the game, it is responsible for initializing the 
 * game.
 */
public class PlayState extends AbstractAppState {
	private static final Vector3f GRAVITY = new Vector3f(0f, -9.81f, 0f);
	private static final Vector3f CAM_LOCATION = new Vector3f(160, 70, 190);

	private App app;
	private AppStateManager stateManage;


	private static BulletAppState bulletAppState;
	
	private GameInputState gameInputState;
	private CurveState curveState;
	private CameraState cameraState;
	private SpawnState2 spawnState;
	private ComboState comboState;
	private DifficultyState difState;
	
	/**
	 * Initializes the basic components of the game.
	 * @param stateManager AppStateManager, the statemanager of the application, 
	 * passed by the SimpleApplication
	 * @param appl Application: The main Application
	 * @see com.jme3.app.state.AbstractAppState#initialize(com.jme3.app.state.AppStateManager, 
	 * com.jme3.app.Application)
	 */
	public void initialize(final AppStateManager stateManager, final Application appl) {
		super.initialize(stateManager, appl);
		if (appl instanceof App) {
			this.app = (App) appl;
		} else {
			throw new BadDynamicTypeException();
		}
		stateManage = stateManager;
		handleBulletAppState();	

		handleCamera();
		handleBulletAppState();
		initStates();
		final DirectionalLight sun = new DirectionalLight();
		final Vector3f dir = new Vector3f(0, -1, 1);
		sun.setDirection(dir);
		app.getRootNode().addLight(sun);
	}
	
	/**
	 * This method handles the ingame camera.
	 */
	public void handleCamera(){
		app.getFlyByCamera().setEnabled(true);
		app.getCamera().setLocation(CAM_LOCATION);
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
		
		spawnState = new SpawnState2();
		stateManage.attach(spawnState);
		
		comboState = new ComboState();
		stateManage.attach(comboState);
		
		difState = new DifficultyState();
		stateManage.attach(difState);
		
	}
	
	/**
	 * This method handles bulletAppState.
	 */
	public void handleBulletAppState() {
		bulletAppState = new BulletAppState();
		stateManage.attach(bulletAppState);
		bulletAppState.setDebugEnabled(true);
		bulletAppState.getPhysicsSpace().setGravity(GRAVITY);
	}
	
	/**
	 * Returns the physicsSpace of the application, taken from bulletAppState.
	 * @return PhysicsSpace
	 */
	public static PhysicsSpace getPhysicsSpace() {
		return bulletAppState.getPhysicsSpace();
	}
	
	
	
}