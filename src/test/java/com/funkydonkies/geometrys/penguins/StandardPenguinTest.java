package com.funkydonkies.geometrys.penguins;


import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import org.junit.Test;

import com.funkydonkies.geometrys.penguins.StandardPenguin;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.material.Material;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;

/**
 * This class tests the StandardPenguin.
 */
public class StandardPenguinTest {
	private static StandardPenguin sp;
	private static Node node;
	

	
	/**
	 * The if the penguin is drawn.
	 */
	@Test
	public void testStandardPenguin() {
		node = new Node();
		sp = new StandardPenguin("kw", new Box(1, 1, 1), node, mock(Material.class), mock(PhysicsSpace.class), 1);
		assertTrue(node.hasChild(sp));
	}

}
