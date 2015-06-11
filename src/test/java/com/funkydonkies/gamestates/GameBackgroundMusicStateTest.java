//package com.funkydonkies.gamestates;
//
//import static org.junit.Assert.assertTrue;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.when;
//
//import java.io.ByteArrayOutputStream;
//import java.io.FileDescriptor;
//import java.io.FileOutputStream;
//import java.io.PrintStream;
//import java.nio.file.Path;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.mockito.Mockito;
//
//import com.funkydonkies.w4v3.App;
//import com.jme3.app.state.AppStateManager;
//import com.jme3.asset.AssetManager;
//import com.jme3.input.InputManager;
//import com.jme3.scene.Node;
//
///**
// * tests for GameBackgroundMusicState.
// * @author Olivier Dikken
// *
// */
//public class GameBackgroundMusicStateTest {
//
//	private GameBackgroundMusicState gameBackgroundMusicStateMock;
//	private AppStateManager stateManager;
//	private App app;
//	private InputManager inputManagerMock;
//	private AssetManager assetManagetMock;
//	private Node rootNode;
//	private final float timeInterval = 0.01f;
//
//	/**
//	 * Initializes variables.
//	 * 
//	 * @throws Exception
//	 *             -
//	 */
//	@Before
//	public void setUp() throws Exception {
//		gameBackgroundMusicStateMock = mock(GameBackgroundMusicState.class);
//		stateManager = mock(AppStateManager.class);
//		app = mock(App.class);
//		inputManagerMock = mock(InputManager.class);
//		assetManagetMock = mock(AssetManager.class);
//		rootNode = mock(Node.class);
//		when(app.getRootNode()).thenReturn(rootNode);
//		when(app.getInputManager()).thenReturn(inputManagerMock);
//		when(app.getAssetManager()).thenReturn(assetManagetMock);
//		Mockito.doNothing().when(gameBackgroundMusicStateMock)
//				.initBackgroundMusic(Mockito.any(Path.class));
//	}
//
//	/**
//	 * Check if correct method is called.
//	 */
//	@Test
//	public void testUpdate() {
//		gameBackgroundMusicStateMock.update(timeInterval);
//		Mockito.verify(gameBackgroundMusicStateMock).updateBgMusic();
//	}
//
//	/**
//	 * Check if correct method is called.
//	 */
//	@Test
//	public void testInitializeAppStateManagerApplication() {
//		gameBackgroundMusicStateMock.initialize(stateManager, app);
//		Mockito.verify(gameBackgroundMusicStateMock).initBackgroundMusic(Mockito.any(Path.class));
//	} 
//}
