package com.funkydonkies.factories;

import static org.junit.Assert.assertFalse;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;

import com.funkydonkies.controllers.PolarBearControl;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
/**
 * This class tests the fish factory class.
 */
public class SpearFactoryTest {
	private Geometry geom;
	private SpearFactory fac;
	private SpearFactory mockFactory;
	private AppStateManager assManager;
	private SimpleApplication app;
	private AssetManager assetManager;

	/**
	 * Do this before executing tests.
	 * @throws Exception
	 */
	@Before
	public void setUp() {
		geom = mock(Geometry.class);
		app = mock(SimpleApplication.class);
		assetManager = mock(AssetManager.class);
		fac = new SpearFactory();
		assManager = new AppStateManager(app);
		mockFactory = spy(SpearFactory.class);
//	    doReturn(mock(Material.class)).when(mockFactory).getSpearMaterial();
	    doReturn(mock(Material.class)).when(mockFactory).getLineMaterial();
	    doReturn(geom).when(mockFactory).makeSpatial();
	    doReturn(assetManager).when(app).getAssetManager();
	    doReturn(geom).when(assetManager).loadModel(any(String.class));
	}

	/**
	 * tests if the control is attached.
	 */
	@Test
	public void testControlAttached() {
		mockFactory.makeObject(assManager, app);
		verify(geom).addControl(any(PolarBearControl.class));
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
