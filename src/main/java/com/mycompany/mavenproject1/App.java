package com.mycompany.mavenproject1;

/**
 * Wave
 *
 */

import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.PhysicsSpace;
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
 * 
 * @author Jonathan
 *
 */
public class App extends SimpleApplication {
	private static final Vector3f GRAVITY = new Vector3f(0f, -9.81f, 0f);
	private static final float BALL_RADIUS = 0.5f;
	private static final Vector3f BALL_SPAWN_LOCATION = new Vector3f(30f, 30f, 0f);
	private static final Vector3f CAM_LOCATION = new Vector3f(30, 4, 40);

	private BulletAppState bulletAppState;
	
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

		initWorld();

		final Vector3f[] points = TestPoints();

		final SplineCurve sp = new SplineCurve(SplineType.CatmullRom, points, (float) 0.6, true);
		sp.drawCurve(rootNode, assetManager, getPhysicsSpace());

		cam.setLocation(CAM_LOCATION);

	}

	public Vector3f[] TestPoints() {
		Vector3f[] points = new Vector3f[11];
		points[0] = new Vector3f(0, 6, 0);
		points[1] = new Vector3f(10, 6, 0);
		points[2] = new Vector3f(15, 1, 0);
		points[3] = new Vector3f(20, 3, 0);
		points[4] = new Vector3f(25, 0, 0);
		points[5] = new Vector3f(30, 6, 0);
		points[6] = new Vector3f(35, 2, 0);
		points[7] = new Vector3f(40, 2, 0);
		points[8] = new Vector3f(45, 1, 0);
		points[9] = new Vector3f(50, 5, 0);
		points[10] = new Vector3f(70, 3, 0);
		return points;
	}

	private float time = 1;
	private float timeCount = 0;

	@Override
	public final void simpleUpdate(final float tpf) {
		timeCount += tpf;

		if (timeCount > time) {
			makeBall();
			timeCount = 0;
		}

	}

	public void initWorld() {
		/* Ball */
		makeBall();
	}
	
	/**
	 * Creates a new ball object and instantiates a physics controller for it.
	 * Adds the physics controller to the physics space and the ball to the scene
	 */
	private void makeBall() {
		/* Ball physics control */
		ballPhy = new PhysicsController(new SphereCollisionShape(BALL_RADIUS), BALL_RADIUS * 2);

		/* Ball spatial */
		final Sphere ball = new Sphere(20, 20, BALL_RADIUS);
		final Geometry geom = new Geometry("Sphere", ball);
		final Material mat = new Material(assetManager,
				"Common/MatDefs/Misc/Unshaded.j3md");
		mat.setColor("Color", ColorRGBA.Blue);
		geom.setMaterial(mat);

		/* Ball node */
		final Node node = new Node("physics controlled ball");
		node.setLocalTranslation(BALL_SPAWN_LOCATION);  // Move the ball to its spawn location
		node.addControl(ballPhy);
		node.attachChild(geom);

		/* attach node and add the physics controller to the game */
		rootNode.attachChild(node);
		getPhysicsSpace().add(ballPhy);

		System.out.println("ball made");
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
