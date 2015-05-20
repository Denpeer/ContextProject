package com.funkydonkies.w4v3.obstacles;

import com.funkydonkies.w4v3.Combo;
import com.jme3.asset.AssetManager;
import com.jme3.scene.Node;

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
	 * @param node Node to attach the spatial and add it to the scene
	 * @param assetManager AssetManager for crating obstacles
	 * @param combo Combo needed for creating Targets.
	 * @return the obstacle object or null if invalid obstacle type
	 * 
	 */
	public final Obstacle makeObstacle(final String type, final double width,
			final double height, final double depth, final Node node, final AssetManager assetManager,
			final Combo combo) {
		if ("TARGET".equals(type)) {
	          return new Target(width, height, depth, node, assetManager, combo);
	     } else if ("MOVINGBOX".equals(type)) {   
	    	  return new MovingBox(width, height, depth, node);
	     } else {
	    	 return null;
	     }
	}
}
