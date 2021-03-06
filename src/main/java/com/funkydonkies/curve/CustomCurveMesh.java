package com.funkydonkies.curve;

import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Mesh;
import com.jme3.scene.VertexBuffer.Type;
import com.jme3.util.BufferUtils;

/**
 * This class takes care of the mesh for the curve.
 * 
 * @author SDumasy
 *
 */
public class CustomCurveMesh {
	private static float launchPadHeight;
	private static final float LAUNCHPAD_WIDTH = 50f;
	private static final int COORDS_PER_TRIANGLE = 3;
	private Vector3f[] meshStructurePoints;
	private int[] meshStructureTrianglesIndices;
	private Vector3f[] splinePoints;
	private Vector2f[] texCoords;
	private float[] normals;

	/**
	 * This class take care of the mesh for the spline.
	 * 
	 * @param sPoints
	 *            the spline points on the surface of the spline
	 */
	public CustomCurveMesh(final Vector3f[] sPoints) {
		splinePoints = sPoints.clone();
		final int mulLength = 5, offset = 1, additionalSplinePointLength = 15, launchpadTriangleSize = 12; // TODO
																											// rename
		final int meshStructurePointsSize = splinePoints.length * mulLength + offset;
		meshStructurePoints = new Vector3f[meshStructurePointsSize];
		meshStructureTrianglesIndices = new int[(splinePoints.length - 1)
				* additionalSplinePointLength + launchpadTriangleSize];
		texCoords = new Vector2f[splinePoints.length];
		normals = new float[meshStructurePointsSize * COORDS_PER_TRIANGLE];
	}

	/**
	 * This method creates a mesh.
	 * 
	 * @return the created mesh
	 */
	public Mesh createMesh() {
		for (int i = 0; i < splinePoints.length; i++) {
			setMeshPoints(i);
			setTriangleIndices(i);
			texCoords[i] = new Vector2f(1, 1);
		}
		final int offSet = 3;
		for (int i = 0; i < normals.length; i = i + offSet) {
			normals[i] = 0;
			normals[i + 1] = 0;
			normals[i + 2] = 1;
		}
		launchPadHeight = splinePoints[0].getY();
		final Mesh mesh = new Mesh();
		final int indexBuffer = 3;
		mesh.setBuffer(Type.Position, indexBuffer,
				BufferUtils.createFloatBuffer(meshStructurePoints));
		mesh.setBuffer(Type.TexCoord, 2, BufferUtils.createFloatBuffer(texCoords));
		mesh.setBuffer(Type.Index, indexBuffer,
				BufferUtils.createIntBuffer(meshStructureTrianglesIndices));
		mesh.setBuffer(Type.Normal, indexBuffer, BufferUtils.createFloatBuffer(normals));
		mesh.updateBound();
		return mesh;
	}

	/**
	 * This method gets all the points needed to genearate triangles for the mesh at a splinepoint.
	 * 
	 * @param i
	 *            the index of the splinepoint
	 */
	public void setMeshPoints(final int i) {
		// TODO add comments
		meshStructurePoints[i] = new Vector3f().add(splinePoints[i].getX(), 0, 0);
		meshStructurePoints[splinePoints.length + i] = splinePoints[i];
		meshStructurePoints[splinePoints.length * 2 + i] = new Vector3f().add(splinePoints[i])
				.setZ(-1);

		if (i != splinePoints.length - 1) {
			getSegmentPoint(i); // TODO comment what this does
		}
		getLaunchPadPoints();
	}

	/**
	 * This method gets the launchpad vertices.
	 */
	public void getLaunchPadPoints() {
		final int offSet = 2, additionalSplinePointsLength = 5;
		meshStructurePoints[splinePoints.length * additionalSplinePointsLength - offSet] = new Vector3f()
				.add(meshStructurePoints[0]).setX(meshStructurePoints[0].getX() - LAUNCHPAD_WIDTH);
		meshStructurePoints[splinePoints.length * additionalSplinePointsLength - 1] = new Vector3f()
				.add(meshStructurePoints[splinePoints.length]).setX(
						meshStructurePoints[splinePoints.length].getX() - LAUNCHPAD_WIDTH);
		meshStructurePoints[splinePoints.length * additionalSplinePointsLength] = new Vector3f()
				.add(meshStructurePoints[splinePoints.length * 2]).setX(
						meshStructurePoints[splinePoints.length * 2].getX() - LAUNCHPAD_WIDTH);
	}

	/**
	 * This method gets the two segment point for a splinepoint.
	 * 
	 * @param i
	 *            the index of the splinepoint
	 */
	public void getSegmentPoint(final int i) {
		final int mulLength = 3, offset = 2; // TODO rename
		// TODO add comments
		if (splinePoints[i].getY() < splinePoints[i + 1].getY()) {
			final Vector3f vecNew = new Vector3f().add(splinePoints[i + 1]);
			vecNew.setY(splinePoints[i].getY());
			meshStructurePoints[splinePoints.length * mulLength + offset * i] = splinePoints[i];
			meshStructurePoints[splinePoints.length * mulLength + offset * i + 1] = vecNew;
		} else {
			final Vector3f vecNew = new Vector3f().add(splinePoints[i]);
			vecNew.setY(splinePoints[i + 1].getY());
			meshStructurePoints[splinePoints.length * mulLength + offset * i] = vecNew;
			meshStructurePoints[splinePoints.length * mulLength + offset * i + 1] = splinePoints[i + 1];
		}
	}

	/**
	 * Set the indices(in the meshStructurePoints) for the triangles.
	 * 
	 * @param i
	 *            the index of the splinepoint
	 */
	public void setTriangleIndices(final int i) {
		if (i < (splinePoints.length - 1)) { // TODO add comment explaining range
			addBoxTriangles(i);
			addUpperTriangles(i);
			addOverlappingTriangles(i);
			addLaunchPadTriangles();
		}

	}

	/**
	 * Add triangles to represent the lower boxes of the curve.
	 * 
	 * @param i
	 *            the index of the splinepoint
	 */
	public void addBoxTriangles(final int i) {
		final int mulOffset = 6, range = 5, mulLength = 3, offset = 2; // TODO rename
		// TODO add comments
		meshStructureTrianglesIndices[i * mulOffset] = i;
		meshStructureTrianglesIndices[i * mulOffset + 1] = i + 1;
		meshStructureTrianglesIndices[i * mulOffset + range - 2] = i + 1;
		meshStructureTrianglesIndices[i * mulOffset + 2] = splinePoints.length * mulLength + offset
				* i;
		meshStructureTrianglesIndices[i * mulOffset + range] = splinePoints.length * mulLength
				+ offset * i;
		meshStructureTrianglesIndices[i * mulOffset + range - 1] = splinePoints.length * mulLength
				+ offset * i + 1;
	}

	/**
	 * Add triangles to represent the upper triangles of the curve.
	 * 
	 * @param i
	 *            the index of the splinepoint
	 */
	public void addUpperTriangles(final int i) {
		final int mulOffset = 6, mulLength = 3, offset = 2; // TODO rename
		// TODO add comments
		meshStructureTrianglesIndices[(splinePoints.length - 1) * mulOffset + mulLength * i] = splinePoints.length
				* mulLength + offset * i;
		meshStructureTrianglesIndices[(splinePoints.length - 1) * mulOffset + mulLength * i + 1] = splinePoints.length
				* mulLength + offset * i + 1;
		if (splinePoints[i].getY() > splinePoints[i + 1].getY()) {
			meshStructureTrianglesIndices[(splinePoints.length - 1) * mulOffset + mulLength * i + 2] = splinePoints.length
					+ i;
		} else {
			meshStructureTrianglesIndices[(splinePoints.length - 1) * mulOffset + mulLength * i + 2] = splinePoints.length
					+ i + 1;
		}

	}

	/**
	 * Add triangles to represent the overlapping(3D) triangles of the curve.
	 * 
	 * @param i
	 *            the index of the splinepoint
	 */
	public void addOverlappingTriangles(final int i) {
		final int mulOffset = 9, range = 5, mulLength = 6; // TODO rename
		// TODO add comments
		meshStructureTrianglesIndices[(splinePoints.length - 1) * mulOffset + mulLength * i] = splinePoints.length
				+ i;
		meshStructureTrianglesIndices[(splinePoints.length - 1) * mulOffset + mulLength * i + 1] = splinePoints.length
				+ i + 1;
		meshStructureTrianglesIndices[(splinePoints.length - 1) * mulOffset + mulLength * i + 2] = splinePoints.length
				* 2 + i;
		meshStructureTrianglesIndices[(splinePoints.length - 1) * mulOffset + mulLength * i + range
				- 2] = splinePoints.length + i + 1;
		meshStructureTrianglesIndices[(splinePoints.length - 1) * mulOffset + mulLength * i + range
				- 1] = splinePoints.length * 2 + i + 1;
		meshStructureTrianglesIndices[(splinePoints.length - 1) * mulOffset + mulLength * i + range] = splinePoints.length
				* 2 + i;
	}

	/**
	 * This method adds triangles for the launchbox.
	 */
	public void addLaunchPadTriangles() {
		final int offSet = 2, multiplier = 5, additionalSplinePointLength = 15;
		int i = 1;
		meshStructureTrianglesIndices[(splinePoints.length - 1) * additionalSplinePointLength] = splinePoints.length
				* multiplier - offSet;
		meshStructureTrianglesIndices[(splinePoints.length - 1) * additionalSplinePointLength + i] = 0;
		i++;
		meshStructureTrianglesIndices[(splinePoints.length - 1) * additionalSplinePointLength + i] = splinePoints.length;
		i++;

		meshStructureTrianglesIndices[(splinePoints.length - 1) * additionalSplinePointLength + i] = splinePoints.length
				* multiplier - offSet;
		i++;
		meshStructureTrianglesIndices[(splinePoints.length - 1) * additionalSplinePointLength + i] = splinePoints.length;
		i++;
		meshStructureTrianglesIndices[(splinePoints.length - 1) * additionalSplinePointLength + i] = splinePoints.length
				* multiplier - 1;
		i++;

		meshStructureTrianglesIndices[(splinePoints.length - 1) * additionalSplinePointLength + i] = splinePoints.length
				* multiplier - 1;
		i++;
		meshStructureTrianglesIndices[(splinePoints.length - 1) * additionalSplinePointLength + i] = splinePoints.length
				* offSet;
		i++;
		meshStructureTrianglesIndices[(splinePoints.length - 1) * additionalSplinePointLength + i] = splinePoints.length
				* multiplier;
		i++;

		meshStructureTrianglesIndices[(splinePoints.length - 1) * additionalSplinePointLength + i] = splinePoints.length
				* multiplier - 1;
		i++;
		meshStructureTrianglesIndices[(splinePoints.length - 1) * additionalSplinePointLength + i] = splinePoints.length;
		i++;
		meshStructureTrianglesIndices[(splinePoints.length - 1) * additionalSplinePointLength + i] = splinePoints.length
				* offSet;
	}

	/**
	 * The getter for the launchPadHeight.
	 * 
	 * @return the launchPadHeight
	 */
	public static float getLaunchPadHeight() {
		return launchPadHeight;
	}

}
