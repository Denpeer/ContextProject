package com.funkydonkies.gamestates;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import org.junit.Before;
import org.junit.Test;

import com.funkydonkies.w4v3.App;
import com.jme3.app.state.AppStateManager;

public class CameraStateTest {
	private CameraState cameraState;
	private AppStateManager sManager;
	private App app;
	
	@Before
	public void setup() {
		cameraState = new CameraState();
		sManager = mock(AppStateManager.class);
		app = mock(App.class);
	}
	
	@Test
	public void InitTest() {
		assertFalse(cameraState.isInitialized());
		cameraState.initialize(sManager, app);
		assertTrue(cameraState.isInitialized());
	}

	@Test
	public void setEnabledTest() {
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice[] gs = ge.getScreenDevices();
		if (gs != null) {
			cameraState.initialize(sManager, app);
			assertFalse(cameraState.cameraOpened());
			assertFalse(cameraState.isEnabled());
			
			cameraState.toggleEnabled();
			assertTrue(cameraState.isEnabled());
			assertTrue(cameraState.isEnabled());
			
			cameraState.toggleEnabled();
			assertFalse(cameraState.cameraOpened());
			assertFalse(cameraState.isEnabled());
		}
	}
}
