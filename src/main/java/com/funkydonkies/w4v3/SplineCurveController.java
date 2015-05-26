package com.funkydonkies.w4v3;

import java.util.Arrays;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.math.Vector3f;
import com.mycompany.mavenproject1.BadDynamicTypeException;


public class SplineCurveController extends AbstractAppState {
	private static final String INCREMENTHEIGHTMAPPING = "increment height";
	private static final String DECREMENTHEIGHTMAPPING = "decrement height";
	private static final int POINT_DISTANCE = 10;
	private static final int POINTS_HEIGHT = 100;
	private static final int BASE_CHANGE_SPEED = 20; 
	
	private static int CHANGE_TRESHOLD = 5; 
	
	private Bridge bridge;
	private App app;
	private InputManager inputManager;
	private SplineCurve splineCurve;
	
	private boolean cameraEnabled = false;
	private boolean updateEnabled = false;
	
	public SplineCurveController(final Bridge b, final SplineCurve sp) {
		this.splineCurve = sp;
		this.bridge = b;
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
	
	@Override
	public final void update(final float tpf) {
		float[] points;
		
		if (cameraEnabled) {
			points = bridge.getControlPoints();	// ENABLES CAMERA INPUT
		} else {
			points = new float[32];
			Arrays.fill(points, 480);
			for (int i = 10; i < 15; i++) {	// TESTING CODE
				points[i] = 150 + 10 * (i - 9);
			}
		}
		
		if (updateEnabled) {
			scaleValues(points, 480/*bridge.getImageHeight()*/);
			final Vector3f[] updatedPoints = createVecArray(points, tpf);
			splineCurve.setCurvePoints(updatedPoints);
		}
		
	}
	
	private Vector3f[] createVecArray(final float[] points, final float tpf) {
		Vector3f[] res = new Vector3f[points.length];
		boolean filled = false;
		
		/* Check whether splineCurve has this size array */
		final Vector3f[] tmp = splineCurve.getCurvePoints();
		if (tmp.length == points.length) {
			res = tmp;
			filled = true;
		}
		
		// create from scratch
		if (!filled) {
			for (int i = 0; i < points.length; i++) {
				final Vector3f temp = new Vector3f(POINT_DISTANCE * i, 0, 0);
				temp.y = points[i];
				res[i] = temp;
			}
		// use existing
		} else { 
			for (int i = 0; i < points.length; i++) {
				final Vector3f temp = res[i];
				
				final float current = temp.y;
				final float desired = points[i];
				float heightChange = 0;
				float changeSpeed = 0;
				
				final float delta = Math.abs(current - desired);
				
				changeSpeed = delta / POINTS_HEIGHT + 0.5f;
				
				if (delta < CHANGE_TRESHOLD) {
					continue;
				}
				
				if (current <= desired) {
					heightChange = Math.min(delta, BASE_CHANGE_SPEED);
				} else {
					heightChange = Math.max(-delta, -BASE_CHANGE_SPEED);
				}
				
				heightChange = heightChange * changeSpeed * tpf;
				
				temp.y = current + heightChange;
				res[i] = temp;
			}
		}
		
		return res;
	}

	/**
	 * Scales the values given by the camera.
	 * @param points
	 * @param screenHeight
	 */
	private void scaleValues(final float[] points, final int screenHeight) {
		for (int i = 0; i < points.length; i++) {
			float point = points[i];
			point = screenHeight - point;
			point = point / screenHeight;
			point = point * POINTS_HEIGHT;
			points[i] = point;
		}
	}

	public void initKeys() {
		inputManager.addMapping(INCREMENTHEIGHTMAPPING, new KeyTrigger(KeyInput.KEY_R));
		inputManager.addMapping(DECREMENTHEIGHTMAPPING, new KeyTrigger(KeyInput.KEY_F));

		inputManager.addListener(analogListener, INCREMENTHEIGHTMAPPING);
		inputManager.addListener(analogListener, DECREMENTHEIGHTMAPPING);
	}
	
	private AnalogListener analogListener = new AnalogListener() {
		public void onAnalog(final String name, final float value, final float tpf) {
			if (name.equals(INCREMENTHEIGHTMAPPING)) {
				splineCurve.incrementPoints();
			} else if (name.equals(DECREMENTHEIGHTMAPPING)) {
				splineCurve.decrementPoints();
			}
		}
	};
	
	/** Used to generate testPoints for the curve.
	 * 
	 * @return A variable length Vector3f array
	 */
	public static Vector3f[] testPoints() {
				
		final Vector3f[] points = new Vector3f[32];
				
		for (int i = 0; i < points.length; i++) {
			Arrays.fill(points, i, points.length, new Vector3f(i * POINT_DISTANCE, 2, 0));
		}
				
		return points;
		}
		

	
	/**
	 * Toggle the updating of the dataset points through the camera input.
	 */
	public void toggleCameraEnabled() {
		cameraEnabled = !cameraEnabled;
	}

	
	/**
	 * Toggle the updating of the splineCurve with debug or camera input.
	 */
	public void toggleUpdateEnabled() {
		updateEnabled = !updateEnabled;
	}
	
	/**
	 * Sets the update state, which controls whether the curve is being updated by
	 * debug/camera input.
	 * @param enabled desired update state
	 */
	public void setUpdateEnabled(final boolean enabled) {
		updateEnabled = enabled;
	}

	/** 
	 * Gets current state of boolean determining whether the points used to draw the curve
	 * are being updated.
	 * @return current state of the camera input interpretation
	 */
	public boolean getCameraEnabled() {
		return cameraEnabled;
	}
	
}
