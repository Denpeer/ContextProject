package com.funkydonkies.controllers;

import com.funkydonkies.interfaces.MyAbstractControl;
import com.jme3.math.Vector3f;

/**
 * Displays a faint line where an obstacle will spawn.
 */
public class WarningLineControl extends MyAbstractControl {
	private float time = 0;
	private Vector3f initialSpawn;

	/**
	 * Constructor that takes an x and y offset, which are directly used to choose the initialSpawn
	 * place.
	 * 
	 * @param xOff
	 *            x offset
	 * @param yOff
	 *            y offset
	 */
	public WarningLineControl(final float xOff, final float yOff) {
		initialSpawn = new Vector3f(xOff, yOff, 0);
	}

	@Override
	public void init() {
		spatial.setLocalTranslation(initialSpawn);
	}

	@Override
	protected void controlUpdate(final float tpf) {
		time += tpf;

		if (time > 2) {
			spatial.removeFromParent();
			setEnabled(false);
		}
	}

}
