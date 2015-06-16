package com.funkydonkies.factories;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.funkydonkies.controllers.SquidControl;
import com.funkydonkies.controllers.ThunderControl;
import com.funkydonkies.controllers.ThunderWarningLineControl;
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
 * Test the thunderfactory.
 * @author Olivier Dikken
 *
 */
public class ThunderFactoryTest {
	
	private AppStateManager asmMock;
	private SimpleApplication saMock;
	private AssetManager amMock;
	private ThunderFactory tf;
	private MaterialDef mdMock;
	private MatParam mpMock;
	private PlayState psMock;
	private PhysicsSpace physMock;
	private Node rootNodeMock;
	private Material matMock;
	private RenderState renderStateMock;
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
		tf = new ThunderFactory();
		Mockito.mock(ThunderFactory.class);
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
	 * test makeObject().
	 */
	@Test
	public void testMakeObject() {
		assertTrue(tf.makeObject(asmMock, saMock) instanceof Spatial);
	}
	
	/**
	 * test makeThunder().
	 */
	@Test
	public void testMakeThunder() {
		tf.makeObject(asmMock, saMock);
		assertTrue(tf.makeThunder(01.0f).getControl(0) instanceof ThunderControl);
	}
	
	/**
	 * test makeWarningLine().
	 */
	@Test
	public void testMakeWarningLine() {
		tf.makeObject(asmMock, saMock);
		assertTrue(tf.makeWarningLine(01.0f).getControl(0) instanceof ThunderWarningLineControl);
	}

	/**
	 * test get thunder material.
	 */
	@Test
	public void testGetThunderMaterial() {
		tf.makeObject(asmMock, saMock);
		final Object mat = tf.getThunderMaterial();
		assertTrue(mat instanceof Material);
		Mockito.verify(saMock, Mockito.times(numThree)).getRootNode();
	}
	
	/**
	 * test get squid material.
	 */
	@Test
	public void testGetWarningLineMaterial() {
		tf.makeObject(asmMock, saMock);
		final Object mat = tf.getWarningLineMaterial();
		assertTrue(mat instanceof Material);
		Mockito.verify(saMock, Mockito.times(numThree)).getRootNode();
	}
}
