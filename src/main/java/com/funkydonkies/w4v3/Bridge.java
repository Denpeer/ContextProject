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
}
