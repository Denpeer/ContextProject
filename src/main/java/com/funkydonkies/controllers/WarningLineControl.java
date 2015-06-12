package com.funkydonkies.controllers;

import com.funkydonkies.interfaces.MyAbstractControl;
import com.jme3.math.Vector3f;

public class WarningLineControl extends MyAbstractControl {
	private float time = 0;
	private Vector3f initialSpawn;

	public WarningLineControl(float xOff, float yOff) {
		initialSpawn = new Vector3f(xOff, yOff, 0);
	}
	
	@Override
	public void init() {
		spatial.setLocalTranslation(initialSpawn);
	}

	@Override
	protected void controlUpdate(final float tpf) {
		time += tpf;

		if (time > 1) {
			spatial.removeFromParent();
			setEnabled(false);
		}
	}

}
