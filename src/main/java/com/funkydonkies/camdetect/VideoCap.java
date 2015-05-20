package com.funkydonkies.camdetect;

import java.awt.image.BufferedImage;

import org.opencv.core.Core;
import org.opencv.highgui.VideoCapture;

/**Video Capture class opens the capture source and gets processed frames from the Mat2Image class.
 * Make sure your webcam is configured correctly.
 * If you don't get any video response change the capture source by modifying the inputSourceNumber variable.
 * 
 * @author Olivier Dikken
 *
 */
public class VideoCap {
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    private VideoCapture cap;
    private Mat2Image mat2Img = new Mat2Image();
    private final int inputSourceNumber = 1;
    private boolean camOn = false;
    
    public Mat2Image getMat2Image() {
    	return mat2Img;
    }

    /**
     * 
     */
    VideoCap() {
        cap = new VideoCapture(inputSourceNumber);
        if (!cap.isOpened()) {
            System.out.println("Camera Error; Try changing the inputSourceNumber variable in class VideoCap.java or check you webcam settings.");
            camOn = false;
        } else {
            System.out.println("Camera OK");
            camOn = true;
        }
    } 
    
    /**Exception is throw when camera source is not opened when getOneFrame() is called.
     * 
     * @author Olivier Dikken
     *
     */
    class CameraNotOnException extends Exception {
    	   /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * 
		 * @param msg the message to be displayed when error is thrown
		 */
		public CameraNotOnException(final String msg) {
    	      super(msg);
    	   }
    	}
 
    /**
     * gets the next frame to be displayed.
     * @return next frame to be displayed as buffered image
     * @throws CameraNotOnException if camera source not open
     */
    BufferedImage getOneFrame() {
    	try {
    		if (camOn) {
    		cap.read(mat2Img.getMat());
    		return mat2Img.getImage(mat2Img.getMat());
    		}
    	} catch (final Exception e) {
    		
    	}
    	return null;
    }

    /**
     * when key 'b' press set the bg.
     * calls setBg of the Mat2Image object
     */
	public void setBg() {
		mat2Img.setBg();
	}
}