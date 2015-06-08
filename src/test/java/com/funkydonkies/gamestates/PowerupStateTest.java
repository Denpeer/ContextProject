package com.funkydonkies.gamestates;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;

import com.funkydonkies.powerups.SuperSizePowerup;
import com.funkydonkies.w4v3.App;
import com.jme3.app.state.AppStateManager;

public class PowerupStateTest {
	private PowerupState powerupState;
	private App app;
	private AppStateManager sManager;
	
	@Before
	public void setUp() throws Exception {
		powerupState = new PowerupState();
		app = mock(App.class);
		sManager = mock(AppStateManager.class);
	}

	@Test
	public void testUpdate() {
		powerupState.update(5.01f);
		verify(powerupState).enableSuperSize();
	}

	@Test
	public void testInitializeAppStateManagerApplication() {
		powerupState.initialize(sManager, app);
		verify(sManager).attach(any(SuperSizePowerup.class));
	}

	@Test
	public void testEnableSuperSize() {
		
	}

	@Test
	public void testDisableSuperSize() {

	}

}
