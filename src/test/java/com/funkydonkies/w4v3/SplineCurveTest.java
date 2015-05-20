package com.funkydonkies.w4v3;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.BeforeClass;
import org.junit.Test;

import com.jme3.bullet.PhysicsSpace;
import com.jme3.material.Material;
import com.jme3.math.Spline.SplineType;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;

/**
 * Test test the SplineCurve class.
 */
public class SplineCurveTest {
	private static Vector3f[] vecs;
	private static SplineCurve sp;
	private static Material mat;
	private static PhysicsSpace psy;
	
	/**
	 * Initialize variables before testing.
	 */
	@BeforeClass
	public static void setUpBeforeClass() {
		final Vector3f v0 = new Vector3f(0, 0, 0);
		final Vector3f v1 = new Vector3f(2, 4, 0);
		final Vector3f v2 = new Vector3f(4, 1, 0);
		final int size = 3;
		vecs = new Vector3f[size];
    	vecs[0] = v0;
    	vecs[1] = v1;
    	vecs[2] = v2;
    	
    	final float curveTension = 0.5f;
    	sp = new SplineCurve(SplineType.CatmullRom, vecs, curveTension, true);
		mat = mock(Material.class);
		psy = mock(PhysicsSpace.class);
	}
	/**
	 * Test if the appropriate vectors are returned at the begin of the curve.
	 */
	@Test
	public void getBeginTwoVectorsTest() {
		final Vector3f[] veccies = sp.getTwoVectors(0, 0);
		assertEquals(vecs[0], veccies[0]);
		assertEquals(vecs[1], veccies[1]);
	}
	/**
	 * Test if the appropriate vectors are returned in the middle of the curve.
	 */
	@Test
	public void getMiddleTwoVectorsTest() {
		final Vector3f[] veccies = sp.getTwoVectors(1, 0);
		final float val = 0.01f;
		assertEquals(vecs[1], veccies[0]);
		assertEquals(sp.interpolate(val, 1, null), veccies[1]);
	}
	
	/**
	 * Test if the appropriate vectors are returned at the end of the curve.
	 */
	@Test
	public void getEndTwoVectorsTest() {
		final Vector3f[] veccies = sp.getTwoVectors(1, 0.5);
		final float val0 = 0.5f;
		final float val1 = 0.51f;
		assertEquals(sp.interpolate(val0, 1, null), veccies[0]);
		assertEquals(sp.interpolate(val1, 1, null), veccies[1]);
	}
	
	/**
	 * Test if the box is added to the rootNode.
	 */
	@Test
	public void drawBoxTest() {
		final Node n = mock(Node.class);
		sp.drawBox(vecs, mat, n);
		verify(n).attachChild(any(Geometry.class));
	} 
	
	/**
	 * Test if the curve is added to the rootNode.
	 */
	@Test
	public void drawCurveTest() {
		final Node rootNode = new Node();
		sp.drawCurve(rootNode, mat, psy);
		assertEquals(rootNode.getChildren().size(), 1);
	}
	
	/**
	 * test if loopthroughspline is attaches a geometry a certain amount of time.
	 */
	@Test
	public void loopThroughSplineTest() {
		final Node n = mock(Node.class);
		sp.loopThroughSpline(n, mat);
		final int amount = 200;
		verify(n, times(amount)).attachChild(any(Geometry.class));
	}
	
}
