package com.mycompany.mavenproject1;

import java.awt.Point;

import com.jme3.math.Vector3f;
import com.jogamp.openal.sound3d.Vec3f;

public class Bezier {
	private Vector3f[] controlPoints;
	
	public Bezier(Vector3f[] points){
		this.controlPoints = points;
	}
	
	public Vector3f getPointOnFrame(double t){
		
		double yCoord = 1 * Math.pow(t, 0) * Math.pow((1 - t), 3)  * controlPoints[0].x
			     + 3 * Math.pow(t, 1) * Math.pow((1 - t), 2)  * controlPoints[1].x
			     + 3 * Math.pow(t, 2) * Math.pow((1 - t), 1)  * controlPoints[2].x
			     + 1 * Math.pow(t, 3) * Math.pow((1 - t), 0)  * controlPoints[3].x;
		
		double xCoord = 1 * Math.pow(t, 0) * Math.pow((1 - t), 3)  * controlPoints[0].y
			     + 3 * Math.pow(t, 1) * Math.pow((1 - t), 2)  * controlPoints[1].y
			     + 3 * Math.pow(t, 2) * Math.pow((1 - t), 1)  * controlPoints[2].y
			     + 1 * Math.pow(t, 3) * Math.pow((1 - t), 0)  * controlPoints[3].y;
		Vector3f vec = new Vector3f();
		vec.set((float)yCoord, (float)xCoord, (float)0.0);
		return vec;
	}
}
