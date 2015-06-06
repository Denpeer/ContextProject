package com.funkydonkies.gamestates;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import org.junit.Before;
import org.junit.Test;

import com.funkydonkies.appstates.CameraState;
import com.funkydonkies.camdetect.MyFrame;
import com.funkydonkies.core.App;
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
			cameraState.initialize(sManager, app);
			assertFalse(cameraState.cameraOpened());
			assertFalse(cameraState.isEnabled());
			
			// Opening the frame will break the maven build, so this is currently disabled
//			cameraState.toggleEnabled();
//			assertTrue(cameraState.isEnabled());
//			assertTrue(cameraState.isEnabled());
//			
//			cameraState.toggleEnabled();
//			assertFalse(cameraState.cameraOpened());
//			assertFalse(cameraState.isEnabled());
	}
}
