package com.funkydonkies.gamestates;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import com.funkydonkies.core.App;
import com.funkydonkies.curve.SplineCurve;
import com.funkydonkies.interfaces.Bridge;
import com.jme3.app.state.AppStateManager;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.material.Material;
import com.jme3.math.Spline.SplineType;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;

public class CurveStateTest {
	private static final float TPF = 0.01f;
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
	private SplineCurve spySplineCurve;
	private Geometry geom;
	private Bridge bridge;
	
	@Before
	public void setUp() {
		bridge = mock(Bridge.class);
		curveState = new CurveState();
		spyCurveState = spy(curveState);
		sManager = mock(AppStateManager.class);
		app = mock(App.class);
		material = mock(Material.class);
		camState = mock(CameraState.class);
		splineCurve = new SplineCurve(material, SplineType.CatmullRom, true);
		spySplineCurve = spy(splineCurve);
		node = mock(Node.class);
		physicsSpace = mock(PhysicsSpace.class);
		control = mock(RigidBodyControl.class);
		playState = mock(PlayState.class);
		geom = mock(Geometry.class);
		when(playState.getPhysicsSpace()).thenReturn(physicsSpace);
		when(sManager.getState(CameraState.class)).thenReturn(camState);
		when(sManager.getState(PlayState.class)).thenReturn(playState);
		when(camState.getBridge()).thenReturn(bridge);
		when(bridge.isBgSet()).thenReturn(true);
		when(app.getSceneNode()).thenReturn(node);
		when(spyCurveState.initializeSplineCurve()).thenReturn(spySplineCurve);
		when(spyCurveState.makeRigidBodyControl()).thenReturn(control);
		when(spySplineCurve.getGeometry()).thenReturn(geom);
		when(bridge.getControlPoints()).thenReturn(getTestPoints());
		doReturn(material).when(spyCurveState).initializeMaterial();
		doNothing().when(spySplineCurve).drawCurve(
				physicsSpace, control, node);
		
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
	
	private float[] getTestPoints() {
		float[] points = new float[curveState.getAmountOfControlPoints()];
		Arrays.fill(points, 480);
		final int bottomX = 1;
		final int topX = curveState.getAmountOfControlPoints() - 1;
		for (int i = bottomX; i < topX; i++) { // TESTING CODE
			final int scale = 10;
			points[i] = i * scale;
		}
		return points;
	}
	
	@Test
	public void updateTest() {
		spyCurveState.initialize(sManager, app);
		spyCurveState.update(TPF);
		verify(node).detachChildNamed("curve");
		verify(spySplineCurve).drawCurve(physicsSpace, control, node);
		verify(geom).removeControl(any(RigidBodyControl.class));

		verify(geom, never()).removeControl(control);
		verify(spyCurveState, never()).makeRigidBodyControl();
		spyCurveState.update(TPF);
		
	}
	
	
	@Test
	public void updateCameraEnabledTest() {
		spyCurveState.initialize(sManager, app);
		assertFalse(spyCurveState.getCameraEnabled());
		spyCurveState.toggleCameraEnabled();
		assertTrue(spyCurveState.getCameraEnabled());
		spyCurveState.update(TPF);
		verify(node).detachChildNamed("curve");
		verify(spySplineCurve).drawCurve(physicsSpace, control, node);
		verify(geom).removeControl(any(RigidBodyControl.class));

		verify(geom, never()).removeControl(control);
		verify(spyCurveState, never()).makeRigidBodyControl();
		
		/* TODO checking for getNoOfControlPOints , setMaxHeightdiff but they're private...
		 *  make them public of check for their effects? */
	}
	
	@Test
	public void updateUpdateEnabledTest() {
		spyCurveState.initialize(sManager, app);
		spyCurveState.setUpdateEnabled(true);
		spyCurveState.update(TPF);
		verify(node).detachChildNamed("curve");
		verify(spySplineCurve).drawCurve(physicsSpace, control, node);
		verify(geom).removeControl(any(RigidBodyControl.class));

		verify(geom, never()).removeControl(control);
		verify(spyCurveState, never()).makeRigidBodyControl();
		spyCurveState.update(TPF);

		Vector3f[] points = splineCurve.getCurvePoints();
		/* TODO check scaleValues and the curvepoints, but they're NaN?? */
	}

	
}
