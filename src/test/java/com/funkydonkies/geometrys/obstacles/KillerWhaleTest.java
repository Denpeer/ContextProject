package com.funkydonkies.geometrys.obstacles;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import com.funkydonkies.geometries.obstacles.KillerWhale;

import org.junit.Test;

import com.jme3.bullet.PhysicsSpace;
import com.jme3.material.Material;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;

/**
 * This class tests the killerWhale.
 * @author SDumasy
 *
 */
public class KillerWhaleTest {
	private static KillerWhale kw;
	private static Node node;
	

	
	/**
	 * The if the whale is drawn.
	 */
	@Test
	public void testKillerWhale() {
		node = new Node();
		kw = new KillerWhale("kw", new Box(1, 1, 1), node, mock(Material.class), mock(PhysicsSpace.class));
		assertTrue(node.hasChild(kw));
	}

}
