package com.funkydonkies.gamestates;

import com.funkydonkies.controllers.SplineCurveController;
import com.funkydonkies.exceptions.BadDynamicTypeException;
import com.funkydonkies.w4v3.App;
import com.funkydonkies.w4v3.Ball;
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
	private static final String MAPPING_ENABLE_CAMERA_DETECTION = "Start Camera";
	private static final String INCREMENT_HEIGHT_MAPPING = "increment height";
	private static final String DECREMENT_HEIGHT_MAPPING = "decrement height";

	private static final float TIME_PER_BALL_SPAWN = 1f;
	
	private float time = TIME_PER_BALL_SPAWN;
	private float timeCount = 0;
	
	private static final int FLY_BY_CAM_MOVE_SPEED = 50;
	
	private App app;
	private InputManager inputManager;
	private AssetManager assetManager;
	private AppStateManager stateManager;
	
	private SplineCurveController curveState;
	private CameraState cameraState;

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
		cameraState = stateManager.getState(CameraState.class);
		app.getFlyByCamera().setMoveSpeed(FLY_BY_CAM_MOVE_SPEED);
		
		// init stuff that is independent of whether state is PAUSED or RUNNING
		// this.app.doSomething(); // call custom methods...
	}


	// Note that update is only called while the state is both attached and enabled
	@Override
	public final void update(final float tpf) {
		timeCount += tpf;
		if (curveState == null) {
			curveState = stateManager.getState(SplineCurveController.class);
		}
		if (timeCount > TIME_PER_BALL_SPAWN) {
			timeCount = 0;
			final Ball ball = new Ball(assetManager);
			ball.spawn(app.getRootNode(), PlayState.getPhysicsSpace(), true);
		}
	}

	/** Custom Keybinding: Map named actions to inputs. */
	public void initKeys() {
//		inputManager.addMapping(MAPPING_NAME_ROTATE, new KeyTrigger(MouseInput.BUTTON_LEFT));
		inputManager.addMapping(MAPPING_TOGGLE_CAMERA, new KeyTrigger(KeyInput.KEY_C));
		inputManager.addMapping(MAPPING_TOGGLE_CURVE_UPDATE, new KeyTrigger(KeyInput.KEY_U));
		
		//Control for spawning balls
		inputManager.addMapping(MAPPING_NAME_SPAWN_BALL, new KeyTrigger(KeyInput.KEY_SPACE));
		inputManager.addMapping(MAPPING_ENABLE_CAMERA_DETECTION, new KeyTrigger(KeyInput.KEY_S));
		
		inputManager.addMapping(INCREMENT_HEIGHT_MAPPING, new KeyTrigger(KeyInput.KEY_R));
		inputManager.addMapping(DECREMENT_HEIGHT_MAPPING, new KeyTrigger(KeyInput.KEY_F));

		inputManager.addListener(analogListener, INCREMENT_HEIGHT_MAPPING);
		inputManager.addListener(analogListener, DECREMENT_HEIGHT_MAPPING);
		// Add the names to the action listener
		inputManager.addListener(actionListener, MAPPING_TOGGLE_CAMERA, 
				MAPPING_TOGGLE_CURVE_UPDATE, MAPPING_ENABLE_CAMERA_DETECTION);
//		inputManager.addListener(analogListener, MAPPING_NAME_LEFT, MAPPING_NAME_RIGHT, 
//				MAPPING_NAME_ROTATE);
		inputManager.addListener(analogListener, MAPPING_NAME_SPAWN_BALL);

	}

	private ActionListener actionListener = new ActionListener() {
		public void onAction(final String name, final boolean keyPressed, final float tpf) {
			if (name.equals(MAPPING_TOGGLE_CAMERA) && !keyPressed) {
				//C KEY
				if (cameraState.cameraOpened()) {
					curveState.toggleCameraEnabled();
					curveState.setUpdateEnabled(curveState.getCameraEnabled());
				} else {
					System.err.println("Open the Camera first! (S key)");
				}
			}
			if (name.equals(MAPPING_TOGGLE_CURVE_UPDATE) && !keyPressed) {
				//U KEY
				curveState.toggleUpdateEnabled();
			}
			if (name.equals(MAPPING_ENABLE_CAMERA_DETECTION) && !keyPressed) {
				//S KEY
				stateManager.getState(CameraState.class).toggleEnabled();
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
					ball.spawn(app.getRootNode(), PlayState.getPhysicsSpace(), true);
					timeCount = 0;
				}
			}
			if (name.equals(INCREMENT_HEIGHT_MAPPING)) {
				curveState.getSplineCurve().incrementPoints();
			} else if (name.equals(DECREMENT_HEIGHT_MAPPING)) {
				curveState.getSplineCurve().decrementPoints();
			}
		}
	};
}