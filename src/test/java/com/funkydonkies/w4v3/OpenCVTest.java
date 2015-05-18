package com.funkydonkies.w4v3;

import static org.junit.Assert.*;

import org.junit.Test;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

public class OpenCVTest {
	static{ System.loadLibrary(Core.NATIVE_LIBRARY_NAME); }

	@Test
	public void test() {
		 System.out.println("Welcome to OpenCV " + Core.VERSION);
	        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	        Mat m  = Mat.eye(3, 3, CvType.CV_8UC1);
	        System.out.println("m = " + m.dump());
	}

}
