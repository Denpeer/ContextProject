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
	private float[] previousInterestPoints;
	private float[] prevPreviousInterestPoints;
	private static final int DEFAULT_XDIST = 20;
	private static final int MEDBLUR = 3;
	private static final int MAXCOL = 255;
	private static final int THRESHBLOCKSIZE = 41;
	private static final int IGNORESIZE = 5;
	private static final int CIRCLEDIAM = 5;
	private static final int NUMCHANNELS = 3;
	private static final float MAX_JUMP_DIST = 10.0f;
	private static final int CONNECTED_PIXEL_BLOCK_SIZE = 5;
	private static final int DEFAULT_HEIGHT = 480;
	private static final int DEFAULT_MIN_HEIGHT = 30;
	private static final int BOUND_MOVE_AMOUNT = 10;
	private int upperBound = DEFAULT_MIN_HEIGHT;
	private int lowerBound = DEFAULT_HEIGHT - 1;

	private int xDist = DEFAULT_XDIST;

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
		lowerBound = imMatrix.height();
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
		Imgproc.adaptiveThreshold(res, res, MAXCOL, Imgproc.ADAPTIVE_THRESH_GAUSSIAN_C,
				Imgproc.THRESH_BINARY, THRESHBLOCKSIZE, IGNORESIZE);
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
		resetPrevIPArray(im);
		Arrays.fill(interestPoints, (float) lowerBound);
		for (int i = 0; i < numPoints; i++) { // for every chosen x find the
												// highest pixel value equal to
												// 0
			for (int j = upperBound; j < lowerBound; j++) {
				final double val = im.get(j, i * xDist)[0];
				if (val == 0.0 && isConnected(im, CONNECTED_PIXEL_BLOCK_SIZE, i * xDist, j)) {
					if (heightDiffPass(prevPreviousInterestPoints[i], j)) {
						interestPoints[i] = j;
					} else { // take the lowest of the two (ex: y:480 is
								// bottom
								// and y:0 is top)
						interestPoints[i] = Math.max(prevPreviousInterestPoints[i], j);
					}
					prevPreviousInterestPoints[i] = previousInterestPoints[i];
					previousInterestPoints[i] = j;
					break;
				}
			}
		}
	}

	/**
	 * Checks if the concerned pixel is the center of a block of connected
	 * pixels. (i.e. not a single pixel)
	 * 
	 * @param im
	 *            the mat of the current image being processed
	 * @param blockSize
	 *            is minimum size the connected pixel block has to be for it to
	 *            pass. Should be uneven.
	 * @param pixelX
	 *            the x of the pixel being checked
	 * @param pixelY
	 *            the y of the pixel being checked
	 * @return true if the pixel is the center of a block of detected pixels.
	 */
	public boolean isConnected(final Mat im, final int blockSize, final int pixelX, final int pixelY) {
		int halfBlock;
		int newPixelX = pixelX;
		int newPixelY = pixelY;
		if (blockSize % 2 != 0) {
			halfBlock = (blockSize - 1) / 2;
		} else {
			halfBlock = blockSize / 2;
		}
		if (pixelX <= halfBlock) {
			newPixelX += halfBlock;
		}
		if (pixelX >= im.size().width - halfBlock) {
			newPixelX -= halfBlock;
		}
		if (pixelY <= halfBlock) {
			newPixelY += halfBlock;
		}
		if (pixelY >= im.size().height - halfBlock) {
			newPixelY -= halfBlock;
		}
		final Mat subim = im.submat(newPixelY - halfBlock, newPixelY + halfBlock, newPixelX
				- halfBlock, newPixelX + halfBlock);
		final double sum = Core.sumElems(subim).val[0] / MAXCOL;
		if (sum == 0.0) {
			return true;
		}
		return false;
	}

	/**
	 * checks whether the interest points have not made a big jump since the
	 * last frame. it is supposed to take care of flickering spots detected by
	 * the camera.
	 * 
	 * @param prev
	 *            the previous interest point height
	 * @param height
	 *            the current interest point height
	 * @return whether the diff is less than the threshold
	 */
	public boolean heightDiffPass(final float prev, final float height) {
		if (Math.abs(prev - height) < MAX_JUMP_DIST) {
			return true;
		}
		return false;
	}

	/**
	 * reset the previousInterestPoints Array and prevPreviousInterestPoints
	 * Array and fill with the default value.
	 * 
	 * @param im
	 *            the mat of the image currently being processed
	 */
	public void resetPrevIPArray(final Mat im) {
		if (prevPreviousInterestPoints == null) {
			prevPreviousInterestPoints = new float[numPoints];
			Arrays.fill(prevPreviousInterestPoints, (float) im.size().height);
		}
		if (previousInterestPoints == null) {
			previousInterestPoints = new float[numPoints];
			Arrays.fill(previousInterestPoints, (float) im.size().height);
		}
		if (interestPoints.length != previousInterestPoints.length) {
			previousInterestPoints = new float[numPoints];
			Arrays.fill(previousInterestPoints, (float) im.size().height);
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
			Core.circle(im, new Point(i * xDist, iP[i]), CIRCLEDIAM, new Scalar(MAXCOL), 0, 0, 2);
		}
		return im;
	}

	/**
	 * draw red horizontal lines at the beginning and end of the influence zone.
	 * 
	 * @param matMatrix
	 *            the image to which the delimiting lines need to be added
	 * @return the image with the lines added
	 */
	public Mat drawInfluenceZoneDelimiters(final Mat matMatrix) {
		final Mat im = matMatrix.clone();
		Core.line(im, new Point(0, lowerBound), new Point(im.width(), lowerBound), new Scalar(0, 0,
				MAXCOL));
		Core.line(im, new Point(0, upperBound), new Point(im.width(), upperBound), new Scalar(0, 0,
				MAXCOL));
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
			// convert to color image and draw red dots at the interest point
			// locations
			res = drawInterestPoints(res, interestPoints);
			res = drawInfluenceZoneDelimiters(res);
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
	 * Method comes from implementing the bridge interface. It is used by
	 * classes outside of camdetect package to access the control points.
	 * Normalize the control points between 0 and 1 with 0 being lowest and 1
	 * highest along the vertical axis.
	 * 
	 * @return float array containing the control points
	 */
	public float[] getControlPoints() {
		final int defaultNumPoints = 32;
		float[] normCP = new float[defaultNumPoints];
		Arrays.fill(normCP, 0);
		
		if (interestPoints != null) {
			normCP = Arrays.copyOf(interestPoints, numPoints);
		}
		
		for (int i = 0; i < normCP.length; i++) {
			normCP[i] = normCP[i] - upperBound;
			normCP[i] = Math.abs(normCP[i] - (lowerBound - upperBound));
			normCP[i] = normCP[i] / (lowerBound - upperBound);
		}
		return normCP;
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
		} else {
			System.out.println(newDist + newXDist
					+ " is and invalid xDist. newXDist must be inbetween: 0 and " + mat.width());
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
	 * 
	 * @return boolean state of background setting
	 */
	public boolean isBgSet() {
		return bgSet;
	}

	/**
	 * increment the upper bound.
	 */
	public void upInc() {
		if (upperBound > DEFAULT_MIN_HEIGHT) {
			upperBound -= BOUND_MOVE_AMOUNT;
		}
	}

	/**
	 * decrement the upper bound.
	 */
	public void upDec() {
		if (upperBound < (lowerBound - BOUND_MOVE_AMOUNT)) {
			upperBound += BOUND_MOVE_AMOUNT;
		}
	}

	/**
	 * increment the lower bound.
	 */
	public void lowInc() {
		if (lowerBound > (upperBound + BOUND_MOVE_AMOUNT)) {
			lowerBound -= BOUND_MOVE_AMOUNT;
		}
	}

	/**
	 * decrement the lower bound.
	 */
	public void lowDec() {
		if (lowerBound < mat.height() - 1) {
			lowerBound += BOUND_MOVE_AMOUNT;
		}
	}
}
