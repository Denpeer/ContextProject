package com.funkydonkies.tiers;

import java.util.ArrayList;

import com.funkydonkies.core.App;
import com.funkydonkies.exceptions.BadDynamicTypeException;
import com.funkydonkies.gamestates.DisabledState;
import com.funkydonkies.interfaces.FactoryInterface;
import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import com.jme3.font.BitmapText;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;

/**
 * The general tier class.
 */
public class Tier extends DisabledState {
	private ArrayList<FactoryInterface> obstacleArray;
	public static final int TEXT_SIZE = 50;
	private final Vector3f LOCATION = new Vector3f(200, 450, 0);
	private BitmapText tierText;
	private Node guiNode;
	private App app;
	private final float fadeTime = 3;
	private float timer = 0;
	/**
	 * The initialize method.
	 */
	@Override
	public void initialize(final AppStateManager stateManager, final Application appl) {
		super.initialize(stateManager, app);
		if (appl instanceof App) {
			this.app = (App) appl;
		} else {
			throw new BadDynamicTypeException();
		}
		guiNode = app.getGuiNode();
		obstacleArray = new ArrayList<FactoryInterface>();
		createTierText();

	}
	/**
	 * @see com.jme3.app.state.AbstractAppState#update(float)
	 */
	@Override
	public void update(final float tpf) {
		super.update(tpf);
		if (guiNode.hasChild(tierText)) {
			timer += tpf;
			if (timer > fadeTime) {
				guiNode.detachChild(tierText);
				timer = 0;
			}
		}
	}
	
	/**
	 * The getter for the obstacle array.
	 * @return the obstacle array
	 */
	public ArrayList<FactoryInterface> getObstacleArray() {
		return obstacleArray;
	}
	
	/**
	 * This method creates the tier text.
	 */
	public void createTierText() {
		tierText = createText();
		tierText.setSize(TEXT_SIZE);
		tierText.setColor(ColorRGBA.Red);
		tierText.setLocalTranslation(LOCATION);	
	}
	
	public void setText(String text){	
		tierText.setText(text);
		guiNode.attachChild(tierText);
	}
	
	
	/**
	 * Creates and returns a new BitmapText to display the tier.
	 * 
	 * @return new BitmapText
	 */
	public BitmapText createText() {
		return new BitmapText(app.getAssetManager().loadFont("Interface/Fonts/Default.fnt"), false);
	}

}