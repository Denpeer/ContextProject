package com.funkydonkies.factories;

import static org.junit.Assert.assertFalse;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;

import com.funkydonkies.controllers.SquidControl;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppStateManager;
import com.jme3.material.Material;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
/**
 * This class tests the Squid factory class.
 */
public class SquidFactoryTest {
	private SquidFactory mockFactory;
	private SquidFactory fac;
	private Geometry geom;
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
		geom = mock(Geometry.class);
		mockFactory = spy(SquidFactory.class);
		fac = new SquidFactory();
	    doReturn(mock(Material.class)).when(mockFactory).getSquidMaterial();
	    doReturn(geom).when(mockFactory).makeGeometry(any(Box.class));
	}
	
	/**
	 * tests if the control is attached.
	 */
	@Test
	public void testControlAttached() {
		mockFactory.makeObject(assManager, app);
		verify(geom).addControl(any(SquidControl.class));
	}
	
	/**
	 * This method tests if every method is called.
	 */
	@Test
	public void methodsCalled() {
		@SuppressWarnings("unused")
		final Geometry Squid = mockFactory.makeObject(assManager, app);
		verify(mockFactory).makeSquid();
	}
	
	/**
	 * tests the makeGeometry method.
	 */
	@Test
	public void testMakeGeometry() {
		assertFalse(fac.makeGeometry(new Box(1, 1, 1)) == null);
	}

}
