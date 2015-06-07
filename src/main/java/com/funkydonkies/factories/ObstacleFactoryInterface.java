package com.funkydonkies.factories;

import com.jme3.asset.AssetManager;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;


/**
 * This is the factory interface for obstacles.
 * @author SDumasy
 *
 */
public interface ObstacleFactoryInterface {
	public final Vector3f SPAWN = new Vector3f(0, 0, 0);
	
	public Spatial makeObst(AssetManager assetManager);
	
}
