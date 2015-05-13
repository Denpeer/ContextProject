package com.mycompany.mavenproject1;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;

/**
 * Changes the control in-game.
 * 
 * @author Danilo
 *
 */
public class GameInputState extends AbstractAppState {
	private static final String MAPPING_NAME_LEFT = "Left";
	private static final String MAPPING_NAME_RIGHT = "Right";
	private static final String MAPPING_NAME_ROTATE = "Rotate";
	private static final String MAPPING_NAME_SPAWN_BALL = "Spawn Ball";
	
	private static final int FLY_BY_CAM_MOVE_SPEED = 50;
	
	private App app;
	private InputManager inputManager;

	@Override
	public final void initialize(final AppStateManager sManager,
			final Application appl) {
		super.initialize(sManager, appl);

		this.app = (App) appl;
		this.inputManager = this.app.getInputManager();
		
		int I_WANT_A_CHECKSTYLE_WARNING = 2201012;
		
		initKeys();
		
		app.getFlyByCamera().setMoveSpeed(FLY_BY_CAM_MOVE_SPEED);
		
		// this.app.getRootNode();
		// this.app.getAssetManager();
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
	private void initKeys() {
		inputManager.addMapping(MAPPING_NAME_LEFT, new KeyTrigger(KeyInput.KEY_J));
		inputManager.addMapping(MAPPING_NAME_RIGHT, new KeyTrigger(KeyInput.KEY_K));
		inputManager.addMapping(MAPPING_NAME_ROTATE, new KeyTrigger(KeyInput.KEY_SPACE));
		
		//Control for spawing balls
		inputManager.addMapping(MAPPING_NAME_SPAWN_BALL, 
				new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
		
		// Add the names to the action listener
		inputManager.addListener(actionListener, MAPPING_NAME_SPAWN_BALL);
		inputManager.addListener(analogListener, MAPPING_NAME_LEFT, MAPPING_NAME_RIGHT, 
				MAPPING_NAME_ROTATE);

	}

	private ActionListener actionListener = new ActionListener() {
		public void onAction(final String name, final boolean keyPressed, final float tpf) {
//			if (name.equals(MAPPING_NAME_PAUSE) && !keyPressed) {
//				// pause game
//			}
		}
	};

	private AnalogListener analogListener = new AnalogListener() {
		public void onAnalog(final String name, final float value, final float tpf) {
			if (name.equals(MAPPING_NAME_ROTATE)) {
				//player.rotate(0, value * speed, 0);
				app.makeBall();
			}
//			if (name.equals(MAPPING_NAME_RIGHT)) {
////				Vector3f v = player.getLocalTranslation();
////				player.setLocalTranslation(v.x + value * speed, v.y, v.z);
//			}
//			if (name.equals(MAPPING_NAME_LEFT)) {
////				Vector3f v = player.getLocalTranslation();
////				player.setLocalTranslation(v.x - value * speed, v.y, v.z);
//			}
		}
	};
	
//	private ActionListener actionListener = new ActionListener() {
//        public void onAction(final String name, final boolean pressed, final float tpf) {
//            System.out.println(name + "  = " + pressed);
//        }
//    };
//    
//    private AnalogListener analogListener = new AnalogListener() {
//        public void onAnalog(final String name, final float value, final float tpf) {
//            System.out.println(name + " = " + value);
//        }
//    };
	

}