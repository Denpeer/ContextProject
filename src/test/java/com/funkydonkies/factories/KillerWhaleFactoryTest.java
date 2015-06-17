package com.funkydonkies.factories;

import static org.junit.Assert.assertFalse;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;

import com.funkydonkies.controllers.FishControl;
import com.funkydonkies.controllers.KillerWhaleControl;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppStateManager;
import com.jme3.material.Material;
import com.jme3.scene.Spatial;

/**
 * This class tests the fish factory class.
 */
public class KillerWhaleFactoryTest {
	private KillerWhaleFactory mockFactory;
	private KillerWhaleFactory fac;
	private AppStateManager assManager;
	private SimpleApplication app;
	private Spatial geom;

	/**
	 * Do this before executing tests.
	 * 
	 * @throws Exception
	 */
	@Before
	public void setUp() {
		geom = mock(Spatial.class);
		app = mock(SimpleApplication.class);
		assManager = new AppStateManager(app);
		mockFactory = spy(KillerWhaleFactory.class);
		fac = new KillerWhaleFactory();
		doReturn(mock(Material.class)).when(mockFactory).getKillerWhaleMaterial();
		doReturn(mock(Material.class)).when(mockFactory).getLineMaterial();
		doReturn(mock(KillerWhaleControl.class)).when(mockFactory).makeWhaleControl(
				any(Float.class), any(Float.class), any(Spatial.class));
		doReturn(geom).when(mockFactory).makeSpatial();

	}

	/**
	 * tests if the fishcontrol is enabled.
	 */
	@Test
	public void testControlAttached() {
		mockFactory.makeObject(assManager, app);
		verify(geom).addControl(any(FishControl.class));
	}

	/**
	 * This method tests if every method is called.
	 */
	@Test
	public void methodsCalled() {
		@SuppressWarnings("unused")
		final Spatial spear = mockFactory.makeObject(assManager, app);
		verify(mockFactory).makeWarningLine(any(float.class));
		verify(mockFactory).makeKillerWhale(any(float.class), any(float.class));
	}

}
