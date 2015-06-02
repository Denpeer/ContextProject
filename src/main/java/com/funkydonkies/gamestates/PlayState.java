package com.funkydonkies.gamestates;

import com.funkydonkies.exceptions.BadDynamicTypeException;
import com.funkydonkies.obstacles.MovingBox;
import com.funkydonkies.obstacles.ObstacleFactory;
import com.funkydonkies.obstacles.Target;
import com.funkydonkies.w4v3.App;
import com.funkydonkies.w4v3.Combo;
import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.font.BitmapText;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;

/**
 * The AppState that controls the basic aspects of the game, it is responsible for initializing the 
 * game.
 */
public class PlayState extends AbstractAppState {
	private static final Vector3f GRAVITY = new Vector3f(0f, -9.81f, 0f);
	private static final String COLOR = "Color";
	private static final String UNSHADED_MATERIAL_PATH = "Common/MatDefs/Misc/Unshaded.j3md";
	private static final Vector3f CAM_LOCATION = new Vector3f(160, 70, 190);

	private App app;
	private Target target;
	private MovingBox movBox;
	private Combo combo;
	private ObstacleFactory factory;

	private static BulletAppState bulletAppState = new BulletAppState();
	private GameInputState gameInputState;
	private CurveState spController;
	private CameraState cameraState;
	private GameSoundState gameSoundState;
	
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
		
		factory = new ObstacleFactory();
		
		final BitmapText comboText = new BitmapText(
				app.getAssetManager().loadFont("Interface/Fonts/Default.fnt"), false);
		combo = new Combo(app.getGuiNode(), comboText);
		movBox = factory.makeMovingBox(app.getRootNode(), app.getAssetManager());
		target = factory.makeTarget(app.getRootNode());
		target.getControl().setCombo(combo);
		
		stateManager.attach(bulletAppState);
		bulletAppState.setDebugEnabled(true);
		bulletAppState.getPhysicsSpace().setGravity(GRAVITY);
		app.getFlyByCamera().setEnabled(false);

		cameraState = new CameraState();
		stateManager.attach(cameraState);
		
		gameInputState = new GameInputState();
		stateManager.attach(gameInputState);
		
		spController = new CurveState();
		stateManager.attach(spController);
		
		gameSoundState = new GameSoundState();
		stateManager.attach(gameSoundState);

		final Material mat2 = new Material(app.getAssetManager(), UNSHADED_MATERIAL_PATH);
		mat2.setColor(COLOR, ColorRGBA.Red);
		movBox.draw(mat2, getPhysicsSpace());
		target.draw(mat2, bulletAppState.getPhysicsSpace());
		app.getCamera().setLocation(CAM_LOCATION);
		combo.display();
	}
	
	/**
	 * Returns the physicsSpace of the application, taken from bulletAppState.
	 * @return PhysicsSpace
	 */
	public static PhysicsSpace getPhysicsSpace() {
		return bulletAppState.getPhysicsSpace();
	}
	
	
	
}
