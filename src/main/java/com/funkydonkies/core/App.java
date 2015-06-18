package com.funkydonkies.core;

import com.funkydonkies.gamestates.PlayState;
import com.jme3.app.SimpleApplication;
import com.jme3.font.BitmapText;
import com.jme3.post.FilterPostProcessor;
import com.jme3.post.filters.BloomFilter;
import com.jme3.post.ssao.SSAOFilter;
import com.jme3.scene.Node;
/**
 * Game is run through this class.
 *
 */
public class App extends SimpleApplication {
	private PlayState playState;
	private Node penguinNode;
	/**		
	 * Main method. Instantiates the app and starts its execution.
	 * 
	 * @param args
	 *            run arguments
	 */
	public static void main(final String[] args) {
		final App app = new App();
		app.start();
	}

	@Override
	public void simpleInitApp() {
		playState = new PlayState();
		stateManager.attach(playState);
		
		penguinNode = new Node("penguins");
		rootNode.attachChild(penguinNode);
		
		setRootNodeUserData();
		setPauseOnLostFocus(false);
		
		setDisplayFps(false);
		setDisplayStatView(false);
		
		addEffects();
	}
	
	/**
	 * Adds Bloom (objects can glow) and ambient occlusion.
	 */
	public void addEffects() {
		final FilterPostProcessor fpp = new FilterPostProcessor(assetManager);
		final BloomFilter bloom = new BloomFilter(BloomFilter.GlowMode.Objects);
		final SSAOFilter ssaoFilter = new SSAOFilter();
		fpp.addFilter(bloom);
		fpp.addFilter(ssaoFilter);
		getViewPort().addProcessor(fpp);
	}
	
	/**
	 * Initializes default materials and sets them as User Data in the root node.
	 */
	public void setRootNodeUserData() {
		rootNode.setUserData("default text", new BitmapText(
				assetManager.loadFont("Interface/Fonts/Default.fnt"), false));
	}

	/**
	 * Returns the penguin node, containing all the penguins.
	 * @return penguinNode Node
	 */
	public Node getPenguinNode() {
		return penguinNode;
	}
}
