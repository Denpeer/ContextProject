package com.funkydonkies.w4v3;

import com.jme3.terrain.heightmap.AbstractHeightMap;

/**
 * Custom HeightMap for the generation of a Wave.
 * 
 * @author Danilo
 *
 */
public class WaveHeightMap extends AbstractHeightMap {

	private static Bridge bridge;

	/** 
	 * @see com.jme3.terrain.heightmap.HeightMap#load().
	 * 
	 * @return true if load was succesful 
	 */
	public boolean load() {
		bridge = new DummyCameraDetection();

		heightData = bridge.getControlPoints();
		
		return true;
	}

}
