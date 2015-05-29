//package com.funkydonkies.camdetect;
//
//import java.awt.AWTException;
//import java.awt.HeadlessException;
//import java.io.ByteArrayOutputStream;
//import java.io.FileDescriptor;
//import java.io.FileOutputStream;
//import java.io.PrintStream;
//
//import static org.junit.Assert.assertEquals;
//
//import javax.swing.JPanel;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.Mockito;
//import org.mockito.runners.MockitoJUnitRunner;
//import org.opencv.core.Core;
//
//import static org.junit.Assert.assertTrue;
//
///**
// * Test the MyFrame class.
// * @author Olivier Dikken
// *
// */
//@RunWith(MockitoJUnitRunner.class)
//public class MyFrameTest {
//	
//	/**
//	 * Get videocap objects and compare.
//	 */
//	@Test
//	public void testGetVideoCap() {
//		final MyFrame test = Mockito.mock(MyFrame.class);
//		final VideoCap first = test.getVideoCap();
//		final VideoCap second = test.getVideoCap();
//		assertEquals(first, second);
//	}
//
//	/**
//	 * Load the lib and check if the version is as expected.
//	 * @throws UnsatisfiedLinkError in case the library is not found
//	 */
//	@Test
//	public void testLoadLib() throws UnsatisfiedLinkError {
//		final ByteArrayOutputStream sink = new ByteArrayOutputStream();
//		System.setOut(new PrintStream(sink, true));
//		try {
//			MyFrame.loadLib();
//		} catch (final UnsatisfiedLinkError e) {
//		}
//		final String javaLibPath = "java.library.path";
//    	System.setProperty(javaLibPath, "./lib");
//    	final StringBuilder result = new StringBuilder();
//    	result.append("Lib name: " + Core.NATIVE_LIBRARY_NAME);
//    	final String lineseparator = "line.separator";
//    	result.append(System.getProperty(lineseparator));
//    	result.append("Path: " + System.getProperty(javaLibPath));
//    	result.append(System.getProperty(lineseparator));
//		assertEquals(new String(sink.toByteArray()), result.toString());
//		System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
//	}
//	
//	/**
//	 * Check if the frame is visible.
//	 * @throws HeadlessException in case there is no display
//	 */
//	@Test
//	public void testMyFrame() throws HeadlessException {
//		try {
//			final MyFrame test = new MyFrame();
//			assertTrue(test.isVisible());
//		} catch (final HeadlessException e) {
//		}
//	}
//
//	/**
//	 * Confirm that the correct key is set for calling the setTheBg action.
//	 * @throws AWTException catches GUI error
//	 * @throws HeadlessException in case there is no display
//	 */
//	@Test
//	public void testInitBgSetKey() throws AWTException, HeadlessException {
//		JPanel testpanel = new JPanel();
//		try {
//			final MyFrame test = new MyFrame();
//			test.initBgSetKey();
//			testpanel = (JPanel) test.getContentPane();
//			assertEquals(testpanel.getInputMap().allKeys()[0].getKeyChar(), 'b');
//		} catch (final HeadlessException e) {
//		}
//	}
//
//	/**
//	 * Repaint the graphics.
//	 */
//	@Test
//	public void testPaintGraphics() {
//		final MyFrame test = Mockito.mock(MyFrame.class);
//		test.repaint();
//	}
//
//	/**
//	 * Run the thread.
//	 */
//	@Test
//	public void testRun() {
//		final MyFrame test = Mockito.mock(MyFrame.class);
//		test.run();
//	}
//
//}
