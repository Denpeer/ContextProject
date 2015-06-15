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
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppStateManager;
import com.jme3.material.Material;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
/**
 * This class tests the fish factory class.
 */
public class KillerWhaleFactoryTest {
	private KillerWhaleFactory mockFactory;
	private KillerWhaleFactory fac;
	private AppStateManager assManager;
	private SimpleApplication app;
	private Geometry geom;

	/**
	 * Do this before executing tests.
	 * @throws Exception
	 */
	@Before
	public void setUp() {
		geom = mock(Geometry.class);
		app = mock(SimpleApplication.class);
		assManager = new AppStateManager(app);
		mockFactory = spy(KillerWhaleFactory.class);
		fac = new KillerWhaleFactory();
	    doReturn(mock(Material.class)).when(mockFactory).getKillerWhaleMaterial();
	    doReturn(mock(Material.class)).when(mockFactory).getLineMaterial();
	    doReturn(geom).when(mockFactory).makeGeometry(any(Box.class));

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
	 * tests the makeGeometry method.
	 */
	@Test
	public void testMakeGeometry() {
		assertFalse(fac.makeGeometry(new Box(1, 1, 1)) == null);
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
