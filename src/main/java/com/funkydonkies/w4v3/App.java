package com.funkydonkies.w4v3;

import com.funkydonkies.camdetect.MyFrame;
import com.funkydonkies.w4v3.curve.CustomCurveMesh;
import com.funkydonkies.w4v3.curve.SplineCurve;
import com.funkydonkies.w4v3.curve.SplineCurveController;
import com.funkydonkies.w4v3.obstacles.MovingBox;
import com.funkydonkies.w4v3.obstacles.ObstacleFactory;
import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Spline.SplineType;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.mycompany.mavenproject1.GameInputState;
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
	private MovingBox clBox;
	//private PhysicsController ballPhy;
	boolean bool = true;
	private float time = 1;		// needs better name
	private float timeCount = 0;	// needs better name
	private static RigidBodyControl oldRigi;
	private static RigidBodyControl rigi;
	
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
		bulletAppState = new BulletAppState();
		stateManager.attach(bulletAppState);
		//bulletAppState.setDebugEnabled(true);
		bulletAppState.getPhysicsSpace().setGravity(GRAVITY);
		flyCam.setEnabled(true);
		gameInputState = new GameInputState();
		stateManager.attach(gameInputState);
		
		sp = new SplineCurve(SplineType.CatmullRom, (float) 0.6, true);

		spController = new SplineCurveController(bridge, sp);
		stateManager.attach(spController);

		mat = new Material(assetManager, UNSHADED_MATERIAL_PATH);
		mat.setColor(COLOR, ColorRGBA.Yellow);
		
		final ObstacleFactory facto = new ObstacleFactory();
		final int obstacleWidth = 2;
		final int obstacleHeight = 4;
		final int obstacleDepth = 1;
		
//		clBox = (MovingBox) facto.makeObstacle("MOVINGBOX", obstacleWidth, obstacleHeight, 
//				obstacleDepth);
		
		final Material mat2 = new Material(assetManager, UNSHADED_MATERIAL_PATH);
		mat2.setColor(COLOR, ColorRGBA.Red);
//		clBox.draw(mat2, getPhysicsSpace(), rootNode);
		
		cam.setLocation(CAM_LOCATION);
		
	}

	@Override
	public void simpleUpdate(final float tpf) {
		timeCount += tpf;
		if(rigi != null){
			oldRigi = new RigidBodyControl(0f);
			oldRigi = rigi;
		}
		rigi = new RigidBodyControl(0f);
		Vector3f[] pts = new Vector3f[100];
		sp.drawCurve(mat, getPhysicsSpace(), rigi, getRootNode(), pts);
		sp.getGeometry().removeControl(oldRigi);
		oldRigi.setEnabled(false);
		
		if (timeCount > time) {
			timeCount = 0;
		}
		
		

	}

//	/**
//	 * Creates a new ball object and instantiates a physics controller for it.
//	 * Adds the physics controller to the physics space and the ball to the scene
//	 */
//	private void makeBall() {
//		/* Ball physics control */
//		ballPhy = new PhysicsController(new SphereCollisionShape(BALL_RADIUS), BALL_RADIUS * 2);
//		ballPhy.setLinearVelocity(BALL_INITIAL_SPEED);
//
//		/* Ball spatial */
//		final Sphere ball = new Sphere(20, 20, BALL_RADIUS);
//		final Geometry geom = new Geometry("Sphere", ball);
//		final Material mat = new Material(assetManager,
//				"Common/MatDefs/Misc/Unshaded.j3md");
//		mat.setColor("Color", ColorRGBA.Blue);
//		geom.setMaterial(mat);
//
//		/* Ball node */
//		final Node node = new Node("ball");
//		node.addControl(ballPhy);
//		node.attachChild(geom);
//		/* attach node and add the physics controller to the game */
//		rootNode.attachChild(node);
//		getPhysicsSpace().add(ballPhy);
//		ballPhy.setRestitution(1f);
//		ballPhy.setPhysicsLocation(BALL_SPAWN_LOCATION);  // Move the ball to its spawn location
//	}

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
