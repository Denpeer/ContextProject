package com.funkydonkies.gamestates;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;

import com.funkydonkies.w4v3.App;
import com.jme3.app.state.AppStateManager;

public class SpawnStateTest {
	private SpawnState spawnState;
	private App app;
	private AppStateManager sManager;
	
	@Before
	public void setup() {
		spawnState = new SpawnState();
		app = mock(App.class);
		sManager = mock(AppStateManager.class);
	}
	
	@Test
	public void initTest() {
		spawnState.initialize(sManager, app);
		verify(app).getAssetManager();
		assertTrue(spawnState.isInitialized());
	}

}
