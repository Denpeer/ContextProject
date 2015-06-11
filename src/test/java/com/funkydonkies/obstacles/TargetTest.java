//package com.funkydonkies.obstacles;
//
//import static org.junit.Assert.assertNotNull;
//import static org.junit.Assert.assertTrue;
//import static org.mockito.Matchers.any;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.verify;
//
//import org.junit.Before;
//import org.junit.Test;
//
//import com.funkydonkies.controllers.FishControl;
//import com.funkydonkies.gamestates.CurveState;
//import com.jme3.bullet.PhysicsSpace;
//import com.jme3.material.Material;
//import com.jme3.scene.Node;
//import com.jme3.scene.Spatial;
//
///**
// * This class takes care of testing the target class.
// * 
// * @author SDumasy
// *
// */
//public class TargetTest {
//	private static ObstacleFactory obF;
//	private static Target tar;
//	private static Material mat;
//	private static Node rootNode;
//	private static PhysicsSpace psySpace;
//	/**
//	 * Setup the test class, mock and instantiate objects.
//	 */
//	@Before
//	public void setUp() {
//		obF = new ObstacleFactory();
//		rootNode = mock(Node.class);
//		tar = obF.makeTarget(rootNode);
//		mat = mock(Material.class);
//
//		psySpace = mock(PhysicsSpace.class);
//	}
//
//	/**
//	 * Tests if the box is succesfully attached to the rootnode.
//	 */
//	@Test
//	public final void drawTest() {
//		tar.draw(mat, psySpace);
//		verify(rootNode).attachChild(any(Spatial.class));
//		verify(psySpace).add(any(FishControl.class));
//	}
//
//	/**
//	 * verifies the correct destroying of the target.
//	 */
//	@Test
//	public final void destroyTest() {
//		tar.destroy();
//		verify(rootNode).detachChild(any(Spatial.class));
//	}
//
//	/**
//	 * Tests the getlocation method.
//	 */
//	@Test
//	public final void testGetLocation() {
//		tar.draw(mat, psySpace);
//		assertTrue(tar.getLocation().x <= CurveState.POINT_DISTANCE
//				* CurveState.DEFAULT_CONTROL_POINTS_COUNT);
//		assertTrue(tar.getLocation().x >= 0);
//
//		assertTrue(tar.getLocation().y <= CurveState.POINTS_HEIGHT + 20);
//		assertTrue(tar.getLocation().y >= 0);
//
//		assertTrue(tar.getLocation().z == 1.5f);
//	}
//
//	/**
//	 * Tests the respawn method.
//	 */
//	@Test
//	public final void testRespawn() {
//		tar.draw(mat, psySpace);
//		tar.respawn();
//		assertTrue(tar.getLocation().x <= CurveState.POINT_DISTANCE
//				* CurveState.DEFAULT_CONTROL_POINTS_COUNT);
//		assertTrue(tar.getLocation().x >= 0);
//
//		assertTrue(tar.getLocation().y <= CurveState.POINTS_HEIGHT + 20);
//		assertTrue(tar.getLocation().y >= 0);
//
//		assertTrue(tar.getLocation().z == 1.5f);
//	}
//
//	/**
//	 * Test the getters of obstacle.
//	 */
//	@Test
//	public final void getterTest() {
//		final float testWidth = ObstacleFactory.TARGET_WIDTH;
//		final float testHeight = ObstacleFactory.TARGET_HEIGHT;
//		final float testDepth = ObstacleFactory.TARGET_DEPTH;
//		final float testX = 30;
//		final float testY = 0.5f;
//		final float testZ = 1;
//		assertTrue(tar.getxCoord() == testX);
//		assertTrue(tar.getyCoord() == testY);
//		assertTrue(tar.getzCoord() == testZ);
//		assertTrue(tar.getWidth() == testWidth);
//		assertTrue(tar.getHeight() == testHeight);
//		assertTrue(tar.getDepth() == testDepth);
//		assertNotNull(tar.getControl());
//	}
//}
