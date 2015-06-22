package com.funkydonkies.gamestates;

import com.jme3.app.Application;
import com.jme3.app.state.AppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.renderer.RenderManager;

/**
 * Abstract powerup class. All powerups extend from this class. This is basically the same as
 * AbstractAppState with the exception that it starts disables and that the class is actually
 * abstract.
 * 
 * @see com.jme3.app.state.AbstractAppState
 */
public abstract class DisabledState implements AppState {

	private boolean initialized = false;
	private boolean enabled = false;

	@Override
	public void initialize(final AppStateManager stateManager, final Application app) {
		initialized = true;
	}

	@Override
	public boolean isInitialized() {
		return initialized;
	}

	@Override
	public void setEnabled(final boolean e) {
		this.enabled = e;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

	@Override
	public void stateAttached(final AppStateManager stateManager) {
	}

	@Override
	public void stateDetached(final AppStateManager stateManager) {
	}

	@Override
	public void update(final float tpf) {
	}

	@Override
	public void render(final RenderManager rm) {
	}

	@Override
	public void postRender() {
	}

	@Override
	public void cleanup() {
		initialized = false;
	}

}
