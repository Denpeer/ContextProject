package com.funkydonkies.w4v3.curve;

import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Mesh;
import com.jme3.scene.VertexBuffer.Type;
import com.jme3.util.BufferUtils;


public class CustomCurveMesh {
	private Vector3f[] meshStructurePoints;
	private int[] meshStructureTrianglesIndices;
	private final Vector3f[] splinePoints;
	private static Vector2f[] texCoords;
	/**
	 * This class take care of the mesh for the spline.
	 * @param sPoints the spline points on the surface of the spline
	 */
	public CustomCurveMesh(Vector3f[] sPoints){
		splinePoints = sPoints;
		meshStructurePoints = new Vector3f[splinePoints.length * 5 - 2];
		meshStructureTrianglesIndices = new int[(splinePoints.length - 1) * 15];
		texCoords = new Vector2f[splinePoints.length];
	}
	
	/**
	 * This method creates a mesh.
	 */
	public Mesh CreateMesh(){
		for(int i = 0; i < splinePoints.length; i++){
			setMeshPoints(i);
			setTriangleIndices(i);
			texCoords[i] = new Vector2f(1,1);
		}	
		Mesh mesh = new Mesh();
		mesh.setBuffer(Type.Position, 3, BufferUtils.createFloatBuffer(meshStructurePoints));
		mesh.setBuffer(Type.TexCoord, 2, BufferUtils.createFloatBuffer(texCoords));
		mesh.setBuffer(Type.Index,    3, BufferUtils.createIntBuffer(meshStructureTrianglesIndices));
		mesh.updateBound();
		return mesh;
	}
	
	/**
	 * This method gets all the points needed to genearate triangles for the mesh at a splinepoint.
	 * @param i the index of the splinepoint
	 */
	public void setMeshPoints(int i){
		meshStructurePoints[i] = new Vector3f().add(splinePoints[i].getX(), 0, 0);
		meshStructurePoints[splinePoints.length + i] = splinePoints[i];
		meshStructurePoints[splinePoints.length * 2 + i] = new Vector3f().add(splinePoints[i]).setZ(-1);
		
		if(i != splinePoints.length - 1){
			getSegmentPoint(i);
		}
	}
	
	/**
	 * This method gets the two segment point for a splinepoint.
	 * @param i the index of the splinepoint
	 * @return an array of size 2 containing the two splinepoints
	 */
	public void getSegmentPoint(int i){
		if(splinePoints[i].getY() < splinePoints[i + 1].getY()){
			Vector3f vecNew = new Vector3f().add(splinePoints[i + 1]);
			vecNew.setY(splinePoints[i].getY());
			meshStructurePoints[splinePoints.length * 3 + 2 * i] = splinePoints[i];
			meshStructurePoints[splinePoints.length * 3 + 2 * i + 1] = vecNew;		
		}else{
			Vector3f vecNew = new Vector3f().add(splinePoints[i]);
			vecNew.setY(splinePoints[i + 1].getY());
			meshStructurePoints[splinePoints.length * 3 + 2 * i] = vecNew;
			meshStructurePoints[splinePoints.length * 3 + 2 * i + 1] = splinePoints[i + 1];
		}
	}
	
	/**
	 * Set the indices(in the meshStructurePoints) for the triangles.
	 * @param i the index of the splinepoint
	 */
	public void setTriangleIndices(int i){
		if(i < splinePoints.length - 1){
			addBoxTriangles(i);
			addUpperTriangles(i);
			addOverlappingTriangles(i);
		}
		
	}
	
	/**
	 * Add triangles to represent the lower boxes of the curve.
	 * @param i the index of the splinepoint
	 */
	public void addBoxTriangles(int i){
		meshStructureTrianglesIndices[i * 6] = i;
		meshStructureTrianglesIndices[i * 6 + 1] = i + 1;
		meshStructureTrianglesIndices[i * 6 + 2] = splinePoints.length * 3 + 2 * i;
		meshStructureTrianglesIndices[i * 6 + 3] = i + 1;
		meshStructureTrianglesIndices[i * 6 + 4] = splinePoints.length * 3 + 2 * i + 1;
		meshStructureTrianglesIndices[i * 6 + 5] = splinePoints.length * 3 + 2 * i;
	}
	
	/**
	 * Add triangles to represent the upper triangles of the curve.
	 * @param i the index of the splinepoint
	 */
	public void addUpperTriangles(int i){
		meshStructureTrianglesIndices[(splinePoints.length - 1) * 6 + 3 * i] = splinePoints.length * 3 + 2 * i; 
		meshStructureTrianglesIndices[(splinePoints.length - 1) * 6 + 3 * i + 1] = splinePoints.length * 3 + 2 * i + 1;
		if(splinePoints[i].getY() > splinePoints[i + 1].getY()){
			meshStructureTrianglesIndices[(splinePoints.length - 1) * 6 + 3 * i + 2] = splinePoints.length + i;
		}else{
			meshStructureTrianglesIndices[(splinePoints.length - 1) * 6 + 3 * i + 2] = splinePoints.length + i + 1;
		}
		
	}
	/**
	 * Add triangles to represent the overlapping(3D) triangles of the curve.
	 * @param i the index of the splinepoint
	 */
	public void addOverlappingTriangles(int i){
		meshStructureTrianglesIndices[(splinePoints.length - 1) * 9 + 6 * i] = splinePoints.length + i;
		meshStructureTrianglesIndices[(splinePoints.length - 1) * 9 + 6 * i + 1] = splinePoints.length + i + 1;
		meshStructureTrianglesIndices[(splinePoints.length - 1) * 9 + 6 * i + 2] = splinePoints.length * 2 + i;
		meshStructureTrianglesIndices[(splinePoints.length - 1) * 9 + 6 * i + 3] = splinePoints.length + i + 1;
		meshStructureTrianglesIndices[(splinePoints.length - 1) * 9 + 6 * i + 4] = splinePoints.length * 2 + i + 1;
		meshStructureTrianglesIndices[(splinePoints.length - 1) * 9 + 6 * i + 5] = splinePoints.length * 2 + i;
	}
}
