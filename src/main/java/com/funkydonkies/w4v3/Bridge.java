package com.funkydonkies.w4v3;

/**
 * Bridge between openCV and DetectionInterpreter.
 * 
 * @author Danilo
 *
 */
public interface Bridge {
	
	/** Gets current state of background. 
	 * 
	 * @return current state of background
	 */
	boolean isBgSet();
	
	/** Gets dataset control points.
	 * 
	 * @return dataset containing the points on the curve
	 */
	float[] getControlPoints();
	
	/**
	 * gets dataset control points horizontal interval.
	 * @return int representing the x interval between elements of the array returned by getControlPoints()
	 */
	int getxdist();
	
	/**
	 * get the height of the images captured by the webcam.
	 * @return the largest y coordinate of the current image
	 */
	int getImageHeight();
	
	/**
	 * get the width of the images captured by the webcam.
	 * @return the largest x coordinate of the current image
	 */
	int getImageWidth();
}
