package com.funkydonkies.gamestates;

import java.util.Arrays;

import com.funkydonkies.core.App;
import com.funkydonkies.curve.SplineCurve;
import com.funkydonkies.exceptions.BadDynamicTypeException;
import com.funkydonkies.interfaces.Bridge;
import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Spline.SplineType;
import com.jme3.math.Vector3f;

/**
 *	Controls the creation and refreshing of the curve.
 */
public class CurveState extends AbstractAppState {
	public static final int POINT_DISTANCE = 10;
	public static final int POINTS_HEIGHT = 100;
	public static final int DEFAULT_CONTROL_POINTS_COUNT = 32;
	private static final int BASE_CHANGE_SPEED = 30;
	private static final int CHANGE_THRESHOLD = 5;
	private static final float MAX_SLOPE_ANGLE = 70f;
	private static final float DEFAULT_MAX_HEIGHT_DIFFERENCE = 100;
	private static final float SPEED_MULTIPLIER = 1.5f;
	private static final String UNSHADED_MATERIAL_PATH = "Common/MatDefs/Misc/Unshaded.j3md";
	private static final String COLOR = "Color";
	private static final float DEFAULT_IMAGE_HEIGHT = 480;

	// set to 32 as default this is what we currently use to test the program.
	private int controlPointsCount = DEFAULT_CONTROL_POINTS_COUNT;
	private float maxHeightDifference = DEFAULT_MAX_HEIGHT_DIFFERENCE; 

	private Bridge bridge;
	private App app;
	private SplineCurve splineCurve;
	
	private float[] updatedXPoints = null;

	private boolean cameraEnabled = false;
	private boolean updateEnabled = false;
	private boolean invertControlPoints = false;

	private RigidBodyControl oldRigi;
	private RigidBodyControl rigi;

	private Material curveMaterial;

	private AppStateManager stateManager;

	@Override
	public final void initialize(final AppStateManager sManager, final Application appl) {
		super.initialize(sManager, appl);
		stateManager = sManager;
		if (appl instanceof App) {
			this.app = (App) appl;
		} else {
			throw new BadDynamicTypeException();
		}

		bridge = sManager.getState(CameraState.class).getBridge();
		curveMaterial = new Material(appl.getAssetManager(), UNSHADED_MATERIAL_PATH);
		curveMaterial.setColor(COLOR, ColorRGBA.randomColor());
		oldRigi = new RigidBodyControl(0f);
		splineCurve = new SplineCurve(SplineType.CatmullRom, true);
	}

	/**
	 * calculates the number of control points that the getControlPoints() method should return.
	 * 
	 * @param imageWidth
	 *            the width of the images captured by the camera
	 * @param xdist
	 *            the horizontal interval between control points
	 * @return the number of control points
	 */
	private int getNumberOfControlPoints(final float imageWidth, final float xdist) {
		final float tempdiv = imageWidth / xdist;
		return (int) Math.floor(tempdiv);
	}

	/**
	 * Make sure two neighboring control points do not have too large a vertical distance. In this
	 * case when points are too far apart the lower point is brought up.
	 * 
	 * @param cp
	 *            list of control points
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
	 * sets the mHD variable by calculating the 'opposite' edge of the triangle using the 'adjacent'
	 * edge and the angle.
	 */
	private void setMaxHeightDiff() {
		maxHeightDifference = (float) (bridge.getxdist() * Math
				.tan(Math.toRadians(MAX_SLOPE_ANGLE)));
	}
	
	public void setInvertControlPoints(boolean b) {
		System.out.println("inverted");
		invertControlPoints = b;
	}
	
	public float[] reverse(float[] points) {
		for(int i = 0; i < points.length / 2; i++)
		{
		    float temp = points[i];
		    points[i] = points[points.length - i - 1];
		    points[points.length - i - 1] = temp;
		}
		return points;
	}
	
	@Override
	public final void update(final float tpf) {
		float[] points;
		if (bridge == null) {
			bridge = stateManager.getState(CameraState.class).getBridge();
		}

		if (cameraEnabled && bridge.isBgSet()) {
			controlPointsCount = getNumberOfControlPoints(bridge.getImageWidth(), bridge.getxdist());
			points = bridge.getControlPoints();
			setMaxHeightDiff();
			points = removeLargeHeightDifferences(points);
		} else {
			points = new float[controlPointsCount];
			Arrays.fill(points, DEFAULT_IMAGE_HEIGHT);
			final int bottomX = 10;
			final int topX = 15;
			for (int i = bottomX; i < topX; i++) { // TESTING CODE
				final int scale = 10;
				points[i] = i * scale;
			}
		}
		if (invertControlPoints) {
//			points = reverse(points);
		}

		if (updateEnabled) {
			if (bridge != null && bridge.isBgSet()) {
				scaleValues(points, bridge.getImageHeight());
			} else {
				scaleValues(points, (int) DEFAULT_IMAGE_HEIGHT);
			}
			updatedXPoints = points;
			final Vector3f[] updatedPoints = createVecArray(points, tpf * SPEED_MULTIPLIER);
			splineCurve.setCurvePoints(updatedPoints);
		}

		app.getRootNode().detachChildNamed("curve");
		if (rigi != null) {
			oldRigi = rigi;
		}
		rigi = new RigidBodyControl(0f);
		splineCurve.drawCurve(curveMaterial, stateManager.getState(PlayState.class).getPhysicsSpace(), rigi, app.getRootNode());
		splineCurve.getGeometry().removeControl(oldRigi);
		oldRigi.setEnabled(false);
	}

	/**
	 * Method generates Vector3f[] represenation of controlpoints, using the POINT_DISTANCE to
	 * define the distance between points on the x-axis.
	 * @param points controlpoints array received from camera, already processed and flipped by 
	 * scaleValues() method
	 * @param tpf time per frame received from update
	 * @return Vector3f[] representation of controlpoints
	 */
	private Vector3f[] createVecArray(final float[] points, final float tpf) {
		Vector3f[] res = new Vector3f[points.length];
		boolean filled = false;

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
		} else { // use existing
			for (int i = 0; i < points.length; i++) {
				final Vector3f temp = res[i];

				final float current = temp.y;
				final float desired = points[i];
				float heightChange = 0;
				float changeSpeed = 0;
				final float offset = 0.5f;
				final float delta = Math.abs(current - desired);

				changeSpeed = delta / POINTS_HEIGHT + offset; // offset by 0.5 so range (0.5, 1.5)

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
	 * 
	 * @param points
	 *            list of control points
	 * @param screenHeight
	 *            the
	 */
	private void scaleValues(final float[] points, final int screenHeight) {
		for (int i = 0; i < points.length; i++) {
			float point = points[i];
			if (!invertControlPoints) {
				point = screenHeight - point;
			}
			point = point / screenHeight;
			point = point * POINTS_HEIGHT;
			points[i] = point;
		}
	}

	/**
	 * Used to generate testPoints for the curve.
	 * 
	 * @return A variable length Vector3f array
	 */
	public static Vector3f[] testPoints() {

		final Vector3f[] points = new Vector3f[DEFAULT_CONTROL_POINTS_COUNT];

		for (int i = 0; i < points.length; i++) {
			Arrays.fill(points, i, points.length, new Vector3f(i * POINT_DISTANCE, 15, 0));
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
	 * Sets the update state, which controls whether the curve is being updated by debug/camera
	 * input.
	 * 
	 * @param enabled
	 *            desired update state
	 */
	public void setUpdateEnabled(final boolean enabled) {
		updateEnabled = enabled;
	}

	/**
	 * Gets current state of boolean determining whether the points used to draw the curve are being
	 * updated.
	 * 
	 * @return current state of the camera input interpretation
	 */
	public boolean getCameraEnabled() {
		return cameraEnabled;
	}

	/**
	 * Returns the amount of control points used by the wave.
	 * 
	 * @return int the amount of control points
	 */
	public int getAmountOfControlPoints() {
		return controlPointsCount;
	}

	/**
	 * Returns the splinecurve that the controller controlls.
	 * 
	 * @return splineCurve SplineCurve
	 */
	public SplineCurve getSplineCurve() {
		return splineCurve;
	}
	
	/** Loops over the curvepoints and gets the x location of the highest controlpoint. 
	 * @return the x location of the highest controlpoint
	 */
	public float getHighestPointX() {
		float highest = 0;
		int highestIndex = -1;
		
		if (updatedXPoints != null) {
			for (int i = 0; i < updatedXPoints.length; i++) {
				final float tmp = updatedXPoints[i];
				if (tmp > highest) {
					highest = tmp;
					highestIndex = i;
				}
			}
		}
		
		return highestIndex * POINT_DISTANCE;
	}
}
