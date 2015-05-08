package com.mycompany.mavenproject1;

/**
 * Hello world!
 *
 */

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Spline;
import com.jme3.math.Spline.SplineType;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Curve;
import com.jme3.scene.shape.Line;

public class App extends SimpleApplication {

    public static void main(String[] args) {
        App app = new App();
        app.start();
    }

    @Override
    public void simpleInitApp() {
      //  Box b = new Box(1, 1, 1);

        Vector3f[] points = TestPoints();
        //Bezier bez = new Bezier(points);
        //bez.drawCompleteCurve(rootNode, assetManager);

        SplineCurve sp = new SplineCurve(SplineType.CatmullRom, points, (float)0.6, true);
        sp.drawCurve(rootNode, assetManager);
        flyCam.setEnabled(false);
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
    @Override
    public void simpleUpdate(float tpf) {
        //TODO: add update code
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
}
