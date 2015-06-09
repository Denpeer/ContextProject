package com.funkydonkies.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.spy;

import org.junit.Before;
import org.junit.Test;

import com.funkydonkies.gamestates.PlayState;
import com.jme3.scene.Node;

public class AppTest {

	private App app;
	
	@Before
	public void setUp() throws Exception {
		app = new App();
	}

	@Test
	public void testSimpleInitApp() {
		app.simpleInitApp();
		assertNotNull(app.getStateManager().getState(PlayState.class));
		assertEquals(app.getRootNode().getChild(0).getName(), "penguins");
		
	}

	@Test
	public void testGetPenguinNode() {
		app.simpleInitApp();
		assertTrue(app.getPenguinNode() instanceof Node);
		assertEquals(app.getPenguinNode().getName(), "penguins");
	}

}
