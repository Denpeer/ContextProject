package com.funkydonkies.w4v3;

import com.funkydonkies.w4v3.obstacles.MovingBox;
import com.funkydonkies.w4v3.obstacles.ObstacleFactory;
import com.funkydonkies.w4v3.obstacles.Target;
import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.PhysicsSpace;
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
	private static final Vector3f CAM_LOCATION = new Vector3f(30, 4, 40);
	private static final String UNSHADED_MATERIAL_PATH = "Common/MatDefs/Misc/Unshaded.j3md";

	private BulletAppState bulletAppState;
	private GameInputState gameInputState;
	
	private MovingBox clBox;
	private Target tar;
	//private PhysicsController ballPhy;
	
	private float time = 1;		// needs better name
	private float timeCount = 0;	// needs better name

	/**
	 * Main method. Instantiates the app and starts its execution.
	 * @param args run arguments
	 */
	public static void main(final String[] args) {
		final App app = new App();

		app.start();
	}

	@Override
	public void simpleInitApp() {
		// inputManager.setCursorVisible( true );
		/* Set up physics */

		bulletAppState = new BulletAppState();
		stateManager.attach(bulletAppState);
		//bulletAppState.setDebugEnabled(true);
		bulletAppState.getPhysicsSpace().setGravity(GRAVITY);
		//flyCam.setEnabled(false);
		
		gameInputState = new GameInputState();
		stateManager.attach(gameInputState);

		final Vector3f[] points = testPoints();

		final SplineCurve sp = new SplineCurve(SplineType.CatmullRom, points, (float) 0.6, true);
		final Material mat = new Material(assetManager, UNSHADED_MATERIAL_PATH);
		mat.setColor(COLOR, ColorRGBA.Orange);
		sp.drawCurve(rootNode, mat, getPhysicsSpace());
		
		final ObstacleFactory facto = new ObstacleFactory();
		
		final int obstacleWidth = 2;
		final int obstacleHeight = 4;
		final int obstacleDepth = 1;
		
		final int targetWidth = 1;
		final int targetHeight = 1;
		final int targetDepth = 1;
		
		clBox = (MovingBox) facto.makeObstacle("MOVINGBOX", obstacleWidth, obstacleHeight, 
				obstacleDepth);
		
		tar = (Target) facto.makeObstacle("TARGET", targetWidth, targetHeight, targetDepth);
		
		final Material mat2 = new Material(assetManager, UNSHADED_MATERIAL_PATH);
		mat2.setColor(COLOR, ColorRGBA.Red);
		clBox.draw(mat2, getPhysicsSpace(), rootNode);
		final Material mat3 = new Material(assetManager, UNSHADED_MATERIAL_PATH);
		mat3.setColor(COLOR, ColorRGBA.Yellow);
		tar.draw(mat3, getPhysicsSpace(), rootNode);
		cam.setLocation(CAM_LOCATION);
		
	}

	/** Used to generate testPoints for the curve.
	 * 
	 * @return A variable length Vector3f array
	 */
	public Vector3f[] testPoints() {
		
		final Vector3f v0 = new Vector3f(0, 6, 0),
				v1 = new Vector3f(10, 6, 0),
				v2 = new Vector3f(15, 1, 0),
				v3 = new Vector3f(20, 3, 0),
				v4 = new Vector3f(25, 0, 0),
				v5 = new Vector3f(30, 5, 0),
				v6 = new Vector3f(35, 2, 0),
				v7 = new Vector3f(40, 2, 0),
				v8 = new Vector3f(45, 1, 0),
				v9 = new Vector3f(50, 5, 0),
				v10 = new Vector3f(70, 3, 0);

		
		final Vector3f[] points = { v0, v1, v2, v3, v4, v5, v6, v7, v8, v9, v10 };
		
		return points;
	}

	@Override
	public void simpleUpdate(final float tpf) {
		clBox.move(tpf);
		timeCount += tpf;

		tar.collides(rootNode);
		
		

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
