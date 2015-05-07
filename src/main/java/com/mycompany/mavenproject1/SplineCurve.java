package com.mycompany.mavenproject1;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Spline;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Line;

public class SplineCurve extends Spline {
	
	
	public SplineCurve(SplineType splineType, Vector3f[] controlPoints, float curveTension, boolean cycle){
		super(splineType, controlPoints, curveTension, cycle);
	}
	
	public void drawCurve(Node rootNode, AssetManager assetManager){
		Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
		mat.setColor("Color", ColorRGBA.Blue);
		for(int i = 0; i < getControlPoints().size() - 1; i++){
			double t = 0;
			while(t + 0.1 <= 1){
	            Vector3f vec = interpolate((float)t, i, null);
	            Vector3f vec1 = interpolate((float)(t + 0.1), i, null);
	            System.out.println(vec.toString());
	            System.out.println(vec1.toString());
	        	Line lc = new Line(vec, vec1);
	        	Geometry geomLine = new Geometry("curve", lc);
	            
	            
	            geomLine.setMaterial(mat);
	            rootNode.attachChild(geomLine);
				t = t + 0.1;
			}
			
		}
	}
}
