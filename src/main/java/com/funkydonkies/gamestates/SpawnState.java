package com.funkydonkies.gamestates;

import com.funkydonkies.exceptions.BadDynamicTypeException;
import com.funkydonkies.w4v3.App;
import com.funkydonkies.w4v3.Ball;
import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;

/**
 * Controls spawning behavior of all object in the game (e.g Ball, obstacles).
 */
public class SpawnState extends AbstractAppState {
	private static float BALL_SPAWN_SPEED = 2f;
	public static final Vector3f BALL_DEFAULT_SPAWN_LOCATION = new Vector3f(25f, 130f, 0f);
	public static final Vector3f BALL_DEFAULT_INITIAL_SPEED = new Vector3f(10, -22, 0);
	
	private float ballTimer;
	private App app;
	private AppStateManager sManager; 
	private AssetManager assetManager;
	private PlayState playState;
	
	/**
	 * Initializes the spawnstate, i.e. it initializes its attributes.
	 * @see com.jme3.app.state.AbstractAppState#initialize(com.jme3.app.state.AppStateManager, com.jme3.app.Application)
	 */
	@Override
	public void initialize(final AppStateManager stateManager, final Application appl) {
		super.initialize(stateManager, app);
		sManager = stateManager;
		if (appl instanceof App) {
			this.app = (App) appl;
		} else {
			throw new BadDynamicTypeException();
		}
		assetManager = app.getAssetManager();
		playState = sManager.getState(PlayState.class);
	}
	
	/**
	 * Checks whether something new should be spawned, if so it calls the appropriate method.
	 * @see com.jme3.app.state.AbstractAppState#update(float)
	 */
	@Override
	public void update(final float tpf) {
		super.update(tpf);
		ballTimer += tpf;
		if (ballTimer > BALL_SPAWN_SPEED) {
			spawnBall(BALL_DEFAULT_SPAWN_LOCATION, BALL_DEFAULT_INITIAL_SPEED);
			ballTimer = 0;
		}
	}
	
	/**
	 * Returns a standard unshaded material.
	 * @return mat Material
	 */
	private Material makeMaterial() {
		final Material mat = new Material(assetManager,
				"Common/MatDefs/Misc/Unshaded.j3md");
		mat.setColor("Color", ColorRGBA.Blue);
		return mat;
	}
	
	
	/**
	 * Creates and spawns a new ball in the scene.
	 * @param startLocation Vector3f location in the scene to spawn the ball at.
	 * @paam initialSpeed Vector3f the speed which is given when it spawns.
	 */
	public void spawnBall(Vector3f startLocation, Vector3f initialspeed) {
		final Ball ball = new Ball("ball");
		ball.setMaterial(makeMaterial());
		playState.getPhysicsSpace().add(ball.getControl());
		app.getRootNode().attachChild(ball);
		ball.setSpeed(initialspeed);
		ball.setLocation(startLocation);
	}
	
}
