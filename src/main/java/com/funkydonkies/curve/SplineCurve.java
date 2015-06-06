package com.funkydonkies.curve;

import com.funkydonkies.appstates.CurveState;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.material.Material;
import com.jme3.math.Spline;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;


/**
 * This class represent the spline(curve).
 */
public class SplineCurve extends Spline {
	
	private static final float TENSION = 0.6f;
	private static final float RESTITUTION = 0.5f;
	private static final float FRICTION = 0.5f;
	private static final int OFFSET = 1;
	private static final int NO_OF_UNUSED_POINTS = 4;
	private static final int SEGMENTPOINTS_PER_CONTROLPOINT = 9;
	private static final int TOTAL_POINTS_PER_CONTROLPOINT = 10;
	private static final float SCALE_POINTS = 0.1f;
	private Vector3f[] curvePoints;

	private Geometry curveGeom;

	private RigidBodyControl phys;
	
	/**
	 * The constructor of the SplineCurve class.
	 * @param splineType the type of the SplineCurve, in our case Catmulrom
	 * @param cycle the cycle of the curve
	 */
	public SplineCurve(final SplineType splineType, final boolean cycle) {
		super(splineType, CurveState.testPoints(), TENSION, cycle);
		curveGeom = new Geometry("curve");
		curvePoints = CurveState.testPoints();
	}
	
 
	
	/**
	 * This method draws the curve.
	 * @param mat the material of the curve.
	 * @param physicsSpace the physics space
	 * @param rigidBody the rigid body control
	 * @param node the rootNode everything gets attached too
	 */
	public final void drawCurve(final Material mat,
			final PhysicsSpace physicsSpace, final RigidBodyControl rigidBody, final Node node) {
		phys = rigidBody;
		this.refreshControlPoints();
		
		final CustomCurveMesh curve = new CustomCurveMesh(getSplinePoints());
		final Mesh mesh = curve.createMesh();
		curveGeom.setMesh(mesh);
		curveGeom.setMaterial(mat);
		curveGeom.addControl(phys);
		phys.setRestitution(RESTITUTION);
		phys.setFriction(FRICTION);
		physicsSpace.add(phys);
		node.attachChild(curveGeom);
	}
	
	/**
	 * This method calculates the splinepoints of the curve using interpolate.
	 * @return an array with splinepoints
	 */
	public Vector3f[] getSplinePoints() {
		int q = 0;
		
		final Vector3f[] vecs = new Vector3f[(curvePoints.length - NO_OF_UNUSED_POINTS) 
		                                     * TOTAL_POINTS_PER_CONTROLPOINT];
		
		for (int i = 2; i < curvePoints.length - 2; i++) {
			for (double j = 0; j < SEGMENTPOINTS_PER_CONTROLPOINT * SCALE_POINTS;
					j = j + OFFSET * SCALE_POINTS) {
				vecs[q] = interpolate((float) j, i, null);
				q++;
			}
		}
		return vecs;
	}
	/**
	 * This method replaces the controlPoints with the curvepoints.
	 */
	public void refreshControlPoints() {
		this.clearControlPoints();
		for (int i = 0; i < curvePoints.length; i++) {
			this.addControlPoint(curvePoints[i]);
		}
	}
	/**
	 * This methods increments the curvePoints.
	 */
	public void incrementPoints() {
		for (int i = 0; i < curvePoints.length; i++) {
			final Vector3f vec = curvePoints[i];
			curvePoints[i] = vec.setY(curvePoints[i].getY() + OFFSET * SCALE_POINTS);
		}
	}
	
	/**
	 * This methods increments and decrements curvepoints.
	 */
	public void incDecPoints() {
		for (int i = 0; i < curvePoints.length; i = i + 2) {
			final Vector3f vec = curvePoints[i];
			if (i % 2 == 0) {
				curvePoints[i] = vec.setY(curvePoints[i].getY() - OFFSET * SCALE_POINTS);
			} else {
				curvePoints[i] = vec.setY(curvePoints[i].getY() + OFFSET * SCALE_POINTS);
			}
			
		}
	}
	
	/**
	 * This method decrements the curvePoints.
	 */
	public void decrementPoints() {
		for (int i = 0; i < curvePoints.length; i++) {
			final Vector3f vec = curvePoints[i];
			curvePoints[i] = vec.setY(curvePoints[i].getY() - OFFSET * SCALE_POINTS);
		}
	}
	
	/**
	 * This method is used to get the geometry.
	 * @return the geometry
	 */
	public Geometry getGeometry() {
		return curveGeom;
	}
	
	/**
	 * Sets the array of Vector3f containing the points the curve uses to create itself.
	 * @param points desired Vector3f[] containing the points the curve uses to create itself.
	 */
	public void setCurvePoints(final Vector3f[] points) {
		curvePoints = points.clone();
	}
	
	/** Get current Vector3f array containing the points the curve uses to create itself.
	 * @return Vector3f[] containing the points the curve uses to create itself.
	 */
	public Vector3f[] getCurvePoints() {
		return curvePoints.clone();
	}
	
}
