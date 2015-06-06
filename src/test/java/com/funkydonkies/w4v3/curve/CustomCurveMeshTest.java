package com.funkydonkies.w4v3.curve;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.Random;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.mockito.Mockito.*;

import com.funkydonkies.curve.CustomCurveMesh;
import com.jme3.math.Vector3f;
import com.jme3.scene.Mesh;
import com.jme3.scene.VertexBuffer.Type;

public class CustomCurveMeshTest {
	static Vector3f[] splinePoints;
	static Vector3f splinePoint;
	static CustomCurveMesh customMesh;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		int x = 10;
		Random rand = new Random();
		splinePoints = new Vector3f[32];
		customMesh = mock(CustomCurveMesh.class);
		for(int i = 0; i < splinePoints.length; i++){
			splinePoints[i] = new Vector3f(x, rand.nextInt(30),1);
			x += 10;
		}
		
	}

	@Test
	public void createMeshTest() {
		CustomCurveMesh customMesh = new CustomCurveMesh(splinePoints);
		Mesh mesh = customMesh.createMesh();
		assertFalse(mesh.getBound() == null);
		assertFalse(mesh.getBuffer(Type.Position) == null);
		assertFalse(mesh.getBuffer(Type.TexCoord) == null);
		assertFalse(mesh.getBuffer(Type.Index) == null);
	}
	
	@Test
	public void setMeshPoints(){
		CustomCurveMesh customMesh = new CustomCurveMesh(splinePoints);
		Mesh mesh = customMesh.createMesh();
		
	}

}
