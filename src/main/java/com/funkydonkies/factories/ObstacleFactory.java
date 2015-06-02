package com.funkydonkies.factories;

import com.funkydonkies.geometrys.Target;
import com.funkydonkies.geometrys.obstacles.KillerWhale;
import com.jme3.asset.AssetManager;
import com.jme3.scene.Node;

/**
 * ObstacleFactory is the class for obstacles.
 * @author SDumasy
 *
 */
public class ObstacleFactory {
	public static final float OBSTACLE_WIDTH = 20;
	public static final float OBSTACLE_HEIGHT = 40;
	public static final float OBSTACLE_DEPTH = 10;
	public static final float OBSTACLE_X = 150;
	public static final float OBSTACLE_Y = 100;
	public static final float OBSTACLE_Z = 0.5f;
	public static final float TARGET_WIDTH = 5;
	public static final float TARGET_HEIGHT = 5;
	public static final float TARGET_DEPTH = 5;
	
	/**
	 * This method creates a moving box obstacle with the specified parameters.
	 * @param node Node to attach the spatial and add it to the scene
	 * @param assetManager AssetManager for crating obstacles
	 * @return a MovingBox object
	 */
	public final KillerWhale makeMovingBox(final Node node, final AssetManager assetManager) {
		return new KillerWhale(OBSTACLE_WIDTH, OBSTACLE_HEIGHT, OBSTACLE_DEPTH,
				OBSTACLE_X, OBSTACLE_Y, OBSTACLE_Z, node);
	}
	
	/**
	 * This method creates a target with the specified parameters.
	 * @param node Node the node that the target will be attached to
	 * @return a target object
	 */
	public final Target makeTarget(final Node node) {
		final float targetX = 30;
		final float targetY = 0.5f;
		final float targetZ = 1;
		return new Target(TARGET_WIDTH, TARGET_HEIGHT, TARGET_DEPTH,
				targetX, targetY, targetZ, node);
	}
}
