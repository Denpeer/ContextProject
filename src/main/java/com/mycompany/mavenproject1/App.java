package com.mycompany.mavenproject1;

/**
 * Wave
 *
 */

import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.collision.shapes.SphereCollisionShape;
import com.jme3.bullet.control.GhostControl;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.MouseAxisTrigger;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Line;
import com.jme3.scene.shape.Quad;
import com.jme3.scene.shape.Sphere;

public class App extends SimpleApplication {
	
	private BulletAppState bulletAppState;
	
	Material line_mat;
	Material ball_mat;
	Material floor_mat;
	
	private RigidBodyControl ball_phy;
	private Sphere sphere;
	private Box floor;
	private RigidBodyControl floor_phy;
	
	
    public AnalogListener analogListener = new AnalogListener() {
        public void onAnalog(String name, float value, float tpf) {
            System.out.println(name + " = " + value);
        }
    };

    public static void main(String[] args) {
        App app = new App();
        app.start();
    }

    @Override
    public void simpleInitApp() {
   // 	inputManager.setCursorVisible( true );
    	/* Set up physics */
    	bulletAppState = new BulletAppState();
        stateManager.attach(bulletAppState);
        bulletAppState.setDebugEnabled(true);

//    	flyCam.setEnabled(false);
        
        // Add a physics sphere to the world
//        Node physicsSphere = PhysicsTestHelper.createPhysicsTestNode(assetManager, new SphereCollisionShape(1), 1);
//        physicsSphere.getControl(RigidBodyControl.class).setPhysicsLocation(new Vector3f(3, 6, 0));
//        rootNode.attachChild(physicsSphere);
//        getPhysicsSpace().add(physicsSphere);
        
        
//        Quad ground = new Quad(4f, 4f);
//        Geometry groundgeom = new Geometry("Ground", ground);
//        Material groundmat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
//        groundmat.setColor("Color", ColorRGBA.DarkGray);
//        groundgeom.setMaterial(groundmat);
        
//        rootNode.attachChild(groundgeom);
       
//        rootNode.attachChild(linegeom);
        initWorld();
        getPhysicsSpace().add(ball_phy);
        inputManager.addMapping("MouseMovement",
                new MouseAxisTrigger(MouseInput.AXIS_X, false), 
                new MouseAxisTrigger(MouseInput.AXIS_X, true));
        
        inputManager.addListener(analogListener, "MouseMovement");

    }
    
    public void initWorld(){
    	/* physics control */
    	SphereCollisionShape shape = new SphereCollisionShape(2f);
    	ball_phy = new RigidBodyControl(shape, 1f);
    	ball_phy.setLinearVelocity(new Vector3f(0.0f, 10.0f, 0.0f));
    	Node node = new Node("physics controlled ball");
    	node.addControl(ball_phy);
    	
    	/* Line */    	
    	Line line = new Line(new Vector3f(15.0f, -1f, 0f), new Vector3f(-15.0f, -1f, 0f));
        Geometry linegeom = new Geometry("Line", line);
        Material linemat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        linemat.setColor("Color", ColorRGBA.Red);
        linegeom.setMaterial(linemat);
        
        /* Ball */
        sphere = new Sphere(20, 20, 2);
        Geometry geom = new Geometry("Sphere", sphere);
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Blue);
        geom.setMaterial(mat);
        geom.setLocalTranslation(new Vector3f(1.0f, 5.0f, 1.0f));
        
        geom.addControl(ball_phy);
        
        /* Floor */
        floor = new Box(10f, 0.1f, 5f);
        Geometry floor_geom = new Geometry("Floor", floor);
        floor_mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        floor_geom.setMaterial(floor_mat);
        floor_phy = new RigidBodyControl(0.0f);
        floor_geom.addControl(floor_phy);
        getPhysicsSpace().add(floor_phy);
        
        rootNode.attachChild(floor_geom);

		node.attachChild(geom);
		node.attachChild(linegeom);

		rootNode.attachChild(node);
    }

    @Override
    public void simpleUpdate(float tpf) {
        //TODO: add update code
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
    
    private PhysicsSpace getPhysicsSpace(){
    	return bulletAppState.getPhysicsSpace();
    }
    
    private void initKeys() {



    	inputManager.clearMappings();



    	inputManager.addMapping("RotateX", new MouseAxisTrigger(MouseInput.AXIS_X, false));

    	inputManager.addMapping("RotateY", new MouseAxisTrigger(MouseInput.AXIS_Y, false));



    //	inputManager.addListener(analogListener, new String[]{"RotateX", "RotateY"});





    	}
}
