package com.funkydonkies.w4v3;

import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.material.Material;
import com.jme3.math.Spline;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;

/**
 * This class represent the spline(curve).
 * 
 * @author SDumasy
 *
 */
public class SplineCurve extends Spline {
	
	private static final double BOXWIDTH = 0.01;
	private static final double ADDITIONALBOXHEIGHT = 10;
	private static final float YCURVETRANSLATION = -15;
	/**
	 * The constructor of the SplineCurve class.
	 * @param splineType the type of the SplineCurve, in our case Catmulrom
	 * @param controlPoints the controlpoints of the curve
	 * @param curveTension the tension of the curve, betweeen 0-1
	 * @param cycle the cycle of the curve
	 */
	public SplineCurve(final SplineType splineType, 
			final Vector3f[] controlPoints, final float curveTension, 
			final boolean cycle) {
		super(splineType, controlPoints, curveTension, cycle);
	}

	/**
	 * This method draws the curve.
	 * @param rootNode the rootnode of the program
	 * @param mat takes a pre-defined jme3 material
	 * @param physicsSpace takes a pre-defined jme3 physicsSpace
	 */
	public void drawCurve(final Node rootNode, final Material mat, 
			final PhysicsSpace physicsSpace) {
		final RigidBodyControl phys = new RigidBodyControl(0f);
		final Node node = new Node("curve");
		loopThroughSpline(node, physicsSpace, mat);
		rootNode.attachChild(node);
		node.addControl(phys);
		physicsSpace.add(phys);
	}
	
	/**
	 * This method loops through the whole spine.
	 * @param node is the curve node
	 * @param physicsSpace the physic space
	 * @param mat the material
	 */
	public void loopThroughSpline(final Node node, 
			final PhysicsSpace physicsSpace, final Material mat) { 
		for (int i = 0; i < getControlPoints().size() - 1; i++) {
			double t = 0;
			while (t <= 1) {
				final Vector3f[] vecs = getTwoVectors(i, t);
				drawBox(vecs, mat, physicsSpace, node);
				t = t + BOXWIDTH;
			}
		}
	}
	
	/**
	 * This method takes care of drawing a box and the collision of it.
	 * @param vecs the coordinates where the box needs to be drawn
	 * @param mat the material of the box
	 * @param physicsSpace the physics of the box
	 * @param node the node ???
	 * 
	 * @return returns a node... what node?
	 */
	public Node drawBox(final Vector3f[] vecs, final Material mat,
			final PhysicsSpace physicsSpace, final Node node) {
		
		final Box box = new Box(vecs[0].x - vecs[1].x , 
				(float) (vecs[0].getY() + ADDITIONALBOXHEIGHT), 1f);
		final Geometry squad = new Geometry("square", box);
		squad.move(vecs[0].x, YCURVETRANSLATION, 0);
		squad.setMaterial(mat);
		node.attachChild(squad);
		return node;
	}
	
	/**
	 * This method return the coordinates of two close vectors on the spline.
	 * @param controlPoint the controlpoint
	 * @param t how far until next controlpoint 
	 * @return an array with 2 vectors
	 */
	public Vector3f[] getTwoVectors(final int controlPoint,
			final double t) {
		final Vector3f[] vecs = new Vector3f[2];
		if (controlPoint == 0) {
			vecs[0] = this.getControlPoints().get(0);
			vecs[1] = this.getControlPoints().get(1);
		} else {
			vecs[0] = interpolate((float) t, controlPoint, null);
			vecs[1] = interpolate((float) (t + BOXWIDTH), controlPoint, null);
		}
		return vecs;
	}
}
