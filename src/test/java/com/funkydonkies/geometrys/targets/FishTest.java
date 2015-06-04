package com.funkydonkies.geometrys.targets;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import com.funkydonkies.geometrys.targets.Fish;

import org.junit.Test;

import com.jme3.bullet.PhysicsSpace;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;

/**
 * This class tests the FatPenguin.
 */
public class FishTest {
	private static Fish f;
	private static Node node;
	

	
	/**
	 * Test if the target is drawn.
	 */
	@Test
	public void testFatPenguin() {
		node = new Node();
		f = new Fish("kw", new Box(1, 1, 1), node, 
				mock(Material.class), mock(PhysicsSpace.class), new Vector3f(1, 1, 1));
		assertTrue(node.hasChild(f));
	}

}
