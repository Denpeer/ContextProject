package com.funkydonkies.w4v3;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;

import com.funkydonkies.camdetect.MyFrame;
import com.funkydonkies.controllers.SplineCurveController;
import com.funkydonkies.gamestates.GameInputState;
import com.funkydonkies.obstacles.MovingBox;
import com.funkydonkies.obstacles.ObstacleFactory;
import com.funkydonkies.obstacles.Target;
import com.funkydonkies.w4v3.curve.SplineCurve;
import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.font.BitmapText;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Spline.SplineType;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Spatial;

/**
 * Game is run through this class.
 *
 */
public class App extends SimpleApplication {
	private static final Vector3f GRAVITY = new Vector3f(0f, -9.81f, 0f);
	private static final String COLOR = "Color";
	private static final Vector3f CAM_LOCATION = new Vector3f(160, 70, 190);
	private static final String UNSHADED_MATERIAL_PATH = "Common/MatDefs/Misc/Unshaded.j3md";
	// path to the scene to be loaded on start up
	private static final Path SCENE_PATH = Paths.get("Scenes/testScene.j3o");
	// 1 default value, defines the scale of the scene to be loaded by
	// 'initScene()'
	private static final int SCENE_SCALE = 1;
	// default scene translation, defines the translation of the scene to be
	// loaded by 'initScene()'
	private static final Vector3f SCENE_TRANSLATION = new Vector3f(0, 0, 0);

	private BulletAppState bulletAppState;
	private GameInputState gameInputState;
	private SplineCurveController spController;
	private SplineCurve sp;
	private Material mat;
	private Target target;
	private MovingBox movBox;
	private Combo combo;
	private static RigidBodyControl oldRigi;
	private static RigidBodyControl rigi;
	private ObstacleFactory factory;

	private static Bridge bridge;

	/**
	 * Main method. Instantiates the app and starts its execution.
	 * 
	 * @param args
	 *            run arguments
	 */
	public static void main(final String[] args) {
		final App app = new App();
		oldRigi = new RigidBodyControl(0f);
		rigi = new RigidBodyControl(0f);

		final MyFrame frame = new MyFrame();
		bridge = frame.getVideoCap().getMat2Image();
		new Thread(frame).start();

		app.start();
	}

	@Override
	public void simpleInitApp() {
		factory = new ObstacleFactory();
		bulletAppState = new BulletAppState();
		stateManager.attach(bulletAppState);
		bulletAppState.setDebugEnabled(true);
		bulletAppState.getPhysicsSpace().setGravity(GRAVITY);
		flyCam.setEnabled(false);
		gameInputState = new GameInputState();
		stateManager.attach(gameInputState);

		initScene();

		sp = new SplineCurve(SplineType.CatmullRom, true);

		spController = new SplineCurveController(bridge, sp);
		stateManager.attach(spController);

		final BitmapText comboText = new BitmapText(
				assetManager.loadFont("Interface/Fonts/Default.fnt"), false);
		combo = new Combo(guiNode, comboText);
		// movBox = factory.makeMovingBox(rootNode, assetManager);
		target = factory.makeTarget(rootNode);
		target.getControl().setCombo(combo);

		mat = new Material(assetManager, UNSHADED_MATERIAL_PATH);
		mat.setColor(COLOR, ColorRGBA.Yellow);

		final Material mat2 = new Material(assetManager, UNSHADED_MATERIAL_PATH);
		mat2.setColor(COLOR, ColorRGBA.Red);
		// movBox.draw(mat2, getPhysicsSpace());
		target.draw(mat2, getPhysicsSpace());
		cam.setLocation(CAM_LOCATION);
		combo.display();

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
		final PathMatcher sceneMatcher = FileSystems.getDefault()
				.getPathMatcher("glob:*.scene");
		final PathMatcher j3oMatcher = FileSystems.getDefault().getPathMatcher(
				"glob:*.j3o");
		System.out.println(SCENE_PATH.toString());
		if (SCENE_PATH.toFile().exists()) {
			if (SCENE_PATH.toFile().isDirectory()) {
				if (SCENE_PATH.toFile().isFile()) {
					if (sceneMatcher.matches(SCENE_PATH)) {
						System.out.println("SCENE_PATH .scene file found!");
						System.out.println("=> loading .scene Spatial ...");
						loadScene(SCENE_PATH, scale, translation);
						System.out
								.println("... DONE loading .scene Spatial <=");
						return;
					} else if (j3oMatcher.matches(SCENE_PATH)) {
						System.out.println("SCENE_PATH .j3o file found!");
						System.out.println("=> loading .j3o Spatial ...");
						loadScene(SCENE_PATH, scale, translation);
						System.out.println("... DONE loading .j3o Spatial <=");
						return;
					}

				} else {
					System.out.println(SCENE_PATH.toString()
							+ " Fails on: path.isFile()");
				}
			} else {
				System.out.println(SCENE_PATH.toString()
						+ " Fails on: path.isDirectory()");
			}
		} else {
			System.out.println(SCENE_PATH.toString()
					+ " Fails on: path.exists()");
		}
		System.out
				.println("ERROR => SCENE_PATH is not a valid path or does not point to a .scene or .j3o file.");
	}

	/**
	 * Method is called from the 'initScene()' method. Loads the specified
	 * scene.
	 * 
	 * @param scenePath
	 *            the directory of the .j3o or .scene file.
	 * @param scale
	 *            the scale
	 * @param trans
	 *            the translation
	 */
	private void loadScene(final Path scenePath, final int scale,
			final Vector3f trans) {
		final Spatial gameLevel = assetManager.loadModel(scenePath.toString()
				.replace('\\', '/'));
		gameLevel.setLocalTranslation(trans);
		gameLevel.setLocalScale(scale);
		rootNode.attachChild(gameLevel);
	}

	@Override
	public void simpleUpdate(final float tpf) {
		// movBox.move(tpf);
		getRootNode().detachChildNamed("curve");
		// System.out.println(rigi);
		if (rigi != null) {
			oldRigi = new RigidBodyControl(0f);
			oldRigi = rigi;
		}
		// System.out.println(oldRigi);
		rigi = new RigidBodyControl(0f);
		sp.drawCurve(mat, getPhysicsSpace(), rigi, getRootNode());
		sp.getGeometry().removeControl(oldRigi);
		oldRigi.setEnabled(false);
	}

	@Override
	public void simpleRender(final RenderManager rm) {
		// TODO: add render code
	}

	/**
	 * For easy access, returns the physics space used by the application.
	 * 
	 * @return PhysicsSpace jme3 object
	 */
	public PhysicsSpace getPhysicsSpace() {
		return bulletAppState.getPhysicsSpace();
	}

}
