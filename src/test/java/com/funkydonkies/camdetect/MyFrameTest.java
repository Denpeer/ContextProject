package com.funkydonkies.camdetect;

import java.awt.AWTException;
import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.PrintStream;
import static org.junit.Assert.assertEquals;
import javax.swing.JPanel;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.opencv.core.Core;
import static org.junit.Assert.assertTrue;

/**
 * Test the MyFrame class.
 * @author Olivier Dikken
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class MyFrameTest {
	
	/**
	 * Get videocap objects and compare.
	 */
	@Test
	public void testGetVideoCap() {
		final MyFrame test = Mockito.mock(MyFrame.class);
		final VideoCap first = test.getVideoCap();
		final VideoCap second = test.getVideoCap();
		assertEquals(first, second);
	}

	/**
	 * Load the lib and check if the version is as expected.
	 */
	@Test
	public void testLoadLib() {
		final ByteArrayOutputStream sink = new ByteArrayOutputStream();
		System.setOut(new PrintStream(sink, true));
		MyFrame.loadLib();
		final String javaLibPath = "java.library.path";
    	System.setProperty(javaLibPath, "./lib");
    	final StringBuilder result = new StringBuilder();
    	result.append("Lib name: " + Core.NATIVE_LIBRARY_NAME);
    	final String lineseparator = "line.separator";
    	result.append(System.getProperty(lineseparator));
    	result.append("Path: " + System.getProperty(javaLibPath));
    	result.append(System.getProperty(lineseparator));
		assertEquals(new String(sink.toByteArray()), result.toString());
		System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
	}
	
	/**
	 * Check if the frame is visible.
	 */
	@Test
	public void testMyFrame() {
		final MyFrame test = new MyFrame();
		assertTrue(test.isVisible());
	}

	/**
	 * Confirm that the correct key is set for calling the setTheBg action.
	 * @throws AWTException
	 */
	@Test
	public void testInitBgSetKey() throws AWTException {
		final MyFrame test = new MyFrame();
		test.initBgSetKey();
		JPanel testpanel = new JPanel();
		testpanel = (JPanel) test.getContentPane();
		assertEquals(testpanel.getInputMap().allKeys()[0].getKeyChar(), 'b');
	}

	/**
	 * Repaint the graphics.
	 */
	@Test
	public void testPaintGraphics() {
		final MyFrame test = new MyFrame();
		test.repaint();
	}

	/**
	 * Run the thread.
	 */
	@Test
	public void testRun() {
		final MyFrame test = new MyFrame();
		test.run();
	}

}
