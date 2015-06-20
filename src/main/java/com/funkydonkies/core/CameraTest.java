package com.funkydonkies.core;

import com.funkydonkies.camdetect.Mat2Image;
import com.funkydonkies.camdetect.MyFrame;
import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.jme3.texture.Image;
import com.jme3.texture.Image.Format;
import com.jme3.texture.Texture.WrapAxis;
import com.jme3.texture.Texture.WrapMode;
import com.jme3.texture.Texture2D;

/**
 * This test renders a scene to a texture, then displays the texture on a cube.
 */
public class CameraTest extends SimpleApplication {

	private Geometry quad;
	private Material quadMat;
	private Mat2Image m2;
	private Texture2D quadTex;

	public static void main(String[] args) {
		final CameraTest app = new CameraTest();
		app.setPauseOnLostFocus(false);
		app.start();
	}
	
	public CameraTest() {
		final MyFrame f = new MyFrame();
		new Thread(f).start();
		m2 = f.getVideoCap().getMat2Image(); 
		quadTex = new Texture2D();
	}

	@Override
	public void simpleInitApp() {
		cam.setLocation(new Vector3f(3, 3, 3));
		cam.lookAt(Vector3f.ZERO, Vector3f.UNIT_Y);

		quadTex = new Texture2D();

		quad = new Geometry("box", new Box(Vector3f.ZERO, 1, 1, 1));

		quadMat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
		quadMat.setTexture("ColorMap", quadTex);
		quad.setMaterial(quadMat);
		rootNode.attachChild(quad);
	}

	@Override
	public void simpleUpdate(final float tpf) {
		if (m2 != null) {
			final Image im = new Image(Format.RGB8, m2.getImageWidth(), m2.getImageHeight(), m2.getByteBuffer());
			quadTex.setImage(im);
			quadMat.clearParam("ColorMap");
			quadMat.setTexture("ColorMap", quadTex);
			quad.setMaterial(quadMat);
		}
	}

}
