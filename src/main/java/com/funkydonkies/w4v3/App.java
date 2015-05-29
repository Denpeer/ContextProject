package com.funkydonkies.w4v3;

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
/**
 * Game is run through this class.
 *
 */
public class App extends SimpleApplication {
	private static final Vector3f GRAVITY = new Vector3f(0f, -9.81f, 0f);
	private static final String COLOR = "Color";
	private static final Vector3f CAM_LOCATION = new Vector3f(160, 70, 190);
	private static final String UNSHADED_MATERIAL_PATH = "Common/MatDefs/Misc/Unshaded.j3md";

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
	 * @param args run arguments
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
		
		sp = new SplineCurve(SplineType.CatmullRom, true);

		spController = new SplineCurveController(bridge, sp);
		stateManager.attach(spController);

		final BitmapText comboText = new BitmapText(assetManager.loadFont("Interface/Fonts/Default.fnt"),
				false);
		combo = new Combo(guiNode, comboText);
//		movBox = factory.makeMovingBox(rootNode, assetManager);
		target = factory.makeTarget(rootNode);
		target.getControl().setCombo(combo);
		
		mat = new Material(assetManager, UNSHADED_MATERIAL_PATH);
		mat.setColor(COLOR, ColorRGBA.Yellow);
		
		final Material mat2 = new Material(assetManager, UNSHADED_MATERIAL_PATH);
		mat2.setColor(COLOR, ColorRGBA.Red);
//		movBox.draw(mat2, getPhysicsSpace());
		target.draw(mat2, getPhysicsSpace());
		cam.setLocation(CAM_LOCATION);
		combo.display();
	}

	@Override
	public void simpleUpdate(final float tpf) {
//		movBox.move(tpf);
		getRootNode().detachChildNamed("curve");
//		System.out.println(rigi);
		if (rigi != null) {
			oldRigi = new RigidBodyControl(0f);
			oldRigi = rigi;
		}
//			System.out.println(oldRigi);
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
	 * @return PhysicsSpace jme3 object
	 */
	public PhysicsSpace getPhysicsSpace() {
		return bulletAppState.getPhysicsSpace();
	}
	
}
