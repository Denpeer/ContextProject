package com.funkydonkies.geometrys.targets;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import org.junit.Test;

import com.funkydonkies.geometries.targets.Krill;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.material.Material;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;

/**
 * This class tests the Krill.
 */
public class KrillTest {
	private static Krill k;
	private static Node node;
	

	
	/**
	 * Test if the target is drawn.
	 */
	@Test
	public void testFatPenguin() {
		node = new Node();
		k = new Krill("kw", new Box(1, 1, 1), node, 
				mock(Material.class), mock(PhysicsSpace.class));
		assertTrue(node.hasChild(k));
	}

}
