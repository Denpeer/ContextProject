package com.funkydonkies.gamestates;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.nio.file.Path;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.funkydonkies.core.App;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioNode;
import com.jme3.input.InputManager;
import com.jme3.scene.Node;

/**
 * tests for GameBackgroundMusicState.
 * @author Olivier Dikken
 *
 */
public class GameBackgroundMusicStateTest {
	
	private GameBackgroundMusicState gameBackgroundMusicState;
	private GameBackgroundMusicState gameBackgroundMusicStateSpy;
	private GameBackgroundMusicState gameBackgroundMusicStateMock;
	private AppStateManager stateManager;
	private App app;
	private InputManager inputManagerMock;
	private AssetManager assetManagetMock;
	private Node rootNode;
	private final float timeInterval = 0.01f;
	private AudioNode audioNode;

	/**
	 * Initializes variables.
	 * 
	 * @throws Exception
	 *             -
	 */
	@Before
	public void setUp() throws Exception {
		gameBackgroundMusicStateMock = mock(GameBackgroundMusicState.class);
		stateManager = mock(AppStateManager.class);
		app = mock(App.class);
		inputManagerMock = mock(InputManager.class);
		assetManagetMock = mock(AssetManager.class);
		audioNode = mock(AudioNode.class);
		rootNode = mock(Node.class);
		when(app.getInputManager()).thenReturn(inputManagerMock);
		when(app.getAssetManager()).thenReturn(assetManagetMock);
		Mockito.doNothing().when(gameBackgroundMusicStateMock)
				.initBackgroundMusic(Mockito.any(Path.class));
		gameBackgroundMusicState = new GameBackgroundMusicState();
		gameBackgroundMusicStateSpy = spy(gameBackgroundMusicState);
		doReturn(audioNode).when(gameBackgroundMusicStateSpy).createAudioNode(any(String.class));
		doReturn(rootNode).when(app).getRootNode();
	}

	/**
	 * Check if correct method is called.
	 */
	@Test
	public void testUpdate() {
		gameBackgroundMusicStateMock.update(timeInterval);
		Mockito.verify(gameBackgroundMusicStateMock).updateBgMusic();
	}

	/**
	 * Check if correct method is called.
	 */
	@Test
	public void testInitializeAppStateManagerApplication() {
		gameBackgroundMusicStateMock.initialize(stateManager, app);
		Mockito.verify(gameBackgroundMusicStateMock).initBackgroundMusic(Mockito.any(Path.class));
	} 
	
	@Test
	public void testInitBackgroundMusic() {
		gameBackgroundMusicStateSpy.initialize(stateManager, app);
 
		verify(rootNode).attachChild(audioNode);
		verify(audioNode).play();
	}
	
	@Test
	public void updateBgMusic() {
		gameBackgroundMusicStateSpy.initialize(stateManager, app);
		gameBackgroundMusicStateSpy.updateBgMusic();
		
		verify(gameBackgroundMusicStateSpy, times(2)).initBackgroundMusic(Mockito.any(Path.class));
	}
	
}
