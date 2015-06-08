package com.funkydonkies.gamestates;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.stub;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.funkydonkies.w4v3.App;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.input.FlyByCamera;
import com.jme3.input.InputManager;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

public class SceneStateTest {
	
	private AppStateManager stateManager;
	private SimpleApplication sApp;
	private App app;
	private InputManager inputManagerMock;
	private AssetManager assetManagerMock;
	private Spatial gameLevelMock;
	private Node rootNodeMock;
	
	private SceneState sceneState;
	
	/**
	 * Initializes variables.
	 * 
	 * @throws Exception
	 *             -
	 */
	@Before
	public void setUp() throws Exception {
		stateManager = mock(AppStateManager.class);
		sApp = mock(SimpleApplication.class);
		app = mock(App.class);
		inputManagerMock = mock(InputManager.class);
		assetManagerMock = mock(AssetManager.class);
		gameLevelMock = mock(Spatial.class);
		rootNodeMock = mock(Node.class);
		sceneState = new SceneState();
		
		when(app.getInputManager()).thenReturn(inputManagerMock);
		when(app.getAssetManager()).thenReturn(assetManagerMock);
		when(assetManagerMock.loadModel(Mockito.any(String.class))).thenReturn(gameLevelMock);
		when(app.getRootNode()).thenReturn(rootNodeMock);
	}

	@Test
	public void testInitializeAppStateManagerApplication() {
		sceneState.initialize(stateManager, app);
		assertTrue(sceneState.isInitialized());
	}

	@Test
	public void testInitScene() {
		sceneState.initialize(stateManager, app);
		sceneState.initScene();
		Mockito.verify(rootNodeMock, Mockito.times(2)).attachChild(Mockito.any(Spatial.class));
	}

	@Test
	public void testInitSceneIntVector3f() {
		sceneState.initialize(stateManager, app);
		final Vector3f vec3f = new Vector3f(1, 2, 1);
		final int scale = 2;
		sceneState.initScene(scale, vec3f);
		Mockito.verify(gameLevelMock).setLocalTranslation(vec3f);
		Mockito.verify(gameLevelMock).setLocalScale(scale);
		Mockito.verify(rootNodeMock, Mockito.times(2)).attachChild(Mockito.any(Spatial.class));
	}

}
