package com.mycompany.mavenproject1;

/**
 * Hello world!
 *
 */

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
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
        Bezier bez = new Bezier(points);

  

        double t = 0;
        while(t + 0.01 < 1){
            Vector3f vec = bez.getPointOnFrame(t);
            Vector3f vec1 = bez.getPointOnFrame(t + 0.01);
        	Line lc = new Line(vec, vec1);
        	Geometry geomLine = new Geometry("curve", lc);
            Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
            mat.setColor("Color", ColorRGBA.Orange);
            geomLine.setMaterial(mat);
            rootNode.attachChild(geomLine);
            t = t + 0.01;
        }
       
    }
    public Vector3f[] TestPoints(){
    	Vector3f[] points = new Vector3f[4];
    	points[0] = new Vector3f(0,0,0);
    	points[1] = new Vector3f(2,2,0);
    	points[2] = new Vector3f(4,0,0);
    	points[3] = new Vector3f(6,0,0);	
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
