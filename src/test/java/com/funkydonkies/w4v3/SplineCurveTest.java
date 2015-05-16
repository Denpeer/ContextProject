package com.funkydonkies.w4v3;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import org.junit.BeforeClass;
import org.junit.Test;

import com.jme3.bullet.PhysicsSpace;
import com.jme3.material.Material;
import com.jme3.math.Spline.SplineType;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;

/**
 * Testing SplineCurve.
 *
 */
public class SplineCurveTest {
	private static Vector3f[] vecs;
	private static SplineCurve sp;
//	private static SplineCurve sp1;
	private static Material mat;
	private static PhysicsSpace psy;
	
	/** Initializes class variables once.
	 * 
	 * @throws Exception -
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
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
//    	sp1 = mock(SplineCurve.class);
		mat = mock(Material.class);
		psy = mock(PhysicsSpace.class);
	}

	/**
	 * Test getTwoVectors.
	 */
	@Test
	public void getBeginTwoVectorsTest() {
		final Vector3f[] veccies = sp.getTwoVectors(0, 0);
		assertEquals(vecs[0], veccies[0]);
		assertEquals(vecs[1], veccies[1]);
	}
	
	/**
	 * Test getTwoVectors.
	 */
	@Test
	public void getMiddleTwoVectorsTest() {
		final Vector3f[] veccies = sp.getTwoVectors(1, 0);
		final float val = 0.01f;
		assertEquals(vecs[1], veccies[0]);
		assertEquals(sp.interpolate(val, 1, null), veccies[1]);
	}
	
	/**
	 * Test getTwoVectors.
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
	 * Test drawBox.
	 */
	@Test
	public void drawBoxTest() {
		final Node n = new Node();
		final Node node = sp.drawBox(vecs, mat, psy, n);
		assertEquals(node.getChildren().size(), 1);
	} 
	
	/**
	 * Test drawCurve.
	 */
	@Test
	public void drawCurveTest() {
		final Node rootNode = new Node();
		sp.drawCurve(rootNode, mat, psy);
		assertEquals(rootNode.getChildren().size(), 1);
	}
	
//	/**
//	 * Test loopThroughSpline.
//	 */
//	@Test
//	public void loopThroughSplineTest() {
//		final Node n = mock(Node.class);
//		sp1.loopThroughSpline(n, psy, mat);
//		final int amount = 1000000;
//		verify(sp1, times(amount)).drawBox(vecs, mat, psy, n);
//	}
	
}
