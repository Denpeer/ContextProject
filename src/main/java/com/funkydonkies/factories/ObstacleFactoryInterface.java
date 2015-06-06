package com.funkydonkies.factories;

import com.jme3.asset.AssetManager;
import com.jme3.scene.Spatial;


/**
 * This is the factory interface for obstacles.
 * @author SDumasy
 *
 */
public interface ObstacleFactoryInterface {
	
	public Spatial makeObst(AssetManager assetManager);
	
}
