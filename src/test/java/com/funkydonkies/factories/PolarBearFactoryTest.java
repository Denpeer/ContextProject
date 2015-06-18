package com.funkydonkies.factories;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;

import com.funkydonkies.controllers.FishControl;
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
public class PolarBearFactoryTest {
	private PolarBearFactory mockFactory;
	private PolarBearFactory fac;
	private AppStateManager assManager;
	private SimpleApplication app;
	private Geometry geom;
	private AssetManager assetManager;
	
	/**
	 * Do this before executing tests.
	 * @throws Exception
	 */
	@Before
	public void setUp() {
		geom = mock(Geometry.class);
		assetManager = mock(AssetManager.class);
		app = mock(SimpleApplication.class);
		fac = new PolarBearFactory();
		assManager = new AppStateManager(app);
		mockFactory = spy(PolarBearFactory.class);
//	    doReturn(mock(Material.class)).when(mockFactory).getPolarBearMaterial();
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
	 * This method tests both branched of the floatSide method.
	 */
	@Test
	public void testFloatSide() {
		final int floatLeft = -200;
		final int floatRight = 500;
		assertTrue(fac.getFloatSide(0) == floatLeft);
		assertTrue(fac.getFloatSide(1) == floatRight);
	}
	/**
	 * tests the makeGeometry method.
	 */
	@Test
	public void testMakeGeometry() {
		fac.makeObject(assManager, app);

		verify(geom).setName(PolarBearFactory.POLAR_BEAR_NAME);
	}
}
