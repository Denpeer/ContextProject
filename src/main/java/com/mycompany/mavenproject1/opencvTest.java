//package com.mycompany.mavenproject1;
//
//import org.opencv.core.Core;
//import org.opencv.core.Mat;
//import org.opencv.core.CvType;
//import org.opencv.core.Scalar;
//
//class opencvTest {
//
//  static{ System.loadLibrary(Core.NATIVE_LIBRARY_NAME); }
//  
//  public static Mat makeMatrix(int n){
//	  Mat m = new Mat(n, n, CvType.CV_8UC1, new Scalar(0));
//	  return m;
//  }
//  
//  public static void setupCam() {
//	  //checkout https://github.com/andrewssobral/java_motion_detection/blob/master/src/javaopencv/Main.java
//	  
//  }
//
//  public static void main(String[] args) {
////    System.out.println("Welcome to OpenCV " + Core.VERSION);
////    Mat m = new Mat(5, 10, CvType.CV_8UC1, new Scalar(0));
////    System.out.println("OpenCV Mat: " + m);
////    Mat mr1 = m.row(1);
////    mr1.setTo(new Scalar(1));
////    Mat mc5 = m.col(5);
////    mc5.setTo(new Scalar(5));
////    System.out.println("OpenCV Mat data:\n" + m.dump());
//  }
//}
//
//
////package com.mycompany.mavenproject1;
////
////import org.bytedeco.javacpp.opencv_core.IplImage;
////import org.bytedeco.javacpp.opencv_imgproc.CvDistanceFunction;
////import org.bytedeco.javacv.*;
////import com.googlecode.javacpp.Loader;
////
////import com.jme3.scene.plugins.blender.materials.MaterialHelper.DiffuseShader;
////
////import de.lessvoid.nifty.layout.manager.AbsolutePositionLayout;
////
////public class opencvTest {
////    
////    public static void main(String[] args){
////    	//Create canvas frame for displaying webcam.
////        CanvasFrame canvas = new CanvasFrame("Webcam");
////        
////        //Set Canvas frame to close on exit
////        canvas.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
////        canvas.setSize(640, 480);
////        
////        //Declare FrameGrabber to import output from webcam
////        FrameGrabber grabber = new OpenCVFrameGrabber("");
////        
////        //init background
////        IplImage bg; 
////        IplImage res;
////        boolean firstPass = false;
////        
////        try {      
////            
////            //Start grabber to capture video
////            grabber.start();      
////             
////            IplImage img;
////             
////            while (true) {
////              
////             //inser grabed video fram to IplImage img
////             img = grabber.grab();
////
////             if(firstPass == false){
////            	 bg = img;
////            	 firstPass = true;
////             }
////             
////             //Set canvas size as per dimentions of video frame.
////             canvas.setCanvasSize(grabber.getImageWidth(), grabber.getImageHeight()); 
////              
////             if (img != null) {      
////              //Show video frame in canvas
////              canvas.showImage(img);               
////              }
////             }
////            }
////           catch (Exception e) {      
////           }
////    }
////}