package com.funkydonkies.gamestates;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.stub;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import com.funkydonkies.controllers.CurveState;
import com.funkydonkies.exceptions.BadDynamicTypeException;
import com.funkydonkies.w4v3.App;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.input.FlyByCamera;
import com.jme3.input.InputManager;
import com.jme3.input.controls.ActionListener;

/**
 * Tests GameInputState class for correct method calling and valid interactions.
 * 
 * @author Danilo
 *
 */
public class GameInputStateTest {

	private GameInputState gameInputState;
	private GameInputState gameInputStateMock;
	private AppStateManager stateManager;
	private SimpleApplication sApp;
	private App app;
	private FlyByCamera camera;
	private InputManager inputManagerMock;
	private AssetManager assetManagetMock;
	private CurveState splineController;
	private CameraState cameraState;
	
	/**
	 * Initializes variables.
	 * 
	 * @throws Exception
	 *             -
	 */
	@Before
	public void setUp() throws Exception {
		gameInputStateMock = mock(GameInputState.class);
		gameInputState = new GameInputState();
		stateManager = mock(AppStateManager.class);
		sApp = mock(SimpleApplication.class);
		app = mock(App.class);
		camera = mock(FlyByCamera.class);
		inputManagerMock = mock(InputManager.class);
		assetManagetMock = mock(AssetManager.class);
		splineController = mock(CurveState.class);
		cameraState = mock(CameraState.class);
		
		stub(app.getFlyByCamera()).toReturn(camera);
		when(app.getInputManager()).thenReturn(inputManagerMock);
		when(app.getAssetManager()).thenReturn(assetManagetMock);
		when(stateManager.getState(CurveState.class)).thenReturn(
				splineController);
		stub(stateManager.getState(CameraState.class)).toReturn(cameraState);
	}

	@Test
	public void initializeTest() {
		gameInputState.initialize(stateManager, app);
		verify(app).getInputManager();
		verify(app).getAssetManager();
		assertTrue(gameInputState.isInitialized());
	}

	/**
	 * Tests whether initKeys() is being called.
	 */
	@Test
	public void initKeysTest() {
		gameInputStateMock.initialize(stateManager, app);
		verify(gameInputStateMock).initKeys();
	}

	@Test
	public void updateTest() {
		gameInputState.initialize(stateManager, app);
		gameInputState.update(0.01f);
		gameInputState.update(0.01f);
		verify(stateManager, times(1)).getState(CurveState.class);
	}

	/**
	 * Tests exception thrown for App cast.
	 */
	@Test(expected = BadDynamicTypeException.class)
	public void appCastTest() {
		gameInputState.initialize(stateManager, sApp);
	}
	
	/* ------------------------- Action Listener Tests ----------------------------*/
	
	@Test
	public void toggleCurveUpdateTest() {
		gameInputState.initialize(stateManager, app);
		gameInputState.update(0.01f);
		ActionListener listener = gameInputState.getActionListener();
		
		listener.onAction("Toggle Curve Update", false, 0.01f);
		verify(splineController).toggleUpdateEnabled();
	}
	
	@Test
	public void enableCameraDetectionTest() {
		gameInputState.initialize(stateManager, app);
		gameInputState.update(0.01f);
		ActionListener listener = gameInputState.getActionListener();
		
		listener.onAction("Start Camera", false, 0.01f);
		verify(cameraState).toggleEnabled();
	}
	
//	@Test
//	public void toggleCameraTest() {
//		gameInputState.initialize(stateManager, app);
//		gameInputState.update(0.01f);
//		ActionListener listener = gameInputState.getActionListener();
//		doReturn(false).when(cameraState).cameraOpened();
////		stub(CameraState.cameraOpened()).toReturn(false);
//		listener.onAction("Toggle Camera", false, 0.01f);
//		verifyNoMoreInteractions(splineController);
//	}
//
//	@Test
//	public void toggleCameraTest2() {
//		gameInputState.initialize(stateManager, app);
//		gameInputState.update(0.01f);
//		ActionListener listener = gameInputState.getActionListener();
//		
//		when(cameraState.cameraOpened()).thenReturn(new Boolean(true));
//		listener.onAction("Toggle Camera", false, 0.01f);
//		verify(splineController).toggleCameraEnabled();
//		verify(splineController).setUpdateEnabled(true);
//	}
	
	
}
