package com.funkydonkies.factories;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;

import com.funkydonkies.controllers.WarningLineControl;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppStateManager;
import com.jme3.material.Material;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
/**
 * This class tests the fish factory class.
 */
public class SpearFactoryTest {
	private SpearFactory mockFactory;
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
		mockFactory = spy(SpearFactory.class);
	    doReturn(mock(Material.class)).when(mockFactory).getSpearMaterial();
	    doReturn(mock(Material.class)).when(mockFactory).getLineMaterial();

	}

	/**
	 * tests if the fishcontrol is enabled.
	 */
	@Test
	public void testMakeObject() {
		final Spatial spear = mockFactory.makeObject(assManager, app);
		assertTrue(((Node) spear).getChildren().size() == 2);
	}
	
	/**
	 * This method tests if every method is called.
	 */
	@Test
	public void methodsCalled() {
		@SuppressWarnings("unused")
		final Spatial spear = mockFactory.makeObject(assManager, app);
		verify(mockFactory).makeWarningLine(any(float.class));
		verify(mockFactory).makeSpear(any(float.class), any(float.class));
	}

}
