package com.funkydonkies.w4v3.curve;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.BeforeClass;
import org.junit.Test;

import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.material.Material;

public class SplineCurveTest {
	static SplineCurve sCurve;
	static Material mat;
	static PhysicsSpace phys;
	static RigidBodyControl rigid;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		sCurve = mock(SplineCurve.class);
		mat = mock(Material.class);
		phys = mock(PhysicsSpace.class);
		rigid = mock(RigidBodyControl.class);
	}

	@Test
	public void drawCurvetest() {
		
	}

}
