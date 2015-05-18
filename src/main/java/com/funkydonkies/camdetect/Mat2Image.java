package com.funkydonkies.camdetect;

import java.awt.image.BufferedImage;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

public class Mat2Image {
    Mat mat = new Mat();
    BufferedImage img;
    byte[] dat;
    Mat bg = new Mat();
    Mat res = new Mat();
    Boolean bgSet = false;
    public Mat2Image() {
    }
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
    	Imgproc.cvtColor(res,res,Imgproc.COLOR_BGR2GRAY);
    	Imgproc.medianBlur(res, res, 5);
    	Imgproc.adaptiveThreshold(res, res, 255, Imgproc.ADAPTIVE_THRESH_GAUSSIAN_C, Imgproc.THRESH_BINARY, 51, 5);
    	return res;
    }
    public void getSpace(Mat mat) {
    	//convert from RGB to BGR
    	Imgproc.cvtColor(mat,mat,Imgproc.COLOR_RGB2BGR);
    	
        this.mat = mat;
        
        //if bg is set then subtract foreground from background
        if(bgSet){
        	res = threshIt(mat,bg,res);
        	int w = res.cols(), h = res.rows();
            if (dat == null || dat.length != w * h)
                dat = new byte[w * h];
            if (img == null || img.getWidth() != w || img.getHeight() != h
                || img.getType() != BufferedImage.TYPE_BYTE_GRAY)
                    img = new BufferedImage(w, h, 
                                BufferedImage.TYPE_BYTE_GRAY);
        } else {
        	this.res = mat;
        	int w = res.cols(), h = res.rows();
            if (dat == null || dat.length != w * h * 3)
                dat = new byte[w * h * 3];
            if (img == null || img.getWidth() != w || img.getHeight() != h
                || img.getType() != BufferedImage.TYPE_3BYTE_BGR)
                    img = new BufferedImage(w, h, 
                                BufferedImage.TYPE_3BYTE_BGR);
        }
    }
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
}
