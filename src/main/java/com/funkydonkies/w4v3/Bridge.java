package com.funkydonkies.w4v3;

/**
 * Bridge between openCV and DetectionInterpreter.
 * 
 * @author Danilo
 *
 */
public interface Bridge {
	
	/** Gets dataset control points.
	 * 
	 * @return dataset containing the points on the curve
	 */
	float[] getControlPoints();
	/**
	 * gets dataset control points horizontal interval.
	 * @return int representing the x interval between elements of the array returned by getControlPoints()
	 */
	int getxdist();//returns the horizontal interval between control points
}
