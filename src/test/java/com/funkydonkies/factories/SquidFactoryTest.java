package com.funkydonkies.factories;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import com.funkydonkies.controllers.SquidControl;
import com.funkydonkies.gamestates.PlayState;
import com.funkydonkies.sounds.Sound;
import com.funkydonkies.sounds.SoundState;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetKey;
import com.jme3.asset.AssetManager;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.material.MatParam;
import com.jme3.material.MaterialDef;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;

/**
 * Test the squidfactory.
 * @author Olivier Dikken
 *
 */
public class SquidFactoryTest {

	private AppStateManager asmMock;
	private SimpleApplication saMock;
	private AssetManager amMock;
	private SquidFactory sf;
	private SquidFactory sfMock;
	private MaterialDef mdMock;
	private MatParam mpMock;
	private PlayState psMock;
	private PhysicsSpace physMock;
	private SoundState soundState;
	private Spatial spatial;
	
	private final int numThree = 3;

	/**
	 * setup for tests. Mock all that is needed.
	 * @throws Exception exception
	 */
	@SuppressWarnings("unchecked")
	@Before
	public void setUp() throws Exception {
		asmMock = Mockito.mock(AppStateManager.class);
		saMock = Mockito.mock(SimpleApplication.class);
		sf = new SquidFactory();
		sfMock = Mockito.mock(SquidFactory.class);
		amMock = Mockito.mock(AssetManager.class);
		mdMock = Mockito.mock(MaterialDef.class);
		mpMock = Mockito.mock(MatParam.class);
		spatial = Mockito.mock(Spatial.class);
		psMock = Mockito.mock(PlayState.class);
		physMock = Mockito.mock(PhysicsSpace.class);
		soundState = Mockito.mock(SoundState.class);
		Mockito.when(saMock.getAssetManager()).thenReturn(amMock);
		Mockito.when(amMock.loadAsset(Mockito.any(AssetKey.class))).thenReturn(mdMock);
		Mockito.when(mdMock.getMaterialParam(Mockito.any(String.class))).thenReturn(mpMock);
		Mockito.when(asmMock.getState(PlayState.class)).thenReturn(psMock);
		Mockito.when(psMock.getPhysicsSpace()).thenReturn(physMock);
		Mockito.doNothing().when(physMock).add(Mockito.any(SquidControl.class));
		Mockito.doReturn(soundState).when(asmMock).getState(SoundState.class);
		Mockito.doReturn(spatial).when(amMock).loadModel(Mockito.any(String.class));;
		Mockito.doNothing().when(soundState).queueSound(Mockito.any(Sound.class));
	}

	/**
	 * test make object.
	 */
	@Test
	public void testMakeObject() {
		assertTrue(sf.makeObject(asmMock, saMock) instanceof Spatial);
		Mockito.verify(spatial).setName(SquidFactory.SQUID_NAME);
		Mockito.verify(spatial).addControl(Mockito.any(SquidControl.class));
	}

	/**
	 * test make squid.
	 */
	@Test
	public void testMakeSquid() {
		Mockito.reset(saMock);
		Mockito.when(saMock.getAssetManager()).thenReturn(amMock);
		sf.makeObject(asmMock, saMock);
		assertTrue(sf.makeSquid() instanceof Spatial);
		sfMock.makeSquid();
		Mockito.verify(saMock, Mockito.times(4)).getAssetManager();
	}

	/**
	 * test get squid material.
	 */
	@Test
	public void testGetSquidMaterial() {
		//null pointer at app.getAssetManager => difficult to mock & test
	}
}
