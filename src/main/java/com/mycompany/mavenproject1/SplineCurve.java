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
	 * 
	 * @param rootNode
	 * @param assetManager
	 */

	public void drawCurve(Node rootNode, AssetManager assetManager,
			PhysicsSpace physics_space) {
		Material mat = new Material(assetManager,
				"Common/MatDefs/Misc/Unshaded.j3md");
		mat.setColor("Color", ColorRGBA.Cyan);
		Node node = new Node("curve");
		for (int i = 0; i < getControlPoints().size() - 1; i++) {
			double t = 0;
			while (t <= 1) {
				//BoxCollisionShape shape = new BoxCollisionShape();
				RigidBodyControl phys = new RigidBodyControl(0f);
				Vector3f vec;
				Vector3f vec1;
				if (i == 0) {
					vec = this.getControlPoints().get(0);
					vec1 = this.getControlPoints().get(1);
					t++;
				} /*else if (i == getControlPoints().size() - 2) {
					vec = this.getControlPoints().get(
							this.getControlPoints().size() - 2);
					vec1 = this.getControlPoints().get(
							this.getControlPoints().size() - 1);
					t++;
				} */else {
					vec = interpolate((float) t, i, null);
					vec1 = interpolate((float) (t + 0.01), i, null);
				}

				Box box = new Box((float) vec1.x - vec.x , vec.getY() + 10, 1f);
				Geometry squad = new Geometry("square", box);
				squad.move(vec.x, -15, 0);
				squad.setMaterial(mat);
				node.attachChild(squad);
				t = t + 0.01;
				squad.addControl(phys);
				physics_space.add(phys);
			}
		}
		rootNode.attachChild(node);
	}
}
