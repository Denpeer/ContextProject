package com.funkydonkies.w4v3.obstacles;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import org.junit.BeforeClass;
import org.junit.Test;

import com.jme3.bullet.PhysicsSpace;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Sphere;
import com.mycompany.mavenproject1.Ball;

/**
 * This class takes care of testing the target class.
 * @author SDumasy
 *
 */
public class TargetTest {
	private static final Sphere SHAPE =  new Sphere(20, 20, 0.5f);
	private static ObstacleFactory obF;
	private static Target tar;
	private static Material mat;
	private static Node rootNode;
	private static PhysicsSpace psySpace;
	private static Ball ball;
	private static Geometry geom;

	/**
	 * Setup the test class, mock and instantiate objects.
	 */
	@BeforeClass
	public static void setUpBeforeClass() {
		obF = new ObstacleFactory();
		tar = obF.makeTarget();
		mat = mock(Material.class);
		ball = mock(Ball.class);
		geom = new Geometry("ball", SHAPE);

		psySpace = mock(PhysicsSpace.class); 
	}
	/**
	 * Tests if the box is succesfully attached to the rootnode.
	 */
	@Test
	public final void drawTest() {		
		rootNode = new Node();
		tar.draw(mat, psySpace, rootNode);
		assertEquals(rootNode.getChildren().size(), 1);
	}

	/**
	 * Tests if the collision method works accordingly, not colliding.
	 */
	@Test
	public final void notCollidingTest() {
		rootNode = new Node();
		final Ball bal = new Ball(SHAPE, geom, mat, null);
		bal.spawn(rootNode, psySpace, false);
		tar.draw(mat, psySpace, rootNode);
		tar.collides(rootNode);
		assertEquals(rootNode.getChildren().size(), 2);
	}
	
	/**
	 * Tests if the collision method works accordingly, colliding.
	 */
	@Test
	public final void collidingTest() {
		rootNode = new Node();
		final Ball bal = new Ball(SHAPE, geom, mat, null);
		tar.draw(mat, psySpace, rootNode);
		bal.spawn(rootNode, psySpace, false);
		final Vector3f vec = new Vector3f();
		vec.add((float) tar.getxCoord(), (float) tar.getyCoord(), (float) tar.getzCoord());
		ball.setLocation(vec);
		tar.collides(rootNode);
		assertEquals(rootNode.getChildren().size(), 2);
	}
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
