package com.funkydonkies.w4v3.obstacles;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;

import com.funkydonkies.w4v3.Combo;
import com.funkydonkies.w4v3.TargetControl;
import com.jme3.asset.AssetManager;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

/**
 * This class takes care of testing the target class.
 * @author SDumasy
 *
 */
public class TargetTest {
	private static final Vector3f INITIAL_SPAWN_LOCATION = new Vector3f(30f, 0.5f, 1f);

	private static ObstacleFactory obF;
	private static Target tar;
	private static Material mat;
	private static Node rootNode;
	private static PhysicsSpace psySpace;
	private static Combo combo;
	
	/**
	 * Setup the test class, mock and instantiate objects.
	 */
	@Before
	public void setUp() {
		obF = new ObstacleFactory();
		assetManager = mock(AssetManager.class);
		combo = mock(Combo.class);
		rootNode = mock(Node.class);
		tar = obF.makeTarget(rootNode, combo);
		mat = mock(Material.class);

		psySpace = mock(PhysicsSpace.class); 
	}
	/**
	 * Tests if the box is succesfully attached to the rootnode.
	 */
	@Test
	public final void drawTest() {		
		tar.draw(mat, psySpace);
		verify(rootNode).attachChild(any(Spatial.class));
//		verify(any(Spatial.class)).setMaterial(mat);
		verify(psySpace).add(any(TargetControl.class));
	}
	
	@Test
	public final void destroyTest() {
		tar.destroy();
		verify(rootNode).detachChild(any(Spatial.class));
	}
	
	@Test
	public final void testGetLocation() {
		tar.draw(mat, psySpace);
		assertEquals(INITIAL_SPAWN_LOCATION, tar.getLocation());
	}
	
	@Test 
	public final void testRespawn() {
		tar.draw(mat, psySpace);
		final Vector3f startLoc = tar.getLocation();
		System.out.println(startLoc);
		tar.respawn();
		Vector3f newLoc = tar.getLocation();
		System.out.println(newLoc);
		System.out.println(startLoc);
//		assertNotEquals(tar.getLocation(), startLoc);
	}
	
	/**
	 * Test the getters of obstacle.
	 */
	@Test
	public final void getterTest() {
		final float testX = 30;
		final float testY = 0.5f;
		final float testZ = 1;
		final float testWidth = 1;
		final float testHeight = 1;
		final float testDepth = 1;
		assertTrue(tar.getxCoord() == testX);
		assertTrue(tar.getyCoord() == testY);
		assertTrue(tar.getzCoord() == testZ);
		assertTrue(tar.getWidth() == testWidth);
		assertTrue(tar.getHeight() == testHeight);
		assertTrue(tar.getDepth() == testDepth);
	}
}
