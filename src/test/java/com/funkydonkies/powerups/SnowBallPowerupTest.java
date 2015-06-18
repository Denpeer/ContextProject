package com.funkydonkies.powerups;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import com.funkydonkies.controllers.GrowingSnowballControl;
import com.funkydonkies.controllers.PenguinControl;
import com.funkydonkies.core.App;
import com.funkydonkies.gamestates.PlayState;
import com.jme3.app.state.AppStateManager;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

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
	public void testAddSnowballcontrol() {
		Spatial penguin = new Node("penguin");
		SnowballPowerup spy = spy(powerup);
		doReturn(mock(Material.class)).when(spy).createSnowMaterial();
		PenguinControl pControl = mock(PenguinControl.class);
		penguin.addControl(pControl);
		Vector3f vel = new Vector3f(0, 1, 0);
		when(pControl.getLinearVelocity()).thenReturn(vel);
		spy.addSnowballControl(penguin);
		
		assertEquals(penguin.getControl(GrowingSnowballControl.class).getLinearVelocity(), vel);
		assertEquals(penguin.getName(), SnowballPowerup.SNOW_PENGUIN_NAME);
	}
}
