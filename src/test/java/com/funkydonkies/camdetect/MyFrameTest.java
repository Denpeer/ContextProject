package com.funkydonkies.camdetect;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.awt.AWTException;
import java.awt.HeadlessException;

import javax.swing.JPanel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Test the MyFrame class.
 * 
 * @author Olivier Dikken
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class MyFrameTest {
	
	private MyFrame frame;
	private boolean runTest = false;
	
	/**
	 * Initialize vars, make sure opencv is not run on travis.
	 */
	@Before
	public void setUp() {
		runTest = true;
		try {
			MyFrame.loadLib();
		} catch (final UnsatisfiedLinkError e) {
			runTest = false;
		}
	}
	
	/**
	 * Runs after every test, kills frame thread.
	 */
	@After
	public void tearDown() {
		if (frame != null) {
			frame.dispose();
		}
	}
	
	/**
	 * Get videocap objects and compare.
	 */
	@Test
	public void testGetVideoCap() {
		frame = Mockito.mock(MyFrame.class);
		final VideoCap first = frame.getVideoCap();
		final VideoCap second = frame.getVideoCap();
		assertEquals(first, second);
	}

	/**
	 * Check if the frame is visible.
	 * 
	 * @throws HeadlessException
	 *             in case there is no display
	 */
	@Test
	public void testMyFrame() {
		if (runTest) {
			frame = new MyFrame();
			assertTrue(frame.isVisible());
		}
	}

	/**
	 * Confirm that the correct key is set for calling the setTheBg action.
	 * 
	 * @throws AWTException
	 *             catches GUI error
	 * @throws HeadlessException
	 *             in case there is no display
	 */
	@Test
	public void testInitBgSetKey() {
		if (runTest) {
			JPanel testpanel = new JPanel();
			frame = new MyFrame();
			frame.initBgSetKey();
			testpanel = (JPanel) frame.getContentPane();
			assertEquals(testpanel.getInputMap().allKeys()[0].getKeyChar(), 'b');
		}
	}

	/**
	 * Repaint the graphics.
	 */
	@Test
	public void testPaintGraphics() {
		if (runTest) {
			frame = new MyFrame();
			frame.repaint();
		}
	}

	/**
	 * Run the thread.
	 */
	@Test
	public void testRun() {
		if (runTest) {
			frame = new MyFrame();
			new Thread(frame).start();
			
		}
	}

}
