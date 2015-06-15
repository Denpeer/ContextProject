package com.funkydonkies.factories;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;

import com.funkydonkies.controllers.KrillControl;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.shape.Box;
/**
 * This class tests the Krill factory class.
 */
public class KrillFactoryTest {
	private KrillFactory mockFactory;
	private AppStateManager assManager;
	private SimpleApplication app;

	/**
	 * Do this before executing tests.
	 * @throws Exception
	 */
	@Before
	public void setUp() {
		app = mock(SimpleApplication.class);
		assManager = new AppStateManager(app);
		mockFactory = spy(KrillFactory.class);
	    doReturn(mock(Material.class)).when(mockFactory).getkrillMaterial();

	}

	/**
	 * tests if the Krillcontrol is enabled.
	 */
	@Test
	public void testMakeObject() {
		final Geometry Krill = mockFactory.makeObject(assManager, app);
		assertTrue(((KrillControl) (Krill).getControl(0)).isEnabled());
	}
	
	/**
	 * tests if the control is attached.
	 */
	@Test
	public void testControlAttached() {
		final Geometry Krill = mockFactory.makeObject(assManager, app);
		assertTrue(((KrillControl) (Krill).getControl(0)) != null);
	}
	
	/**
	 * This method tests if every method is called.
	 */
	@Test
	public void methodsCalled() {
		@SuppressWarnings("unused")
		final Geometry Krill = mockFactory.makeObject(assManager, app);
		verify(mockFactory).makeKrill();
	}

}
