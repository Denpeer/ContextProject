package com.mycompany.mavenproject1;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import org.junit.Test;
import org.mockito.Mockito;

import com.funkydonkies.w4v3.SplineCurve;
import com.jme3.asset.AssetManager;
import com.jme3.asset.DesktopAssetManager;
import com.jme3.material.Material;
import com.jme3.math.Spline.SplineType;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;

public class SplineCurveTest {

	
	/*@Test
	 public void testDoSmth() {
		Material mat = new Material(assetManager,
				"Common/MatDefs/Misc/Unshaded.j3md");
		mat.setColor("Color", ColorRGBA.Cyan);
		Node node = new Node("curve")
		SplineCurve testMock = mock(SplineCurve.class);
		Vector3f[] vecs = new Vector3f[3];
    	vecs[0] = new Vector3f(0,0,0);
    	vecs[1] = new Vector3f(2,4,0);
    	vecs[2] = new Vector3f(4,1,0);
		SplineCurve sp = new SplineCurve(SplineType.CatmullRom, vecs, 0.5f, false);
		testMock.loopThroughSpline();
		verify(testMock, times(3)).getTwoVectors(Mockito.anyInt(), Mockito.anyDouble());
	 }*/

	@Test
	public void getBeginTwoVectorsTest(){
		Vector3f[] vecs = new Vector3f[3];
    	vecs[0] = new Vector3f(0,0,0);
    	vecs[1] = new Vector3f(2,4,0);
    	vecs[2] = new Vector3f(4,1,0);
		SplineCurve sp = new SplineCurve(SplineType.CatmullRom, vecs, (float)0.5, true);
		Vector3f[] veccies = sp.getTwoVectors(0, 0);
		assertEquals(vecs[0], veccies[0]);
		assertEquals(vecs[1], veccies[1]);
	}
	
	@Test
	public void getMiddleTwoVectorsTest(){
		Vector3f[] vecs = new Vector3f[3];
    	vecs[0] = new Vector3f(0,0,0);
    	vecs[1] = new Vector3f(2,4,0);
    	vecs[2] = new Vector3f(4,1,0);
		SplineCurve sp = new SplineCurve(SplineType.CatmullRom, vecs, (float)0.5, true);
		Vector3f[] veccies = sp.getTwoVectors(1, 0);
		assertEquals(vecs[1], veccies[0]);
		assertEquals(sp.interpolate(0.01f, 1, null), veccies[1]);
	}
	
	@Test
	public void getEndTwoVectorsTest(){
		Vector3f[] vecs = new Vector3f[3];
    	vecs[0] = new Vector3f(0,0,0);
    	vecs[1] = new Vector3f(2,4,0);
    	vecs[2] = new Vector3f(4,1,0);
		SplineCurve sp = new SplineCurve(SplineType.CatmullRom, vecs, (float)0.5, true);
		Vector3f[] veccies = sp.getTwoVectors(1, 0.5);
		assertEquals(sp.interpolate(0.5f, 1, null), veccies[0]);
		assertEquals(sp.interpolate(0.51f, 1, null), veccies[1]);
	}
	
	@Test
	public void drawBoxTest(){
		
	}
	
	@Test
	public void drawCurveTest(){
		Node rootNode = new Node();
	}
}
