package com.funkydonkies.powerups;

import com.jme3.app.Application;
import com.jme3.app.state.AppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.renderer.RenderManager;

/**
 * Abstract powerup class. All powerups extend from this class.
 * This is basically the same as AbstractAppState with the exception that it starts disables and 
 * that the class is actually abstract.
 * @see com.jme3.app.state.AbstractAppState
 */
public abstract class AbstractPowerup implements AppState {
	
	protected boolean initialized = false;
    private boolean enabled = false;

    public void initialize(AppStateManager stateManager, Application app) {
        initialized = true;
    }

    public boolean isInitialized() {
        return initialized;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    
    public boolean isEnabled() {
        return enabled;
    }

    public void stateAttached(AppStateManager stateManager) {
    }

    public void stateDetached(AppStateManager stateManager) {
    }

    public void update(float tpf) {
    }

    public void render(RenderManager rm) {
    }

    public void postRender(){
    }

    public void cleanup() {
        initialized = false;
    }

}
