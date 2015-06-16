package com.funkydonkies.curve;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import org.junit.BeforeClass;
import org.junit.Test;

import com.funkydonkies.curve.SplineCurve;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.material.Material;
import com.jme3.math.Spline.SplineType;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;

public class SplineCurveTest {
	static SplineCurve sCurve;
	static Material mat;
	static PhysicsSpace phys;
	static RigidBodyControl rigid;
	static SplineCurve sp;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		sCurve = mock(SplineCurve.class);
		mat = mock(Material.class);
		phys = mock(PhysicsSpace.class);
		rigid = mock(RigidBodyControl.class);
		sp = new SplineCurve(SplineType.CatmullRom, true);
	}

	
	@Test
	public void testConstruct() {
		assertFalse(sp.getCurvePoints() == null);
	}
	
	@Test
	public void drawCurvetest() {
		Node node = new Node();
		sp.drawCurve(mat, phys, rigid, node);
		assertTrue(node.getChildren().size() == 1);
	}
	
	@Test
	public void incrementPointsTest(){
		Vector3f curveP = new Vector3f();
		curveP = sp.getCurvePoints()[0];
		sp.incrementPoints();
		assertEquals(sp.getCurvePoints()[0], curveP);
	}

	@Test
	public void decrementPointsTest(){
		Vector3f curveP = new Vector3f();
		curveP = sp.getCurvePoints()[0];
		sp.decrementPoints();
		assertEquals(sp.getCurvePoints()[0], curveP);
	}
	
	@Test
	public void getGeometryTest(){
		Geometry geom;
		sp.getGeometry();
		geom = sp.getGeometry();
		assertFalse(geom == null);
	}
	
	
}
