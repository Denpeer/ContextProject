package com.mycompany.mavenproject1;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.stub;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;

import com.funkydonkies.w4v3.App;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppStateManager;
import com.jme3.input.FlyByCamera;

/**
 * Tests GameInputState class for correct method calling and valid interactions.
 * 
 * @author Danilo
 *
 */
public class GameInputStateTest {
	
	private GameInputState gameInputState;
	private AppStateManager stateManager;
	private SimpleApplication sApp;
	private App app;
	private FlyByCamera camera;
	
	/**
	 * Initializes variables.
	 * 
	 * @throws Exception -
	 */
	@Before
	public void setUp() throws Exception {
		gameInputState = mock(GameInputState.class);
		stateManager = mock(AppStateManager.class);
		sApp = mock(SimpleApplication.class);
		app = mock(App.class);
		camera = mock(FlyByCamera.class);
		stub(app.getFlyByCamera()).toReturn(camera);
		
	}

	/**
	 * Tests whether initKeys() is being called.
	 */
	@Test
	public void initKeysTest() {
		gameInputState.initialize(stateManager, app);
		verify(gameInputState).initKeys();
	}
	
	/**
	 * Tests exception thrown for App cast.
	 */
	@Test(expected = BadDynamicTypeException.class)
	public void appCastTest() {
		gameInputState.initialize(stateManager, sApp);
	}
	
//	@Test
//	public void setEnabledTest() {
//		gameInputState.setEnabled(true);
//		verify(gameInputState).
//	}

}
