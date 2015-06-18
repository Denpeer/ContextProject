package com.funkydonkies.controllers;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.funkydonkies.factories.PenguinFactory;
import com.funkydonkies.factories.SpearFactory;
import com.funkydonkies.gamestates.DifficultyState;
import com.funkydonkies.gamestates.PlayState;
import com.funkydonkies.sounds.SoundState;
import com.jme3.app.state.AppStateManager;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.collision.PhysicsCollisionEvent;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;

/**
 * test the SpearControl class.
 * @author Olivier Dikken
 *
 */
public class SpearControlTest {
	
	private static final float SPEED = 5;
	private static final float TPF = 0.01f;
	private static final float TIME_TWO = 2;
	private CollisionShape shape;
	private AppStateManager sManager;
	private SoundState ss;
	private Spatial spatial;
	private Spatial spatial2;
	private Vector3f tf;
	private Quaternion quat;
	private PhysicsSpace ps;
	private PlayState plays;
	private PhysicsCollisionEvent event;
	private DifficultyState ds;
	private Vector3f iLoc = new Vector3f(0, 0, 0);

	/**
	 * prepare all mocks.
	 * @throws Exception catches exception.
	 */
	@Before
	public void setUp() throws Exception {
		shape = Mockito.mock(CollisionShape.class);
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
		Mockito.when(spatial.getName()).thenReturn(SpearFactory.SPEAR_NAME);
		Mockito.when(spatial2.getName()).thenReturn(PenguinFactory.PENGUIN_NAME);
	}

	/**
	 * test constructor.
	 */
	@Test
	public void testSpearControl() {
		new SpearControl(shape, sManager, iLoc);
		Mockito.verify(sManager).getState(DifficultyState.class);
	}
	
	/**
	 * test setPhysicsSpace.
	 */
	@Test
	public void testSetPhysicsSpace() {
		final SpearControl sc = new SpearControl(shape, sManager, iLoc);
		sc.setPhysicsSpace(ps);
		assertEquals(sc.getPhysicsSpace(), ps);
		Mockito.verify(ps).addCollisionListener(sc);
	}
	
	/**
	 * test init.
	 */
	@Test
	public void testInit() {
		final SpearControl sc = new SpearControl(shape, sManager, iLoc);
		sc.setSpatial(spatial);
		sc.init();
		Mockito.verify(sManager).getState(DifficultyState.class);
		Mockito.verify(sManager, Mockito.times(2)).getState(PlayState.class);
		assertEquals(sc.getPhysicsLocation(), iLoc);
	}
	
	/**
	 * test update.
	 */
	@Test
	public void testUpdate() {
		final float destroyXCoordinate = -100;
		Mockito.when(spatial2.getLocalTranslation()).thenReturn(new Vector3f(destroyXCoordinate - 1, 0, 0));
		Mockito.when(spatial2.getWorldTranslation()).thenReturn(new Vector3f(destroyXCoordinate - 1, 0, 0));
		Mockito.when(spatial2.getLocalRotation()).thenReturn(quat);
		Mockito.when(spatial2.getWorldRotation()).thenReturn(quat);
		final SpearControl sc = new SpearControl(shape, sManager, iLoc);
		sc.setSpatial(spatial2);
		sc.init();
		sc.update(TPF);
		Mockito.verify(spatial2).removeFromParent();
	}
	
	/**
	 * test move.
	 */
	@Test
	public void testMove() {
		final SpearControl sc = new SpearControl(shape, sManager, iLoc);
		sc.setSpatial(spatial);
		sc.init();
		final Vector3f vec = spatial.getLocalTranslation();
		sc.update(TIME_TWO + TPF);
		sc.update(TIME_TWO + TPF);
		final Vector3f loc = new Vector3f((float) (vec.getX() - SPEED), vec.getY(), vec.getZ());
		assertEquals(sc.getPhysicsLocation(), loc);
	}
	
	/**
	 * test Collision.
	 */
	@Test
	public void testCollision() {
		final int three = 3;
		final SpearControl sc = new SpearControl(shape, sManager, iLoc);
		sc.setSpatial(spatial);
		sc.init();
		sc.update(TPF);
		sc.collision(event);
		Mockito.verify(sManager, Mockito.times(2)).getState(SoundState.class);
		Mockito.verify(ds).resetDiff();
	}
}
