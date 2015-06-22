package com.funkydonkies.gamestates;

import com.funkydonkies.camdetect.MyFrame;
import com.funkydonkies.interfaces.Bridge;
import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;

/**
 * Controls the state of the camera. i.e. it is in charge of opening and closing the camera.
 */
public class CameraState extends AbstractAppState {
	private Bridge bridge;
	private MyFrame cameraFrame;
	private boolean cameraOpened = false;
	private AppStateManager sManager;
	private boolean cameraPointsActivated = false;
	private CurveState curveState;

	@Override
	public void initialize(final AppStateManager stateManager, final Application app) {
		super.initialize(stateManager, app);
		super.setEnabled(false);
		sManager = stateManager;
		curveState = sManager.getState(CurveState.class);
	}

	/**
	 * Opens the camera frame when it is called with true, disposes the frame when it is called with
	 * false.
	 * 
	 * @see com.jme3.app.state.AbstractAppState#setEnabled(boolean)
	 * @param enabled
	 *            Desired state of cameraState
	 */
	@Override
	public void setEnabled(final boolean enabled) {
		super.setEnabled(enabled);
		if (!enabled) {
			cameraFrame.dispose();
			cameraOpened = false;
			cameraPointsActivated = false;
			curveState.setCameraEnabled(false);
			curveState.setUpdateEnabled(false);
		} else {
			cameraFrame = new MyFrame();
		}
	}

	/**
	 * Opens the camera frame in a new thread.
	 */
	public void openCamera() {
		new Thread(cameraFrame).start();
		bridge = cameraFrame.getVideoCap().getMat2Image();
		cameraOpened = true;
	}

	/**
	 * Opens the camera if the camerastate becomes active and videocap has been initialized. Once
	 * the background has been set it toggles the camera enabled bit and the update enabled bit.
	 * 
	 * @param tpf
	 *            time per frame
	 * @see com.jme3.app.state.AbstractAppState#update(float)
	 */
	@Override
	public void update(final float tpf) {
		super.update(tpf);
		if (cameraFrame.getVideoCap() != null && !cameraOpened) {
			openCamera();
		}
		if (bridge.isBgSet() && !cameraPointsActivated) {
			cameraPointsActivated = true;
			curveState.setCameraEnabled(true);
			curveState.setUpdateEnabled(true);
		}
	}

	/**
	 * Returns the bridge.
	 * 
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
	 * 
	 * @return boolean cameraOpened
	 */
	public boolean cameraOpened() {
		return cameraOpened;
	}
}
