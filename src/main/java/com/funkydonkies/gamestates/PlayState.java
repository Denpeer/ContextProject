package com.funkydonkies.gamestates;

import com.funkydonkies.combo.Combo;
import com.funkydonkies.core.App;
import com.funkydonkies.exceptions.BadDynamicTypeException;
import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;

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
	private SpawnState spawnState;
	private Combo combo;
	private Node penguinNode;
	
	private PowerUpState powerUpState;
	
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

		
		app.getFlyByCamera().setEnabled(true);
		app.getCamera().setLocation(CAM_LOCATION);

		handleBulletAppState();
		initStates();
		
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
		
		powerUpState = new PowerUpState();
		stateManage.attach(powerUpState);
		
		spawnState = new SpawnState();
		stateManage.attach(spawnState);
		
		//comboState = new ComboState();
		//stateManage.attach(comboState);
		
//		difState = new DifficultyState();
//		stateManage.attach(difState);
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
	public PhysicsSpace getPhysicsSpace() {
		return bulletAppState.getPhysicsSpace();
	}
	
	public Combo getCombo() {
		return combo;
	}
	
	public Node getRootNode() {
		return app.getRootNode();
	}
	
}
