package com.funkydonkies.sounds;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Random;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.funkydonkies.w4v3.App;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.scene.Node;

/**
 * test the SoundState class.
 * @author Olivier Dikken
 *
 */
public class SoundStateTest {

	private SoundState soundState;
	private SoundState soundStateMock;
	private AppStateManager stateManager;
	private SimpleApplication sApp;
	private App app;
	private AssetManager assetManagetMock;
	private Node rootNodeMock;
	private Sound soundMock;

	private static final float UPDATE_TIME = 0.01f;

	/**
	 * Initializes variables.
	 * 
	 * @throws Exception
	 *             -
	 */
	@Before
	public void setUp() throws Exception {
		soundStateMock = mock(SoundState.class);
		soundState = new SoundState();
		stateManager = mock(AppStateManager.class);
		sApp = mock(SimpleApplication.class);
		app = mock(App.class);
		assetManagetMock = mock(AssetManager.class);
		rootNodeMock = new Node();
		soundMock = mock(PenguinSpawnSound.class); // could be any sound

		// stub(app.getFlyByCamera()).toReturn(camera);
		when(app.getAssetManager()).thenReturn(assetManagetMock);
		when(app.getRootNode()).thenReturn(rootNodeMock);
	}

	/**
	 * test if the update method can play no sound - a sound - no sound without
	 * fail.
	 */
	@Test
	public void testUpdate() {
		soundState.initialize(stateManager, app);
		soundState.update(UPDATE_TIME);
		soundState.queueSound(soundMock);
		soundState.update(UPDATE_TIME);
		Mockito.verify(soundMock).play(Mockito.any(SoundPlayer.class));
		soundState.update(UPDATE_TIME);
	}

	/**
	 * check if code runs without errors and the SoundPlayer constructor is
	 * called.
	 */
	@Test
	public void testInitializeAppStateManagerApplication() {
		soundState.initialize(stateManager, app);
		// called once by soundState.initialize() and once by SoundPlayer's
		// constructor
		Mockito.verify(app, Mockito.times(2)).getAssetManager();
		assertTrue(soundState.isInitialized());
	}

	/**
	 * test whether sounds that are queued are played during the next update
	 * call.
	 */
	@Test
	public void testQueueSound() {
		soundState.initialize(stateManager, app);
		soundState.update(UPDATE_TIME);
		Mockito.verify(soundMock, Mockito.times(0)).play(
				Mockito.any(SoundPlayer.class));
		int count;
		final int baseCount = 10;
		final int randomRange = 30;
		final Random generator = new Random();
		for (count = 0; count < generator.nextInt(randomRange) + baseCount; count++) {
			soundState.queueSound(soundMock);
		}
		soundState.update(UPDATE_TIME);
		Mockito.verify(soundMock, Mockito.times(count)).play(
				Mockito.any(SoundPlayer.class));
		soundState.update(UPDATE_TIME);
		// no additional calls on .play()
		Mockito.verify(soundMock, Mockito.times(count)).play(
				Mockito.any(SoundPlayer.class));
	}

}
