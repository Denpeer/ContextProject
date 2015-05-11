package com.mycompany.mavenproject1;

import com.jme3.asset.AssetManager;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Spline;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;

public class SplineCurve extends Spline {

	public SplineCurve(SplineType splineType, Vector3f[] controlPoints,
			float curveTension, boolean cycle) {
		super(splineType, controlPoints, curveTension, cycle);
	}

	/**
	 * This methods draws the curve with quads
	 * @param rootNode the rootnode
	 * @param assetManager the assetmanager
	 */
	public void drawCurve(Node rootNode, AssetManager assetManager, PhysicsSpace physics_space) {
		Material mat = new Material(assetManager,
				"Common/MatDefs/Misc/Unshaded.j3md");
		mat.setColor("Color", ColorRGBA.Cyan);
		Node node = new Node("curve");
		loopThroughSpline(node, assetManager, physics_space, mat);
		rootNode.attachChild(node);
	}
	
	/**
	 * This method loops through the whole spine
	 * @param node is the curve node
	 * @param assetManager the assetmanager
	 * @param physics_space the physic space
	 * @param mat the material
	 */
	public void loopThroughSpline(Node node, AssetManager assetManager, PhysicsSpace physics_space, Material mat){
		for (int i = 0; i < getControlPoints().size() - 1; i++) {
			double t = 0;
			while (t <= 1) {
				Vector3f[] vecs = getTwoVectors(i,t);
				drawBox(vecs, mat, physics_space, node);
				t = t + 0.01;
			}
		}
	}
	
	/**
	 * This method takes care of drawing a box and the collision of it
	 * @param vecs the coordinates where the box needs to be drawn
	 * @param mat the material of the box
	 * @param physics_space the physics of the box
	 * @param node the node
	 */
	public void drawBox(Vector3f[] vecs, Material mat, PhysicsSpace physics_space, Node node){
		RigidBodyControl phys = new RigidBodyControl(0f);
		Box box = new Box((float) vecs[0].x - vecs[1].x , vecs[0].getY() + 10, 1f);
		Geometry squad = new Geometry("square", box);
		squad.move(vecs[0].x, -15, 0);
		squad.setMaterial(mat);
		node.attachChild(squad);
		
		squad.addControl(phys);
		physics_space.add(phys);
	}
	
	/**
	 * This method return the coordinates of two close vectors on the spline
	 * @param controlPoint the controlpoint
	 * @param t how far until next controlpoint (0 is current controlpoint, 1 is next)
	 * @return an array with 2 vectors
	 */
	public Vector3f[] getTwoVectors(int controlPoint, double t){
		Vector3f[] vecs = new Vector3f[2];
		if (controlPoint == 0) {
			vecs[0] = this.getControlPoints().get(0);
			vecs[1] = this.getControlPoints().get(1);
			t++;
		} else {
			vecs[0] = interpolate((float)t, controlPoint, null);
			vecs[1] = interpolate((float)(t + 0.01), controlPoint, null);
		}
		return vecs;
	}
}
