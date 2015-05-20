package com.funkydonkies.w4v3.obstacles;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
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
		final MovingBox mBox = obF.makeMovingBox();
		assertNotEquals(mBox, null);
	}
	



}
