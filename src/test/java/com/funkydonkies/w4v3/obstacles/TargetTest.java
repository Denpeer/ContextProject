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
	private static AssetManager assetManager;
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
	
//	/**
//	 * Tests if the collision method works accordingly, not colliding.
//	 */
//	@Test
//	public final void notCollidingTest() {
//		rootNode = new Node();
//		final Ball bal = new Ball(SHAPE, geom, mat, null);
//		bal.spawn(rootNode, psySpace, false);
//		tar.draw(mat, psySpace);
//		assertEquals(rootNode.getChildren().size(), 2);
//	}
	
//	/**
//	 * Tests if the collision method works accordingly, colliding.
//	 */
//	@Test
//	public final void collidingTest() {
//		rootNode = new Node();
//		final Ball bal = new Ball(SHAPE, geom, mat, null);
//		tar.draw(mat, psySpace);
//		bal.spawn(rootNode, psySpace, false);
//		final Vector3f vec = new Vector3f();
//		vec.add((float) tar.getxCoord(), (float) tar.getyCoord(), (float) tar.getzCoord());
//		ball.setLocation(vec);
//		assertEquals(rootNode.getChildren().size(), 2);
//	}
	
	/**
	 * Test the getters of obstacle.
	 */
	@Test
	public final void getterTest() {
		final double testX = 30;
		final double testY = 0.5;
		final double testZ = 1;
		final double testWidth = 1;
		final double testHeight = 1;
		final double testDepth = 1;
		assertTrue(tar.getxCoord() == testX);
		assertTrue(tar.getyCoord() == testY);
		assertTrue(tar.getzCoord() == testZ);
		assertTrue(tar.getWidth() == testWidth);
		assertTrue(tar.getHeight() == testHeight);
		assertTrue(tar.getDepth() == testDepth);
	}
}
