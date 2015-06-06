package com.funkydonkies.core;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.funkydonkies.appstates.PlayState;
/**
 * The tests for app.
 */
public class AppTest {
	private static App app;
	
	@Test
	public void testSimpleInit() {
		app = new App();
		app.simpleInitApp();
		assertTrue(app.getStateManager().getState(PlayState.class) != null);
	}


}
