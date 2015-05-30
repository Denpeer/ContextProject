package com.funkydonkies.camdetect;

import java.awt.image.BufferedImage;

import org.opencv.highgui.VideoCapture;

/**
 * Video Capture class opens the capture source and gets processed frames from
 * the Mat2Image class. Make sure your webcam is configured correctly. If you
 * don't get any video response change the capture source by modifying the
 * INPUTSOURCENUMBER variable.
 * 
 * @author Olivier Dikken
 *
 */
public class VideoCap {

	private VideoCapture cap;
	private Mat2Image mat2Img = new Mat2Image();
	private static final int INPUTSOURCENUMBER = 0;
	private boolean camOn = false;

	/**
	 * Create capture object which reads from the input source defined by
	 * INPUTSOURCENUMBER (genrally {0;1;2;3}).
	 */
	VideoCap() {
		cap = new VideoCapture(INPUTSOURCENUMBER);
		if (!cap.isOpened()) {
			System.out
					.println("Camera Error; Try changing the INPUTSOURCENUMBER variable in class VideoCap.java or check you webcam settings.");
			camOn = false;
		} else {
			System.out.println("Camera OK");
			camOn = true;
		}
	}

	/**
	 * Gets the Mat2Image object. The m2i class is responsible for the image
	 * processing.
	 * 
	 * @return Mat2Image object containing current frame and dataset
	 *         controlpoints.
	 */
	public Mat2Image getMat2Image() {
		return mat2Img;
	}

	/**
	 * Exception is throw when camera source is not opened when getOneFrame() is
	 * called.
	 * 
	 * @author Olivier Dikken
	 *
	 */
	static class CameraNotOnException extends Exception {
		private static final long serialVersionUID = 1L;

		/**
		 * @param msg
		 *            the message to be displayed when error is thrown
		 */
		public CameraNotOnException(final String msg) {
			super(msg);
		}
	}

	/**
	 * gets the next frame to be displayed. The frame is processed by. m2i
	 * before being returned.
	 * 
	 * @return next frame to be displayed as buffered image
	 * @throws CameraNotOnException
	 *             if camera source not open
	 */
	BufferedImage getOneFrame() throws CameraNotOnException {
		if (camOn) {
			cap.read(mat2Img.getMat());
			return mat2Img.getImage(mat2Img.getMat());
		}
		throw new CameraNotOnException("Camera input not found.");
	}


	/**
	 * when key 'b' press set the bg. calls setBg of the Mat2Image object
	 */
	public void setBg() {
		mat2Img.setBg();
	}

	/**
	 * increment the xdist by 1.
	 */
	public void incXD() {
		mat2Img.setxdist(mat2Img.getxdist() - 1);
	}

	/**
	 * decrement the xdist by 1.
	 */
	public void decXD() {
		mat2Img.setxdist(mat2Img.getxdist() + 1);
	}
}
