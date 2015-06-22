package com.funkydonkies.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;

import com.funkydonkies.gamestates.PlayState;
import com.jme3.post.FilterPostProcessor;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;

public class AppTest {

	private App app;
	private App appSpy;
	private ViewPort viewPort;

	@Before
	public void setUp() throws Exception {
		app = new App();
		
		appSpy = spy(app);
		viewPort = mock(ViewPort.class);
		
		doReturn(viewPort).when(appSpy).getViewPort();
		doNothing().when(appSpy).setRootNodeUserData();
	}

	@Test
	public void testSimpleInitApp() {
		appSpy.simpleInitApp();
		assertNotNull(app.getStateManager().getState(PlayState.class));
		verify(appSpy).setRootNodeUserData();
		verify(appSpy).addEffects();
	}

	@Test
	public void testGetPenguinNode() {
		appSpy.simpleInitApp();
		assertTrue(appSpy.getPenguinNode() instanceof Node);
		assertEquals(appSpy.getPenguinNode().getName(), "penguins");
	}
	
	@Test
	public void testAddEffects() {
		appSpy.addEffects();
		verify(viewPort).addProcessor(any(FilterPostProcessor.class));
	}

}
