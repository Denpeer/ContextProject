package com.funkydonkies.factories;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.funkydonkies.controllers.SquidControl;
import com.funkydonkies.gamestates.PlayState;
import com.funkydonkies.sounds.SoundState;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetKey;
import com.jme3.asset.AssetManager;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.material.MatParam;
import com.jme3.material.MaterialDef;
import com.jme3.scene.Geometry;

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
		psMock = Mockito.mock(PlayState.class);
		physMock = Mockito.mock(PhysicsSpace.class);
		Mockito.when(saMock.getAssetManager()).thenReturn(amMock);
		Mockito.when(amMock.loadAsset(Mockito.any(AssetKey.class))).thenReturn(mdMock);
		Mockito.when(mdMock.getMaterialParam(Mockito.any(String.class))).thenReturn(mpMock);
		Mockito.when(asmMock.getState(PlayState.class)).thenReturn(psMock);
		Mockito.when(psMock.getPhysicsSpace()).thenReturn(physMock);
		Mockito.doNothing().when(physMock).add(Mockito.any(SquidControl.class));
		Mockito.doReturn(Mockito.mock(SoundState.class)).when(asmMock).getState(SoundState.class);
	}

	/**
	 * test make object.
	 */
	@Test
	public void testMakeObject() {
		assertTrue(sf.makeObject(asmMock, saMock) instanceof Geometry);
		assertTrue(sf.makeObject(asmMock, saMock).getControl(0) instanceof SquidControl);
	}

	/**
	 * test make squid.
	 */
	@Test
	public void testMakeSquid() {
		Mockito.reset(saMock);
		Mockito.when(saMock.getAssetManager()).thenReturn(amMock);
		sf.makeObject(asmMock, saMock);
		assertTrue(sf.makeSquid() instanceof Geometry);
		assertTrue(sf.makeSquid().getControl(0) instanceof SquidControl);
		sfMock.makeSquid();
		Mockito.verify(saMock, Mockito.times(numThree)).getAssetManager();
	}

	/**
	 * test get squid material.
	 */
	@Test
	public void testGetSquidMaterial() {
//		final Object mat = sf.getSquidMaterial();
//		assertTrue(mat instanceof Material);
	}

}
