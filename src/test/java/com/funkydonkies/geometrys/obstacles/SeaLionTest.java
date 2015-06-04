package com.funkydonkies.geometrys.obstacles;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import org.junit.Test;

import com.funkydonkies.geometrys.obstacles.SeaLion;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.material.Material;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;

/**
 * This class tests the sea lion.
 * @author SDumasy
 *
 */
public class SeaLionTest {
	private static SeaLion sl;
	private static Node node;
	

	
	/**
	 * The if the whale is drawn.
	 */
	@Test
	public void testKillerWhale() {
		node = new Node();
		sl = new SeaLion("kw", new Box(1, 1, 1), node, mock(Material.class), mock(PhysicsSpace.class));
		assertTrue(node.hasChild(sl));
	}

}
