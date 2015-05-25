package com.funkydonkies.w4v3.obstacles;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import org.junit.BeforeClass;
import org.junit.Test;

import com.jme3.asset.AssetManager;
import com.jme3.scene.Node;

/**
 * This test tests the obstacle factory.
 * @author SDumasy
 *
 */
public class ObstacleFactoryTest {
	
	private static ObstacleFactory obF;
	private static AssetManager assetManager;
	private static Node node;
	
	private final float obstacleWidth = 2;
	private final float obstacleHeight = 4;
	private final float obstacleDepth = 1;
	private final float obstacleX = 20;
	private final float obstacleY = 0;
	private final float obstacleZ = 0.5f;
		
	private final float targetWidth = 1;
	private final float targetHeight = 1;
	private final float targetDepth = 1;
	private final float targetX = 30;
	private final float targetY = 0.5f;
	private final float targetZ = 1;
	
	/**
	 * setup a obstacle factory.
	 */
	@BeforeClass
	public static void setUpBeforeClass() {
		obF = new ObstacleFactory();
		assetManager = mock(AssetManager.class);
		node = mock(Node.class);
	}
	
	/**
	 * Test if the movingbox can be instantiated correctly.
	 */
	@Test
	public final void instantiateMovingBox() {
		final MovingBox mBox = obF.makeMovingBox(node, assetManager);
		assertNotNull(mBox);
		assertTrue(obstacleDepth == mBox.getDepth());
		assertTrue(obstacleHeight == mBox.getHeight());
		assertTrue(obstacleWidth == mBox.getWidth());
		assertTrue(obstacleX == mBox.getxCoord());
		assertTrue(obstacleY == mBox.getyCoord());
		assertEquals(obstacleZ, mBox.getzCoord(), 0);
	}
	
	/**
	 * Tests the creating of target.
	 */
	@Test
	public final void testMakeTarget(){
		final Target target = obF.makeTarget(node);
		assertNotNull(target);
		assertTrue(targetDepth == target.getDepth());
		assertTrue(targetHeight == target.getHeight());
		assertTrue(targetWidth == target.getWidth());
		assertTrue(targetX == target.getxCoord());
		assertTrue(targetY == target.getyCoord());
		assertEquals(targetZ, target.getzCoord(), 0);
	}


}
