package com.funkydonkies.factories;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.funkydonkies.controllers.SquidControl;
import com.funkydonkies.controllers.YetiControl;
import com.funkydonkies.gamestates.PlayState;
import com.funkydonkies.sounds.SoundState;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetKey;
import com.jme3.asset.AssetManager;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.material.MatParam;
import com.jme3.material.Material;
import com.jme3.material.MaterialDef;
import com.jme3.material.RenderState;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

/**
 * Testing the yetifactory class.
 * @author Olivier Dikken
 *
 */
public class YetiFactoryTest {

	private AppStateManager asmMock;
	private SimpleApplication saMock;
	private AssetManager amMock;
	private YetiFactory yf;
	private MaterialDef mdMock;
	private MatParam mpMock;
	private PlayState psMock;
	private PhysicsSpace physMock;
	private Node rootNodeMock;
	private Material matMock;
	private RenderState renderStateMock;

	/**
	 * setup for tests. Mock all that is needed.
	 * @throws Exception exception
	 */
	@SuppressWarnings("unchecked")
	@Before
	public void setUp() throws Exception {
		asmMock = Mockito.mock(AppStateManager.class);
		saMock = Mockito.mock(SimpleApplication.class);
		yf = new YetiFactory();
		Mockito.mock(YetiFactory.class);
		amMock = Mockito.mock(AssetManager.class);
		mdMock = Mockito.mock(MaterialDef.class);
		mpMock = Mockito.mock(MatParam.class);
		psMock = Mockito.mock(PlayState.class);
		physMock = Mockito.mock(PhysicsSpace.class);
		rootNodeMock = Mockito.mock(Node.class);
		matMock = Mockito.mock(Material.class);
		renderStateMock = Mockito.mock(RenderState.class);
		Mockito.when(saMock.getAssetManager()).thenReturn(amMock);
		Mockito.when(amMock.loadAsset(Mockito.any(AssetKey.class))).thenReturn(mdMock);
		Mockito.when(mdMock.getMaterialParam(Mockito.any(String.class))).thenReturn(mpMock);
		Mockito.when(asmMock.getState(PlayState.class)).thenReturn(psMock);
		Mockito.when(psMock.getPhysicsSpace()).thenReturn(physMock);
		Mockito.doNothing().when(physMock).add(Mockito.any(SquidControl.class));
		Mockito.when(saMock.getRootNode()).thenReturn(rootNodeMock);
		Mockito.when(rootNodeMock.getUserData(Mockito.any(String.class))).thenReturn(matMock);
		Mockito.when(matMock.clone()).thenReturn(matMock);
		Mockito.when(matMock.getAdditionalRenderState()).thenReturn(renderStateMock);
		Mockito.doReturn(Mockito.mock(SoundState.class)).when(asmMock).getState(SoundState.class);
	}

	/**
	 * testing makeObject().
	 */
	@Test
	public void testMakeObject() {
		assertTrue(yf.makeObject(asmMock, saMock) instanceof Spatial);
	}
	
	/**
	 * testing makeYetiSnowBall().
	 */
	@Test
	public void testMakeYetiSnowBall() {
		yf.makeObject(asmMock, saMock);
		assertTrue(yf.makeYetiSnowBall().getControl(0) instanceof YetiControl);
	}
	
	/**
	 * testing getyetiSnowBallMaterial().
	 */
	@Test
	public void testGetYetiSnowBallMaterial() {
		yf.makeObject(asmMock, saMock);
		final Object mat = yf.getYetiSnowBallMaterial();
		assertTrue(mat instanceof Material);
		Mockito.verify(saMock, Mockito.times(2)).getRootNode();
	}

}
