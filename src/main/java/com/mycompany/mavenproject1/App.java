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
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Line;
import com.jme3.scene.shape.Sphere;

public class App extends SimpleApplication {
	
	private BulletAppState bulletAppState;
	
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
    	flyCam.setEnabled(false);
    	
        Sphere sphere = new Sphere(3, 3, 3);
        Geometry geom = new Geometry("Sphere", sphere);

        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Blue);
        geom.setMaterial(mat);
        geom.setLocalTranslation(new Vector3f(1.0f, 1.0f, 1.0f));
        
        // Add a physics sphere to the world
//        Node physicsSphere = PhysicsTestHelper.createPhysicsTestNode(assetManager, new SphereCollisionShape(1), 1);
//        physicsSphere.getControl(RigidBodyControl.class).setPhysicsLocation(new Vector3f(3, 6, 0));
//        rootNode.attachChild(physicsSphere);
//        getPhysicsSpace().add(physicsSphere);
//        
        Line line = new Line(new Vector3f(5.0f, 0f, 0f), new Vector3f(-5.0f, 0f, 0f));
        Geometry linegeom = new Geometry("Line", line);
        
        Material linemat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        linemat.setColor("Color", ColorRGBA.Red);
        linegeom.setMaterial(linemat);
        
        rootNode.attachChild(geom);
        rootNode.attachChild(linegeom);
        
        inputManager.addMapping("MouseMovement",
                new MouseAxisTrigger(MouseInput.AXIS_X, false), 
                new MouseAxisTrigger(MouseInput.AXIS_X, true));
        
        inputManager.addListener(analogListener, "MouseMovement");

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
