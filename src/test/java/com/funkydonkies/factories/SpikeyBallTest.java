package com.funkydonkies.factories;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.funkydonkies.controllers.SpikeyBallControl;
import com.funkydonkies.gamestates.PlayState;
import com.funkydonkies.sounds.Sound;
import com.funkydonkies.sounds.SoundState;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppStateManager;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.material.Material;
import com.jme3.scene.Spatial;
/**
 * This class tests the fish factory class.
 */
public class SpikeyBallTest {
	private SpikeyBallFactory mockFactory;
	private AppStateManager mockSManager;
	private PlayState mockPlayState;
	private SimpleApplication app;
	private SoundState soundstate;
	
	/**
	 * Do this before executing tests.
	 * @throws Exception
	 */
	@Before
	public void setUp() {
		app = mock(SimpleApplication.class);
		mockSManager = mock(AppStateManager.class);
		mockPlayState = mock(PlayState.class);
		mockFactory = spy(SpikeyBallFactory.class);
		soundstate = mock(SoundState.class);
		
		doReturn(mockPlayState).when(mockSManager).getState(PlayState.class);
		doReturn(soundstate).when(mockSManager).getState(SoundState.class);
		doReturn(mock(PhysicsSpace.class)).when(mockPlayState).getPhysicsSpace();
	    doReturn(mock(Material.class)).when(mockFactory).getSpikeyBallMaterial();
		Mockito.doNothing().when(soundstate).queueSound(Mockito.any(Sound.class));

	}

	/**
	 * tests if the fishcontrol is enabled.
	 */
	@Test
	public void testMakeObject() {
		final Spatial ball = mockFactory.makeObject(mockSManager, app);
		assertTrue(((SpikeyBallControl) (ball).getControl(0)).isEnabled());
	}
	
	/**
	 * tests if the control is attached.
	 */
	@Test
	public void testControlAttached() {
		final Spatial ball = mockFactory.makeObject(mockSManager, app);
		assertTrue(((SpikeyBallControl) (ball).getControl(0)) != null);
	}

}
