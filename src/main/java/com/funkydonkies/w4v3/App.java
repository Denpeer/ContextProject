package com.funkydonkies.w4v3;

import com.funkydonkies.w4v3.obstacles.MovingBox;
import com.funkydonkies.w4v3.obstacles.ObstacleFactory;
import com.funkydonkies.w4v3.obstacles.Target;
import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Spline.SplineType;
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
	private ObstacleFactory factory;
	private MovingBox movBox;
	private Target target;
	private Combo combo;
	
	private float ballSpawntimer = 0;
	private float spawnPerSec = 1;
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
		factory = new ObstacleFactory();
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
		
		final BitmapText comboText = new BitmapText(assetManager.loadFont("Interface/Fonts/Default.fnt"),
				false);
		combo = new Combo(guiNode, comboText);
		movBox = factory.makeMovingBox(rootNode, assetManager);
		target = factory.makeTarget(rootNode, assetManager, combo);
		
		
		
		final Material mat2 = new Material(assetManager, SHADED_MATERIAL_PATH);
		mat2.setBoolean("UseMaterialColors", true);    
		 mat2.setColor("Diffuse", ColorRGBA.Green);  // minimum material color
	    mat2.setColor("Specular", ColorRGBA.White); // for shininess
	    mat2.setFloat("Shininess", 64f); // [1,128] for shininess
	    
		movBox.draw(mat2, getPhysicsSpace());
		target.draw(null, getPhysicsSpace());
	    mat2.setFloat("Shininess", 64f); // [1,128] for shininess
		
		cam.setLocation(CAM_LOCATION);
		combo.display();
	}

	@Override
	public void simpleUpdate(final float tpf) {
//		
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
	
	public AppSettings getSettings() {
		return settings;
	}

	public BitmapFont getGuiFont() {
		return guiFont;
	}
	
}
