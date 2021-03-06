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
import com.funkydonkies.controllers.PenguinControl;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
/**
 * This class tests the penguin factory class.
 */
public class PenguinFactoryTest {
	private PenguinFactory mockFactory;
	private PenguinFactory fac;
	private AppStateManager assManager;
	private SimpleApplication app;
	private Node node;
	private AssetManager assetManager;

	/**
	 * Do this before executing tests.
	 * @throws Exception
	 */
	@Before
	public void setUp() {
		fac = new PenguinFactory();
		node = mock(Node.class);
		app = mock(SimpleApplication.class);
		assetManager = mock(AssetManager.class);
		assManager = new AppStateManager(app);
		mockFactory = spy(PenguinFactory.class);
	    doReturn(node).when(mockFactory).makePengNode();
	    
	    doReturn(assetManager).when(app).getAssetManager();
	    doReturn(node).when(assetManager).loadModel(any(String.class));
	}

	/**
	 * tests if the control is attached.
	 */
	@Test
	public void testControlAttached() {
		mockFactory.makeObject(assManager, app);
		verify(node).addControl(any(PenguinControl.class));
	}
	
	/**
	 * tests the makeGeometry method.
	 */
	@Test
	public void testMakeNode() {
		PenguinFactory facSpy = spy(fac);
		doReturn(node).when(facSpy).makePengNode();
		facSpy.makeNode();
		verify(node).addControl(any(PenguinControl.class));
	}
	


}

