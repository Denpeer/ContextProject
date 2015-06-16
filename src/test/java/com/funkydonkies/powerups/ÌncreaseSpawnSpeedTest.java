package com.funkydonkies.powerups;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import com.funkydonkies.core.App;
import com.funkydonkies.gamestates.CurveState;
import com.funkydonkies.gamestates.SpawnState;
import com.jme3.app.state.AppStateManager;

public class ÌncreaseSpawnSpeedTest {

	private AppStateManager sManager;
	private App app;
	private IncreaseSpawnSpeedPowerup powerup;
	private IncreaseSpawnSpeedPowerup powerupSpy;
	private CurveState curveState;
	private SpawnState spawnState;
	
	@Before
	public void setUp() {
		app = mock(App.class);
		sManager = mock(AppStateManager.class);
		curveState = mock(CurveState.class);
		spawnState = mock(SpawnState.class);
		powerup = new IncreaseSpawnSpeedPowerup(5);
		powerupSpy = spy(powerup);
		when(sManager.getState(CurveState.class)).thenReturn(curveState);
		when(sManager.getState(SpawnState.class)).thenReturn(spawnState);
		powerup.initialize(sManager, app);
		powerupSpy.initialize(sManager, app);
	}
	
	@Test
	public void testSetEnabled() {
		assertFalse(powerup.isEnabled());
		powerup.setEnabled(true);
		assertTrue(powerup.isEnabled());
		verify(spawnState).setBallSpawnTime(5);
	}
	
	@Test
	public void testSetEnabledFalse() {
		powerup.setEnabled(false);
		assertFalse(powerup.isEnabled());
		verify(spawnState).setBallSpawnTime(SpawnState.DEFAULT_BALL_SPAWN_TIME);
	}
}
