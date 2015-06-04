package com.funkydonkies.core;

import com.funkydonkies.gamestates.PlayState;
import com.jme3.app.SimpleApplication;
import com.jme3.renderer.RenderManager;
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
	 * @param args run arguments
	 */
	public static void main(final String[] args) {
		final App app = new App();
		app.start();
	}

	@Override
	public void simpleInitApp() {
		playState = new PlayState();
		stateManager.attach(playState);
		
		penguinNode = new Node("penguins");//*********
		rootNode.attachChild(penguinNode);
	}

	@Override
	public void simpleUpdate(final float tpf) {

	}

	@Override
	public void simpleRender(final RenderManager rm) {

	}
	
	/**
	 * Returns the penguin node, containing all the penguins.
	 * @return penguinNode Node
	 */
	public Node getPenguinNode() {
		return penguinNode;
	}
}
