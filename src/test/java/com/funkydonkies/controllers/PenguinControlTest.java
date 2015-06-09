package com.funkydonkies.controllers;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.collision.PhysicsCollisionEvent;
import com.jme3.bullet.collision.shapes.SphereCollisionShape;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;

/**
 * @author Jonathan
 *
 */
public class PenguinControlTest {
	private static final float BALL_RADIUS = 0.5f;
	private static final String BALL_NAME = "ball";
	private static final String CURVE_NAME = "curve";
	
	//Moving fast, not affected by prePhysicsTick (x > 1)
	private static final Vector3f SPEED_HIGH = new Vector3f(5f, 0f, 0f);
	
	//Not moving, is affected by prePhysicstTick (x <= 1)
	private static final Vector3f SPEED_ZERO = new Vector3f(0f, 0f, 0f); 

	//Low speed, is affected by prePhysicstTick (x <= 1)
	private static final Vector3f SPEED_LOW = new Vector3f(1f, 1f, 1f); 
	
	//Normal speed, this is the speed after the x value was modified by prePhysicsTick
	private static final Vector3f SPEED_NORMAL = new Vector3f(2f, 1f, 1f); 

	private static PenguinControl contr;
	private static PhysicsCollisionEvent eventMock;
	private static Spatial ballSpatialMock;
	private static Spatial curveSpatialMock;
	
	/** Initialize test variables once.
	 * 
	 * @throws Exception -
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		contr = new PenguinControl(new SphereCollisionShape(BALL_RADIUS), BALL_RADIUS * 2);
		eventMock = mock(PhysicsCollisionEvent.class);
		ballSpatialMock = mock(Spatial.class);
		curveSpatialMock = mock(Spatial.class);
		when(eventMock.getNodeA()).thenReturn(ballSpatialMock);
		when(eventMock.getNodeB()).thenReturn(curveSpatialMock);
	}
	
	/**
	 *  Initialize test variables before every test.
	 */
	@Before
	public void setUp() {
		when(ballSpatialMock.getName()).thenReturn(BALL_NAME);
		when(curveSpatialMock.getName()).thenReturn(CURVE_NAME);
	}
	
	/**
	 * Test ? TODO
	 */
	@Test
	public void resetZTest() {
		contr.setPhysicsLocation(new Vector3f(1f, 1f, 1f));
		assertEquals(new Vector3f(1f, 1f, 1f), contr.getPhysicsLocation());
		
		contr.prePhysicsTick(new PhysicsSpace(), 1);
		assertEquals(new Vector3f(1f, 1f, 0f), contr.getPhysicsLocation());
	}
	
	/**
	 *  Test ? TODO
	 */
	@Test
	public void resetNegativeZtest() {
		contr.setPhysicsLocation(new Vector3f(1f, 1f, -1f));
		assertEquals(new Vector3f(1f, 1f, -1f), contr.getPhysicsLocation());
		
		contr.prePhysicsTick(new PhysicsSpace(), 1);
		assertEquals(new Vector3f(1f, 1f, 0f), contr.getPhysicsLocation());
	}
	
	/**
	 *  Test ? TODO
	 */
	@Test
	public void collisionTestOnStillBall() {
		contr.setLinearVelocity(SPEED_ZERO);
		assertEquals(new Vector3f(0f, 0f, 0f), contr.getLinearVelocity()); 

		contr.collision(eventMock);
		assertEquals(new Vector3f(2f, 0f, 0f), contr.getLinearVelocity());
	}
	
	/**
	 * Test ? TODO
	 */
	@Test
	public void collisionTestOn1fBall() {
		contr.setLinearVelocity(SPEED_LOW);
		assertEquals(SPEED_LOW, contr.getLinearVelocity()); 

		contr.collision(eventMock);
		assertEquals(SPEED_NORMAL, contr.getLinearVelocity());
	}
	
	/**
	 * Test ? TODO
	 */
	@Test
	public void collisionTestOnNotAffectedBall() {
		contr.setLinearVelocity(SPEED_HIGH);
		assertEquals(SPEED_HIGH, contr.getLinearVelocity()); 

		contr.collision(eventMock);
		assertEquals(SPEED_HIGH, contr.getLinearVelocity());
	}
	
	/**
	 * Test ? TODO
	 */
	@Test
	public void collisionTestOnBallNotCollidingWithCurve() {
		contr.setLinearVelocity(SPEED_ZERO);
		when(curveSpatialMock.getName()).thenReturn("not-curve");
		assertEquals(SPEED_ZERO, contr.getLinearVelocity()); 

		contr.collision(eventMock);
		assertEquals(SPEED_ZERO, contr.getLinearVelocity());
	}
	
	/**
	 * Test ? TODO
	 */
	@Test
	public void testSetPhysicsSpace() {
		final PhysicsSpace space = mock(PhysicsSpace.class);
		contr.setPhysicsSpace(space);
		verify(space).addTickListener(contr);
		verify(space).addCollisionListener(contr);
	}
}
