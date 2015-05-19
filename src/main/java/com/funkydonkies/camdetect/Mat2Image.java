package com.funkydonkies.camdetect;

import java.awt.image.BufferedImage;
import java.util.Arrays;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

import com.funkydonkies.w4v3.Bridge;

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
    public void setBg(){//save the current mat as the background to work with
    	this.bg = mat.clone();
    	bgSet = true;
    	System.out.println("Background has been set");
    }
    /**
     * absdiff(img,bg)>img2GRAY>blur>thresh
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
    public void drawInterestPoints(Mat im, float[] iP){
    	Imgproc.cvtColor(im,im,Imgproc.COLOR_GRAY2BGR);//convert back to bgr to draw interest points for visual feedback255
    	double[] red = {0,0,255};
    	int size = (int)im.channels();
    	System.out.println(size);
    	for(int i = 0; i < iP.length; i++){
    		im.put((int)iP[i], i*xdist, red);
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
     * 
     * @param mat matrix of image
     * @return processed frame
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
	public float[] getControlPoints() {
		// TODO Auto-generated method stub
		return null;
	}
}
