package com.funkydonkies.controllers;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.any;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import com.funkydonkies.gamestates.CameraState;
import com.funkydonkies.gamestates.PlayState;
import com.funkydonkies.w4v3.App;
import com.funkydonkies.w4v3.curve.SplineCurve;
import com.jme3.app.state.AppStateManager;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.material.Material;
import com.jme3.math.Spline.SplineType;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;

public class CurveStateTest {
	private CurveState curveState;
	@Mock private AppStateManager sManager;
	@Mock private App app;
	private CameraState camState;
	private SplineCurve splineCurve;
	private Material material;
	private Node node;
	private PhysicsSpace physicsSpace;
	private RigidBodyControl control;
	private PlayState playState;
	private CurveState spyCurveState;
	private SplineCurve spySplinaCurve;
	private Geometry geom;
	
	@Before
	public void setUp() {
		
		curveState = new CurveState();
		spyCurveState = spy(curveState);
		sManager = mock(AppStateManager.class);
		app = mock(App.class);
		camState = mock(CameraState.class);
		splineCurve = new SplineCurve(SplineType.CatmullRom, true);
		spySplinaCurve = spy(splineCurve);
		material = mock(Material.class);
		node = mock(Node.class);
		physicsSpace = mock(PhysicsSpace.class);
		control = mock(RigidBodyControl.class);
		playState = mock(PlayState.class);
		geom = mock(Geometry.class);
		when(playState.getPhysicsSpace()).thenReturn(physicsSpace);
		when(sManager.getState(CameraState.class)).thenReturn(camState);
		when(sManager.getState(PlayState.class)).thenReturn(playState);
		doReturn(material).when(spyCurveState).initializeMaterial();
		when(app.getRootNode()).thenReturn(node);
		when(spyCurveState.initializeSplineCurve()).thenReturn(spySplinaCurve);
		when(spyCurveState.makeRigidBodyControl()).thenReturn(control);
		when(spySplinaCurve.getGeometry()).thenReturn(geom);
		doNothing().when(spySplinaCurve).drawCurve(
				material, physicsSpace, control, node);
	}

	@Test
	public void initTest() {
		spyCurveState.initialize(sManager, app);
		verify(spyCurveState).initializeMaterial();
		verify(spyCurveState).initializeSplineCurve();
	}
	
	@Test
	public void getNumberOfControlPointsTest() {
		
	}
	
	@Test
	public void updateTest() {
		spyCurveState.initialize(sManager, app);
		spyCurveState.update(0.01f);
		spyCurveState.setUpdateEnabled(true);
		verify(node).detachChildNamed("curve");
		verify(spySplinaCurve).drawCurve(material, physicsSpace, control, node);
		verify(geom).removeControl(any(RigidBodyControl.class));

		verify(geom, never()).removeControl(control);
		verify(spyCurveState, never()).makeRigidBodyControl();
		
		spyCurveState.update(0.01f);
		
		verify(spyCurveState).makeRigidBodyControl();
//		verify(geom).removeControl(rb);
//		verify(any(RigidBodyControl.class)).setEnabled(false);
	}
	
}
