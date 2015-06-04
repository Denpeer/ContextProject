package com.funkydonkies.geometrys.targets;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import org.junit.Test;

import com.jme3.bullet.PhysicsSpace;
import com.jme3.material.Material;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;

/**
 * This class tests the Squid.
 */
public class SquidTest {
	private static Squid sq;
	private static Node node;
	

	
	/**
	 * Test if the target is drawn.
	 */
	@Test
	public void testSquid() {
		node = new Node();
		sq = new Squid("kw", new Box(1, 1, 1), node, 
				mock(Material.class), mock(PhysicsSpace.class));
		assertTrue(node.hasChild(sq));
	}

}
