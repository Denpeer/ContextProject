package com.funkydonkies.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;

import com.funkydonkies.gamestates.PlayState;
import com.jme3.scene.Node;

public class AppTest {

	private App app;
	private App appSpy;

	@Before
	public void setUp() throws Exception {
		app = new App();
		appSpy = spy(app);
		doNothing().when(appSpy).setRootNodeUserData();
	}

	@Test
	public void testSimpleInitApp() {
		appSpy.simpleInitApp();
		assertNotNull(app.getStateManager().getState(PlayState.class));
		assertEquals(app.getRootNode().getChild(0).getName(), "penguins");
		verify(appSpy).setRootNodeUserData();
	}

	@Test
	public void testGetPenguinNode() {
		appSpy.simpleInitApp();
		assertTrue(appSpy.getPenguinNode() instanceof Node);
		assertEquals(appSpy.getPenguinNode().getName(), "penguins");
	}

}
