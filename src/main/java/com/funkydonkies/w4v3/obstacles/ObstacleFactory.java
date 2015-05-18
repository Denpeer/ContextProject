package com.funkydonkies.w4v3.obstacles;

/**
 * ObstacleFactory is the class for obstacles.
 * @author SDumasy
 *
 */
public class ObstacleFactory {
	
	/**
	 * This method instantiates obstacle objects.
	 * @param type the type of the obstacle
	 * @param width the width of the obstacle
	 * @param height the height of the obstacle
	 * @param depth the depth of the obstacle
	 * @return the obstacle object or null if invalid obstacle type
	 */
	public final Obstacle makeObstacle(final String type, final double width,
			final double height, final double depth) {
		if ("TARGET".equals(type)) {
	          return new Target(width, height, depth);
	     } else if ("MOVINGBOX".equals(type)) {   
	    	  return new MovingBox(width, height, depth);
	     } else {
	    	 return null;
	     }
	}
}
