package com.funkydonkies.w4v3;

import java.util.List;

import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.material.Material;
import com.jme3.math.Spline;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.scene.VertexBuffer.Type;
import com.jme3.util.BufferUtils;

/**
 * This class represent the spline(curve).
 */
public class SplineCurve extends Spline {
	private static Vector3f[] splinePoints;
	
	private static Vector3f[] backFrontVertices;
	private static Vector3f[] frontVertices;
	private static Vector3f[] backVertices;
	
	private static Vector3f[] curvePoints;
	private static Vector3f[] segmentPoints;
	private static Vector3f[] basePoints;
	private static Vector2f[] texCoords;
	
	private static int[] totalTriangles;
	private static int[] overLappingTriangles;
	private static int[] backFrontTriangles;
	private static int[] backTriangles;
	private static int[] frontTriangles;
	private static int[] boxfrontTriangles;
	private static int[] upperfrontTriangles;
	Boolean bool = true;
	private static Geometry geo;
	private RigidBodyControl phys;
	
	/**
	 * The constructor of the SplineCurve class.
	 * @param splineType the type of the SplineCurve, in our case Catmulrom
	 * @param controlPoints the controlpoints of the curve
	 * @param curveTension the tension of the curve, betweeen 0-1
	 * @param cycle the cycle of the curve
	 */
	public SplineCurve(final SplineType splineType, final float curveTension, final boolean cycle) {
		super(splineType, SplineCurveController.testPoints(), curveTension, cycle);
		
		curvePoints = SplineCurveController.testPoints();
	}

	public void drawCurve(final Material mat,
			final PhysicsSpace physicsSpace, RigidBodyControl rigidBody, final Node node, Vector3f[] pts) {
		phys = rigidBody;
		pts = getSplinePoints();
		splinePoints = pts;
		segmentPoints = new Vector3f[(splinePoints.length - 3) * 2 + 4];
		basePoints = new Vector3f[splinePoints.length];
		frontVertices = new Vector3f[splinePoints.length + segmentPoints.length + basePoints.length];
		backVertices = new Vector3f[frontVertices.length];
		backFrontVertices = new Vector3f[frontVertices.length + backVertices.length];
		texCoords = new Vector2f[frontVertices.length];
		
		overLappingTriangles = new int[(splinePoints.length - 1) * 6];
		boxfrontTriangles = new int[segmentPoints.length * 3];
		upperfrontTriangles = new int[splinePoints.length * 3];
		frontTriangles = new int[boxfrontTriangles.length + upperfrontTriangles.length];
		backTriangles = new int[frontTriangles.length];
		backFrontTriangles = new int[frontTriangles.length + backTriangles.length];
		totalTriangles = new int[overLappingTriangles.length + backFrontTriangles.length];
		
			fillBasePoints();
			fillTexCoords();
			segmentPoints[0] = new Vector3f().add( 0, 0, 0);
			segmentPoints[1] = new Vector3f().add( 1, 0, 0);
			segmentPoints[segmentPoints.length - 2] = new Vector3f().add(curvePoints[curvePoints.length - 1].getX() - 1, 0, 0);
			segmentPoints[segmentPoints.length - 1] = new Vector3f().add(curvePoints[curvePoints.length - 1].getX(), 0, 0);

		int j = 0;
		int q = 0;
		int v = 0;
		for(int i = 0; i < splinePoints.length - 1; i++){
			if(splinePoints[i].getY() < splinePoints[i + 1].getY()){
				Vector3f vecNew = new Vector3f().add(splinePoints[i + 1]);
				vecNew.setY(splinePoints[i].getY());
				segmentPoints[q] = splinePoints[i];
				segmentPoints[q + 1] = vecNew;		
			}else{
				Vector3f vecNew = new Vector3f().add(splinePoints[i]);
				vecNew.setY(splinePoints[i + 1].getY());
				segmentPoints[q] = vecNew;
				segmentPoints[q + 1] = splinePoints[i + 1];
			}
			addBoxfrontTriangles(j, i, q, basePoints.length);
			int indexStartFrontCurvePoints = basePoints.length + segmentPoints.length;
			int indexStartBackCurvePoints = frontVertices.length + basePoints.length + segmentPoints.length;
			addUpperfrontTriangles(i, v, q, indexStartFrontCurvePoints, basePoints.length);
			getOverlappingTriangles(q, i, j, indexStartFrontCurvePoints, indexStartBackCurvePoints);
			j = j + 6;
			q = q + 2;
			v = v + 3;
		}

		addfrontVerticesToEachOther();
		addfrontTrianglesToEachOther();
		
		getBackTriangles();
		getBackVertices();
		putFrontAndBackTogether();

		getTotalTriangles();
		Mesh mesh = new Mesh();
		mesh.setBuffer(Type.Position, 3, BufferUtils.createFloatBuffer(backFrontVertices));
		mesh.setBuffer(Type.TexCoord, 2, BufferUtils.createFloatBuffer(texCoords));
		mesh.setBuffer(Type.Index,    3, BufferUtils.createIntBuffer(totalTriangles));
		mesh.updateBound();
		geo = new Geometry("curve", mesh); 
		geo.setMaterial(mat);
		geo.addControl(phys);
		phys.setRestitution(0.5f);
		phys.setFriction(0.5f);
		physicsSpace.add(phys);
		node.attachChild(geo);
	
	}
	public void addUpperfrontTriangles(int i, int v, int q, int indexStartCurvePoints, int indexStartSegmentPoints){
		upperfrontTriangles[v] = indexStartSegmentPoints + q; 
		upperfrontTriangles[v + 1] = indexStartSegmentPoints + 1 + q;
		if(splinePoints[i].getY() > splinePoints[i + 1].getY()){
			upperfrontTriangles[v + 2] = indexStartCurvePoints + i;
		}else{
			upperfrontTriangles[v + 2] = indexStartCurvePoints + i + 1;
		}
		
	}
	
	public void addBoxfrontTriangles(int j, int i, int q, int basePointLength){
		boxfrontTriangles[j] = i;
		boxfrontTriangles[j + 1] = i + 1;
		boxfrontTriangles[j + 2] = basePointLength + q;
		boxfrontTriangles[j + 3] = boxfrontTriangles[j + 1];
		boxfrontTriangles[j + 4] = basePointLength + q + 1;
		boxfrontTriangles[j + 5] = basePointLength + q;
	}
	
	public void fillBasePoints(){
		for(int i = 0; i < basePoints.length; i++){
			basePoints[i] = new Vector3f().add(splinePoints[i].getX(), 0, 0);
		}	
	}
	
	public void fillTexCoords(){
		for(int i = 0; i < texCoords.length; i++){
			texCoords[i] = new Vector2f(1,1);
		}
	}
	
	public void addfrontVerticesToEachOther(){
		for(int i = 0; i < frontVertices.length; i++){
			if(i < basePoints.length){
				frontVertices[i] = basePoints[i];
			}else if(i < basePoints.length + segmentPoints.length){
				frontVertices[i] = segmentPoints[i - basePoints.length];
			}else{
				frontVertices[i] = splinePoints[i - basePoints.length - segmentPoints.length];
			}
		}
	}
	
	public void addfrontTrianglesToEachOther(){
		for(int i = 0; i < frontTriangles.length; i++){
			if(i < boxfrontTriangles.length){
				frontTriangles[i] = boxfrontTriangles[i];
			}else {
				frontTriangles[i] = upperfrontTriangles[i - boxfrontTriangles.length];
			}
		}
	}
	
	/**
	 * This method return the coordinates of two close vectors on the spline.
	 * @param controlPoint the controlpoint
	 * @param t how far until next controlpoint 
	 * @return an array with 2 vectors
	 */
	public Vector3f[] getSplinePoints() {
		int q = 0;
		this.clearControlPoints();
		for(int i = 0; i< curvePoints.length; i++){
			this.addControlPoint(curvePoints[i]);
		}
		final Vector3f[] vecs = new Vector3f[(curvePoints.length - 4)* 10];
		for(int i = 2; i < curvePoints.length - 2; i++){
			for(double j = 0; j < 0.9; j = j + 0.1){
				vecs[q] = interpolate((float) j, i, null);
				q++;
			}
		}
		return vecs;
	}

	public void incrementPoints(){
		for(int i = 0; i < curvePoints.length; i++){
			Vector3f vec = curvePoints[i];
			curvePoints[i] = vec.setY(curvePoints[i].getY() + 0.1f);
		}
	}
	
	public void decrementPoints(){
		for(int i = 0; i < curvePoints.length; i++){
			Vector3f vec = curvePoints[i];
			curvePoints[i] = vec.setY(curvePoints[i].getY() - 0.1f);
		}
	}
	
	public void getBackVertices(){	
		for(int i = 0; i < frontVertices.length; i++){
			backVertices[i] = new Vector3f().add(frontVertices[i]).setZ(-0.5f);
		}
	}
	
	public void getBackTriangles(){
		
		for(int i = 0; i < backTriangles.length; i++){

			backTriangles[i] = frontTriangles[i] + frontVertices.length;
		}
	}
	
	public void putFrontAndBackTogether(){
		for(int i = 0; i < backFrontTriangles.length; i++){
			if(i < frontTriangles.length){
				backFrontTriangles[i] = frontTriangles[i];
			}else {
				backFrontTriangles[i] = backTriangles[i - frontTriangles.length];
			}
		}
		
		for(int i = 0; i < backFrontVertices.length; i++){
			if(i < frontVertices.length){
				backFrontVertices[i] = frontVertices[i];
			}else{
				backFrontVertices[i] = backVertices[i - frontVertices.length];
			}
		}
	}
	
	public void getOverlappingTriangles(int q, int i, int j, int indexStartCurvePoints, int indexBackCurvePoints){
		overLappingTriangles[j] = indexStartCurvePoints + i;
		overLappingTriangles[j + 1] = indexStartCurvePoints + i + 1;
		overLappingTriangles[j + 2] = indexBackCurvePoints + i;
		overLappingTriangles[j + 3] = indexBackCurvePoints + i;
		overLappingTriangles[j + 4] = indexStartCurvePoints + i + 1;
		overLappingTriangles[j + 5] = indexBackCurvePoints + i + 1;
	}
	public Geometry getGeometry(){
		return geo;
	}
	
	public void getTotalTriangles(){
		for(int i = 0; i < totalTriangles.length; i++){
			if(i < backFrontTriangles.length){
				totalTriangles[i] = backFrontTriangles[i];
			}else {
				totalTriangles[i] = overLappingTriangles[i - backFrontTriangles.length];
			}
		}
	}
	
	/**
	 * Sets the array of Vector3f containing the points the curve uses to create itself.
	 * @param points desired Vector3f[] containing the points the curve uses to create itself.
	 */
	public void setCurvePoints(final Vector3f[] points) {
		curvePoints = points;
	}
	
	/** Get current Vector3f array containing the points the curve uses to create itself.
	 * @return Vector3f[] containing the points the curve uses to create itself.
	 */
	public Vector3f[] getCurvePoints() {
		return curvePoints;
	}
	
}
