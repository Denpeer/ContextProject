package com.funkydonkies.combo;

import com.funkydonkies.core.App;
import com.funkydonkies.gamestates.DifficultyState;
import com.funkydonkies.interfaces.Observable;
import com.funkydonkies.interfaces.Observer;
import com.jme3.font.BitmapText;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;

/**
 * Displays the combo on the HUD. This class uses the observer pattern to get a notification when it
 * should update the HUD texts.
 */
public class ComboDisplay implements Observer {
	public static final Vector3f COUNTER_LOCATION = new Vector3f(250, 100, 0);
	public static final int TEXT_SIZE = 60;
	private static final float X_TRANSLATION = 800;
	private BitmapText comboText;
	private BitmapText highestComboText;
	private Node guiNode;
	private App app;
	private DifficultyState diffState;

	/**
	 * Constructor for Combo.
	 * 
	 * @param appl
	 *            App to get nodes, sManager, etc
	 */
	public ComboDisplay(final App appl) {
		this.app = appl;
		guiNode = app.getGuiNode();
		diffState = app.getStateManager().getState(DifficultyState.class);
		diffState.addObserver(this);
		updateText();
	}

	/**
	 * Initializes the comboDisplay by calling the methods to create the texts and calling
	 * updateText to set the right text.
	 */
	public void init() {
		createCurrentComboText();
		createHighestComboText();
		updateText();
	}

	/**
	 * Creates the BitMapTExt that displays the current combo count.
	 */
	public void createCurrentComboText() {
		comboText = createText();
		comboText.setSize(TEXT_SIZE);
		comboText.setColor(ColorRGBA.Red);
		COUNTER_LOCATION.y = comboText.getLineHeight();
		comboText.setLocalTranslation(COUNTER_LOCATION);
		guiNode.attachChild(comboText);
	}

	/**
	 * Creates the BitMapTExt that displays the highest combo count yet.
	 */
	public void createHighestComboText() {
		highestComboText = createText();
		highestComboText.setSize(TEXT_SIZE);
		highestComboText.setColor(ColorRGBA.Yellow);
		COUNTER_LOCATION.y = highestComboText.getLineHeight();
		highestComboText.setLocalTranslation(COUNTER_LOCATION.setX(X_TRANSLATION));
		guiNode.attachChild(highestComboText);
	}

	/**
	 * Creates and returns a new BitmapText to display the combo.
	 * 
	 * @return new BitmapText
	 */
	public BitmapText createText() {
		return new BitmapText(app.getAssetManager().loadFont("Interface/Fonts/Default.fnt"), false);
	}

	/**
	 * Calls setText on the bitmapText to update it to represent the current combo.
	 */
	public void updateText() {
		if (comboText != null && highestComboText != null) {
			comboText.setText("Current combo: " + diffState.getCombo());
			if (diffState.getCombo() >= diffState.getMaxCombo()) {
				highestComboText.setText("Highest combo: " + diffState.getCombo());
			}
		}
	}

	/**
	 * @param o
	 *            the Observable that called the update.
	 * @param arg
	 *            Object, not used in our app.
	 * @see com.funkydonkies.interfaces.Observer#update(com.funkydonkies.interfaces.Observable,
	 *      java.lang.Object)
	 */
	@Override
	public void update(final Observable o, final Object arg) {
		updateText();
	}
}
