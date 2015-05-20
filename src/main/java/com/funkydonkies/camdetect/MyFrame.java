package com.funkydonkies.camdetect;

import java.awt.Graphics;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.border.EmptyBorder;

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
  * Launches the application.
  * @param args -
  */
    public static void main(final String[] args) {
    	new MyFrame();
    }
    
    private VideoCap videoCap = new VideoCap();
    
    public VideoCap getVideoCap() {
    	return videoCap;
    }
    
  /**
  * Create the frame.
  * Start the thread.
  */
    public MyFrame() {
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
        g.drawImage(videoCap.getOneFrame(), 0, 0, this);
    }

    public void run() {
		for (;;) {
            repaint();
            try { 
            	Thread.sleep(SLEEP_TIME);
            } catch (final InterruptedException e) {    }
        }  
	}
}