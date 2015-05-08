package com.mycompany.mavenproject1;

/**
 * Wave
 *
 */

import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.collision.shapes.SphereCollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.MouseAxisTrigger;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Sphere;

public class App extends SimpleApplication {
	
	private BulletAppState bulletAppState;
	
	private Material floor_mat;
	private Geometry floor_geom;
	
	private Box floor;
	private RigidBodyControl floor_phy;
	private PhysicsController ball_phy;
	
	private Node floornode;
	
	
    public AnalogListener analogListener = new AnalogListener() {
        public void onAnalog(String name, float value, float tpf) {

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
        bulletAppState.getPhysicsSpace().setGravity(new Vector3f(0f,-9.81f,0f));
//    	flyCam.setEnabled(false);
        
        initWorld();
        
        inputManager.addMapping("MouseMovement",
                new MouseAxisTrigger(MouseInput.AXIS_X, false), 
                new MouseAxisTrigger(MouseInput.AXIS_X, true));
        
        inputManager.addListener(analogListener, "MouseMovement");

    }
    
    public void initWorld(){
    	/* physics control */
//    	SimplexCollisionShape lineshape = new SimplexCollisionShape(new Vector3f(-50.0f, 5f, 0f), new Vector3f(50.0f, 5f, 0f));
//    	RigidBodyControl line_phy = new RigidBodyControl(lineshape, 0f);
    	floor_phy = new RigidBodyControl(/*lineshape,*/ 0.0f);
    	
    	/* Line */    	
//    	Line line = new Line(new Vector3f(-50.0f, 5f, 0f), new Vector3f(50.0f, 5f, 0f));
//        Geometry linegeom = new Geometry("Line", line);
//        line.setLineWidth(50);
//        Material linemat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
//        linemat.setColor("Color", ColorRGBA.Red);
//        linegeom.setMaterial(linemat);
        
        /* Ball */
    	makeBall();
        
        /* Floor */
        floor = new Box(20f, 0.1f,1f);
        floor_geom = new Geometry("Floor", floor);
        floor_mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        floor_geom.setMaterial(floor_mat);
        floornode = new Node("floor");
        floor_geom.addControl(floor_phy);
        floornode.attachChild(floor_geom);
        
        getPhysicsSpace().add(floor_phy);
//      getPhysicsSpace().add(line_phy);
        rootNode.attachChild(floornode);
        
//      linegeom.addControl(line_phy);
//		rootNode.attachChild(linegeom);
		
		floornode.rotate(0, 0, -FastMath.DEG_TO_RAD * 15);
//		floor_geom.removeControl(floor_phy);
		floornode.addControl(floor_phy); //IMPORTANT TO DO THE ROTATION
		
		cam.setLocation( new Vector3f(0, 0, 50) );
    }
    
    float c = 0;
    @Override
    public void simpleUpdate(float tpf) {
    	c = (c + 1/tpf);
    	if(c % 50 < 1){
    		makeBall();
    		
    	}
    	if(c < 0)
    		c = 0;
    }
    
    private void makeBall(){
    	/* Ball physics control */
    	ball_phy = new PhysicsController(new SphereCollisionShape(0.5f), 1f);
    	
    	/*  Ball spatial */ 
    	Sphere ball = new Sphere(20, 20, 0.5f);
        Geometry geom = new Geometry("Sphere", ball);
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Blue);
        geom.setMaterial(mat);
        
        /* Ball node */
        Node node = new Node("physics controlled ball");
        node.setLocalTranslation(new Vector3f(0f, 30.0f, 0f));	//Move the ball up
        node.addControl(ball_phy);
        node.attachChild(geom);
        
        /* attach node and add the physics controller to the game */
		rootNode.attachChild(node);
        getPhysicsSpace().add(ball_phy);

    }
    

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
    
    private PhysicsSpace getPhysicsSpace(){
    	return bulletAppState.getPhysicsSpace();
    }
}
