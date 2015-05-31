package com.funkydonkies.gamestates;

import com.funkydonkies.camdetect.MyFrame;
import com.funkydonkies.w4v3.Bridge;
import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;

/**
 * Controls the state of the camera.
 * i.e. it is in charge of opening and closing the camera.
 */
public class CameraState extends AbstractAppState {
	private Bridge bridge;
	private MyFrame cameraFrame;
	private boolean cameraOpened = false;
	
	@Override
	public void initialize(final AppStateManager stateManager, final Application app) {
		super.initialize(stateManager, app);
		super.setEnabled(false);
	}
	
	/**
	 * Opens the camera frame when it is called with true, 
	 * disposes the frame when it is called with false.
	 * @see com.jme3.app.state.AbstractAppState#setEnabled(boolean)
	 * @param enabled Desired state of cameraState
	 */
	@Override
	public void setEnabled(final boolean enabled) {
		super.setEnabled(enabled);
		if (!enabled) {
			cameraFrame.dispose();
			cameraOpened = false;
		} else {
			cameraFrame = new MyFrame();
		}
	}
	
	public void openCamera() {
		new Thread(cameraFrame).start();
		bridge = cameraFrame.getVideoCap().getMat2Image();
		cameraOpened = true;
	}
	
	@Override
	public void update(float tpf) {
		super.update(tpf);
		if (cameraFrame.getVideoCap() != null && !cameraOpened) {
			System.out.println("CameraState: openedCamera");
			openCamera();
		}
	}
	
	/**
	 * Returns the bridge.
	 * @return Bridge
	 */
	public Bridge getBridge() {
		return bridge;
	}
	
	/**
	 * Toggles the camera.
	 */
	public void toggleEnabled() {
		if (isEnabled()) {
			setEnabled(false);
		} else {
			setEnabled(true);
		}
	}
	
	/**
	 * Returns whether the camera frame is opened or not.
	 * @return boolean cameraOpened
	 */
	public boolean cameraOpened() {
		return cameraOpened;
	}
}
