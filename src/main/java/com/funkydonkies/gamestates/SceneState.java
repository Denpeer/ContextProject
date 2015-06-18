package com.funkydonkies.gamestates;

import com.funkydonkies.core.App;
import com.funkydonkies.exceptions.BadDynamicTypeException;
import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.water.SimpleWaterProcessor;

/**
 * Loads the scene.
 * 
 * @author Olivier Dikken
 *
 */
public class SceneState extends AbstractAppState {

	private App app;
	private AssetManager assetManager;
	private Node sceneNode = new Node();

	// path to the scene to be loaded on start up
	private static final String SCENE_PATH = "/Scenes/main.j3o";
	// 1 default value, defines the scale of the scene to be loaded by
	// 'initScene()'
	private static final int SCENE_SCALE = 1;
	// default scene translation, defines the translation of the scene to be
	// loaded by 'initScene()'
	private static final Vector3f SCENE_TRANSLATION = new Vector3f(0, 0, 0);
	
	private static final Vector3f WATER_LOCATION = new Vector3f(-400, 2, 200);
	
	private static final int WATER_SCALE = 60;
	
	private static final float AMBIENT_MUL = 1f;

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
		System.out.println("=> loading " + SCENE_PATH + " Spatial ...");
		loadScene(scale, translation);
		System.out.println("... DONE loading .scene Spatial <=");
		
		//create processor
        final SimpleWaterProcessor waterProcessor = new SimpleWaterProcessor(assetManager);
        waterProcessor.setReflectionScene(app.getRootNode());
        waterProcessor.setDebug(false);
        
        app.getViewPort().addProcessor(waterProcessor);

        //create water quad
        final Spatial waterPlane = waterProcessor.createWaterGeometry(100, 100);
        waterPlane.setMaterial(waterProcessor.getMaterial());
        waterPlane.setLocalScale(WATER_SCALE);
        waterPlane.setLocalTranslation(WATER_LOCATION);

        sceneNode.attachChild(waterPlane);
        
        final DirectionalLight light = new DirectionalLight();
        final Vector3f dir = new Vector3f(0, -1, 0);
        light.setDirection(dir);
        
        final AmbientLight aLight = new AmbientLight();
        aLight.setColor(ColorRGBA.White.mult(AMBIENT_MUL));
        
        app.getRootNode().addLight(aLight);
        app.getRootNode().addLight(light);
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
		sceneNode.attachChild(gameLevel);
		app.getRootNode().attachChild(sceneNode);
	}
}
