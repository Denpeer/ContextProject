package com.funkydonkies.camdetect;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;

import org.opencv.core.Core;

import com.funkydonkies.camdetect.VideoCap.CameraNotOnException;

/**
 * Makes frame to display images got from video capture object processed by
 * Mat2Image. Keeps frame updated by repainting most recent images
 * 
 * @author Olivier Dikken
 *
 */
public class MyFrame extends JFrame implements Runnable, ActionListener {

	private static final long serialVersionUID = 1L;
	private static final int SLEEP_TIME = 30;
	private JPanel contentPane;
	private boolean triedCameras = false;
	private ArrayList<Integer> cameras;
	private ArrayList<JButton> buttons;

	/**
	 * Launch the application by starting a new thread.
	 * 
	 * @param args
	 *            -
	 */
	public static void main(final String[] args) {
		new Thread(new MyFrame()).start();
	}

	// the video capture object handles camera input
	private VideoCap videoCap;

	/**
	 * Runs the thread that paints the frames.
	 * 
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		new MyThread().start();
	}

	/**
	 * Accessor method to get this video capture object.
	 * 
	 * @return VideoCap object that contains the Mat2Image bridge.
	 */
	public VideoCap getVideoCap() {
		return videoCap;
	}

	/**
	 * Loads the native libraries (opencv), setting the java.library.path in the
	 * process.
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
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}

	/**
	 * Create the frame. Load the library. Set the key bindings. Start the
	 * thread.
	 */
	public MyFrame() {
		loadLib();
		initVideoCap();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		final int xbound = 100, ybound = 100, boundwidth = 650, boundheight = 490;
		setBounds(xbound, ybound, boundwidth, boundheight);
		contentPane = new JPanel();
		initBgSetKey();
		initXDAmountSetKey();
		final int top = 5, left = 5, bottom = 5, right = 5;
		contentPane.setBorder(new EmptyBorder(top, left, bottom, right));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(5, 1));
		setVisible(true);
	}

	public void initVideoCap() {
		videoCap = new VideoCap();
	}
	
	/**
	 * press 'b' to set background to current frame.
	 */
	public void initBgSetKey() {
		// create action
		Action setTheBg = new AbstractAction() {
			private static final long serialVersionUID = 1L;

			public void actionPerformed(final ActionEvent e) {
				System.out.println("action: " + e.getActionCommand());
				if (e.getActionCommand().equals("b")){
					System.out.println("setbg called");
					videoCap.setBg();
				}
				if(e.getActionCommand().equals("n")){
					System.out.println("n called");
					initVideoCap();
					
				}
			}
		};
		// add keypress 'b' sets current frame as background
		final String callSetTheBg = "callSetTheBg";
		contentPane.getInputMap()
				.put(KeyStroke.getKeyStroke('b'), callSetTheBg);
		contentPane.getInputMap()
				.put(KeyStroke.getKeyStroke('n'), "start video");
		contentPane.getActionMap().put(callSetTheBg, setTheBg);
		contentPane.getActionMap().put("start video", setTheBg);
		
		
	}

	/**
	 * Use '+' and '-' keys to increment / decrement the xdist of m2i.
	 */
	public void initXDAmountSetKey() {
		// create actions
		Action incXD = new AbstractAction() {
			private static final long serialVersionUID = 1L;

			public void actionPerformed(final ActionEvent e) {
				videoCap.incXD();
			}
		};
		Action decXD = new AbstractAction() {
			private static final long serialVersionUID = 1L;

			public void actionPerformed(final ActionEvent e) {
				videoCap.decXD();
			}
		};
		// add keypress 'b' sets current frame as background
		final String callIncXD = "incXD";
		contentPane.getInputMap().put(KeyStroke.getKeyStroke('+'), callIncXD);
		contentPane.getActionMap().put(callIncXD, incXD);
		final String callDecXD = "decXD";
		contentPane.getInputMap().put(KeyStroke.getKeyStroke('-'), callDecXD);
		contentPane.getActionMap().put(callDecXD, decXD);
	}

	/**
	 * draws the next frame to be displayed. calls videocap.[...] method which
	 * returns a processed video frame
	 * 
	 * @param g graphics
	 */
	public void paint(final Graphics g) {
		try {
			if (videoCap != null) {
				contentPane.removeAll();
				g.drawImage(videoCap.getOneFrame(), 0, 0, this);
			} else {
			}
		} catch (final CameraNotOnException e) {
			contentPane.removeAll();
			JLabel label = new JLabel();
			label.setText("Please Select an Input Source \n");
			contentPane.add(label);
			if (!triedCameras) {
				cameras = videoCap.tryCameras();
				for (Integer camera : cameras) {
					buttons.add(makeButton("Input number: " + camera));
				}
				triedCameras = true;
			}
			for (JButton jButton : buttons) {
				contentPane.add(jButton);
			}
			setContentPane(contentPane);
			
		}
	}
	
	/**
	 * Creates a new button for selecting an input source.
	 * @param name String to be displayed on the button.
	 * @return
	 */
	private JButton makeButton(String name) {
		final JButton button = new JButton(name);
		button.setActionCommand(name.substring(name.length() - 1));
		button.addActionListener(this);
		button.setEnabled(true);
		return button;
	}

	/**
	 * Thread that updates the frame.
	 * 
	 * @author Olivier Dikken
	 *
	 */
	class MyThread extends Thread {

		@Override
		public void run() {
			buttons = new ArrayList<>();
			try {
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e1) {
				e1.printStackTrace();
			} catch (UnsupportedLookAndFeelException e1) {
				e1.printStackTrace();
			}
			for (;;) {
				repaint();
				try {
					Thread.sleep(SLEEP_TIME);
				} catch (final InterruptedException e) {
					throw new RuntimeException(e);
				}
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		videoCap.openCamera(Integer.parseInt(e.getActionCommand()));
	}
}
