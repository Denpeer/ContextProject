package com.funkydonkies.w4v3;

import com.jme3.font.BitmapText;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Node;

public class Combo {
	private int currentCombo;
	private BitmapText comboText;
	private Node guiNode;
	
	public Combo(Node node, BitmapText text){
		currentCombo = 0;
		guiNode = node;
//		myFont = assetManager.loadFont("Interface/Fonts/Console.fnt");
//		comboText = new BitmapText(myFont, false);
		comboText = text;
		comboText.setSize(30);
		comboText.setColor(ColorRGBA.Red);
		comboText.setLocalTranslation(300, comboText.getLineHeight(), 0);
		comboText.setText("Current combo: " + (currentCombo));
	}
	
	public int incCombo(){
		comboText.setText("Current combo: " + (currentCombo + 1));
		return ++currentCombo;
	}
	
	public int getCombo(){
		return currentCombo;
	}
	
	public void resetCombo(){
		comboText.setText("Current combo: " + (currentCombo));
		currentCombo = 0;
	}
	
	public void display(){
		guiNode.attachChild(comboText);
	}
}
