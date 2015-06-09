package com.funkydonkies.powerups;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import com.funkydonkies.core.App;
import com.funkydonkies.gamestates.PlayState;
import com.jme3.app.state.AppStateManager;

public class SnowBallPowerupTest {
	
	private AppStateManager sManager;
	private App app;
	private SnowballPowerup powerup;
	private SnowballPowerup powerupSpy;
	private PlayState playState;
	
	@Before
	public void setUp() throws Exception {
		app = mock(App.class);
		sManager = mock(AppStateManager.class);
		playState = mock(PlayState.class);
		powerup = new SnowballPowerup();
		powerupSpy = spy(powerup);
		when(sManager.getState(PlayState.class)).thenReturn(playState);
		powerup.initialize(sManager, app);
	}

	@Test
	public void testSetEnabled() {
		powerup.setEnabled(true);
		assertTrue(powerup.isEnabled());
	}

	@Test
	public void testUpdate() {
		doNothing().when(powerupSpy).activate();
		powerupSpy.update(0.1f);
		verify(powerupSpy, times(0)).activate();
		powerupSpy.setEnabled(true);
		powerupSpy.update(0.1f);
		powerupSpy.update(0.1f);
		verify(powerupSpy, times(1)).activate();

	}

	@Test
	public void testActivate() {
	}
}
