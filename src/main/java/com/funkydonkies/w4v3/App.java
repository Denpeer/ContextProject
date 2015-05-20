package com.funkydonkies.w4v3;

import com.funkydonkies.w4v3.obstacles.MovingBox;
import com.funkydonkies.w4v3.obstacles.ObstacleFactory;
import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.font.BitmapFont;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.system.AppSettings;
import com.mycompany.mavenproject1.GameInputState;
/**
 * Game is run through this class.
 *
 */
public class App extends SimpleApplication {
	private static final Vector3f GRAVITY = new Vector3f(0f, -15.81f, 0f);
	private static final String COLOR = "Color";
	private static final Vector3f CAM_LOCATION = new Vector3f(0, 150, 600);
	private static final String UNSHADED_MATERIAL_PATH = "Common/MatDefs/Misc/Unshaded.j3md";
	private static final String SHADED_MATERIAL_PATH = "Common/MatDefs/Light/Lighting.j3md";

	private BulletAppState bulletAppState;
	private GameInputState gameInputState;
	private WaveState waveState;
	
	private MovingBox clBox;
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

		bulletAppState = new BulletAppState();
		stateManager.attach(bulletAppState);
		
//		bulletAppState.setDebugEnabled(true);
		bulletAppState.getPhysicsSpace().setGravity(GRAVITY);
		bulletAppState.getPhysicsSpace().setAccuracy(1f/80f);
		//flyCam.setEnabled(false);

		gameInputState = new GameInputState();
		stateManager.attach(gameInputState);

		waveState = new WaveState();
		stateManager.attach(waveState);
		
		final ObstacleFactory facto = new ObstacleFactory();
		
		final int obstacleWidth = 20;
		final int obstacleHeight = 40;
		final int obstacleDepth = 10;
		
		clBox = (MovingBox) facto.makeObstacle("MOVINGBOX", obstacleWidth, obstacleHeight, 
				obstacleDepth);
		
		final Material mat2 = new Material(assetManager, SHADED_MATERIAL_PATH);
		mat2.setBoolean("UseMaterialColors", true);    
	    mat2.setColor("Diffuse", ColorRGBA.Green);  // minimum material color
	    mat2.setColor("Specular", ColorRGBA.White); // for shininess
	    mat2.setFloat("Shininess", 64f); // [1,128] for shininess
		
		clBox.draw(mat2, getPhysicsSpace(), rootNode);
		
		cam.setLocation(CAM_LOCATION);
		
	}

	@Override
	public void simpleUpdate(final float tpf) {
//		clBox.move(tpf);
		timeCount += tpf;

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
	
	public AppSettings getSettings() {
		return settings;
	}

	public BitmapFont getGuiFont() {
		return guiFont;
	}
	
}
