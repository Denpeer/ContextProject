package com.funkydonkies.w4v3;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.mycompany.mavenproject1.BadDynamicTypeException;


public class SplineCurveController extends AbstractAppState {
	private static final String INCREMENTHEIGHTMAPPING = "increment height";
	private static final String DECREMENTHEIGHTMAPPING = "decrement height";
	private App app;
	private InputManager inputManager;
	private SplineCurve splineCurve;
	
	public SplineCurveController(SplineCurve sp){
		splineCurve = sp;
	}
	@Override
	public final void initialize(final AppStateManager sManager,
			final Application appl) {
		super.initialize(sManager, appl);
	
		if (appl instanceof App) {
			this.app = (App) appl;
		} else {
			throw new BadDynamicTypeException();
		}
		this.inputManager = this.app.getInputManager();
		
		initKeys();
	}
	
	public void initKeys() {
		
		//Control for spawing balls
		inputManager.addMapping(INCREMENTHEIGHTMAPPING, 
				new KeyTrigger(KeyInput.KEY_R));
		inputManager.addMapping(DECREMENTHEIGHTMAPPING, 
				new KeyTrigger(KeyInput.KEY_F));

		inputManager.addListener(analogListener, INCREMENTHEIGHTMAPPING);
		inputManager.addListener(analogListener, DECREMENTHEIGHTMAPPING);
	}
	
	private AnalogListener analogListener = new AnalogListener() {
		public void onAnalog(final String name, final float value, final float tpf) {
			if (name.equals(INCREMENTHEIGHTMAPPING)) {
				splineCurve.incrementPoints();
			} else if(name.equals(DECREMENTHEIGHTMAPPING)){
				splineCurve.decrementPoints();
			}
		
		}
	};
}
