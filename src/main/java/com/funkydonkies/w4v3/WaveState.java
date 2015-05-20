package com.funkydonkies.w4v3;

import java.util.ArrayList;
import java.util.List;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
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
import com.jme3.terrain.Terrain;
import com.jme3.terrain.geomipmap.TerrainLodControl;
import com.jme3.terrain.geomipmap.TerrainQuad;
import com.jme3.terrain.geomipmap.lodcalc.DistanceLodCalculator;
import com.jme3.terrain.heightmap.HeightMap;
import com.mycompany.mavenproject1.BadDynamicTypeException;

/**
 * Interfaces with openCV to interpret dataset and change curve.
 */
public class WaveState extends AbstractAppState {
//	private Bridge bridge;
	
	private static final String UNSHADED_MATERIAL_PATH = "Common/MatDefs/Misc/Unshaded.j3md";
	private static final String SHADED_MATERIAL_PATH = "Common/MatDefs/Light/Lighting.j3md";
	private static final String COLOR = "Color";
	
	private App app;
	private AssetManager assetManager;
	private Vector3f intersection;
	
	private Node curve;
	
	private HeightMap heightmap;
	private Spatial terrain;
	
	private boolean raiseTerrain;
	private boolean lowerTerrain;
	private Node guiNode;
	private BitmapFont guiFont;
	private BitmapText hintText;
	
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
		
		final Material mat = new Material(assetManager, SHADED_MATERIAL_PATH);
//		mat.getAdditionalRenderState().setWireframe(true);
		mat.setBoolean("UseMaterialColors", true);    
	    mat.setColor("Diffuse", ColorRGBA.Orange);  // minimum material color
	    mat.setColor("Specular", ColorRGBA.White); // for shininess
	    mat.setFloat("Shininess", 64f); // [1,128] for shininess
//		mat.setColor(COLOR, ColorRGBA.Green);
		
		final Material matBlue = new Material(assetManager, UNSHADED_MATERIAL_PATH);
		matBlue.setColor(COLOR, ColorRGBA.Blue);
		
		this.heightmap = new WaveHeightMap();
		this.heightmap.load();
		
		this.guiNode = this.app.getGuiNode();
		this.guiFont = this.app.getGuiFont();
		
		loadHintText();
		
		final int tileSize = 65;
		final int blockSize = 513;
		
		terrain = new TerrainQuad("myWave", tileSize, blockSize, this.heightmap.getHeightMap());
        final TerrainLodControl lODcontrol = 
        		new TerrainLodControl((Terrain) terrain, this.app.getCamera());
        lODcontrol.setLodCalculator(new DistanceLodCalculator(65, 1.2f)); // patch size, multiplier
//        terrain.addControl(lODcontrol);
        terrain.setMaterial(mat);
//        terrain.move(30, -150, -200);
//        terrain.scale(.4f, .3f, .1f);
        
        curve = new Node("curve");
        curve.attachChild(terrain);
//        curve.setUserData("terrain", terrain);

        // add terrain to physicsSpace
        final RigidBodyControl RigidBControl = new RigidBodyControl(0);
        terrain.addControl(RigidBControl);
        this.app.getPhysicsSpace().add(RigidBControl);
        this.app.getPhysicsSpace().addAll(terrain);
        
        this.app.getRootNode().attachChild(curve);
        
//        terrain.addControl(new DetectionInterpreterControl());
        
		// this.app.getRootNode();
		// this.app.getStateManager();
		// this.app.getViewPort();
		// this.stateManager.getState(BulletAppState.class);

		// init stuff that is independent of whether state is PAUSED or RUNNING
		// this.app.doSomething(); // call custom methods...
        
        DirectionalLight sun = new DirectionalLight();
        sun.setDirection(new Vector3f(1, -10, -2).normalizeLocal());
        sun.setColor(ColorRGBA.White);
        app.getRootNode().addLight(sun);
	}

	@Override
	public final void cleanup() {
		 super.cleanup();
		
	}

	@Override
	public final void setEnabled(final boolean enabled) {
		// Pause and unpause
		super.setEnabled(enabled);
		
//		if (enabled) {
//			// init stuff that is in use while this state is RUNNING
//			// this.app.doSomethingElse(); // call custom methods...
//		} 
//			else {
//			// take away everything not needed while this state is PAUSED
//			// ...
//		}
	}

	// Note that update is only called while the state is both attached and enabled
	@Override
	public final void update(final float tpf) {
		// do the following while game is RUNNING
		// this.app.getRootNode().getChild("blah").scale(tpf); // modify scene
		// graph...
		// x.setUserData(...); // call some methods...
		
		intersection = getWorldIntersection();
		
		updateHintText(intersection);
		
		if (raiseTerrain) {
            
            if (intersection != null) {
//                adjustHeight(intersection, tpf * 100, tpf); // non-radial
            	adjustHeight(intersection, 64, tpf * 100);
            }
        } else if (lowerTerrain) {
            if (intersection != null) {
//                adjustHeight(intersection, -tpf * 100, tpf); // non-radial
            	adjustHeight(intersection, 64, -tpf * 100);
            }
        }
		
	}

	private void adjustHeight(final Vector3f loc, final float radius, final float height) {

        // offset it by radius because in the loop we iterate through 2 radii
        final int radiusStepsX = (int) (radius / terrain.getLocalScale().x);

        final float xStepAmount = terrain.getLocalScale().x;
        
        final List<Vector2f> locs = new ArrayList<Vector2f>();
        final List<Float> heights = new ArrayList<Float>();
        
        for (int i = -512; i < 512; i++) {
            for (int x = -radiusStepsX; x < radiusStepsX; x++) {

                final float locX = loc.x + (x * xStepAmount);

                // see if it is in the radius of the tool
                if (isInRadius(locX - loc.x, loc.z - loc.z, radius)) {
                    final float h = calculateHeight(radius, height, locX - loc.x, loc.z - loc.z);
                    locs.add(new Vector2f(locX, i));
                    heights.add(h);
                }
            }
        }

        ((TerrainQuad) terrain).adjustHeight(locs, heights);
        terrain.updateModelBound();
        CollisionShape colShape = CollisionShapeFactory.createDynamicMeshShape(terrain); 
//        CollisionShape colShape = new HeightfieldCollisionShape(new float[1]);
//        HullCollisionShape colShape = new HullCollisionShape(terrain);
        terrain.getControl(RigidBodyControl.class).setCollisionShape(colShape);
    }
/*
	private void adjustHeight(final Vector3f loc, final float height, final float tpf) {

        final float xStepAmount = terrain.getLocalScale().x;
        final float zStepAmount = terrain.getLocalScale().z;
        
        final List<Vector2f> locs = new ArrayList<Vector2f>();
        final List<Float> heights = new ArrayList<Float>();
        
        final float locX = loc.x + (0 * xStepAmount);
        
//        final float locZ = loc.z + (0 * zStepAmount);

        for (int i = -512; i < 512; i++) {
        	final float h = height;
	        locs.add(new Vector2f(locX, i));
	        heights.add(h);
        }
        
        // see if it is in the radius of the tool
//        if (isInRadius(locX - loc.x, locZ - loc.z, radius)) {
//        final float h = calculateHeight(radius, height, locX - loc.x, locZ - loc.z);
//	        final float h = height;
//	        locs.add(new Vector2f(locX, locZ));
//	        heights.add(h);
//        }

        ((TerrainQuad) terrain).adjustHeight(locs, heights);
        terrain.updateModelBound();
        CollisionShape colShape = CollisionShapeFactory.createDynamicMeshShape(terrain); 
        terrain.getControl(RigidBodyControl.class).setCollisionShape(colShape);
    }
	*/
	
	/** Returns true if the distance is less than or equal to the radius.
	 * @param x x-coordinate
	 * @param y y-coordinate
	 * @param radius desired radius to test the points on
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
        Vector3f origin = this.app.getCamera().getWorldCoordinates(new Vector2f(
        		this.app.getSettings().getWidth() / 2, 
        		this.app.getSettings().getHeight() / 2), 0.0f);
        Vector3f direction = this.app.getCamera().getWorldCoordinates(new Vector2f(
        		this.app.getSettings().getWidth() / 2, 
        		this.app.getSettings().getHeight() / 2), 0.3f);
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
        hintText.setText("Hit left mouse to raise terrain, hit right mouse to lower terrain \n "
        		+ "please lower the terrain first as it starts at its maximum");
        guiNode.attachChild(hintText);
    }

    public void updateHintText(Vector3f target) {
        int x = (int) app.getCamera().getLocation().x;
        int y = (int) app.getCamera().getLocation().y;
        int z = (int) app.getCamera().getLocation().z;
        String targetText = "";
        if (target!= null)
            targetText = "  intersect: "+target.toString();
        hintText.setText("Press left mouse button to raise terrain, press right mouse button to lower terrain.  " + x + "," + y + "," + z+targetText + "\n please lower the terrain first as it starts at its maximum");
    }
	
	public boolean getRaiseTerrain() {
		return raiseTerrain;
	}
	
	public void setRaiseTerrain(final boolean rt) {
		Vector3f inetersection = getWorldIntersection();
		float current = 0;
		if(intersection != null) {
			current = ((TerrainQuad) terrain).getHeight(new Vector2f(intersection.x, 0));
			if(current < 70) {
				raiseTerrain = rt;
				return;
			}
		}
		raiseTerrain = false;
	}
	
	public boolean getLowerTerrain() {
		return lowerTerrain;
	}
	
	public void setLowerTerrain(final boolean lt) {
		Vector3f inetersection = getWorldIntersection();
		float current = 0;
		if(intersection != null) {
			current = ((TerrainQuad) terrain).getHeight(new Vector2f(intersection.x, 0));
			if(current > 0) {
				lowerTerrain = lt;
				return;
			}
		}
		raiseTerrain = false;
	}
	
}
