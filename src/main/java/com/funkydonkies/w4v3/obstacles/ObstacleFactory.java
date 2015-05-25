package com.funkydonkies.w4v3.obstacles;

import com.jme3.asset.AssetManager;
import com.jme3.scene.Node;

/**
 * ObstacleFactory is the class for obstacles.
 * @author SDumasy
 *
 */
public class ObstacleFactory {
	
	/**
	 * This method creates a moving box obstacle with the specified parameters.
	 * @param node Node to attach the spatial and add it to the scene
	 * @param assetManager AssetManager for crating obstacles
	 * @return a MovingBox object
	 */
	public final MovingBox makeMovingBox(final Node node, final AssetManager assetManager) {
		final float obstacleWidth = 2;
		final float obstacleHeight = 4;
		final float obstacleDepth = 1;
		final float obstacleX = 20;
		final float obstacleY = 0;
		final float obstacleZ = 0.5f;
		return new MovingBox(obstacleWidth, obstacleHeight, obstacleDepth,
				obstacleX, obstacleY, obstacleZ, node);
	}
	
	/**
	 * This method creates a target with the specified parameters.
	 * @param node Node the node that the target will be attached to
	 * @return a target object
	 */
	public final Target makeTarget(final Node node) {
		final float targetWidth = 1;
		final float targetHeight = 1;
		final float targetDepth = 1;
		final float targetX = 30;
		final float targetY = 0.5f;
		final float targetZ = 1;
		return new Target(targetWidth, targetHeight, targetDepth,
				targetX, targetY, targetZ, node);
	}
}
