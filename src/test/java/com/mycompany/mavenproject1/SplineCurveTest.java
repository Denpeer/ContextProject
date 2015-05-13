package com.mycompany.mavenproject1;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import com.jme3.asset.AssetManager;
import com.jme3.asset.DesktopAssetManager;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.collision.PhysicsCollisionEvent;
import com.jme3.bullet.collision.shapes.SphereCollisionShape;
import com.jme3.material.Material;
import com.jme3.math.Spline.SplineType;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

public class SplineCurveTest {
	private static Vector3f[] vecs;
	static SplineCurve sp;
	static SplineCurve sp1;
	static Material mat;
	static PhysicsSpace psy;
	static AssetManager ass;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		vecs = new Vector3f[3];
    	vecs[0] = new Vector3f(0,0,0);
    	vecs[1] = new Vector3f(2,4,0);
    	vecs[2] = new Vector3f(4,1,0);
    	sp = new SplineCurve(SplineType.CatmullRom, vecs, (float)0.5, true);
    	sp1 = mock(SplineCurve.class);
		mat = mock(Material.class);
		psy = mock(PhysicsSpace.class);
		ass = mock(AssetManager.class);
	}

	@Test
	public void getBeginTwoVectorsTest(){
		Vector3f[] veccies = sp.getTwoVectors(0, 0);
		assertEquals(vecs[0], veccies[0]);
		assertEquals(vecs[1], veccies[1]);
	}
	
	@Test
	public void getMiddleTwoVectorsTest(){
		Vector3f[] veccies = sp.getTwoVectors(1, 0);
		assertEquals(vecs[1], veccies[0]);
		assertEquals(sp.interpolate(0.01f, 1, null), veccies[1]);
	}
	
	@Test
	public void getEndTwoVectorsTest(){
		Vector3f[] veccies = sp.getTwoVectors(1, 0.5);
		assertEquals(sp.interpolate(0.5f, 1, null), veccies[0]);
		assertEquals(sp.interpolate(0.51f, 1, null), veccies[1]);
	}
	
	@Test
	public void drawBoxTest(){
		Node n = new Node();
		Node node = sp.drawBox(vecs, mat, psy, n);
		assertEquals(node.getChildren().size(), 1);
	} 
	
	@Test
	public void drawCurveTest(){
		Node rootNode = new Node();
		sp.drawCurve(rootNode, mat, psy);
		assertEquals(rootNode.getChildren().size(), 1);
	}
	
	@Test
	public void loopThroughSplineTest(){
		Node n = mock(Node.class);
		sp1.loopThroughSpline(n, psy, mat);
		verify(sp1, times(1000000)).drawBox(vecs, mat, psy, n);
	}
	
}
