package com.funkydonkies.camdetect;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.lang.reflect.Field;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
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
/**
 * @author Jonathan
 *
 */
public class MyFrame extends JFrame implements Runnable, ActionListener {

	private static final long serialVersionUID = 1L;
	private static final int SLEEP_TIME = 30;
	private static final String REFRESH_COMMAND = "refresh";
	private JPanel contentPane;
	private boolean triedCameras = false;
	private boolean runThread = true;
	private ArrayList<Integer> cameras;
	private ArrayList<JButton> buttons;
	private JButton refreshButton;
	private JPanel upperblock;
	private JTextArea label;
	private static final int CONTROL_TEXT_X = 10;
	private static final int CONTROL_TEXT_Y = 470;
	private static final int CONTROL_TEXT_BOX_YDIFF = -15;
	private static final int CONTROL_TEXT_BOX_WIDTH = 370;
	private static final int CONTROL_TEXT_BOX_HEIGHT = 20;

	// the video capture object handles camera input
	private VideoCap videoCap;

	/**
	 * Runs the thread that paints the frames.
	 * 
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		while (runThread) {
			repaint();
			try {
				Thread.sleep(SLEEP_TIME);
			} catch (final InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void dispose() {
		super.dispose();
		kill();
		videoCap.releaseCap();
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
	 * Creates the frame's layout for choosing the camera input.
	 */
	public void createUI() {
		upperblock = new JPanel(null);
		label = new JTextArea();
		label.setText("Please Select an Input Source \n"
				+ "When a source is chosen press 'b' to set a background \n"
				+ "or press Esc to return here.");
		label.setEditable(false);
		final float[] hsb = Color.RGBtoHSB(238, 238, 238, null);
		label.setBackground(Color.getHSBColor(hsb[0], hsb[1], hsb[2]));

		final int labelWidth = 500, labelHeight = 75;
		label.setSize(labelWidth, labelHeight);

		final ImageIcon icon = new ImageIcon("assets/refresh-icon.png", "");

		final int xLoc = 585, yLoc = 2, buttonWidth = 40, buttonHeight = 40;
		refreshButton = new JButton(icon);
		refreshButton.setBounds(xLoc, yLoc, buttonWidth, buttonHeight);
		refreshButton.setActionCommand(REFRESH_COMMAND);
		refreshButton.addActionListener(this);
	}

	/**
	 * Loads the native libraries (opencv), setting the java.library.path in the process.
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
	 * Create the frame. Load the library. Set the key bindings. Start the thread.
	 */
	public MyFrame() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (final ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e1) {
			e1.printStackTrace();
		}
		loadLib();
		initVideoCap();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		final int xbound = 100, ybound = 100, boundwidth = 650, boundheight = 490;
		setBounds(xbound, ybound, boundwidth, boundheight);
		contentPane = new JPanel();
		initBgSetKey();
		initXDAmountSetKey();
		initMoveBoundSetKey();
		final int top = 5, left = 5, bottom = 5, right = 5;
		contentPane.setBorder(new EmptyBorder(top, left, bottom, right));
		final int rows = 5, cols = 1;
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(rows, cols));
		buttons = new ArrayList<>();
		createUI();
		setVisible(true);
	}

	/**
	 * Initializes the VideoCap class.
	 */
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
				if ("b".equals(e.getActionCommand())) {
					videoCap.setBg();
				}
			}
		};
		Action startVid = new AbstractAction() {
			private static final long serialVersionUID = 1L;

			public void actionPerformed(final ActionEvent e) {
				if ("n".equals(e.getActionCommand())) {
					initVideoCap();
				}
			}
		};
		Action goBack = new AbstractAction() {
			private static final long serialVersionUID = 1L;

			public void actionPerformed(final ActionEvent e) {
				videoCap.closeAndReturn();
			}
		};
		// add keypress 'b' sets current frame as background
		final String callSetTheBg = "callSetTheBg";
		final String callStartVid = "start video";
		final String callBack = "back";

		contentPane.getInputMap().put(KeyStroke.getKeyStroke('b'), callSetTheBg);
		contentPane.getInputMap().put(KeyStroke.getKeyStroke('n'), callStartVid);
		contentPane.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), callBack);
		contentPane.getActionMap().put(callSetTheBg, setTheBg);
		contentPane.getActionMap().put(callStartVid, startVid);
		contentPane.getActionMap().put(callBack, goBack);
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
		final String callIncXD = "incXD";
		contentPane.getInputMap().put(KeyStroke.getKeyStroke('+'), callIncXD);
		contentPane.getActionMap().put(callIncXD, incXD);
		final String callDecXD = "decXD";
		contentPane.getInputMap().put(KeyStroke.getKeyStroke('-'), callDecXD);
		contentPane.getActionMap().put(callDecXD, decXD);
	}

	/**
	 * set the keys to move the bounds within which the camera detection works.
	 */
	public void initMoveBoundSetKey() {
		Action upInc = new AbstractAction() {

			private static final long serialVersionUID = 1L;

			public void actionPerformed(final ActionEvent e) {
				videoCap.upInc();
			}
		};
		Action upDec = new AbstractAction() {

			private static final long serialVersionUID = 1L;

			public void actionPerformed(final ActionEvent e) {
				videoCap.upDec();
			}
		};
		Action lowInc = new AbstractAction() {

			private static final long serialVersionUID = 1L;

			public void actionPerformed(final ActionEvent e) {
				videoCap.lowInc();
			}
		};
		Action lowDec = new AbstractAction() {

			private static final long serialVersionUID = 1L;

			public void actionPerformed(final ActionEvent e) {
				videoCap.lowDec();
			}
		};
		final String callUpInc = "upInc";
		final String callUpDec = "upDec";
		final String callLowInc = "lowInc";
		final String callLowDec = "lowDec";

		contentPane.getInputMap().put(KeyStroke.getKeyStroke('u'), callUpInc);
		contentPane.getInputMap().put(KeyStroke.getKeyStroke('i'), callUpDec);
		contentPane.getInputMap().put(KeyStroke.getKeyStroke('j'), callLowInc);
		contentPane.getInputMap().put(KeyStroke.getKeyStroke('k'), callLowDec);

		contentPane.getActionMap().put(callUpInc, upInc);
		contentPane.getActionMap().put(callUpDec, upDec);
		contentPane.getActionMap().put(callLowInc, lowInc);
		contentPane.getActionMap().put(callLowDec, lowDec);

	}

	/**
	 * draws the next frame to be displayed. calls videocap.[...] method which returns a processed
	 * video frame
	 * 
	 * @param g
	 *            graphics
	 */
	public void paint(final Graphics g) {
		try {
			if (videoCap != null) {
				contentPane.removeAll();
				g.drawImage(videoCap.getOneFrame(), 0, 0, this);
				g.setColor(Color.GRAY);
				g.fillRect(CONTROL_TEXT_X, CONTROL_TEXT_Y + CONTROL_TEXT_BOX_YDIFF,
						CONTROL_TEXT_BOX_WIDTH, CONTROL_TEXT_BOX_HEIGHT);
				g.setColor(Color.GREEN);
				g.drawString(
						"Controls: -b setBg(), -u upInc(), -i upDec(), -j lowInc(), -k lowDec()",
						CONTROL_TEXT_X, CONTROL_TEXT_Y);
			}
		} catch (final CameraNotOnException e) {
			contentPane.removeAll();
			upperblock.add(refreshButton);
			upperblock.add(label);
			contentPane.add(upperblock);
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
	 * 
	 * @param name
	 *            String to be displayed on the button.
	 * @return button JButton
	 */
	private JButton makeButton(final String name) {
		final JButton button = new JButton(name);
		button.setActionCommand(name.substring(name.length() - 1));
		button.addActionListener(this);
		button.setEnabled(true);
		return button;
	}

	/**
	 * Shuts the running thread down.
	 */
	public void kill() {
		runThread = false;
	}

	@Override
	public void actionPerformed(final ActionEvent e) {
		if (REFRESH_COMMAND.equals(e.getActionCommand())) {
			triedCameras = false;
			buttons.clear();
		} else {
			videoCap.openCamera(Integer.parseInt(e.getActionCommand()));
		}
	}
}
