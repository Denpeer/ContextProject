package com.funkydonkies.combo;

import java.util.Observable;

import com.funkydonkies.core.App;
import com.jme3.font.BitmapText;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;

/**
 * Handles the combo in game.
 * (displaying, increasing, resetting etc)
 */
public class Combo extends Observable {
	public static final Vector3f COUNTER_LOCATION = new Vector3f(250, 100, 0);
	public static final int TEXT_SIZE = 30;
	private int currentCombo;
	private int highestCombo;
	private BitmapText comboText;
	private BitmapText highestComboText;
	private Node guiNode;
	
	/**
	 * Constructor for Combo.
	 * @param node guiNode, to attach the HUD counter
	 * @param text BitMapText, to store the HUD text
	 */
	public Combo(final App app) {
		currentCombo = 0;
		highestCombo = 0;
		guiNode = app.getGuiNode();
		updateText();
	}
	
	public void createCurrentComboText(App app) {
		comboText = createText(app);
		comboText.setSize(TEXT_SIZE);
		comboText.setColor(ColorRGBA.Red);
		COUNTER_LOCATION.y = comboText.getLineHeight();
		comboText.setLocalTranslation(COUNTER_LOCATION);
		guiNode.attachChild(comboText);
	}
	
	public void createHighestComboText(App app) {
		highestComboText = createText(app);
		highestComboText.setSize(TEXT_SIZE);
		highestComboText.setColor(ColorRGBA.Yellow);
		COUNTER_LOCATION.y = highestComboText.getLineHeight();
		highestComboText.setLocalTranslation(COUNTER_LOCATION.setX(500));
		guiNode.attachChild(highestComboText);
}
	
	public BitmapText createText(App app) {
		return new BitmapText(app.getAssetManager().loadFont("Interface/Fonts/Default.fnt"), false);
	}
	
	/**
	 * Increments the current combo by one and updates the text to represent this.
	 * @return currentCombo int the current combo.
	 */
	public int incCombo() {
		currentCombo++;
		updateText();
		return currentCombo;
	}
	
	/**
	 * Returns the current combo count.
	 * @return currentCombo int, the current combo
	 */
	public int getCombo() {
		return currentCombo;
	}
	
	/**
	 * Resets the current combo to 0 and updates the text.
	 */
	public void resetCombo() {
		currentCombo = 0;
		updateText();
	}
	
	/**
	 * Calls setText on the bitmapText to update it to represent the current combo.
	 */
	public void updateText() {
		if (comboText != null && highestComboText != null) {
			comboText.setText("Current combo: " + (currentCombo));
			if(currentCombo >= highestCombo){
				highestCombo = currentCombo;
				highestComboText.setText("Highest combo: " + (currentCombo));
			}
		}
	}
}
