package com.funkydonkies.powerups;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;

import com.funkydonkies.core.App;
import com.funkydonkies.gamestates.CurveState;
import com.jme3.app.state.AppStateManager;

public class InvertControlsPowerupTest {
	
	private AppStateManager sManager;
	private App app;
	private InvertControlsPowerup powerup;
	private InvertControlsPowerup powerupSpy;
	private CurveState curveState;
	
	@Before
	public void setUp() {
		app = mock(App.class);
		sManager = mock(AppStateManager.class);
		curveState = mock(CurveState.class);
		powerup = new InvertControlsPowerup();
		powerupSpy = spy(powerup);
		when(sManager.getState(CurveState.class)).thenReturn(curveState);
		powerup.initialize(sManager, app);
		powerupSpy.initialize(sManager, app);
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
