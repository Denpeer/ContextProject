//package com.funkydonkies.factories;
//
//import static org.junit.Assert.assertTrue;
//import static org.mockito.Matchers.any;
//import static org.mockito.Mockito.doReturn;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.spy;
//import static org.mockito.Mockito.verify;
//
//import org.junit.Before;
//import org.junit.Test;
//
//import com.funkydonkies.controllers.FishControl;
//import com.funkydonkies.controllers.PenguinControl;
//import com.jme3.app.SimpleApplication;
//import com.jme3.app.state.AppStateManager;
//import com.jme3.material.Material;
//import com.jme3.scene.Spatial;
///**
// * This class tests the fish factory class.
// */
//public class PenguinFactoryTest {
//	private PenguinFactory mockFactory;
//	private AppStateManager assManager;
//	private SimpleApplication app;
//
//	/**
//	 * Do this before executing tests.
//	 * @throws Exception
//	 */
//	@Before
//	public void setUp() {
//		app = mock(SimpleApplication.class);
//		assManager = new AppStateManager(app);
//		mockFactory = spy(PenguinFactory.class);
//	    doReturn(mock(Material.class)).when(mockFactory).getPenguinMaterial();
//
//	}
//
//	/**
//	 * tests if the control is attached.
//	 */
//	@Test
//	public void testControlAttached() {
//		mockFactory.makeObject(assManager, app);
//		verify(geom).addControl(any(FishControl.class));
//	}
//	
//	/**
//	 * tests if the control is attached.
//	 */
//	@Test
//	public void testControlAttached() {
//		final Spatial penguin = mockFactory.makeObject(assManager, app);
//		assertTrue(((PenguinControl) (penguin).getControl(0)) != null);
//	}
//	
//
//
//}
//
