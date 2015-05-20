package com.funkydonkies.w4v3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.funkydonkies.camdetect.Mat2Image;
import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Ray;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.terrain.geomipmap.TerrainQuad;
import com.jme3.terrain.heightmap.HeightMap;
import com.mycompany.mavenproject1.BadDynamicTypeException;

/**
 * Interfaces with openCV to interpret dataset and change curve.
 */
public class WaveState extends AbstractAppState {
	private Bridge bridge;

	private static final String UNSHADED_MATERIAL_PATH = "Common/MatDefs/Misc/Unshaded.j3md";
	private static final String SHADED_MATERIAL_PATH = "Common/MatDefs/Light/Lighting.j3md";
	private static final String COLOR = "Color";

	private App app;
	private AssetManager assetManager;
	private PhysicsSpace physicsSpace;

	private Vector3f intersection;

	private Node curve;

	private HeightMap heightmap;
	private Spatial terrain;

	private boolean raiseTerrain = false;
	private boolean lowerTerrain = false;
	private boolean controlsEnabled = false;

	private Node guiNode;
	private BitmapFont guiFont;
	private BitmapText hintText;

	private float time = 0;
	private boolean normalized = false;

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
		this.physicsSpace = this.app.getPhysicsSpace();
		this.bridge = this.app.getBridge();

		final Material mat = new Material(assetManager, SHADED_MATERIAL_PATH);
		// mat.getAdditionalRenderState().setWireframe(true);
		mat.setBoolean("UseMaterialColors", true);
		mat.setColor("Diffuse", ColorRGBA.Orange); // minimum material color
		mat.setColor("Specular", ColorRGBA.White); // for shininess
		mat.setFloat("Shininess", 64f); // [1,128] for shininess
		// mat.setColor(COLOR, ColorRGBA.Green);

		final Material matBlue = new Material(assetManager,
				UNSHADED_MATERIAL_PATH);
		matBlue.setColor(COLOR, ColorRGBA.Blue);

		this.heightmap = new WaveHeightMap();
		this.heightmap.load();

		this.guiNode = this.app.getGuiNode();

		this.guiFont = this.app.getGuiFont();

		loadHintText();

		final int tileSize = 65;
		final int blockSize = 513;

		terrain = new TerrainQuad("myWave", tileSize, blockSize,
				this.heightmap.getHeightMap());
		// final TerrainLodControl lODcontrol =
		// new TerrainLodControl((Terrain) terrain, this.app.getCamera());
		// lODcontrol.setLodCalculator(new DistanceLodCalculator(65, 1.2f)); //
		// patch size, multiplier
		// terrain.addControl(lODcontrol);
		terrain.setMaterial(mat);
		// terrain.move(30, -150, -200);
		// terrain.scale(.4f, .3f, .1f);

		curve = new Node("curve");
		curve.attachChild(terrain);
		// curve.setUserData("terrain", terrain);

		// add terrain to physicsSpace
		final RigidBodyControl RigidBControl = new RigidBodyControl(0);
		terrain.addControl(RigidBControl);
		this.physicsSpace.add(RigidBControl);
		this.physicsSpace.addAll(terrain);

		// add Listener to physicsSpace that updates collisionShape of terrain
		new UpdateTerrain(this.physicsSpace, (TerrainQuad) terrain);

		this.app.getRootNode().attachChild(curve);

		terrain.addControl(new DetectionInterpreterControl());

		// this.stateManager.getState(BulletAppState.class);

		DirectionalLight sun = new DirectionalLight();
		sun.setDirection(new Vector3f(1, -10, -2).normalizeLocal());
		sun.setColor(ColorRGBA.White);
		app.getRootNode().addLight(sun);

		// adjustHeight(-50);
	}

	@Override
	public final void cleanup() {
		super.cleanup();

	}

	@Override
	public final void setEnabled(final boolean enabled) {
		// Pause and unpause
		super.setEnabled(enabled);

		// if (enabled) {
		// // init stuff that is in use while this state is RUNNING
		// // this.app.doSomethingElse(); // call custom methods...
		// }
		// else {
		// // take away everything not needed while this state is PAUSED
		// // ...
		// }
	}

	// Note that update is only called while the state is both attached and
	// enabled
	@Override
	public final void update(final float tpf) {
		// this.app.getRootNode().getChild("blah").scale(tpf); // modify scene
		time += tpf * 100;
		intersection = getWorldIntersection();
		
//		System.out.println(((TerrainQuad) terrain).getHeight(new Vector2f(0, 0)));
		float[] points = new float[32];
		Arrays.fill(points, 480);
		if (controlsEnabled /*&& time > 25*/) {
			time = 0;
			// ((Mat2Image) bridge).refreshInterestPoints();
			points = bridge.getControlPoints();
			
			// if (!normalized) {
			normalize(points, 480);
//			System.out.println(Arrays.toString(points));
			// normalized = true;
			// }
			adjustHeight(points, tpf);
		}

		updateHintText(intersection);

		final int changeSpeed = 20;
		if (raiseTerrain) {
			Arrays.toString(points);
			if (intersection != null) {
				// adjustHeight(intersection, tpf * 100, tpf); // non-radial
				adjustHeight(intersection, 64, tpf * changeSpeed);
			}
		} else if (lowerTerrain) {
			if (intersection != null) {
				// adjustHeight(intersection, -tpf * 100, tpf); // non-radial
				adjustHeight(intersection, 64, -tpf * changeSpeed);
			}
		}
	}

	private void normalize(final float[] points, final int screenHeight) {
		for (int i = 0; i < points.length; i++) {
			float point = points[i];
//			point = screenHeight - point;
			point = point / screenHeight;
			point = point * 128;
			points[i] = point;
		}
	}

	private void adjustHeight(final Vector3f loc, final float radius,
			final float height) {

		// offset it by radius because in the loop we iterate through 2 radii
		final int radiusStepsX = (int) (radius / terrain.getLocalScale().x);
//		System.out.println(radiusStepsX);

		final float xStepAmount = terrain.getLocalScale().x;

		final List<Vector2f> locs = new ArrayList<Vector2f>();
		final List<Float> heights = new ArrayList<Float>();

		for (int i = -256; i < 256; i++) {
			for (int x = -radiusStepsX; x < radiusStepsX; x++) {

				final float locX = loc.x + (x * xStepAmount);

				// see if it is in the radius of the tool
				if (isInRadius(locX - loc.x, loc.z - loc.z, radius)) {
					final float h = calculateHeight(radius, height, locX
							- loc.x, loc.z - loc.z);
					locs.add(new Vector2f(locX, i));
					heights.add(h);
				}
			}
		}
		((TerrainQuad) terrain).adjustHeight(locs, heights);
		 terrain.updateModelBound();
	}

	/**
	 * @param points
	 *            point values are expected to be in the range (0, 255)
	 * @param tpf
	 */
	private void adjustHeight(final float[] points, final float tpf) {
		final int radius = 10;

		final int radiusStepsX = (int) (radius / terrain.getLocalScale().x);

		final float xStepAmount = terrain.getLocalScale().x;

		final List<Vector2f> locs = new ArrayList<Vector2f>();
		final List<Float> heights = new ArrayList<Float>();

		// 32 iterations
		int elem = 0;
		for (int z = 230; z < 256; z++) {
			elem = 0;
			for (int x = -255; x < 255; x += 16) {

				for (int circleX = -radius; circleX < radius; circleX++) {

					final float locX = x + circleX;

					if (isInRadius(locX - x, 0, radius)) {
						// get current height and increment so range (0, 256)
						float current = ((TerrainQuad) terrain)
								.getHeight(new Vector2f(locX, 0));
//						current = current + 128;
						
						float desired = points[elem];
						float delta = Math.abs(current - desired);
						
//						System.out.println("current: " + current + " desired: " + desired + " delta: " + delta);
						float height = 0;

						if (current <= desired) {
							height = Math.min(delta, 20);
						} else {
							height = Math.max(-delta, -20);
						}
						
						final int offset = 10;
//						System.out.println((offset - (Math.abs(x - locX))) / offset);
						
						height = height * (offset - (Math.abs(x - locX))) / offset;
						
//						height = calculateHeight(radius, tpf, locX - x, 1);
//						System.out.println(height);

						locs.add(new Vector2f(locX, z));
						heights.add(height * tpf);
					}
				}

				elem++;
			}
		}

		// for (int i = -256; i < 256; i++) {
		// for (int x = -radiusStepsX; x < radiusStepsX; x++) {
		//
		// final float locX = x + (x * xStepAmount);
		//
		// if (isInRadius(locX - x, 0, radius)) {
		// final float h = calculateHeight(radius, tpf * 20, locX - x, 0);
		// locs.add(new Vector2f(locX, i));
		// heights.add(h);
		// }
		// }
		// }
		((TerrainQuad) terrain).adjustHeight(locs, heights);
		 terrain.updateModelBound();
	}

	/**
	 * Returns true if the distance is less than or equal to the radius.
	 * 
	 * @param x
	 *            x-coordinate
	 * @param y
	 *            y-coordinate
	 * @param radius
	 *            desired radius to test the points on
	 * @return true if the distance is less than or equal to the radius
	 */
	private boolean isInRadius(final float x, final float y, final float radius) {
		final Vector2f point = new Vector2f(x, y);
		return point.length() <= radius;
	}

	/**
	 * 
	 * @param radius
	 * @param heightFactor
	 * @param x
	 * @param z
	 * @return
	 */
	private float calculateHeight(final float radius, final float heightFactor,
			final float x, final float z) {
		// find percentage for each 'unit' in radius
		Vector2f point = new Vector2f(x, z);
		float val = point.length() / radius;
		val = 1 - val;
		if (val <= 0) {
			val = 0;
		}
		return heightFactor * val;
	}

	private Vector3f getWorldIntersection() {
		Vector3f origin = this.app.getCamera().getWorldCoordinates(
				new Vector2f(this.app.getSettings().getWidth() / 2, this.app
						.getSettings().getHeight() / 2), 0.0f);
		Vector3f direction = this.app.getCamera().getWorldCoordinates(
				new Vector2f(this.app.getSettings().getWidth() / 2, this.app
						.getSettings().getHeight() / 2), 0.3f);
		direction.subtractLocal(origin).normalizeLocal();

		Ray ray = new Ray(origin, direction);
		CollisionResults results = new CollisionResults();
		int numCollisions = terrain.collideWith(ray, results);
		if (numCollisions > 0) {
			CollisionResult hit = results.getClosestCollision();
			return hit.getContactPoint();
		}
		return null;
	}

	public void loadHintText() {
		hintText = new BitmapText(guiFont, false);
		hintText.setLocalTranslation(0, app.getCamera().getHeight(), 0);
		hintText.setText("Hit 1 to raise terrain, hit 2 to lower terrain");
		guiNode.attachChild(hintText);
	}

	public void updateHintText(Vector3f target) {
		int x = (int) app.getCamera().getLocation().x;
		int y = (int) app.getCamera().getLocation().y;
		int z = (int) app.getCamera().getLocation().z;
		String targetText = "";
		if (target != null)
			targetText = "  intersect: " + target.toString();
		hintText.setText("Press left mouse button to raise terrain, press right mouse button to lower terrain.  "
				+ x + "," + y + "," + z + targetText);
	}

	public boolean getRaiseTerrain() {
		return raiseTerrain;
	}

	public void setRaiseTerrain(final boolean rt) {
		raiseTerrain = rt;
	}

	public boolean getLowerTerrain() {
		return lowerTerrain;
	}

	public void setLowerTerrain(final boolean lt) {
		lowerTerrain = lt;
	}

	public void setControlsEnabled(final boolean e) {
		controlsEnabled = e;
	}

	public boolean getControlsEnabled() {
		return controlsEnabled;
	}
}
