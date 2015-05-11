package com.mycompany.mavenproject1;

import static org.junit.Assert.*;

import org.junit.Test;

import com.jme3.asset.AssetManager;
import com.jme3.asset.DesktopAssetManager;
import com.jme3.math.Spline.SplineType;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;

public class SplineCurveTest {

	
	/*@Test
	public void testDrawCurve() {
		App app = new App();
		Vector3f[] vecs = new Vector3f[3];
    	vecs[0] = new Vector3f(0,0,0);
    	vecs[1] = new Vector3f(2,4,0);
    	vecs[2] = new Vector3f(4,1,0);
    	Node rootNode = app.getRootNode();
    	AssetManager assetManager = app.getAssetManager();
    	
		SplineCurve sp = new SplineCurve(SplineType.CatmullRom, vecs, (float)0.5, true);
		//sp.drawCurve(rootNode, assetManager);
		assertEquals(rootNode.getChildren().size(), 100);
	}*/
	
	@Test
	public void loopThroughSplineTest(){
		
	}

	@Test
	public void getTwoVectorsTest(){
		Vector3f[] vecs = new Vector3f[3];
    	vecs[0] = new Vector3f(0,0,0);
    	vecs[1] = new Vector3f(2,4,0);
    	vecs[2] = new Vector3f(4,1,0);
		SplineCurve sp = new SplineCurve(SplineType.CatmullRom, vecs, (float)0.5, true);
		Vector3f[] veccies = sp.getTwoVectors(0, 0);
		assertEquals(vecs[0], veccies[0]);
		assertEquals(vecs[1], veccies[1]);
	}
}
