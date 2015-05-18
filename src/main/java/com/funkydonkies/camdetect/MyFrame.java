package com.funkydonkies.camdetect;

import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.border.EmptyBorder;

public class MyFrame extends JFrame {
    private JPanel contentPane;

  /**
  * Launch the application.
  */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    MyFrame frame = new MyFrame();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    VideoCap videoCap = new VideoCap();
    
  /**
  * Create the frame.
  */
    public MyFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 650, 490);
        contentPane = new JPanel();
        initBgSetKey();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        new MyThread().start();
    }
    
    /**
     * press 'b' to set background to current frame
     * currently there is an error every second time the 'b' key is pressed
     * the error does not conflict with the functioning of the program
     */
    public void initBgSetKey(){
    	//create action
        Action setTheBg = new AbstractAction(){
			public void actionPerformed(ActionEvent e) {
				videoCap.setBg();
			}
        };
        //add keypress 'b' sets current frame as background
        contentPane.getInputMap().put(KeyStroke.getKeyStroke('b'),"setBg");
        contentPane.getActionMap().put("setBg", setTheBg);
    }
 
 
    public void paint(Graphics g){
        g = contentPane.getGraphics();
        g.drawImage(videoCap.getOneFrame(), 0, 0, this);
    }
 
    class MyThread extends Thread{
        @Override
        public void run() {
            for (;;){
                repaint();
                try { Thread.sleep(30);
                } catch (InterruptedException e) {    }
            }  
        } 
    }
}