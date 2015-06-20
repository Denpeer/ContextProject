package com.funkydonkies.camdetect;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

/**
 * Testing Mat2Image.
 * 
 * @author Olivier Dikken
 *
 */
public class Mat2ImageTest {

	private static BufferedImage imageBG = null;
	private static BufferedImage imageFG = null;
	private static BufferedImage imageProcessed = null;
	private static BufferedImage imageProcessed2 = null;
	private static BufferedImage imageThreshed = null;
	private static final int NUMBER_OF_CHANNELS = 3;
	private static final String CONTROL_POINTS_TEXT_FILE_PATH = "TestRessources/testInterestPointsArray.txt";
	private boolean runTest = false;

	/**
	 * Converts java BufferedImage type to opencv Mat type.
	 * 
	 * @param im
	 *            image to be converted.
	 * @return Mat of image.
	 */
	public Mat im2Mat(final BufferedImage im) {
		final byte[] data = ((DataBufferByte) im.getRaster().getDataBuffer()).getData();
		final Mat mat = new Mat(im.getHeight(), im.getWidth(), CvType.CV_8UC3);
		mat.put(0, 0, data);
		return mat;
	}

	/**
	 * Converts opencv Mat type to java BufferedImage type.
	 * 
	 * @param matIn
	 *            to be converted. Imgproc.COLOR_GRAY2BGR.
	 * @return BufferedImage representation of the mat.
	 */
	public BufferedImage mat2Im(final Mat matIn) {
		final byte[] data = new byte[matIn.rows() * matIn.cols() * (int) (matIn.elemSize())];
		matIn.get(0, 0, data);
		if (matIn.channels() == NUMBER_OF_CHANNELS) {
			for (int i = 0; i < data.length; i += NUMBER_OF_CHANNELS) {
				final byte temp = data[i];
				data[i] = data[i + 2];
				data[i + 2] = temp;
			}
		}
		final BufferedImage image = new BufferedImage(matIn.cols(), matIn.rows(),
				BufferedImage.TYPE_3BYTE_BGR);
		image.getRaster().setDataElements(0, 0, matIn.cols(), matIn.rows(), data);
		return image;
	}

	/**
	 * Compare to Mat matrices.
	 * 
	 * @param m1
	 *            fist matrix
	 * @param m2
	 *            second matrix
	 * @return if their representation is equal
	 */
	public boolean matEq(final Mat m1, final Mat m2) {
		final Mat res = new Mat();
		Core.subtract(m1, m2, res);
		final Scalar resScal = Core.sumElems(res);
		return resScal.equals(new Scalar(0, 0, 0, 0));
	}

	/**
	 * Preparation.
	 * 
	 * @throws Exception
	 *             in case system path not accessible
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		try {
			imageBG = ImageIO.read(new File("TestRessources/testBG.jpg"));
			imageFG = ImageIO.read(new File("TestRessources/testFG.jpg"));
			imageProcessed = ImageIO.read(new File("TestRessources/testResult.png"));
			imageProcessed2 = ImageIO.read(new File("TestRessources/testResult2.png"));
			imageThreshed = ImageIO.read(new File("TestRessources/threshed.png"));
		} catch (final IOException e) {
		}
	}

	/**
	 * Preparation.
	 * 
	 * @throws Exception
	 *             in case system path not accessible
	 */
	@Before
	public void setUp() throws Exception {
		runTest = true;
		try {
			MyFrame.loadLib();
		} catch (final UnsatisfiedLinkError e) {
			runTest = false;
		}
	}

	/**
	 * Constructor test.
	 */
	@Test
	public void testMat2Image() {
		if (runTest) {
			new Mat2Image();
		}
	}

	/**
	 * Check if getMat() returns the correct type.
	 */
	@Test
	public void testGetMat() {
		if (runTest) {
			final Mat2Image m2i = new Mat2Image();
			assertTrue(m2i.getMat() instanceof Mat);
			final Mat testMat = m2i.getMat();
			im2Mat(imageBG).copyTo(testMat);
			assertTrue(matEq(testMat, im2Mat(imageBG)));
		}
	}

	/**
	 * Check the correct line is printed when background is set.
	 * 
	 * @throws IOException
	 *             -
	 */
	@Test
	public void testSetBg() throws IOException {
		if (runTest) {
			final Mat2Image m2i = new Mat2Image();
			im2Mat(imageBG).copyTo(m2i.getMat());
			m2i.setBg();
			im2Mat(imageFG).copyTo(m2i.getMat());
		}
	}

	/**
	 * Get frame from webcam and thresh it.
	 * 
	 * @throws IOException
	 *             -
	 */
	@Test
	public void testThreshIt() throws IOException {
		if (runTest) {
			final Mat2Image m2i = new Mat2Image();
			im2Mat(imageFG).copyTo(m2i.getMat());
			final Mat res = m2i.threshIt(m2i.getMat(), im2Mat(imageBG));
			Imgproc.cvtColor(res, res, Imgproc.COLOR_GRAY2BGR);
			assertTrue(matEq(res, im2Mat(imageThreshed)));
		}
	}

	/**
	 * Check if interestpoints array if of expected length.
	 * 
	 * @throws IOException
	 *             -
	 */
	@Test
	public void testUpdateIP() throws IOException {
		if (runTest) {
			final Mat2Image m2i = new Mat2Image();
			m2i.updateIP(im2Mat(imageProcessed2));
			m2i.updateIP(im2Mat(imageProcessed2));
			m2i.updateIP(im2Mat(imageProcessed2));
			final float[] ip = m2i.getControlPoints();
			im2Mat(imageProcessed2).copyTo(m2i.getMat());
			final double tempdiv = m2i.getMat().size().width / m2i.getxdist();
			final int numPoints = (int) Math.floor(tempdiv);
			assertEquals(ip.length, numPoints);

			final BufferedReader br = new BufferedReader(new FileReader(
					CONTROL_POINTS_TEXT_FILE_PATH));
			final String testPoints = br.readLine();
			assertEquals(testPoints, Arrays.toString(ip));
			br.close();
		}
	}

	/**
	 * test if isConnected returns true when the given pixel is and false when
	 * it is not the center of the block of pixels who's size is determined by
	 * blockSize.
	 */
	@Test
	public void testIsConnected() {
		if (runTest) {
			final Mat2Image m2i = new Mat2Image();
			final int blockSize = 3;
			final int x1 = 256;
			final int y1 = 423;
			assertTrue(m2i.isConnected(im2Mat(imageThreshed), blockSize, x1, y1));
			final int x2 = 200;
			final int y2 = 300;
			assertFalse(m2i.isConnected(im2Mat(imageThreshed), blockSize, x2, y2));
			assertFalse(m2i.isConnected(im2Mat(imageThreshed), 2, 1, 1));
		}
	}

	/**
	 * check if heighDiffPass passes and refuses as expected.
	 * Mat2Image.MAX_JUMP_DIST = 10.0f at the moment of writing this test.
	 */
	@Test
	public void testHeightDiffPass() {
		if (runTest) {
			final Mat2Image m2i = new Mat2Image();
			final float f10 = 10.0f;
			final float f99 = 9.9f;
			assertTrue(m2i.heightDiffPass(0.0f, 0.0f));
			assertTrue(m2i.heightDiffPass(0.0f, f99));
			assertFalse(m2i.heightDiffPass(0.0f, f10));
		}
	}

	/**
	 * draw the interest points 'manually' and call the method and compare
	 * results.
	 */
	@Test
	public void testDrawInterestPoints() {
		if (runTest) {
			final Mat2Image m2i = new Mat2Image();
			final Mat matty = m2i.getMat();
			im2Mat(imageFG).copyTo(matty);
			m2i.updateIP(matty);
			final float[] ip = m2i.getControlPoints();
			final int xd = m2i.getxdist();
			final int cd = 5;
			final int mc = 255;
			final Mat im = matty.clone();
			for (int i = 0; i < ip.length; i++) {
				Core.circle(im, new Point(i * xd, ip[i]), cd, new Scalar(mc), 0, 0, 2);
			}
			Imgproc.cvtColor(matty, matty, Imgproc.COLOR_BGR2GRAY);
			Core.subtract(im, m2i.drawInterestPoints(matty, ip), im);
			final int elemnum = 3;
			assertEquals(Core.sumElems(im).val[elemnum], 0, 1);
		}
	}

	/**
	 * call preparespace.
	 */
	@Test
	public void testPrepareSpace() {
		if (runTest) {
			final Mat2Image m2i = new Mat2Image();
			final Mat matty = m2i.getMat();
			im2Mat(imageFG).copyTo(matty);
			m2i.updateIP(matty);
			m2i.prepareSpace(matty);
		}
	}

	/**
	 * put mat matrix, call getspace.
	 */
	@Test
	public void testGetSpace() {
		if (runTest) {
			final Mat2Image m2i = new Mat2Image();
			final Mat matty = m2i.getMat();
			im2Mat(imageFG).copyTo(matty);
			m2i.getSpace(im2Mat(imageFG));
			m2i.setBg();
			m2i.getSpace(im2Mat(imageFG));
			assertTrue(m2i.getImageHeight() == imageFG.getHeight());
		}
	}

	/**
	 * call getimage. Check if returned image is as expected.
	 */
	@Test
	public void testGetImage() {
		if (runTest) {
			final Mat2Image m2i = new Mat2Image();
			final Mat matty = m2i.getMat();
			im2Mat(imageBG).copyTo(m2i.getMat());
			m2i.setBg();
			im2Mat(imageFG).copyTo(m2i.getMat());
			final BufferedImage ret = m2i.getImage(matty);
			assertTrue(matEq(im2Mat(ret), im2Mat(imageProcessed)));
		}
	}

	/**
	 * call getcontrolpoints.
	 * 
	 * @throws IOException
	 *             -
	 */
	@Test
	public void testGetControlPoints() throws IOException {
		if (runTest) {
			final Mat2Image m2i = new Mat2Image();
			final Mat matty = m2i.getMat();
			im2Mat(imageProcessed2).copyTo(matty);
			m2i.updateIP(matty);
			m2i.updateIP(matty);
			m2i.updateIP(matty);
			final float[] ip = m2i.getControlPoints();
			final BufferedReader br = new BufferedReader(new FileReader(
					CONTROL_POINTS_TEXT_FILE_PATH));
			final String testPoints = br.readLine();
			assertEquals(testPoints, Arrays.toString(ip));
			br.close();
		}
	}

	/**
	 * check if xdist is as expected.
	 */
	@Test
	public void testGetxdist() {
		if (runTest) {
			final Mat2Image m2i = new Mat2Image();
			final Mat matty = m2i.getMat();
			m2i.updateIP(matty);
			final int xd = m2i.getxdist();
			final int length = m2i.getControlPoints().length;
			final int expected = matty.width() / xd;
			assertEquals(expected, length);
		}
	}

	/**
	 * test for setxdist().
	 */
	@Test
	public void testSetxdist() {
		if (runTest) {
			final Mat2Image m2i = new Mat2Image();
			final Mat matty = m2i.getMat();
			im2Mat(imageProcessed2).copyTo(matty);
			final int xd = m2i.getxdist();
			m2i.setxdist(xd * 2);
			assertEquals(xd * 2, m2i.getxdist());
		}
	}

	/**
	 * test for getImageHeight().
	 */
	@Test
	public void testGetImageHeight() {
		if (runTest) {
			final Mat2Image m2i = new Mat2Image();
			final Mat matty = m2i.getMat();
			im2Mat(imageProcessed2).copyTo(matty);
			assertEquals(m2i.getImageHeight(), imageProcessed2.getHeight());
		}
	}

	/**
	 * test for getImageWidth.
	 */
	@Test
	public void testGetImageWidth() {
		if (runTest) {
			final Mat2Image m2i = new Mat2Image();
			final Mat matty = m2i.getMat();
			im2Mat(imageProcessed2).copyTo(matty);
			assertEquals(m2i.getImageWidth(), imageProcessed2.getWidth());
		}
	}
}
