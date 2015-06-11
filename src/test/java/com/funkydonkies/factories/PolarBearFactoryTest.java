package com.funkydonkies.factories;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

import org.junit.Before;
import org.junit.Test;

import com.funkydonkies.controllers.PolarBearControl;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppStateManager;
import com.jme3.material.Material;
import com.jme3.scene.Spatial;
/**
 * This class tests the fish factory class.
 */
public class PolarBearFactoryTest {
	private PolarBearFactory mockFactory;
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
		mockFactory = spy(PolarBearFactory.class);
	    doReturn(mock(Material.class)).when(mockFactory).getPolarBearMaterial();

	}

	/**
	 * tests if the fishcontrol is enabled.
	 */
	@Test
	public void testMakeObject() {
		final Spatial pBear = mockFactory.makeObject(assManager, app);
		assertTrue(((PolarBearControl) (pBear).getControl(0)).isEnabled());
	}
	
	/**
	 * tests if the control is attached.
	 */
	@Test
	public void testControlAttached() {
		final Spatial pBear = mockFactory.makeObject(assManager, app);
		assertTrue(((PolarBearControl) (pBear).getControl(0)) != null);
	}

}
