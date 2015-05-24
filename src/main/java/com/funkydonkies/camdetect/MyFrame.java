package com.funkydonkies.camdetect;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.lang.reflect.Field;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.border.EmptyBorder;

import org.opencv.core.Core;

import com.funkydonkies.camdetect.VideoCap.CameraNotOnException;

/**
 * Makes frame to display videocapture images.
 * Keeps frame updated by repainting most recent images
 * @author Olivier Dikken
 *
 */
public class MyFrame extends JFrame implements Runnable {

	private static final long serialVersionUID = 1L;
	private static final int SLEEP_TIME = 30;
	private JPanel contentPane;

  /**
  * Launch the application.
  * @param args -
  */
    public static void main(final String[] args) {
    	new Thread(new MyFrame()).start();
    }
    
    private VideoCap videoCap;
    
    /**
     * Loads the native libraries, setting the java.library.path in the process.
     */
    public static void loadLib() {
    	final String javaLibPath = "java.library.path";
    	System.setProperty(javaLibPath, "./lib");
   	 Field fieldSysPath;
		try {
			fieldSysPath = ClassLoader.class.getDeclaredField("sys_paths");
			fieldSysPath.setAccessible(true);
			fieldSysPath.set(null, null);
		} catch (final NoSuchFieldException e) {
			e.printStackTrace();
		} catch (final SecurityException e) {
			e.printStackTrace();
		} catch (final IllegalArgumentException e) {
			e.printStackTrace();
		} catch (final IllegalAccessException e) {
			e.printStackTrace();
		}
	System.out.println("Lib name: " + Core.NATIVE_LIBRARY_NAME);
	System.out.println("Path: " + System.getProperty(javaLibPath));
   	System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }
    
  /**
  * Create the frame.
  * Start the thread.
  */
    public MyFrame() {
    	loadLib();
    	videoCap = new VideoCap();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        final int xbound = 100, ybound = 100, boundwidth = 650, boundheight = 490;
        setBounds(xbound, ybound, boundwidth, boundheight);
        contentPane = new JPanel();
        initBgSetKey();
        final int top = 5, left = 5, bottom = 5, right = 5;
        contentPane.setBorder(new EmptyBorder(top, left, bottom, right));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        setVisible(true);
    }
    
    /**
     * press 'b' to set background to current frame.
     */
    public void initBgSetKey() {
    	//create action
        Action setTheBg = new AbstractAction() {
			private static final long serialVersionUID = 1L;

			public void actionPerformed(final ActionEvent e) {
				videoCap.setBg();
			}
        };
        //add keypress 'b' sets current frame as background
        final String callSetTheBg = "callSetTheBg";
        contentPane.getInputMap().put(KeyStroke.getKeyStroke('b'), callSetTheBg);
        contentPane.getActionMap().put(callSetTheBg, setTheBg);
    }
 
    /**
     * draws the next frame to be displayed.
     * calls videocap.[...] method which returns a processed video frame
     * @param g 
     */
    public void paint(final Graphics g) {
        try {
			g.drawImage(videoCap.getOneFrame(), 0, 0, this);
		} catch (final CameraNotOnException e) {
			e.printStackTrace();
		}
    }
    /** Thread that updates the frame.
     * 
     * @author Olivier Dikken
     *
     */
    class MyThread extends Thread {
    	
        @Override
        public void run() {
            for (;;) {
                repaint();
                try { 
                	Thread.sleep(SLEEP_TIME);
                } catch (final InterruptedException e) {    }
            }  
        } 
    }
    
	/**
	 * Runs the thread that paints the current frame.
	 * 
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		new MyThread().start();
	}
}