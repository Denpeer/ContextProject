package com.funkydonkies.gamestates;

import java.nio.file.Path;
import java.nio.file.Paths;

import com.funkydonkies.exceptions.BadDynamicTypeException;
import com.funkydonkies.w4v3.App;
import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;

/**
 * Loads the scene.
 * 
 * @author Olivier Dikken
 *
 */
public class SceneState extends AbstractAppState {

	private App app;
	private AssetManager assetManager;

	// path to the scene to be loaded on start up
	private static final String SCENE_PATH = "/Scenes/main.j3o";
	// 1 default value, defines the scale of the scene to be loaded by
	// 'initScene()'
	private static final int SCENE_SCALE = 1;
	// default scene translation, defines the translation of the scene to be
	// loaded by 'initScene()'
	private static final Vector3f SCENE_TRANSLATION = new Vector3f(50, 0, 0);

	@Override
	public final void initialize(final AppStateManager sManager,
			final Application appl) {
		super.initialize(sManager, appl);

		if (appl instanceof App) {
			this.app = (App) appl;
		} else {
			throw new BadDynamicTypeException();
		}
		this.assetManager = this.app.getAssetManager();
		initScene();
	}

	/**
	 * check if path is set properly. Load the scene with default scale &
	 * translation and print status to console.
	 */
	public void initScene() {
		initScene(SCENE_SCALE, SCENE_TRANSLATION);
	}

	/**
	 * check if path is set properly. Load the scene and print status to
	 * console.
	 * 
	 * @param scale
	 *            of the scene. default=1
	 * @param translation
	 *            of the scene. default=(0,0,0)
	 */
	public void initScene(final int scale, final Vector3f translation) {
		System.out.println("SCENE_PATH file found!");
		System.out.println("=> loading " + SCENE_PATH.toString() + " Spatial ...");
		loadScene(scale, translation);
		System.out.println("... DONE loading .scene Spatial <=");
	}

	/**
	 * Method is called from the 'initScene()' method. Loads the specified
	 * scene.
	 * 
	 * @param scale
	 *            the scale
	 * @param trans
	 *            the translation
	 */
	private void loadScene(final int scale, final Vector3f trans) {
		final Spatial gameLevel = assetManager.loadModel(SCENE_PATH);
		gameLevel.setLocalTranslation(trans);
		gameLevel.setLocalScale(scale);
		app.getRootNode().attachChild(gameLevel);
		final int x = 100;
		gameLevel.move(x, 0, 0);
	}
}
