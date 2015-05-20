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
 * This class performs the image matrix math and is used by VideoCap to get the current image to display in the frame
 * this includes setting the bg, thresholding into foreground/background, and identifying the interestPoints
 * which are used as controlPoints and can be accessed via the Bridge method getControlPoints() 
 * and getxdist() to know the x interval between elements of the array returned by getControlPoints()
 * also the found interestPoints are draws on the image as visual reference
 * @author Olivier Dikken
 *
 */
public class Mat2Image implements Bridge{
	//init variables
    Mat mat = new Mat();
    BufferedImage img;
    byte[] dat;
    Mat bg = new Mat();
    Mat res = new Mat();
    Boolean bgSet = false;
    float[] interestPoints;
    int xdist = 20;
    
    /**
     * empty constructor without args for now
     */
    public Mat2Image() {
    }
    
    /**
     * mat matrix to image; get space of mat matrix -> does image processing and finds interest points
     * @param mat matrix of image
     */
    public Mat2Image(Mat mat) {
        getSpace(mat);
    }
    
    /**
     * when 'b' is pressed the class MyFrame calls setBG on VideoCap which calls this method
     * the current image matrix becomes the mat matrix identifying the background (used by threshIt method)
     */
    public void setBg(){
    	this.bg = mat.clone();
    	bgSet = true;
    	System.out.println("Background has been set");
    }
    
    /**
     * absdiff(img,bg)>img2GRAY>blur>thresh
     * res is used instead of mat to keep mat as the 'input matrix'
     * @param mat incomming frame
     * @param bg background
     * @param res result mat structure 
     * @return result of img processing
     */
    public Mat threshIt(Mat mat, Mat bg, Mat res){
    	Core.absdiff( mat, bg, res);
    	Imgproc.cvtColor(res,res,Imgproc.COLOR_BGR2GRAY);//convert to grayscale for processign
    	Imgproc.medianBlur(res, res, 5);
    	Imgproc.adaptiveThreshold(res, res, 255, Imgproc.ADAPTIVE_THRESH_GAUSSIAN_C, Imgproc.THRESH_BINARY, 71, 7);
    	return res;
    }
    
    /**
     * curerntly this method finds the highest point for every x belonging to xdist*k with k a natural
     * @param im
     * @return float array of interest points
     */
    public void updateIP(Mat im){
    	//xdist = 20; xrange = 640; total interest points = 32;
    	interestPoints = new float[32];//TODO: change from hardcoded to variables
    	//for every chosen x find the highest pixel value equal to 0
    	for(int i = 0; i < 24; i++){//TODO: change from hardcoded to variables
    		for(int j = 0; j< 480; j++){//TODO: change from hardcoded to variables
    			double val = im.get(j, i*xdist)[0];
    			if(val==0.0){
    				interestPoints[i]=j;
    				break;
    			}
    		}
    	}
    }
    
    /**
     * draws red dots at the locations of the found interestPoints to be used as visual feedback
     * @param im the image matrix
     * @param iP array of interestPoints
     */
    public void drawInterestPoints(Mat im, float[] iP){
    	Imgproc.cvtColor(im,im,Imgproc.COLOR_GRAY2BGR);//convert back to bgr to draw interest points for visual feedback
    	for(int i = 0; i < iP.length; i++){
    		//im.put((int)iP[i], i*xdist, red);
    		Core.circle(im, new Point(i*xdist,iP[i]), 5, new Scalar(255,0,0), 2);
    	}
    }
    
    /**
     * make sure the byte and bufferedimage are of right size and 'color' settings
     * @param mat matrix of the image
     */
    public void prepareSpace(Mat mat){
    	int w = mat.cols(), h = mat.rows();
        if (dat == null || dat.length != w * h * 3)
            dat = new byte[w * h * 3];
        if (img == null || img.getWidth() != w || img.getHeight() != h
            || img.getType() != BufferedImage.TYPE_3BYTE_BGR)
                img = new BufferedImage(w, h, 
                            BufferedImage.TYPE_3BYTE_BGR);
    }
    
    /**
     * sets the matrix properties
     * calls the methods to threshold the matrix (foreground/background)
     * then calls the methods to update the interestPoints and draw them on the image as visual feedback
     * @param mat the image matrix
     */
    public void getSpace(Mat mat) {
    	//convert from RGB to BGR
    	Imgproc.cvtColor(mat,mat,Imgproc.COLOR_RGB2BGR);
        this.mat = mat;
        if(bgSet){//if bg is set then subtract foreground from background
        	res = threshIt(mat,bg,res);
        	updateIP(res);
        	System.out.println(Arrays.toString(interestPoints));
        	drawInterestPoints(res,interestPoints);//convert to color image and draw red dots at the interest point locations
        	prepareSpace(res);
        } else {
        	this.res = mat;
        	prepareSpace(res);
        }
    }
    
    /**
     * this method is called by the VideoCap class
     * it calls the image processing method and returns the processed segmented contoured image
     * @param mat matrix of image
     * @return processed image matrix as image
     */
        BufferedImage getImage(Mat mat){
            getSpace(mat);
            res.get(0, 0, dat);
            img.getRaster().setDataElements(0, 0, 
                               res.cols(), res.rows(), dat);
        return img;
    }
    static{
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }
    
    /**
     * method comes from implementing the bridge interface
     * used by classes outside of camdetect package to access the control points
     */
	public float[] getControlPoints() {
		return interestPoints;
	}
	
	/**
     * method comes from implementing the bridge interface
     * used by classes outside of camdetect package to access horizontal interval between control points
     * this is used to be able to interpret the interestPoints array returned by the getControlPoints() method
     */
	public int getxdist() {
		return xdist;
	}
}
