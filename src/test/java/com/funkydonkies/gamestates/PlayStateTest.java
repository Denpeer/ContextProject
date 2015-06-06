package com.funkydonkies.gamestates;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.spy;

import org.junit.BeforeClass;
import org.junit.Test;

import com.funkydonkies.appstates.CameraState;
import com.funkydonkies.appstates.ComboState;
import com.funkydonkies.appstates.CurveState;
import com.funkydonkies.appstates.DifficultyState;
import com.funkydonkies.appstates.GameInputState;
import com.funkydonkies.appstates.PlayState;
import com.funkydonkies.appstates.SpawnState;
import com.funkydonkies.core.App;
import com.funkydonkies.exceptions.BadDynamicTypeException;
import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;

public class PlayStateTest {
	private static PlayState pState;
	private static App app;
	private static PlayState spy;
	private static AppStateManager sm;
	/**
	 * Do this before the testing begins.
	 */
	@BeforeClass
	public static void setUpBeforeClass() {
		pState = new PlayState();
		app = new App();
		sm = new AppStateManager(app);
		spy = spy(pState);
		doNothing().when(spy).handleCamera();
	}
	/**
	 * Test correct initialize.
	 */
	@Test
	public void testInit() {
		spy.initialize(sm, app);
		assertTrue(sm.getState(CameraState.class) != null);
		assertTrue(sm.getState(GameInputState.class) != null);
		assertTrue(sm.getState(CurveState.class) != null);
		assertTrue(sm.getState(SpawnState.class) != null);
		assertTrue(sm.getState(ComboState.class) != null);
		assertTrue(sm.getState(DifficultyState.class) != null);
	}
	/**
	 * Test bad initialize.
	 */
	@Test(expected=BadDynamicTypeException.class)
	public void testWrongInit() {
		spy.initialize(sm, new Application());
	}
	
	/**
	 * Test physicsSpace getter.
	 */
	@Test
	public void testGetPhysic(){
		assertTrue(PlayState.getPhysicsSpace() != null);
	}

}
