package com.funkydonkies.gamestates;

import com.funkydonkies.camdetect.Mat2Image;
import com.funkydonkies.camdetect.VideoCap;
import com.funkydonkies.core.App;
import com.funkydonkies.exceptions.BadDynamicTypeException;
import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.material.Material;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.jme3.texture.Image;
import com.jme3.texture.Image.Format;
import com.jme3.texture.Texture2D;

/**
 * Controls the state of the camera. i.e. it is in charge of opening and closing the camera.
 */
public class InGameCameraState extends AbstractAppState {

	private static final String COLOR_MAP = "ColorMap";
	private static final Vector3f INIT_LOC = new Vector3f(150, 40, -40);
	private static final float DEFAULT_WIDTH = 150;
	private static final float DEFAULT_HEIGHT = 40;
	private static final float DEFAULT_DEPTH = 1;

	private App app;
	private CameraState camState;

	private Geometry quad;
	private Material quadMat;
	private Mat2Image m2;
	private Texture2D quadTex;

	/**
	 * Constructor, takes a Mat2Image object.
	 * 
	 * @param bridge
	 *            Mat2Image object
	 */
	public InGameCameraState(final Mat2Image bridge) {
		m2 = bridge;
	}

	@Override
	public void initialize(final AppStateManager stateManager, final Application appl) {
		super.initialize(stateManager, appl);
		if (appl instanceof App) {
			app = (App) appl;
		} else {
			throw new BadDynamicTypeException();
		}
		camState = stateManager.getState(CameraState.class);
		
		initCam();
	}

	/**
	 * Initialize the camera.
	 */
	public void initCam() {
		quadTex = new Texture2D();

		quad = new Geometry("box", new Box(DEFAULT_WIDTH, DEFAULT_HEIGHT, DEFAULT_DEPTH));
		quad.setLocalTranslation(INIT_LOC);
		quad.setLocalRotation(quad.getLocalRotation().addLocal(
				new Quaternion().fromAngleAxis(FastMath.TWO_PI, new Vector3f(0, 0, 1))));
		quadMat = new Material(app.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
//		quadMat.getAdditionalRenderState().setBlendMode(BlendMode.Alpha);
		quad.setMaterial(quadMat);
//		quad.setQueueBucket(Bucket.Transparent);
		app.getRootNode().attachChild(quad);
	}

	/**
	 * 
	 * @param tpf
	 *            time per frame
	 * @see com.jme3.app.state.AbstractAppState#update(float)
	 */
	@Override
	public void update(final float tpf) {
		super.update(tpf);
		if (m2 != null) {
			final Image im = new Image(Format.RGB8, m2.getImageWidth(), m2.getImageHeight(),
					m2.getByteBuffer());
			quadTex.setImage(im);
			quadMat.setTexture(COLOR_MAP, quadTex);
			quad.setMaterial(quadMat);
		} else {
			m2 = (Mat2Image) camState.getBridge();
		}
	}

}
