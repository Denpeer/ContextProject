package com.funkydonkies.w4v3.obstacles;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 * This test tests the obstacle factory.
 * @author SDumasy
 *
 */
public class ObstacleFactoryTest {
	
	private static ObstacleFactory obF;
	
	/**
	 * setup a obstacle factory.
	 */
	@BeforeClass
	public static void setup() {
		obF = new ObstacleFactory();
	}
	
	/**
	 * Test if the movingbox can be instantiated correctly.
	 */
	@Test
	public final void instantiateMovingBox() {
		final MovingBox mBox = obF.makeMovingBox();
		assertNotEquals(mBox, null);
	}
	
	/**
	 * Test if the not valid objects aren't instantiated.
	 */
	@Test
	public final void instantiateTarget() {
		final Obstacle notValidOb = obF.makeTarget();
		assertNotEquals(notValidOb, null);
	}

}
