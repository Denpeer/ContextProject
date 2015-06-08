package com.funkydonkies.camdetect;

import java.awt.image.BufferedImage;
import java.util.Arrays;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import com.funkydonkies.interfaces.Bridge;

/**
 * This class performs the image matrix math and is used by VideoCap to get the
 * current image to display in the frame. this includes setting the bg,
 * thresholding into foreground/background, and identifying the interestPoints
 * which are used as controlPoints and can be accessed via the Bridge method
 * getControlPoints() and getXDIST() to know the x interval between elements of
 * the array returned by getControlPoints(). also the found interestPoints are
 * draws on the image as visual reference.
 * 
 * @author Olivier Dikken
 *
 */
public class Mat2Image implements Bridge {
	private Mat mat = new Mat();
	private BufferedImage img;
	private byte[] dat;
	private Mat bg = new Mat();
	private Mat res = new Mat();
    private Boolean bgSet = false;
	private float[] interestPoints;
	private static final int DEFAULT_XDIST = 20;
	private static final int MEDBLUR = 5;
	private static final int MAXCOL = 255;
	private static final int THRESHBLOCKSIZE = 71;
	private static final int IGNORESIZE = 7;
	private static final int CIRCLEDIAM = 5;
	private static final int NUMCHANNELS = 3;

    private int  xDist = DEFAULT_XDIST;

	private int numPoints = 0;

	/**
	 * Empty Constructor.
	 */
	public Mat2Image() {

	}

	/**
	 * Mat matrix to image; get space of mat matrix -> does image processing and
	 * finds interest points.
	 * 
	 * @param imMatrix
	 *            matrix of image
	 */
	public Mat2Image(final Mat imMatrix) {
		getSpace(imMatrix);
	}

	/**
	 * Get the Mat image matrix.
	 * 
	 * @return mat image matrix
	 */
	public Mat getMat() {
		return mat;
	}

	/**
	 * When 'b' is pressed the class MyFrame calls setBG on VideoCap which calls
	 * this method. the current image matrix becomes the mat matrix identifying
	 * the background (used by threshIt method).
	 */
	public void setBg() {
		bg = mat.clone();
		bgSet = true;
	}

	/**
	 * Note: absdiff(img,bg)>img2GRAY>blur>thresh. Res is used instead of mat to
	 * keep mat as the 'input matrix'.
	 * 
	 * @param imIn
	 *            incomming frame
	 * @param background
	 *            background
	 * @return result of img processing
	 */
	public Mat threshIt(final Mat imIn, final Mat background) {
		Core.absdiff(imIn, background, res);
		Imgproc.cvtColor(res, res, Imgproc.COLOR_BGR2GRAY); // convert to
															// grayscale for
															// processign
		Imgproc.medianBlur(res, res, MEDBLUR);
		Imgproc.adaptiveThreshold(res, res, MAXCOL,
				Imgproc.ADAPTIVE_THRESH_GAUSSIAN_C, Imgproc.THRESH_BINARY,
				THRESHBLOCKSIZE, IGNORESIZE);
		return res;
	}

	/**
	 * This method finds the highest point for every x belonging to xDist*k with
	 * k a natural.
	 * 
	 * @param im
	 *            input matrix of image
	 */
	public void updateIP(final Mat im) {
		// Default values: XDIST = 20; xrange (image width) = 640; total
		// interest points = 640/20=32;
		final double tempdiv = im.size().width / xDist;
		numPoints = (int) Math.floor(tempdiv);
		interestPoints = new float[numPoints];
		Arrays.fill(interestPoints, (float) im.size().height);
		for (int i = 0; i < numPoints; i++) { // for every chosen x find the
												// highest pixel value equal to
												// 0
			for (int j = 0; j < im.size().height; j++) {
				final double val = im.get(j, i * xDist)[0];
				if (val == 0.0) {
					interestPoints[i] = j;
					break;
				}
			}
		}
	}

	/**
	 * Draws red dots at the locations of the found interestPoints to be used as
	 * visual feedback.
	 * 
	 * @param matMatrix
	 *            the image matrix
	 * @param iP
	 *            array of interestPoints
	 * @return the processed image converted to BGR with red circles drawn at
	 *         the interest point locations
	 */
	public Mat drawInterestPoints(final Mat matMatrix, final float[] iP) {
		final Mat im = new Mat();
		// convert back to bgr to draw interest points for visual feedback
		Imgproc.cvtColor(matMatrix, im, Imgproc.COLOR_GRAY2BGR);
		for (int i = 0; i < iP.length; i++) {
			Core.circle(im, new Point(i * xDist, iP[i]), CIRCLEDIAM,
					new Scalar(MAXCOL), 0, 0, 2);
		}
		return im;
	}

	/**
	 * Make sure the byte and bufferedimage are of right size and 'color'
	 * settings.
	 * 
	 * @param matMatrix
	 *            matrix of the image
	 */
	public void prepareSpace(final Mat matMatrix) {
		final int w = mat.cols(), h = mat.rows();
		if (dat == null || dat.length != w * h * NUMCHANNELS) {
			dat = new byte[w * h * NUMCHANNELS];
		}
		if (img == null || img.getWidth() != w || img.getHeight() != h
				|| img.getType() != BufferedImage.TYPE_3BYTE_BGR) {
			img = new BufferedImage(w, h, BufferedImage.TYPE_3BYTE_BGR);
		}
	}

	/**
	 * Sets the matrix properties. calls the methods to threshold the matrix
	 * (foreground/background). then calls the methods to update the
	 * interestPoints and draw them on the image as visual feedback.
	 * 
	 * @param matMatrix
	 *            the image matrix
	 */
	public void getSpace(final Mat matMatrix) {
		// convert from RGB to BGR
		Imgproc.cvtColor(matMatrix, matMatrix, Imgproc.COLOR_RGB2BGR);
		if (bgSet) { // if bg is set then subtract foreground from background
			res = threshIt(mat, bg);
			updateIP(res);
			//convert to color image and draw red dots at the interest point locations
			res = drawInterestPoints(res, interestPoints); 
			prepareSpace(res);
		} else {
			this.res = mat;
			prepareSpace(res);
		}
	}

	/**
	 * This method is called by the VideoCap class. it calls the image
	 * processing method and returns the processed segmented contoured image.
	 * 
	 * @param matMatrix
	 *            matrix of image
	 * @return processed image matrix as image
	 */
	BufferedImage getImage(final Mat matMatrix) {
		Core.flip(mat, mat, 1);
		getSpace(mat);
		res.get(0, 0, dat);
		img.getRaster().setDataElements(0, 0, res.cols(), res.rows(), dat);
		return img;
	}

	/**
	 * Method comes from implementing the bridge interface. It is used by classes
	 * outside of camdetect package to access the control points.
	 * 
	 * @return float array containing the control points
	 */
	public float[] getControlPoints() {
		return Arrays.copyOf(interestPoints, numPoints);
	}

	/**
	 * Method comes from implementing the bridge interface. used by classes
	 * outside of camdetect package to access horizontal interval between
	 * control points. this is used to be able to interpret the interestPoints
	 * array returned by the getControlPoints() method.
	 * 
	 * @return the xDist as int
	 */
	public int getxdist() {
		return xDist;
	}

	/**
	 * Modifies the horizontal interval between interest points.
	 * 
	 * @param newXDist
	 *            the new horizontal interval
	 */
	public void setxdist(final int newXDist) {
		final String newDist = "newXDist: ";
		if (newXDist > 0 && newXDist < mat.width()) {
			xDist = newXDist;
			System.out.println(newDist + newXDist);
		} else {
			System.out
					.println(newDist
							+ newXDist
							+ " is and invalid xDist. newXDist must be inbetween: 0 and "
							+ mat.width());
		}
	}

	/**
	 * Method comes from implementing the bridge interface.
	 * 
	 * @return the height of the current image
	 */
	public int getImageHeight() {
		return mat.height();
	}

	/**
	 * Method comes from implementing the bridge interface.
	 * 
	 * @return the width of the current image
	 */
	public int getImageWidth() {
		return mat.width();
	}
	
	/**
	 * Returns whether the background had been set or not.
	 * @return boolean state of background setting
	 */
	public boolean isBgSet() {
		return bgSet;
	}

}
