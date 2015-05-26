package com.funkydonkies.gamestates;

import com.funkydonkies.controllers.SuperSizePowerup;
import com.funkydonkies.exceptions.BadDynamicTypeException;
import com.funkydonkies.w4v3.App;
import com.funkydonkies.w4v3.Ball;
import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;

public class BallState extends AbstractAppState {
	
	private App app;
	SuperSizePowerup superSize = null;
	
	@Override
	public final void initialize(final AppStateManager sManager, final Application appl) {
		super.initialize(sManager, appl);
		
		if (appl instanceof App) {
			this.app = (App) appl;
		} else {
			throw new BadDynamicTypeException();
		}
		superSize = new SuperSizePowerup();
	}
	
	@Override
	public void update(float tpf) {
		super.update(tpf);
	}
	
	public void spawnBall() {
		final Ball ball = new Ball(app.getAssetManager());
		ball.spawn(app.getBallNode(), app.getPhysicsSpace(), true);
	}
}
