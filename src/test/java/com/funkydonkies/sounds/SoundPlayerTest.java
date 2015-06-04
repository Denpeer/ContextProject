package com.funkydonkies.sounds;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.funkydonkies.exceptions.BadDynamicTypeException;
import com.funkydonkies.w4v3.App;
import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.scene.Node;

public class SoundPlayerTest {
	
	private AppStateManager stateManager;
	private SimpleApplication sApp;
	private App appMock;
	private App app;
	private AssetManager assetManagetMock;
	private SoundPlayer soundPlayer;
	private SoundPlayer soundPlayerMock;
	private Node rootNodeMock;
	private SoundState soundState;
	
	@Before
	public void setUp() {
		stateManager = mock(AppStateManager.class);
		sApp = mock(SimpleApplication.class);
		appMock = mock(App.class);
		assetManagetMock = mock(AssetManager.class);
		soundPlayerMock = mock(SoundPlayer.class);
		rootNodeMock = mock(Node.class);
		this.app = new App();
		when(appMock.getAssetManager()).thenReturn(assetManagetMock);
		when(appMock.getRootNode()).thenReturn(rootNodeMock);
	}
	
	@Test
	public void testSoundPlayer() {
		soundPlayer = new SoundPlayer(appMock);
		Mockito.verify(appMock, Mockito.times(1)).getAssetManager();
	}
}
