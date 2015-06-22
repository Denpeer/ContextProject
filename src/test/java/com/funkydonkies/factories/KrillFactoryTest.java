package com.funkydonkies.factories;

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
import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
/**
 * This class tests the Krill factory class.
 */
public class KrillFactoryTest {
	private KrillFactory mockFactory;
	private KrillFactory fac;
	private Geometry geom;
	private AppStateManager assManager;
	private SimpleApplication app;
	private AssetManager assetManager;

	/**
	 * Do this before executing tests.
	 * @throws Exception
	 */
	@Before
	public void setUp() {
		app = mock(SimpleApplication.class);
		assManager = new AppStateManager(app);
		geom = mock(Geometry.class);
		mockFactory = spy(KrillFactory.class);
		assetManager = mock(AssetManager.class);
		fac = new KrillFactory();
	    doReturn(mock(Material.class)).when(mockFactory).getkrillMaterial();
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
		verify(geom).addControl(any(FishControl.class));
	}
	
	/**
	 * This method tests if every method is called.
	 */
	@Test
	public void methodsCalled() {
		@SuppressWarnings("unused")
		final Spatial krill = mockFactory.makeObject(assManager, app);
		verify(mockFactory).makeKrill();
	}
	
	/**
	 * tests the makeGeometry method.
	 */
	@Test
	public void testMakeGeometry() {
		KrillFactory facSpy = spy(fac);
	    doReturn(mock(Material.class)).when(facSpy).getkrillMaterial();
		facSpy.makeObject(assManager, app);
		
//		facSpy.makeSpatial();
		verify(geom).setName(KrillFactory.KRILL_NAME);
	}

}
