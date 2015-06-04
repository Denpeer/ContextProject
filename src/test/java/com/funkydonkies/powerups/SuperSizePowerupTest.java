package com.funkydonkies.powerups;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import com.funkydonkies.gamestates.PlayState;
import com.funkydonkies.w4v3.App;
import com.jme3.app.state.AppStateManager;
import com.jme3.scene.Node;

public class SuperSizePowerupTest {
	private SuperSizePowerup supersize;
	private SuperSizePowerup mockSupersize;
	private AppStateManager sManager;
	private App app;
	private PlayState playState;
	private Node node;
	
	@Before
	public void setUp() throws Exception {
		supersize = new SuperSizePowerup();
		mockSupersize = mock(SuperSizePowerup.class);
		sManager = mock(AppStateManager.class);
		app = mock(App.class);
		playState = mock(PlayState.class);
		node = mock(Node.class);
		Node n = new Node();
		when(sManager.getState(PlayState.class)).thenReturn(playState);
		when(playState.getBallNode()).thenReturn(n);
	}

	@Test
	public void testInitialize() {
		
		
	}

	@Test
	public void testSetEnabled() {
		mockSupersize.initialize(sManager, app);
		supersize.initialize(sManager, app);
		assertFalse(supersize.isEnabled());
		supersize.setEnabled(true);
		mockSupersize.setEnabled(true);
		assertTrue(supersize.isEnabled());
//		verify(mockSupersize).scaleUpAll();
		
	}

	@Test
	public void testUpdate() {
	}

	@Test
	public void testScaleUpAll() {
	}

	@Test
	public void testScaleDownAll() {
		supersize.initialize(sManager, app);
		supersize.scaleDownAll();
		
	}

	@Test
	public void testToggleEnabled() {
		fail("Not yet implemented");
	}

}
