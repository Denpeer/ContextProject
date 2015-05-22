package com.funkydonkies.camdetect;

import java.awt.image.BufferedImage;
import java.util.Arrays;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import com.funkydonkies.w4v3.Bridge;

/**
 * This class performs the image matrix math and is used by VideoCap to get the current image to display in the frame.
 * this includes setting the bg, thresholding into foreground/background, and identifying the interestPoints
 * which are used as controlPoints and can be accessed via the Bridge method getControlPoints() 
 * and getxdist() to know the x interval between elements of the array returned by getControlPoints().
 * also the found interestPoints are draws on the image as visual reference.
 * @author Olivier Dikken
 *
 */
public class Mat2Image implements Bridge {
	//init variables
    private Mat mat = new Mat();
    private BufferedImage img;
    private byte[] dat;
    private Mat bg = new Mat();
    private Mat res = new Mat();
    private Boolean bgSet = false;
    private float[] interestPoints;
    private final int xdist = 20;
    private final int medblur = 5;
    private final int maxcol = 255;
    private final int threshBlockSize = 71;
    private final int ignoreSize = 7;
    private final int circleDiam = 5;
    private final int numChannels = 3;
    
    /**
     * empty constructor without args for now.
     */
    public Mat2Image() {
    }
    
    /**
     * mat matrix to image; get space of mat matrix -> does image processing and finds interest points.
     * @param imMatrix matrix of image
     */
    public Mat2Image(final Mat imMatrix) {
        getSpace(imMatrix);
    }
    
    /**
     * get the Mat image matrix.
     * @return mat image matrix
     */
    public Mat getMat() {
    	return mat;
    }
    
    /**
     * when 'b' is pressed the class MyFrame calls setBG on VideoCap which calls this method.
     * the current image matrix becomes the mat matrix identifying the background (used by threshIt method).
     */
    public void setBg() {
    	bg = mat.clone();
    	bgSet = true;
    	System.out.println("Background has been set");
    }
    
    /**
     * absdiff(img,bg)>img2GRAY>blur>thresh.
     * res is used instead of mat to keep mat as the 'input matrix'.
     * @param imIn incomming frame
     * @param background background
     * @return result of img processing
     */
    public Mat threshIt(final Mat imIn, final Mat background) {
    	Core.absdiff(imIn, background, res);
    	Imgproc.cvtColor(res, res, Imgproc.COLOR_BGR2GRAY); //convert to grayscale for processign
    	Imgproc.medianBlur(res, res, medblur);
    	Imgproc.adaptiveThreshold(res, res, maxcol, Imgproc.ADAPTIVE_THRESH_GAUSSIAN_C, Imgproc.THRESH_BINARY, threshBlockSize, ignoreSize);
    	return res;
    }
    
    /**
     * Currently this method finds the highest point for every x belonging to xdist*k with k a natural.
     * @param im input matrix of image
     */
    public void updateIP(final Mat im) {
    	//xdist = 20; xrange = 640; total interest points = 640/20=32;
    	final double tempdiv = im.size().width / xdist;
    	final int numPoints = (int) Math.floor(tempdiv);
    	interestPoints = new float[numPoints];
    	Arrays.fill(interestPoints, (float) im.size().height);
    	//for every chosen x find the highest pixel value equal to 0
    	for (int i = 0; i < numPoints; i++) {
    		for (int j = 0; j < im.size().height; j++) {
    			final double val = im.get(j, i * xdist)[0];
    			if (val == 0.0) {
    				interestPoints[i] = j;
    				break;
    			}
    		}
    	}
    }
    
    /**
     * draws red dots at the locations of the found interestPoints to be used as visual feedback.
     * @param matMatrix the image matrix
     * @param iP array of interestPoints
     * @return the processed image converted to BGR with red circles drawn at the interest point locations
     */
    public Mat drawInterestPoints(final Mat matMatrix, final float[] iP) {
    	final Mat im = new Mat();
    	Imgproc.cvtColor(matMatrix, im, Imgproc.COLOR_GRAY2BGR); //convert back to bgr to draw interest points for visual feedback
    	for (int i = 0; i < iP.length; i++) {
    		Core.circle(im, new Point(i * xdist, iP[i]), circleDiam, new Scalar(maxcol), 0, 0, 2);
    	}
    	return im;
    }
    
    /**
     * make sure the byte and bufferedimage are of right size and 'color' settings.
     * @param matMatrix matrix of the image
     */
    public void prepareSpace(final Mat matMatrix) {
    	final int w = mat.cols(), h = mat.rows();
        if (dat == null || dat.length != w * h * numChannels) {
            dat = new byte[w * h * numChannels];
        }
        if (img == null || img.getWidth() != w || img.getHeight() != h
            || img.getType() != BufferedImage.TYPE_3BYTE_BGR) {
                img = new BufferedImage(w, h, BufferedImage.TYPE_3BYTE_BGR);
        }
    }
    
    /**
     * sets the matrix properties.
     * calls the methods to threshold the matrix (foreground/background).
     * then calls the methods to update the interestPoints and draw them on the image as visual feedback.
     * @param matMatrix the image matrix
     */
    public void getSpace(final Mat matMatrix) {
    	//convert from RGB to BGR
    	Imgproc.cvtColor(matMatrix, matMatrix, Imgproc.COLOR_RGB2BGR);
        if (bgSet) { //if bg is set then subtract foreground from background
        	res = threshIt(mat, bg);
        	updateIP(res);
        	res = drawInterestPoints(res, interestPoints); //convert to color image and draw red dots at the interest point locations
        	prepareSpace(res);
        } else {
        	this.res = mat;
        	prepareSpace(res);
        }
    }
    
    /**
     * this method is called by the VideoCap class.
     * it calls the image processing method and returns the processed segmented contoured image.
     * @param matMatrix matrix of image
     * @return processed image matrix as image
     */
        BufferedImage getImage(final Mat matMatrix) {
            getSpace(mat);
            res.get(0, 0, dat);
            img.getRaster().setDataElements(0, 0, 
                               res.cols(), res.rows(), dat);
        return img;
    }
        
//    static {
//        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
//    }
    
    /**
     * method comes from implementing the bridge interface.
     * used by classes outside of camdetect package to access the control points.
     * @return float array containing the control points
     */
	public float[] getControlPoints() {
		return interestPoints;
	}
	
	/**
     * method comes from implementing the bridge interface.
     * used by classes outside of camdetect package to access horizontal interval between control points.
     * this is used to be able to interpret the interestPoints array returned by the getControlPoints() method.
     * @return the xdist as int
     */
	public int getxdist() {
		return xdist;
	}
}
