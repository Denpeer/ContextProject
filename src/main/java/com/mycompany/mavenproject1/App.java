package com.mycompany.mavenproject1;

import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.collision.PhysicsCollisionObject;
import com.jme3.bullet.collision.shapes.SphereCollisionShape;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Spline.SplineType;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Sphere;

/**
 * Application startup. Contains all AppStates.
 *
 */
public class App extends SimpleApplication {
	private static final Vector3f GRAVITY = new Vector3f(0f, -9.81f, 0f);
	private static final float BALL_RADIUS = 0.5f;
	private static final Vector3f BALL_SPAWN_LOCATION = new Vector3f(10f, 15f, 0f);
	private static final Vector3f CAM_LOCATION = new Vector3f(30, 4, 40);
	private static final Vector3f BALL_INITIAL_SPEED = new Vector3f(5, -22, 0);

	private BulletAppState bulletAppState;
	private GameInputState gameInputState;
	
	private PhysicsController ballPhy;
	
	/**
	 * Main method. Instantiates the app and starts its execution.
	 * @param args run arguments
	 */
	public static void main(final String[] args) {
		final App app = new App();
		app.start();
	}

	@Override
	public final void simpleInitApp() {
		// inputManager.setCursorVisible( true );
		/* Set up physics */
		bulletAppState = new BulletAppState();
		stateManager.attach(bulletAppState);
//		bulletAppState.setDebugEnabled(true);
		bulletAppState.getPhysicsSpace().setGravity(GRAVITY);
		// flyCam.setEnabled(false);
		
		gameInputState = new GameInputState();
		stateManager.attach(gameInputState);

		initWorld();
		
		final Vector3f[] points = testPoints();

		final SplineCurve sp = new SplineCurve(SplineType.CatmullRom, points, (float) 0.6, true);
		sp.drawCurve(rootNode, assetManager, getPhysicsSpace());
		cam.setLocation(CAM_LOCATION);
		
		
	}

	/** Generates test points, array filled with Vector3fs.
	 * 
	 * @return an array of points filled with Vector3fs
	 */
	public final Vector3f[] testPoints() {
		
		final Vector3f v0 = new Vector3f(0, 6, 0), 
				v1 = new Vector3f(10, 6, 0), 
				v2 = new Vector3f(15, 1, 0), 
				v3 = new Vector3f(20, 3, 0), 
				v4 = new Vector3f(25, 0, 0), 
				v5 = new Vector3f(30, 6, 0), 
				v6 = new Vector3f(35, 2, 0), 
				v7 = new Vector3f(40, 2, 0), 
				v8  = new Vector3f(45, 1, 0), 
				v9 = new Vector3f(50, 5, 0), 
				v10 = new Vector3f(70, 3, 0);
		
		final Vector3f[] points = { v0, v1, v2, v3, v4, v5, v6, v7, v8, v9, v10 };

		return points;
	}

	@Override
	public final void simpleUpdate(final float tpf) {

	}

	/**
	 *  Testing ball spawning. 
	 */
	public final void initWorld() {
		/* Ball */
		makeBall();
	}
	
	/**
	 * Creates a new ball object and instantiates a physics controller for it.
	 * Adds the physics controller to the physics space and the ball to the scene
	 */
	public final void makeBall() {
		/* Ball physics control */
		
		System.out.println("make ball");
		
		ballPhy = new PhysicsController(new SphereCollisionShape(BALL_RADIUS), BALL_RADIUS * 2);
		ballPhy.setLinearVelocity(BALL_INITIAL_SPEED);

		/* Ball spatial */
		final Sphere ball = new Sphere(20, 20, BALL_RADIUS);
		final Geometry geom = new Geometry("Sphere", ball);
		final Material mat = new Material(assetManager,
				"Common/MatDefs/Misc/Unshaded.j3md");
		mat.setColor("Color", ColorRGBA.Blue);
		geom.setMaterial(mat);

		/* Ball node */
		final Node node = new Node("ball");
		node.addControl(ballPhy);
		node.attachChild(geom);
		/* attach node and add the physics controller to the game */
		rootNode.attachChild(node);
		getPhysicsSpace().add(ballPhy);
		ballPhy.setRestitution(1f);
		ballPhy.setPhysicsLocation(BALL_SPAWN_LOCATION);  // Move the ball to its spawn location
		ballPhy.addCollideWithGroup(PhysicsCollisionObject.COLLISION_GROUP_02);
	}

	@Override
	public void simpleRender(final RenderManager rm) {
		// TODO: add render code
	}

	/**
	 * For easy access, returns the physics space used by the application.
	 * @return PhysicsSpace 
	 */
	private PhysicsSpace getPhysicsSpace() {
		return bulletAppState.getPhysicsSpace();
	}
}
