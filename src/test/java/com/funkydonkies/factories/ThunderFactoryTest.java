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
public class ThunderFactoryTest {
	private ThunderFactory mockFactory;
	private ThunderFactory fac;
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
		mockFactory = spy(ThunderFactory.class);
		fac = new ThunderFactory();
	    doReturn(mock(Material.class)).when(mockFactory).getThunderMaterial();
	    doReturn(mock(Material.class)).when(mockFactory).getWarningLineMaterial();
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
		mockFactory.makeObject(assManager, app);
		verify(mockFactory).makeWarningLine(any(float.class));
	}

}
