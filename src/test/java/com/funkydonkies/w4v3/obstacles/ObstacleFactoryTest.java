package com.funkydonkies.w4v3.obstacles;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import org.junit.BeforeClass;
import org.junit.Test;

import com.funkydonkies.w4v3.Combo;
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
	private static Combo combo;
	
	final float obstacleWidth = 2;
	final float obstacleHeight = 4;
	final float obstacleDepth = 1;
	final float obstacleX = 20;
	final float obstacleY = 0;
	final float obstacleZ = 0.5f;
		
	final float targetWidth = 1;
	final float targetHeight = 1;
	final float targetDepth = 1;
	final float targetX = 30;
	final float targetY = 0.5f;
	final float targetZ = 1;
	
	/**
	 * setup a obstacle factory.
	 */
	@BeforeClass
	public static void setUpBeforeClass() {
		obF = new ObstacleFactory();
		assetManager = mock(AssetManager.class);
		node = mock(Node.class);
		combo = mock(Combo.class);
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
	
	@Test
	public final void testMakeTarget(){
		final Target target = obF.makeTarget(node, combo);
		assertNotNull(target);
		assertTrue(targetDepth == target.getDepth());
		assertTrue(targetHeight == target.getHeight());
		assertTrue(targetWidth == target.getWidth());
		assertTrue(targetX == target.getxCoord());
		assertTrue(targetY == target.getyCoord());
		assertEquals(targetZ, target.getzCoord(), 0);
	}


}
