package com.funkydonkies.gamestates;

import com.funkydonkies.exceptions.BadDynamicTypeException;
import com.funkydonkies.w4v3.App;
import com.funkydonkies.w4v3.Ball;
import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;

/**
 * Changes the control in-game.
 * 
 * @author Danilo
 *
 */
public class GameInputState extends AbstractAppState {
//	private static final String MAPPING_NAME_LEFT = "Left";
//	private static final String MAPPING_NAME_RIGHT = "Right";
//	private static final String MAPPING_NAME_ROTATE = "Rotate";
	private static final String MAPPING_NAME_SPAWN_BALL = "Spawn Ball";
	private static final float TIME_PER_BALL_SPAWN = 0.5f; // x sec per ball
	
	private float time = TIME_PER_BALL_SPAWN;
	private float timeCount = 0;
	
	private static final int FLY_BY_CAM_MOVE_SPEED = 50;
	
	private App app;
	private InputManager inputManager;
	private AssetManager assetManager;

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
//		inputManager.addMapping(MAPPING_NAME_LEFT, new KeyTrigger(KeyInput.KEY_J));
//		inputManager.addMapping(MAPPING_NAME_RIGHT, new KeyTrigger(KeyInput.KEY_K));
//		inputManager.addMapping(MAPPING_NAME_ROTATE, new KeyTrigger(MouseInput.BUTTON_LEFT));
		
		//Control for spawing balls
		inputManager.addMapping(MAPPING_NAME_SPAWN_BALL, 
				new KeyTrigger(KeyInput.KEY_SPACE));
		
		// Add the names to the action listener
//		inputManager.addListener(actionListener, MAPPING_NAME_SPAWN_BALL);
//		inputManager.addListener(analogListener, MAPPING_NAME_LEFT, MAPPING_NAME_RIGHT, 
//				MAPPING_NAME_ROTATE);
		inputManager.addListener(analogListener, MAPPING_NAME_SPAWN_BALL);

	}

//	private ActionListener actionListener = new ActionListener() {
//		public void onAction(final String name, final boolean keyPressed, final float tpf) {
//			if (name.equals(MAPPING_NAME_PAUSE) && !keyPressed) {
//				// pause game
//			}
//	
//		}
//	};
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