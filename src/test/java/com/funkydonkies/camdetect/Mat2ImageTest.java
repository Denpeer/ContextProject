//package com.funkydonkies.camdetect;
//
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertTrue;
//
//import java.io.ByteArrayOutputStream;
//import java.io.FileDescriptor;
//import java.io.FileOutputStream;
//import java.io.PrintStream;
//import java.lang.reflect.Field;
//
//import org.junit.BeforeClass;
//import org.junit.Test;
//import org.opencv.core.Core;
//import org.opencv.core.Mat;
//import org.opencv.core.Point;
//import org.opencv.core.Scalar;
//import org.opencv.highgui.VideoCapture;
//import org.opencv.imgproc.Imgproc;
//
///**
// * Testing Mat2Image.
// * @author Olivier Dikken
// *
// */
//public class Mat2ImageTest {
//	private static final int INPUTSOURCENUMBER = 1;
//	
//	/**
//	 * Preparation.
//	 * @throws Exception in case system path not accessible
//	 */
//	@BeforeClass 
//	public static void setUpBeforeClass() throws Exception {
//		
//	}
//	
//	/**
//	 * Constructor test.
//	 */
//	@Test
//	public void testMat2Image() {
//		new Mat2Image();
//	}
//
//	/**
//	 * Check if getMat() returns the correct type.
//	 */
//	@Test
//	public void testGetMat() {
//		final Mat2Image m2i = new Mat2Image();
//		assertTrue(m2i.getMat() instanceof Mat);
//	}
//
//	/**
//	 * Check the correct line is printed when background is set.
//	 */
//	@Test
//	public void testSetBg() {
//		final ByteArrayOutputStream sink = new ByteArrayOutputStream();
//		System.setOut(new PrintStream(sink, true));
//		final StringBuilder result = new StringBuilder();
//		result.append("Background has been set");
//		final String lineseparator = "line.separator";
//    	result.append(System.getProperty(lineseparator));
//		final Mat2Image m2i = new Mat2Image();
//		m2i.setBg();
//		assertEquals(new String(sink.toByteArray()), result.toString());
//		System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
//	}
//
//	/**
//	 * Get frame from webcam and thresh it.
//	 */
//	@Test
//	public void testThreshIt() {
//		final Mat2Image m2i = new Mat2Image();
//		final VideoCapture cap = new VideoCapture(INPUTSOURCENUMBER);
//		cap.read(m2i.getMat());
//		m2i.threshIt(m2i.getMat(), m2i.getMat());
//	}
//
//	/**
//	 * Check if interestpoints array if of expected length.
//	 */
//	@Test
//	public void testUpdateIP() {
//		final Mat2Image m2i = new Mat2Image();
//		final VideoCapture cap = new VideoCapture(INPUTSOURCENUMBER);
//		cap.read(m2i.getMat());
//		m2i.updateIP(m2i.getMat());
//		final float[] ip = m2i.getControlPoints();
//		final double tempdiv = m2i.getMat().size().width / m2i.getxdist();
//    	final int numPoints = (int) Math.floor(tempdiv);
//		assertEquals(ip.length, numPoints);
//	}
//
//	/**
//	 * draw the interest points 'manually' and call the method and compare results.
//	 */
//	@Test
//	public void testDrawInterestPoints() {
//		final Mat2Image m2i = new Mat2Image();
//		final VideoCapture cap = new VideoCapture(INPUTSOURCENUMBER);
//		final Mat matty = m2i.getMat();
//		cap.read(matty);
//		m2i.updateIP(matty);
//		final float[] ip = m2i.getControlPoints();
//		final int xd = m2i.getxdist();
//		final int cd = 5;
//		final int mc = 255;
//		final Mat im = matty.clone();
//    	for (int i = 0; i < ip.length; i++) {
//    		Core.circle(im, new Point(i * xd, ip[i]), cd, new Scalar(mc), 0, 0, 2);
//    	}
//    	Imgproc.cvtColor(matty, matty, Imgproc.COLOR_BGR2GRAY);
//    	Core.subtract(im, m2i.drawInterestPoints(matty, ip), im);
//    	final int elemnum = 3;
//    	assertEquals(Core.sumElems(im).val[elemnum], 0, 1);
//	}
//
//	/**
//	 * call preparespace.
//	 */
//	@Test
//	public void testPrepareSpace() {
//		final Mat2Image m2i = new Mat2Image();
//		final VideoCapture cap = new VideoCapture(INPUTSOURCENUMBER);
//		final Mat matty = m2i.getMat();
//		cap.read(matty);
//		m2i.updateIP(matty);
//		m2i.prepareSpace(matty);
//	}
//
//	/**
//	 * call getspace.
//	 */
//	@Test
//	public void testGetSpace() {
//		final Mat2Image m2i = new Mat2Image();
//		final VideoCapture cap = new VideoCapture(INPUTSOURCENUMBER);
//		final Mat matty = m2i.getMat();
//		cap.read(matty);
//		m2i.getSpace(matty);
//	}
//
//	/**
//	 * call getimage.
//	 */
//	@Test
//	public void testGetImage() {
//		final Mat2Image m2i = new Mat2Image();
//		final VideoCapture cap = new VideoCapture(INPUTSOURCENUMBER);
//		final Mat matty = m2i.getMat();
//		cap.read(matty);
//		m2i.getImage(m2i.getMat());
//	}
//
//	/**
//	 * call getcontrolpoints.
//	 */
//	@Test
//	public void testGetControlPoints() {
//		final Mat2Image m2i = new Mat2Image();
//		final VideoCapture cap = new VideoCapture(INPUTSOURCENUMBER);
//		final Mat matty = m2i.getMat();
//		cap.read(matty);
//		m2i.updateIP(matty);
//		m2i.getControlPoints();
//	}
//
//	/**
//	 * check if xdist is as expected.
//	 */
//	@Test
//	public void testGetxdist() {
//		final Mat2Image m2i = new Mat2Image();
//		final VideoCapture cap = new VideoCapture(INPUTSOURCENUMBER);
//		final Mat matty = m2i.getMat();
//		cap.read(matty);
//		m2i.updateIP(matty);
//		final int xd = m2i.getxdist();
//		final int length = m2i.getControlPoints().length;
//		final int expected = matty.width() / xd;
//		assertEquals(expected, length);
//		
//	}
//}
