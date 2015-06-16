package com.funkydonkies.interfaces;

import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;

/**
 * Abstract class for custom GhostControl methods.
 */
public abstract class MyAbstractControl extends AbstractControl {
	
	/** Constructor for the AbstractControl.
	 */
	public MyAbstractControl() {
		
	}
	
	@Override
	public void setSpatial(final Spatial spatial) {
		super.setSpatial(spatial);
		init();
	}

	/**
	 * Called when the control has been added.
	 */
	public abstract void init();
	
	
	@Override
	protected void controlRender(final RenderManager rm, final ViewPort vp) {

	}
	
}
