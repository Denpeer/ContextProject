package com.funkydonkies.w4v3.obstacles;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

public class ObstacleFactoryTest {
	
	private static ObstacleFactory obF;

	@BeforeClass
	public static void setup() {
		obF = new ObstacleFactory();
	}
	
	@Test
	public void instantiateMovingBox(){
		MovingBox mBox = (MovingBox) obF.makeObstacle("MOVINGBOX", 2, 4, 1);
		assertNotEquals(mBox, null);
	}
	
	@Test
	public void instantiateNotExisting(){
		Obstacle notValidOb = obF.makeObstacle("NOTVALID", 2, 4, 1);
		assertEquals(notValidOb, null);
	}

}
