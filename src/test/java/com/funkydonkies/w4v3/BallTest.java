package com.funkydonkies.w4v3;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import org.junit.Before;
import org.junit.Test;

import com.funkydonkies.controllers.PhysicsController;
import com.funkydonkies.w4v3.Ball;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Sphere;
/**
 * Test class for Ball.
 * @author Jonathan
 *
 */
public class BallTest {
	private static final String COLOR_NAME = "Color";
	private static final Sphere SHAPE =  new Sphere(20, 20, 0.5f);
	private static final Vector3f DEFAULT_SPAWN_LOCATION = new Vector3f(10f, 15f, 0f);;
	private static final Vector3f DEFAULT_INITIAL_SPEED = new Vector3f(5, -22, 0);;
	
	private Ball ball;
	private Material mat;
	private Geometry g;
	private Vector3f speed;
	private Vector3f loc;
	private Node root;
	private PhysicsSpace space;
	private Sphere shape;
	private Ball spyBall;
	private Ball mockBall;
	private PhysicsController phy;
	
	/**
	 * SetUp method for the test. Initializes the attributes and mocks Ball's dependencies.
	 * @throws Exception
	 */
	@Before
	public final void setUp() {
		mat = mock(Material.class);
		mockBall = mock(Ball.class);
		shape = SHAPE;
		g = mock(Geometry.class);
		ball = new Ball("ball");
		spyBall = spy(ball);
		when(mockBall.initControl()).thenReturn(phy);
		doReturn(phy).when(spyBall).initControl();
//		spyBall = spy(ball);
speed = new Vector3f(1, 1, 1);
		loc = new Vector3f(2f, 2f, 2f);
		root = mock(Node.class);
		space = mock(PhysicsSpace.class);
		phy = mock(PhysicsController.class);
	}

	/**
	 * Tests Ball's setColor method which should call setColor on its material.
	 */
	@Test
	public final void testGetControl() {
		assertTrue(spyBall.getControl() instanceof PhysicsController);
		assertEquals(mockBall.getControl(), phy);
	}
	
	/**
	 * Tests Ball's setSpeed method which should call setLinearspeed on its physics object.
	 */
	@Test
	public final void testSetSpeed() {
		ball.setSpeed(speed);
		verify(phy).setLinearVelocity(speed);
	}

	/**
	 * Tests Ball's setLocationmethod which should call setPhysicsLocation on 
	 * the ball's physics object.
	 */
	@Test
	public final void testSetLocation() {
		ball.setLocation(loc);
		verify(phy).setPhysicsLocation(loc);
	}
	
	/**
	 * Tests Ball's spawn method, which adds the physics and adds the ball to the scene.
	 * The method tested is the short spawn method taking only a node and the physicsSpace
	 */
	@Test
	public final void testSpawn() {
		verify(g).addControl(phy);
		verify(space).add(phy);
		verify(root).attachChild(g);
		verifyNoMoreInteractions(phy);
	}
	
	/**
	 * Tests Ball's second spawn method, which sets the location in addition to the physics 
	 * and adding the ball to the scene.
	 */
	@SuppressWarnings("deprecation")
	@Test
	public final void testSpawn2() {
		verify(phy).setPhysicsLocation(loc);
		verify(g).addControl(phy);
		verify(space).add(phy);
		verify(root).attachChild(g);
	}
	
	/**
	 * Tests Ball's third spawn method, which sets the location and speed in addition to the 
	 * physics and adding the ball to the scene.
	 */
	@SuppressWarnings("deprecation")
	@Test
	public final void testSpawn3() {
		verify(phy).setPhysicsLocation(loc);
		verify(g).addControl(phy);
		verify(space).add(phy);
		verify(root).attachChild(g);
		verify(phy).setLinearVelocity(speed);
	}
	
	/**
	 * Tests the spawn method that uses the default values.
	 */
	@Test
	public final void testSpawnUsingDefaults() {
		verify(phy).setPhysicsLocation(Ball.DEFAULT_SPAWN_LOCATION);
		verify(phy).setLinearVelocity(Ball.DEFAULT_INITIAL_SPEED);
		verify(g).addControl(phy);
		verify(space).add(phy);
		verify(root).attachChild(g);
	}
	
	/**
	 * Tests the ball's spawn method when no physics or physicsSpace were defined.
	 */
	@Test
	public final void testSpawnWithoutPhysics() {
		ball = new Ball("ball");
		verifyNoMoreInteractions(phy);
		verifyNoMoreInteractions(space);
		verify(root).attachChild(g);
	}
	
	/**
	 * Tests whether ball returns the same geom as was passed in the constructor.
	 */
	@Test
	public final void testGetGeometry() {
	}
	
	
	
	
}
