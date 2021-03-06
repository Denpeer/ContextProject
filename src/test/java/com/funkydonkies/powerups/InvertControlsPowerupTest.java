package com.funkydonkies.powerups;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import com.funkydonkies.core.App;
import com.funkydonkies.curve.SplineCurve;
import com.funkydonkies.gamestates.CurveState;
import com.funkydonkies.sounds.SoundState;
import com.jme3.app.state.AppStateManager;
import com.jme3.material.Material;
import com.jme3.scene.Geometry;

public class InvertControlsPowerupTest {
	
	private AppStateManager sManager;
	private App app;
	private InvertControlsPowerup powerup;
	private InvertControlsPowerup powerupSpy;
	private CurveState curveState;
	private SoundState ss;
	private SplineCurve mockSplineCurve;
	private Geometry mockGeometry;
	private Material mockMaterial;
	
	@Before
	public void setUp() {
		app = mock(App.class);
		mockSplineCurve = mock(SplineCurve.class);
		mockGeometry = mock(Geometry.class);
		mockMaterial = mock(Material.class);
		sManager = mock(AppStateManager.class);
		curveState = mock(CurveState.class);
		powerup = new InvertControlsPowerup();
		powerupSpy = spy(powerup);
		when(sManager.getState(CurveState.class)).thenReturn(curveState);
		when(curveState.getSplineCurve()).thenReturn(mockSplineCurve);
		when(mockSplineCurve.getGeometry()).thenReturn(mockGeometry);
		when(mockGeometry.getMaterial()).thenReturn(mockMaterial);
		powerup.initialize(sManager, app);
		powerupSpy.initialize(sManager, app);
		ss = mock(SoundState.class);
		when(sManager.getState(SoundState.class)).thenReturn(ss);
	}
	
	@Test
	public void setEnabledTest() {
		assertFalse(powerup.isEnabled());
		powerup.setEnabled(true);
		assertTrue(powerup.isEnabled());
		verify(curveState).setInvertControlPoints(true);
		powerup.setEnabled(false);
		assertFalse(powerup.isEnabled());
		verify(curveState).setInvertControlPoints(false);
	}
	
	@Test
	public void updateTest() {
		powerupSpy.update(0.1f);
		verify(powerupSpy, times(0)).setEnabled(false);
		powerupSpy.update(10);
		verify(powerupSpy, times(1)).setEnabled(false);
		
	}
}
