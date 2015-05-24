package com.funkydonkies.camdetect;

import java.awt.EventQueue;
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
public class MyFrame extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

  /**
  * Launch the application.
  * @param args -
  */
    public static void main(final String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    final MyFrame frame = new MyFrame();
                    frame.setVisible(true);
                } catch (final Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    
    private VideoCap videoCap;
    
    public static void loadLib() {
    	System.setProperty( "java.library.path", "./lib" );
   	 Field fieldSysPath;
		try {
			fieldSysPath = ClassLoader.class.getDeclaredField( "sys_paths" );
			fieldSysPath.setAccessible( true );
			fieldSysPath.set( null, null );
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	System.out.println("Lib name: " + Core.NATIVE_LIBRARY_NAME);
	System.out.println("Path: " + System.getProperty("java.library.path"));
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
        
        new MyThread().start();
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
    	private final int sleepTime = 30;
        @Override
        public void run() {
            for (;;) {
                repaint();
                try { 
                	Thread.sleep(sleepTime);
                } catch (final InterruptedException e) {    }
            }  
        } 
    }
}