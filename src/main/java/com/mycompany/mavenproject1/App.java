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
import com.jme3.math.Spline;
import com.jme3.math.Spline.SplineType;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Curve;
import com.jme3.scene.shape.Line;
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

    	
        Vector3f[] points = TestPoints();
       
        SplineCurve sp = new SplineCurve(SplineType.CatmullRom, points, (float)0.6, true);
        sp.drawCurve(rootNode, assetManager, getPhysicsSpace());

        cam.setLocation(new Vector3f(30, 4, 40));
        
    }
    public Vector3f[] TestPoints(){
    	Vector3f[] points = new Vector3f[11];
    	points[0] = new Vector3f(0,6,0);
    	points[1] = new Vector3f(10,6,0);
    	points[2] = new Vector3f(15,1,0);
    	points[3] = new Vector3f(20,3,0);
    	points[4] = new Vector3f(25,0,0);
    	points[5] = new Vector3f(30,6,0);
    	points[6] = new Vector3f(35,2,0);
    	points[7] = new Vector3f(40,2,0);
    	points[8] = new Vector3f(45,1,0);
    	points[9] = new Vector3f(50,5,0);
    	points[10] = new Vector3f(70,3,0);
    	return points;
    }
    
    float time = 1;
    float timeCount =0;
    @Override
    public void simpleUpdate(float tpf) {
    	timeCount += tpf;
        
    	if(timeCount > time){
    		makeBall();
    		timeCount = 0;
    	}

    }
    
    public void initWorld(){
    	/* physics control */
    	
        /* Ball */
    	makeBall();
        
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
        node.setLocalTranslation(new Vector3f(30f, 30.0f, 0f));	//Move the ball up
        node.addControl(ball_phy);
        node.attachChild(geom);

        /* attach node and add the physics controller to the game */
		rootNode.attachChild(node);
        getPhysicsSpace().add(ball_phy);
        
        System.out.println("ball made");
    }
    

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
    
    private PhysicsSpace getPhysicsSpace(){
    	return bulletAppState.getPhysicsSpace();
    }
}
