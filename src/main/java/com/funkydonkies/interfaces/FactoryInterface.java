package com.funkydonkies.interfaces;

import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppStateManager;
import com.jme3.scene.Spatial;


/**
 * This is the factory interface for obstacles.
 * @author SDumasy
 *
 */
public interface FactoryInterface {
	
	/** Method called to get an Obstacle instance.
	 * @param sManager jme stateManager for getting gamestates
	 * @param app app jme for getting the assetmanager for example
	 * @return A spatial to spawn
	 */
	Spatial makeObject(AppStateManager sManager, SimpleApplication app);
	
}
