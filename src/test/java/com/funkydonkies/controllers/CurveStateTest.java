package com.funkydonkies.controllers;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.stub;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;

import org.junit.Before;
import org.junit.Test;

import com.funkydonkies.gamestates.CameraState;
import com.funkydonkies.gamestates.PlayState;
import com.funkydonkies.w4v3.App;
import com.funkydonkies.w4v3.curve.SplineCurve;
import com.jme3.app.state.AppStateManager;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.material.Material;
import com.jme3.math.Spline.SplineType;
import com.jme3.scene.Node;

public class CurveStateTest {
	private CurveState curveState;
	private AppStateManager sManager;
	private App app;
	private CameraState camState;
	private SplineCurve splineCurve;
	private Material material;
	private Node node;
	private PhysicsSpace ps;
	private RigidBodyControl rb;
	private PlayState playState;
	private CurveState spy;
	private SplineCurve spy2;
	
	@Before
	public void setUp() {
		
		curveState = new CurveState();
		spy = spy(curveState);
		sManager = mock(AppStateManager.class);
		app = mock(App.class);
		camState = mock(CameraState.class);
		splineCurve = new SplineCurve(SplineType.CatmullRom, true);
		spy2 = spy(splineCurve);
		material = mock(Material.class);
		node = mock(Node.class);
		ps = mock(PhysicsSpace.class);
		rb = mock(RigidBodyControl.class);
		playState = mock(PlayState.class);
		when(playState.getPhysicsSpace()).thenReturn(ps);
		when(sManager.getState(CameraState.class)).thenReturn(camState);
		when(sManager.getState(PlayState.class)).thenReturn(playState);
		doReturn(material).when(spy).initializeMaterial();
		when(app.getRootNode()).thenReturn(node);
		when(spy.initializeSplineCurve()).thenReturn(spy2);
		when(spy.makeRigidBodyControl()).thenReturn(rb);
		doNothing().when(spy2).drawCurve(
				material, ps, rb, node);
	}

	@Test
	public void initTest() {
		spy.initialize(sManager, app);
		
	}
	
	@Test
	public void updateTest() {
		spy.initialize(sManager, app);
		spy.update(0.01f);
		
	}
	
}
