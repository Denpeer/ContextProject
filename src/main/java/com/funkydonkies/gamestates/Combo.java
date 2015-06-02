package com.funkydonkies.gamestates;

import com.jme3.font.BitmapText;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;

/**
 * Handles the combo in game.
 * (displaying, increasing, resetting etc)
 */
public class Combo {
	private static final Vector3f COUNTER_LOCATION = new Vector3f(300, 0, 0);
	private static final int TEXT_SIZE = 30;
	private int currentCombo;
	private BitmapText comboText;
	private Node guiNode;
	
	/**
	 * Constructor for Combo.
	 * @param node guiNode, to attach the HUD counter
	 * @param text BitMapText, to store the HUD text
	 */
	public Combo(final Node node, final BitmapText text) {
		currentCombo = 0;
		guiNode = node;
		comboText = text;
		comboText.setSize(TEXT_SIZE);
		comboText.setColor(ColorRGBA.Red);
		COUNTER_LOCATION.y = comboText.getLineHeight();
		comboText.setLocalTranslation(COUNTER_LOCATION);
		updateText();
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
		comboText.setText("Current combo: " + (currentCombo));
	}
	
	/**
	 * Displays the combo by attaching the bitmaptext to the guiNode that was received when the 
	 * Combo was created.
	 */
	public void display() {
		guiNode.attachChild(comboText);
	}
}
