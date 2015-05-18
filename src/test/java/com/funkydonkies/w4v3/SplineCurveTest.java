package com.funkydonkies.w4v3;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.BeforeClass;
import org.junit.Test;

import com.jme3.asset.AssetManager;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.material.Material;
import com.jme3.math.Spline.SplineType;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;

public class SplineCurveTest {
	private static Vector3f[] vecs;
	private static SplineCurve sp;
	private static Material mat;
	private static PhysicsSpace psy;
	private static AssetManager ass;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		vecs = new Vector3f[3];
    	vecs[0] = new Vector3f(0,0,0);
    	vecs[1] = new Vector3f(2,4,0);
    	vecs[2] = new Vector3f(4,1,0);
    	sp = new SplineCurve(SplineType.CatmullRom, vecs, (float)0.5, true);
		mat = mock(Material.class);
		psy = mock(PhysicsSpace.class);
		ass = mock(AssetManager.class);
	}

	@Test
	public void getBeginTwoVectorsTest(){
		final Vector3f[] veccies = sp.getTwoVectors(0, 0);
		assertEquals(vecs[0], veccies[0]);
		assertEquals(vecs[1], veccies[1]);
	}
	
	@Test
	public void getMiddleTwoVectorsTest(){
		final Vector3f[] veccies = sp.getTwoVectors(1, 0);
		assertEquals(vecs[1], veccies[0]);
		assertEquals(sp.interpolate(0.01f, 1, null), veccies[1]);
	}
	
	@Test
	public void getEndTwoVectorsTest(){
		final Vector3f[] veccies = sp.getTwoVectors(1, 0.5);
		assertEquals(sp.interpolate(0.5f, 1, null), veccies[0]);
		assertEquals(sp.interpolate(0.51f, 1, null), veccies[1]);
	}
	
	@Test
	public void drawBoxTest(){
		final Node n = mock(Node.class);
		sp.drawBox(vecs, mat, n);
		verify(n).attachChild(any(Geometry.class));
	} 
	
	@Test
	public void drawCurveTest(){
		final Node rootNode = new Node();
		sp.drawCurve(rootNode, mat, psy);
		assertEquals(rootNode.getChildren().size(), 1);
	}
	
	@Test
	public void loopThroughSplineTest(){
		final Node n = mock(Node.class);
		sp.loopThroughSpline(n, mat);
		verify(n, times(200)).attachChild(any(Geometry.class));
	}
	
}
