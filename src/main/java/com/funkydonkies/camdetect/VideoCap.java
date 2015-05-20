package com.funkydonkies.camdetect;

import java.awt.image.BufferedImage;
import org.opencv.core.Core;
import org.opencv.highgui.VideoCapture;

public class VideoCap {
    static{
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    VideoCapture cap;
    Mat2Image mat2Img = new Mat2Image();

    VideoCap(){
        cap = new VideoCapture();
        cap.open(1);
    } 
 
    BufferedImage getOneFrame() {
        cap.read(mat2Img.mat);
        return mat2Img.getImage(mat2Img.mat);
    }

    /**
     * when key 'b' press set the bg
     * calls setBg of the Mat2Image object
     */
	public void setBg() {
		mat2Img.setBg();
	}
}
