package com.funkydonkies.controllers;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import com.funkydonkies.curve.CustomCurveMesh;
import com.funkydonkies.factories.PenguinFactory;
import com.funkydonkies.gamestates.PlayState;
import com.funkydonkies.sounds.SoundState;
import com.jme3.app.state.AppStateManager;
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
	private static final String BALL_NAME = PenguinFactory.PENGUIN_NAME;
	private static final String CURVE_NAME = "curve";

	// Moving fast, not affected by prePhysicsTick (x > 1)
	private static final Vector3f SPEED_HIGH = new Vector3f(15f, 1f, 1f);

	// Not moving, is affected by prePhysicstTick (x <= 1)
	private static final Vector3f SPEED_ZERO = new Vector3f(0f, 0f, 0f);

	// Low speed, is affected by prePhysicstTick (x <= 1)
	private static final Vector3f SPEED_LOW = new Vector3f(1f, 1f, 1f);

	// Normal speed, this is the speed after the x value was modified by
	// prePhysicsTick
	private static final Vector3f SPEED_NORMAL = new Vector3f(1f, 1f, 1f);
	private static final Vector3f INITIAL_SPEED = new Vector3f(50, 0, 0);

	private static PenguinControl contr;
	private static PhysicsCollisionEvent eventMock;
	private static Spatial ballSpatialMock;
	private static Spatial curveSpatialMock;
	private static AppStateManager appStateMock;
	private static PlayState plays;
	private static PhysicsSpace ps;
	private static SoundState ss;
	private static Vector3f vec;

	/**
	 * Initialize test variables once.
	 * 
	 * @throws Exception
	 *             -
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		appStateMock = mock(AppStateManager.class);
		contr = new PenguinControl(new SphereCollisionShape(BALL_RADIUS), BALL_RADIUS * 2,
				appStateMock);
		eventMock = mock(PhysicsCollisionEvent.class);
		ballSpatialMock = mock(Spatial.class);
		curveSpatialMock = mock(Spatial.class);
		plays = mock(PlayState.class);
		ps = mock(PhysicsSpace.class);
		ss = mock(SoundState.class);
		vec = new Vector3f(1.0f, 0.0f, 0.0f);
		when(eventMock.getNodeA()).thenReturn(ballSpatialMock);
		when(eventMock.getNodeB()).thenReturn(curveSpatialMock);
		when(appStateMock.getState(PlayState.class)).thenReturn(plays);
		when(appStateMock.getState(SoundState.class)).thenReturn(ss);
		when(plays.getPhysicsSpace()).thenReturn(ps);
	}

	/**
	 * Initialize test variables before every test.
	 */
	@Before
	public void setUp() {
		when(ballSpatialMock.getName()).thenReturn(BALL_NAME);
		when(curveSpatialMock.getName()).thenReturn(CURVE_NAME);
	}

	/**
	 * test init.
	 */
	@Test
	public void testInit() {
		contr.init();
		final int yOffSet = 5, xOffSet = -20;
		final Vector3f initialSpawn = new Vector3f(xOffSet, CustomCurveMesh.getLaunchPadHeight()
				+ yOffSet, 0);
		assertEquals(contr.getPhysicsLocation(), initialSpawn);
		assertEquals(contr.getLinearVelocity(), INITIAL_SPEED);
		Mockito.verify(appStateMock).getState(PlayState.class);
		Mockito.verify(appStateMock).getState(SoundState.class);
	}

	/**
	 * testing the MAX_ROTATIONAL_DEVIANCE condition of the prePhysicsTick.
	 */
	@Test
	public void testPrePhyTickMaxRotDev() {
		assertEquals(contr.getLinearVelocity(), SPEED_NORMAL);
		contr.setAngularVelocity(vec);
		contr.prePhysicsTick(new PhysicsSpace(), 1);
		assertEquals(contr.getAngularVelocity(), new Vector3f(0, 0, 0));
		assertEquals(contr.getLinearVelocity(), SPEED_HIGH);
}

	/**
	 * Tests prePhysicsTick for if loc.z > MAX_DEVIANCE_ON_Z to make sure the
	 * objects only move along a 2d plane.
	 */
	@Test
	public void resetZTest() {
		contr.setPhysicsLocation(new Vector3f(1f, 1f, 1f));
		assertEquals(new Vector3f(1f, 1f, 1f), contr.getPhysicsLocation());

		contr.prePhysicsTick(new PhysicsSpace(), 1);
		assertEquals(new Vector3f(1f, 1f, 0f), contr.getPhysicsLocation());
	}

	/**
	 * Tests prePhysicsTick for if loc.z > MAX_DEVIANCE_ON_Z to make sure the
	 * objects only move along a 2d plane.
	 */
	@Test
	public void resetNegativeZtest() {
		contr.setPhysicsLocation(new Vector3f(1f, 1f, -1f));
		assertEquals(new Vector3f(1f, 1f, -1f), contr.getPhysicsLocation());

		contr.prePhysicsTick(new PhysicsSpace(), 1);
		assertEquals(new Vector3f(1f, 1f, 0f), contr.getPhysicsLocation());
	}

	/**
	 * Test collision on still ball.
	 */
	@Test
	public void collisionTestOnStillBall() {
		contr.setLinearVelocity(SPEED_ZERO);
		assertEquals(new Vector3f(0f, 0f, 0f), contr.getLinearVelocity());

		contr.collision(eventMock);
		assertEquals(new Vector3f(0f, 0f, 0f), contr.getLinearVelocity());
	}

	/**
	 * Test collision on moving ball.
	 */
	@Test
	public void collisionTestOn1fBall() {
		contr.setLinearVelocity(SPEED_LOW);
		assertEquals(SPEED_LOW, contr.getLinearVelocity());

		contr.collision(eventMock);
		assertEquals(SPEED_NORMAL, contr.getLinearVelocity());
	}

	/**
	 * Test collision on quickly moving ball.
	 */
	@Test
	public void collisionTestOnNotAffectedBall() {
		contr.setLinearVelocity(SPEED_HIGH);
		assertEquals(SPEED_HIGH, contr.getLinearVelocity());

		contr.collision(eventMock);
		assertEquals(SPEED_HIGH, contr.getLinearVelocity());
	}

	/**
	 * Test collision for still ball and not-curve.
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
	 * Test setPhysicsSpace.
	 */
	@Test
	public void testSetPhysicsSpace() {
		final PhysicsSpace space = mock(PhysicsSpace.class);
		contr.setPhysicsSpace(space);
		verify(space).addTickListener(contr);
		verify(space).addCollisionListener(contr);
	}
}
