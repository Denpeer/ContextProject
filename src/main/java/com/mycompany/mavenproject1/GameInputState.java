package com.mycompany.mavenproject1;

import com.funkydonkies.w4v3.App;
import com.funkydonkies.w4v3.WaveState;
import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.math.Vector2f;
import com.jme3.terrain.geomipmap.TerrainQuad;

/**
 * Changes the control in-game.
 * 
 * @author Danilo
 *
 */
public class GameInputState extends AbstractAppState {
	private static final String MAPPING_NAME_LEFT = "Left";
	private static final String MAPPING_NAME_RIGHT = "Right";
//	private static final String MAPPING_NAME_ROTATE = "Rotate";
	private static final String MAPPING_NAME_SPAWN_BALL = "Spawn Ball";
	private static final String MAPPING_TOGGLE_CONTROLS = "Toggle Controls";
	private static final float TIME_PER_BALL_SPAWN = 0.5f;
	
	private float time = TIME_PER_BALL_SPAWN;
	private float timeCount = 0;
	
	private static final int FLY_BY_CAM_MOVE_SPEED = 125;
	
	private App app;
	private InputManager inputManager;
	private AssetManager assetManager;
	private WaveState waveState;
	
	@Override
	public final void initialize(final AppStateManager stateManager,
			final Application appl) {
		super.initialize(stateManager, appl);

		if (appl instanceof App) {
			this.app = (App) appl;
		} else {
			throw new BadDynamicTypeException();
		}
		this.inputManager = this.app.getInputManager();
		this.assetManager = this.app.getAssetManager();
		
		this.waveState = stateManager.getState(WaveState.class);
		
		initKeys();
		
		app.getFlyByCamera().setMoveSpeed(FLY_BY_CAM_MOVE_SPEED);
		
		// this.app.getRootNode();
		// this.app.getStateManager();
		// this.app.getViewPort();
		// this.stateManager.getState(BulletAppState.class);

		// init stuff that is independent of whether state is PAUSED or RUNNING
		// this.app.doSomething(); // call custom methods...
	}

	@Override
	public final void cleanup() {
		// super.cleanup();
		// unregister all my listeners, detach all my nodes, etc...
		// this.app.doSomethingElse(); // call custom methods...
	}

	@Override
	public final void setEnabled(final boolean enabled) {
		// Pause and unpause
		super.setEnabled(enabled);
		
//		if (enabled) {
//			// init stuff that is in use while this state is RUNNING
//			// this.app.doSomethingElse(); // call custom methods...
//		} 
//			else {
//			// take away everything not needed while this state is PAUSED
//			// ...
//		}
	}

	// Note that update is only called while the state is both attached and enabled
	@Override
	public final void update(final float tpf) {
		// do the following while game is RUNNING
		// this.app.getRootNode().getChild("blah").scale(tpf); // modify scene
		// graph...
		// x.setUserData(...); // call some methods...
		
	}

	/** Custom Keybinding: Map named actions to inputs. */
	public void initKeys() {
		inputManager.addMapping(MAPPING_NAME_LEFT, new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
		inputManager.addMapping(MAPPING_NAME_RIGHT, new MouseButtonTrigger(MouseInput.BUTTON_RIGHT));
		inputManager.addMapping(MAPPING_TOGGLE_CONTROLS, new KeyTrigger(KeyInput.KEY_C));
//		inputManager.addMapping(MAPPING_NAME_ROTATE, new KeyTrigger(MouseInput.BUTTON_LEFT));
		
		//Control for spawing balls
		inputManager.addMapping(MAPPING_NAME_SPAWN_BALL, 
				new KeyTrigger(KeyInput.KEY_SPACE));
		
		// Add the names to the action listener
//		inputManager.addListener(actionListener, MAPPING_NAME_SPAWN_BALL);
		inputManager.addListener(actionListener, MAPPING_NAME_LEFT, MAPPING_NAME_RIGHT, 
				MAPPING_TOGGLE_CONTROLS); 
//				MAPPING_NAME_ROTATE);
		inputManager.addListener(analogListener, MAPPING_NAME_SPAWN_BALL);

	}

	private ActionListener actionListener = new ActionListener() {
		public void onAction(final String name, final boolean keyPressed, final float tpf) {
//			if (name.equals(MAPPING_NAME_PAUSE) && !keyPressed) {
//				// pause game
//			}
			if (name.equals(MAPPING_NAME_RIGHT)) {
				waveState.setLowerTerrain(keyPressed);
			}
			if (name.equals(MAPPING_NAME_LEFT)) {
				waveState.setRaiseTerrain(keyPressed);
			}
			if (name.equals(MAPPING_TOGGLE_CONTROLS) && !keyPressed) {
				waveState.setControlsEnabled(!waveState.getControlsEnabled());
			}
	
		}
	};
	private AnalogListener analogListener = new AnalogListener() {
		public void onAnalog(final String name, final float value, final float tpf) {
//			if (name.equals(MAPPING_NAME_ROTATE)) {
//				player.rotate(0, value * speed, 0);
//			}
			if (name.equals(MAPPING_NAME_SPAWN_BALL)) {
				timeCount += tpf;
				if (timeCount > time) {
					final Ball ball = new Ball(assetManager);
					ball.spawn(app.getRootNode(), app.getPhysicsSpace(), true);
					timeCount = 0;
				}
			}
		
		}
	};
	
	
	
	
}