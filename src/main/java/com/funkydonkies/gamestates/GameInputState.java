package com.funkydonkies.gamestates;

import com.funkydonkies.core.App;
import com.funkydonkies.exceptions.BadDynamicTypeException;
import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
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
	private static final String MAPPING_SPAWN_BALL = "Spawn Ball";
	private static final String MAPPING_TOGGLE_CAMERA = "Toggle Camera";
	private static final String MAPPING_TOGGLE_CURVE_UPDATE = "Toggle Curve Update";
	private static final String MAPPING_ENABLE_CAMERA_DETECTION = "Start Camera";
	private static final String DECREMENT_HEIGHT_MAPPING = "decrement height";
	private static final String INCREMENT_HEIGHT_MAPPING = "increment height";

	private static final String INCREMENT_COMBO = "tier1";

	private static final int FLY_BY_CAM_MOVE_SPEED = 50;

	private App app;
	private InputManager inputManager;
	private AppStateManager stateManager;
	private CameraState cameraState;
	private CurveState curveState;

	@Override
	public final void initialize(final AppStateManager sManager, final Application appl) {
		super.initialize(sManager, appl);

		if (appl instanceof App) {
			this.app = (App) appl;
		} else {
			throw new BadDynamicTypeException();
		}
		this.inputManager = this.app.getInputManager();
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

		if (curveState == null) {
			curveState = stateManager.getState(CurveState.class);
		}

	}

	/** Custom Keybinding: Map named actions to inputs. */
	public void initKeys() {
		inputManager.addMapping(MAPPING_TOGGLE_CAMERA, new KeyTrigger(KeyInput.KEY_C));
		inputManager.addMapping(MAPPING_TOGGLE_CURVE_UPDATE, new KeyTrigger(KeyInput.KEY_U));

		// Control for spawning balls
		inputManager.addMapping(MAPPING_SPAWN_BALL, new KeyTrigger(KeyInput.KEY_SPACE));
		inputManager.addMapping(MAPPING_ENABLE_CAMERA_DETECTION, new KeyTrigger(KeyInput.KEY_T));
		inputManager.addMapping(INCREMENT_HEIGHT_MAPPING, new KeyTrigger(KeyInput.KEY_R));
		inputManager.addMapping(DECREMENT_HEIGHT_MAPPING, new KeyTrigger(KeyInput.KEY_F));
		inputManager.addMapping(INCREMENT_COMBO, new KeyTrigger(KeyInput.KEY_1));

		inputManager.addListener(analogListener, INCREMENT_HEIGHT_MAPPING);
		inputManager.addListener(analogListener, DECREMENT_HEIGHT_MAPPING);
		// Add the names to the action listener
		inputManager.addListener(actionListener, MAPPING_TOGGLE_CAMERA,
				MAPPING_TOGGLE_CURVE_UPDATE, MAPPING_ENABLE_CAMERA_DETECTION);
		inputManager.addListener(analogListener, MAPPING_SPAWN_BALL);

		inputManager.addListener(actionListener, INCREMENT_COMBO);

	}

	private ActionListener actionListener = new ActionListener() {
		public void onAction(final String name, final boolean keyPressed, final float tpf) {
			if (name.equals(MAPPING_TOGGLE_CAMERA) && !keyPressed) {
				if (cameraState.cameraOpened()) { // C KEY
					curveState.toggleCameraEnabled();
					curveState.setUpdateEnabled(curveState.getCameraEnabled());
				} else {
					System.err.println("Open the Camera first! (T key)");
				}
			}
			if (name.equals(MAPPING_TOGGLE_CURVE_UPDATE) && !keyPressed) {
				curveState.toggleUpdateEnabled(); // U KEY
			}
			if (name.equals(MAPPING_ENABLE_CAMERA_DETECTION) && !keyPressed) {
				stateManager.getState(CameraState.class).toggleEnabled(); // S KEY
			}
			if (name.equals(INCREMENT_COMBO) && !keyPressed) {
				stateManager.getState(DifficultyState.class).incDiff(DifficultyState.TIER_BORDER);
			}
		}
	};

	private AnalogListener analogListener = new AnalogListener() {
		public void onAnalog(final String name, final float value, final float tpf) {
			if (name.equals(INCREMENT_HEIGHT_MAPPING)) { // R KEY
				curveState.getSplineCurve().incrementPoints();
			} else if (name.equals(DECREMENT_HEIGHT_MAPPING)) { // F KEY
				curveState.getSplineCurve().decrementPoints();
			}
		}
	};

	/**
	 * Returns ActionListener that implements actions mapped to keypresses.
	 * 
	 * @return ActionListener that implements actions mapped to keypresses
	 */
	public ActionListener getActionListener() {
		return actionListener;
	}
}