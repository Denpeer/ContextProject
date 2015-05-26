package com.mycompany.mavenproject1;

import com.funkydonkies.w4v3.App;
import com.funkydonkies.w4v3.SplineCurveController;
import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;

/**
 * Changes the control in-game.
 * 
 * @author Danilo
 *
 */
public class GameInputState extends AbstractAppState {
	private static final String MAPPING_NAME_SPAWN_BALL = "Spawn Ball";
	private static final String MAPPING_TOGGLE_CAMERA = "Toggle Camera";
	private static final String MAPPING_TOGGLE_CURVE_UPDATE = "Toggle Curve Update";
	private static final float TIME_PER_BALL_SPAWN = 0.5f;
	
	private float time = TIME_PER_BALL_SPAWN;
	private float timeCount = 0;
	
	private static final int FLY_BY_CAM_MOVE_SPEED = 50;
	
	private App app;
	private InputManager inputManager;
	private AssetManager assetManager;
	private AppStateManager stateManager;
	
	private SplineCurveController curveState;
	

	@Override
	public final void initialize(final AppStateManager sManager,
			final Application appl) {
		super.initialize(sManager, appl);

		if (appl instanceof App) {
			this.app = (App) appl;
		} else {
			throw new BadDynamicTypeException();
		}
		this.inputManager = this.app.getInputManager();
		this.assetManager = this.app.getAssetManager();
		this.stateManager = sManager;
		
		initKeys();
		
		app.getFlyByCamera().setMoveSpeed(FLY_BY_CAM_MOVE_SPEED);
		
		// init stuff that is independent of whether state is PAUSED or RUNNING
		// this.app.doSomething(); // call custom methods...
	}


	// Note that update is only called while the state is both attached and enabled
	@Override
	public final void update(final float tpf) {
		if (curveState == null) {
			curveState = stateManager.getState(SplineCurveController.class);
		}
	}

	/** Custom Keybinding: Map named actions to inputs. */
	public void initKeys() {
//		inputManager.addMapping(MAPPING_NAME_ROTATE, new KeyTrigger(MouseInput.BUTTON_LEFT));
		inputManager.addMapping(MAPPING_TOGGLE_CAMERA, new KeyTrigger(KeyInput.KEY_C));
		inputManager.addMapping(MAPPING_TOGGLE_CURVE_UPDATE, new KeyTrigger(KeyInput.KEY_U));
		
		//Control for spawning balls
		inputManager.addMapping(MAPPING_NAME_SPAWN_BALL, new KeyTrigger(KeyInput.KEY_SPACE));
		
		// Add the names to the action listener
		inputManager.addListener(actionListener, MAPPING_TOGGLE_CAMERA, 
				MAPPING_TOGGLE_CURVE_UPDATE);
//		inputManager.addListener(analogListener, MAPPING_NAME_LEFT, MAPPING_NAME_RIGHT, 
//				MAPPING_NAME_ROTATE);
		inputManager.addListener(analogListener, MAPPING_NAME_SPAWN_BALL);

	}

	private ActionListener actionListener = new ActionListener() {
		public void onAction(final String name, final boolean keyPressed, final float tpf) {
			if (name.equals(MAPPING_TOGGLE_CAMERA) && !keyPressed) {
				curveState.toggleCameraEnabled();
				curveState.setUpdateEnabled(curveState.getCameraEnabled());
			}
			if (name.equals(MAPPING_TOGGLE_CURVE_UPDATE) && !keyPressed) {
				curveState.toggleUpdateEnabled();
			}
	
		}
	};
	
	private AnalogListener analogListener = new AnalogListener() {
		public void onAnalog(final String name, final float value, final float tpf) {
//			if (name.equals(MAPPING_NAME_ROTATE)) {
//				player.rotate(0, value * speed, 0);
//			}
//			if (name.equals(MAPPING_NAME_RIGHT)) {
////				Vector3f v = player.getLocalTranslation();
////				player.setLocalTranslation(v.x + value * speed, v.y, v.z);
//			}
//			if (name.equals(MAPPING_NAME_LEFT)) {
////				Vector3f v = player.getLocalTranslation();
////				player.setLocalTranslation(v.x - value * speed, v.y, v.z);
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