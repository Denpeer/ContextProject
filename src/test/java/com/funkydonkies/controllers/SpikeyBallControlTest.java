package com.funkydonkies.controllers;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.funkydonkies.factories.PenguinFactory;
import com.funkydonkies.factories.SpikeyBallFactory;
import com.funkydonkies.gamestates.DifficultyState;
import com.funkydonkies.gamestates.PlayState;
import com.funkydonkies.sounds.SoundState;
import com.jme3.app.state.AppStateManager;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.collision.PhysicsCollisionEvent;
import com.jme3.bullet.collision.shapes.SphereCollisionShape;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;

/**
 * tests for the SpikeyBallControl class.
 * 
 * @author Olivier Dikken
 *
 */
public class SpikeyBallControlTest {

	private static final float MAX_DEVIANCE_ON_Z = 0.1f;
	private static final float TPF = 0.01f;
	private final float mass = 0;
	private SphereCollisionShape shape;
	private AppStateManager sManager;
	private SoundState ss;
	private Spatial spatial;
	private Vector3f tf;
	private Quaternion quat;
	private PhysicsSpace ps;
	private PlayState plays;
	private PhysicsCollisionEvent event;
	private Spatial spatial2;
	private DifficultyState ds;
	private SpikeyBallControl sbcNoSpy;
	private SpikeyBallControl sbcSpy;

	/**
	 * prepare the mocks.
	 * 
	 * @throws Exception
	 *             catch exception
	 */
	@Before
	public void setUp() throws Exception {
		shape = Mockito.mock(SphereCollisionShape.class);
		sManager = Mockito.mock(AppStateManager.class);
		ss = Mockito.mock(SoundState.class);
		spatial = Mockito.mock(Spatial.class);
		spatial2 = Mockito.mock(Spatial.class);
		ps = Mockito.mock(PhysicsSpace.class);
		plays = Mockito.mock(PlayState.class);
		event = Mockito.mock(PhysicsCollisionEvent.class);
		tf = new Vector3f(0, 0, 0);
		ds = Mockito.mock(DifficultyState.class);
		quat = new Quaternion();
		Mockito.when(sManager.getState(SoundState.class)).thenReturn(ss);
		Mockito.when(spatial.getLocalTranslation()).thenReturn(tf);
		Mockito.when(spatial.getWorldTranslation()).thenReturn(tf);
		Mockito.when(spatial.getLocalRotation()).thenReturn(quat);
		Mockito.when(spatial.getWorldRotation()).thenReturn(quat);
		Mockito.when(sManager.getState(PlayState.class)).thenReturn(plays);
		Mockito.when(sManager.getState(DifficultyState.class)).thenReturn(ds);
		Mockito.when(plays.getPhysicsSpace()).thenReturn(ps);
		Mockito.when(event.getNodeA()).thenReturn(spatial);
		Mockito.when(event.getNodeB()).thenReturn(spatial2);
		Mockito.when(spatial.getName()).thenReturn(SpikeyBallFactory.SPIKEYBALL_NAME);
		Mockito.when(spatial2.getName()).thenReturn(PenguinFactory.PENGUIN_NAME);

		sbcNoSpy = new SpikeyBallControl(shape, sManager, mass);
		sbcSpy = Mockito.spy(sbcNoSpy);
		Mockito.doNothing().when(sbcSpy).setLinearVelocity(Mockito.any(Vector3f.class));
	}

	/**
	 * test constructor.
	 */
	@Test
	public void testSpikeyBallControl() {
		new SpikeyBallControl(shape, sManager, mass);
		Mockito.verify(sManager, Mockito.times(2)).getState(DifficultyState.class);
	}

	/**
	 * test init.
	 */
	@Test
	public void testInit() {
		final SpikeyBallControl sbc = sbcSpy;
		sbc.setSpatial(spatial);
		sbc.init();
		Mockito.verify(sManager, Mockito.times(2)).getState(SoundState.class);
		Mockito.verify(sManager, Mockito.times(2)).getState(PlayState.class);
	}

	/**
	 * test setPhysicsSpace.
	 */
	@Test
	public void testSetPhysicsSpace() {
		final SpikeyBallControl sbc = new SpikeyBallControl(shape, sManager, mass);
		sbc.setPhysicsSpace(ps);
		assertEquals(sbc.getPhysicsSpace(), ps);
		Mockito.verify(ps).addCollisionListener(sbc);
	}

	/**
	 * test physicsTick (nothing to test).
	 */
	@Test
	public void testPhysicsTick() {
		final SpikeyBallControl sbc = new SpikeyBallControl(shape, sManager, mass);
		sbc.physicsTick(ps, TPF);
	}

	/**
	 * test prePhysicsTick.
	 */
	@Test
	public void testPrePhysicsTick() {
		final SpikeyBallControl sbc = sbcSpy;
		sbc.setSpatial(spatial);
		sbc.setPhysicsLocation(new Vector3f(0, 0, MAX_DEVIANCE_ON_Z * 2));
		sbc.prePhysicsTick(ps, TPF);
		assertEquals(sbc.getPhysicsLocation(), new Vector3f(0, 0, 0));
	}

	/**
	 * test collision.
	 */
	@Test
	public void testCollision() {
		final SpikeyBallControl sbc = sbcSpy;
		sbc.setSpatial(spatial);
		sbc.update(TPF);
		sbc.collision(event);
		Mockito.verify(sManager, Mockito.times(2)).getState(SoundState.class);
		Mockito.verify(ds).resetDiff();
	}
}
