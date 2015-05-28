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
	private static final String INCREMENT_HEIGHT_MAPPING = "increment height";
	private static final String DECREMENT_HEIGHT_MAPPING = "decrement height";
	private static final int POINT_DISTANCE = 10;
	private static final int POINTS_HEIGHT = 100;
	private static final int BASE_CHANGE_SPEED = 20; 
	private static final int CHANGE_THRESHOLD = 5; 
	private static final double MAX_SLOPE_ANGLE = 70.0;
	private static final int DEFAULT_CONTROL_POINTS_COUNT = 32;
	private static final double DEFAULT_MAX_HEIGHT_DIFFERENCE = 100;
	
	private static int controlPointsCount = DEFAULT_CONTROL_POINTS_COUNT; //set to 32 as default; this is what we currently use to test the program.
	private double maxHeightDifference = DEFAULT_MAX_HEIGHT_DIFFERENCE; //default value without specific reason
	
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
	
	/**
	 * calculates the number of control points that the getControlPoints() method should return.
	 * @param imageWidth the width of the images captured by the camera
	 * @param xdist the horizontal interval between control points
	 * @return the number of control points
	 */
	private int getNumberOfControlPoints(final int imageWidth, final int xdist) {
		final double tempdiv = imageWidth / xdist;
    	return (int) Math.floor(tempdiv);
	}
	
	/**
	 * Make sure two neighboring control points do not have too large a vertical distance.
	 * In this case when points are too far apart the lower point is brought up.
	 * @param cp list of control points
	 * @return list of control points without large height differences
	 */
	private float[] removeLargeHeightDifferences(final float[] cp) {
		for (int i = 0; i < (cp.length - 1); i++) {
			if (Math.abs(cp[i] - cp[i + 1]) > maxHeightDifference) {
				if (cp[i] < cp[i + 1]) {
					cp[i + 1] = cp[i] + (int) maxHeightDifference;
				} else {
					cp[i] = cp[i + 1] + (int) maxHeightDifference;
				}
			}
		}
		for (int i = (cp.length - 2); i > 0; i--) {
			if (Math.abs(cp[i] - cp[i + 1]) > maxHeightDifference) {
				if (cp[i] < cp[i + 1]) {
					cp[i + 1] = cp[i] + (int) maxHeightDifference;
				} else {
					cp[i] = cp[i + 1] + (int) maxHeightDifference;
				}
			}
		}
		return cp;
	}
	
	/**
	 * sets the mHD variable by calculating the 'opposite' edge of the triangle using the 'adjacent' edge and the angle.
	 */
	private void setMaxHeightDiff() {
		maxHeightDifference = bridge.getxdist() * Math.tan(Math.toRadians(MAX_SLOPE_ANGLE));
	}
	
	@Override
	public final void update(final float tpf) {
		float[] points;
		
		if (cameraEnabled) {
			points = bridge.getControlPoints();	// ENABLES CAMERA INPUT
			setMaxHeightDiff();
			points = removeLargeHeightDifferences(points);
		} else {
			controlPointsCount = getNumberOfControlPoints(bridge.getImageWidth(), bridge.getxdist());
			points = new float[controlPointsCount];
			Arrays.fill(points, bridge.getImageHeight());
			for (int i = 10; i < 15; i++) {	// TESTING CODE
				points[i] = 150 + 10 * (i - 9);
			}
		}
		
		if (updateEnabled) {
			scaleValues(points, bridge.getImageHeight());
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
				
				if (delta < CHANGE_THRESHOLD) {
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
	 * @param points list of control points
	 * @param screenHeight the 
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
		inputManager.addMapping(INCREMENT_HEIGHT_MAPPING, new KeyTrigger(KeyInput.KEY_R));
		inputManager.addMapping(DECREMENT_HEIGHT_MAPPING, new KeyTrigger(KeyInput.KEY_F));

		inputManager.addListener(analogListener, INCREMENT_HEIGHT_MAPPING);
		inputManager.addListener(analogListener, DECREMENT_HEIGHT_MAPPING);
	}
	
	private AnalogListener analogListener = new AnalogListener() {
		public void onAnalog(final String name, final float value, final float tpf) {
			if (name.equals(INCREMENT_HEIGHT_MAPPING)) {
				splineCurve.incrementPoints();
			} else if (name.equals(DECREMENT_HEIGHT_MAPPING)) {
				splineCurve.decrementPoints();
			}
		}
	};
	
	/** Used to generate testPoints for the curve.
	 * 
	 * @return A variable length Vector3f array
	 */
	public static Vector3f[] testPoints() {
				
		final Vector3f[] points = new Vector3f[controlPointsCount];
				
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
