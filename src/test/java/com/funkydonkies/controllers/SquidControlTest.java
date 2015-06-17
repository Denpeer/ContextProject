package com.funkydonkies.controllers;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.funkydonkies.factories.FishFactory;
import com.funkydonkies.factories.PenguinFactory;
import com.funkydonkies.factories.SquidFactory;
import com.funkydonkies.gamestates.DifficultyState;
import com.funkydonkies.gamestates.PlayState;
import com.funkydonkies.powerups.SnowballPowerup;
import com.funkydonkies.sounds.SoundState;
import com.jme3.app.state.AppStateManager;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.collision.PhysicsCollisionEvent;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;

/**
 * tests for squidControl.
 * 
 * @author Olivier Dikken
 *
 */
public class SquidControlTest {

	private CollisionShape shape;
	private AppStateManager sManager;
	private Spatial spatial;
	private PhysicsSpace ps;
	private Vector3f tf;
	private Quaternion quat;
	private SoundState ss;
	private PlayState plays;
	private PhysicsCollisionEvent event1;
	private PhysicsCollisionEvent event2;
	private Spatial spatial2;
	private Spatial spatial3;
	private DifficultyState ds;
	private static final float TIME_TO_MOVE_IN_ONE_DIRECTION = 4;
	private static final float TPF = 0.01f;
	private static final float STEP_SIZE = 0.5f;

	/**
	 * prepare the mocks.
	 * 
	 * @throws Exception
	 *             catch exception
	 */
	@Before
	public void setUp() throws Exception {
		shape = Mockito.mock(CollisionShape.class);
		sManager = Mockito.mock(AppStateManager.class);
		spatial = Mockito.mock(Spatial.class);
		spatial2 = Mockito.mock(Spatial.class);
		spatial3 = Mockito.mock(Spatial.class);
		event1 = Mockito.mock(PhysicsCollisionEvent.class);
		event2 = Mockito.mock(PhysicsCollisionEvent.class);
		ds = Mockito.mock(DifficultyState.class);
		ps = Mockito.mock(PhysicsSpace.class);
		tf = new Vector3f(0, 0, 0);
		quat = new Quaternion();
		ss = Mockito.mock(SoundState.class);
		plays = Mockito.mock(PlayState.class);
		Mockito.when(spatial.getLocalTranslation()).thenReturn(tf);
		Mockito.when(spatial.getWorldTranslation()).thenReturn(tf);
		Mockito.when(spatial.getLocalRotation()).thenReturn(quat);
		Mockito.when(spatial.getWorldRotation()).thenReturn(quat);
		Mockito.when(sManager.getState(SoundState.class)).thenReturn(ss);
		Mockito.when(sManager.getState(PlayState.class)).thenReturn(plays);
		Mockito.when(sManager.getState(DifficultyState.class)).thenReturn(ds);
		Mockito.when(plays.getPhysicsSpace()).thenReturn(ps);
		Mockito.when(event1.getNodeA()).thenReturn(spatial);
		Mockito.when(event1.getNodeB()).thenReturn(spatial2);
		Mockito.when(event2.getNodeA()).thenReturn(spatial);
		Mockito.when(event2.getNodeB()).thenReturn(spatial3);
		Mockito.when(spatial.getName()).thenReturn(SquidFactory.SQUID_NAME);
		Mockito.when(spatial2.getName()).thenReturn(PenguinFactory.PENGUIN_NAME);
		Mockito.when(spatial3.getName()).thenReturn(SnowballPowerup.SNOW_PENGUIN_NAME);
	}

	/**
	 * test constructor.
	 */
	@Test
	public void testSquidControl() {
		new SquidControl(shape, sManager);
		Mockito.verify(sManager).getState(DifficultyState.class);
	}

	/**
	 * test init.
	 */
	@Test
	public void testInit() {
		final SquidControl sc = new SquidControl(shape, sManager);
		sc.setSpatial(spatial);
		sc.init();
		Mockito.verify(sManager, Mockito.times(2)).getState(SoundState.class);
		Mockito.verify(sManager, Mockito.times(2)).getState(PlayState.class);
	}

	/**
	 * test setPhysicsSpace.
	 */
	@Test
	public void testSetPhysicsSpace() {
		final SquidControl sc = new SquidControl(shape, sManager);
		sc.setPhysicsSpace(ps);
		assertEquals(sc.getPhysicsSpace(), ps);
		Mockito.verify(ps).addCollisionListener(Mockito.any(SquidControl.class));
	}

	/**
	 * test update and movement direction change.
	 */
	@Test
	public void testUpdate() {
		final SquidControl sc = new SquidControl(shape, sManager);
		sc.setSpatial(spatial);
		sc.update(TPF);
		final Vector3f loc1 = new Vector3f((float) (tf.getX() - STEP_SIZE), tf.getY(), tf.getZ());
		assertEquals(sc.getPhysicsLocation(), loc1);
		sc.update(TIME_TO_MOVE_IN_ONE_DIRECTION);
		final Vector3f loc2 = new Vector3f((float) (tf.getX() + STEP_SIZE), tf.getY(), tf.getZ());
		assertEquals(sc.getPhysicsLocation(), loc2);
		sc.update(TIME_TO_MOVE_IN_ONE_DIRECTION + TPF);
	}
	
	/**
	 * test collision triggering both collision events.
	 */
	@Test
	public void testCollision() {
		final SquidControl sc = new SquidControl(shape, sManager);
		final int three = 3;
		final int four = 4;
		sc.setSpatial(spatial);
		sc.init();
		sc.collision(event1);
		Mockito.verify(sManager, Mockito.times(three)).getState(SoundState.class);
		Mockito.verify(ds).incDiff();
		Mockito.verify(ds).activateSnowBallPowerup();
		sc.collision(event2);
		Mockito.verify(sManager, Mockito.times(four)).getState(SoundState.class);
		Mockito.verify(ds, Mockito.times(2)).incDiff();
		Mockito.verify(ds, Mockito.times(2)).activateSnowBallPowerup();
	}
}
