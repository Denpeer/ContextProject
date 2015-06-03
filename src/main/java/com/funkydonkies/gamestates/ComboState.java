package com.funkydonkies.gamestates;

import com.funkydonkies.core.App;
import com.funkydonkies.exceptions.BadDynamicTypeException;
import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.font.BitmapText;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;

/**
 * Handles the combo in game.
 * (displaying, increasing, resetting etc)
 */
public class ComboState extends AbstractAppState{
	private static final Vector3f COUNTER_LOCATION = new Vector3f(300, 0, 0);
	private static final int TEXT_SIZE = 30;
	private static int currentCombo;
	private static BitmapText comboText;
	private Node guiNode;
	
	private AppStateManager stateManager;
	private App app;
	
	@Override
	public final void initialize(final AppStateManager sManager, final Application appl) {
		super.initialize(sManager, appl);
		stateManager = sManager;
		if (appl instanceof App) {
			this.app = (App) appl;
		} else {
			throw new BadDynamicTypeException();
		}
		currentCombo = 0;
		guiNode = app.getGuiNode();	

		handleText();	
		display();
	}
	/**
	 * This method handles the text to display the combo.
	 */
	public void handleText(){
		comboText = new BitmapText(
				app.getAssetManager().loadFont("Interface/Fonts/Default.fnt"), false);
		comboText.setSize(TEXT_SIZE);
		comboText.setColor(ColorRGBA.Red);
		COUNTER_LOCATION.y = comboText.getLineHeight();
		comboText.setLocalTranslation(COUNTER_LOCATION);		
	}
	
	/**
	 * The update method of the comboState.
	 * @param tpf
	 */
	public void update(float tpf){
		updateText();
	}
	
	/**
	 * Increments the current combo by one and updates the text to represent this.
	 * @return currentCombo int the current combo.
	 */
	public static int incCombo() {
		currentCombo++;
		updateText();
		return currentCombo;
	}
	
	/**
	 * Returns the current combo count.
	 * @return currentCombo int, the current combo
	 */
	public static int getCombo() {
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
	public static void updateText() {
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
