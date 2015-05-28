package com.funkydonkies.gamestates;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import powerups.SuperSizePowerup;

import com.funkydonkies.exceptions.BadDynamicTypeException;
import com.funkydonkies.w4v3.App;
import com.funkydonkies.w4v3.Ball;
import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

public class PowerupState extends AbstractAppState {
	
	private App app;
	SuperSizePowerup superSize = null;
	@Override
	public final void initialize(final AppStateManager sManager,
			final Application appl) {
		super.initialize(sManager, appl);
		
		if (appl instanceof App) {
			this.app = (App) appl;
		} else {
			throw new BadDynamicTypeException();
		}
	//	superSize = new SuperSizePowerup(appl);
		
	}
	
	@Override
	public void update(float tpf) {
		super.update(tpf);
	}
	
	public void enableSuperSize() {
		superSize.setEnabled(true);
	}
	
	
}
