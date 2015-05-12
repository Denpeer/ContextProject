package com.mycompany.mavenproject1;

import org.junit.Test;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;

import junit.framework.TestCase;

public class opencvTestTest extends TestCase {
	
	static{ System.loadLibrary(Core.NATIVE_LIBRARY_NAME); }
	
	@Test
	public void testOpenCV(){
		int n = 5;
		Mat m = new Mat(n, n, CvType.CV_8UC1, new Scalar(0));
		Mat res = opencvTest.makeMatrix(n);
		
		assertEquals(m,res);
	}
	
	 
}
