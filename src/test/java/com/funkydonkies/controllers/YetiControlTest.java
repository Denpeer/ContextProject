package com.funkydonkies.controllers;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.funkydonkies.factories.PenguinFactory;
import com.funkydonkies.factories.YetiFactory;
import com.funkydonkies.gamestates.DifficultyState;
import com.funkydonkies.gamestates.PlayState;
import com.funkydonkies.sounds.SoundState;
import com.jme3.app.state.AppStateManager;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.collision.PhysicsCollisionEvent;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;

/**
 * tests for YetiControl class.
 * @author Olivier Dikken
 *
 */
public class YetiControlTest {
	
	private static final float TPF = 0.01f;
	private AppStateManager sManager;
	private DifficultyState ds;
	private Spatial spatial;
	private Vector3f tf;
	private Quaternion quat;
	private SoundState ss;
	private PlayState plays;
	private PhysicsSpace ps;
	private PhysicsCollisionEvent event;
	private Spatial spatial2;

	/**
	 * prepare the mocks.
	 * @throws Exception catch exception
	 */
	@Before
	public void setUp() throws Exception {
		sManager = Mockito.mock(AppStateManager.class);
		ds = Mockito.mock(DifficultyState.class);
		tf = new Vector3f(0, 0, 0);
		quat = new Quaternion();
		spatial = Mockito.mock(Spatial.class);
		plays = Mockito.mock(PlayState.class);
		ss = Mockito.mock(SoundState.class);
		ps = Mockito.mock(PhysicsSpace.class);
		event = Mockito.mock(PhysicsCollisionEvent.class);
		spatial2 = Mockito.mock(Spatial.class);
		Mockito.when(sManager.getState(DifficultyState.class)).thenReturn(ds);
		Mockito.when(sManager.getState(PlayState.class)).thenReturn(plays);
		Mockito.when(sManager.getState(SoundState.class)).thenReturn(ss);
		Mockito.when(spatial.getLocalTranslation()).thenReturn(tf);
		Mockito.when(spatial.getWorldTranslation()).thenReturn(tf);
		Mockito.when(spatial.getLocalRotation()).thenReturn(quat);
		Mockito.when(spatial.getWorldRotation()).thenReturn(quat);
		Mockito.when(plays.getPhysicsSpace()).thenReturn(ps);
		Mockito.when(event.getNodeA()).thenReturn(spatial);
		Mockito.when(event.getNodeB()).thenReturn(spatial2);
		Mockito.when(spatial.getName()).thenReturn(YetiFactory.YETI_NAME);
		Mockito.when(spatial2.getName()).thenReturn(PenguinFactory.PENGUIN_NAME);
	}

	/**
	 * test constructor.
	 */
	@Test
	public void testYetiControl() {
		new YetiControl(sManager);
		Mockito.verify(sManager).getState(DifficultyState.class);
	}
	
	/**
	 * test init.
	 */
	@Test
	public void testInit() {
		final YetiControl yc = new YetiControl(sManager);
		yc.setSpatial(spatial);
		yc.init();
		Mockito.verify(sManager, Mockito.times(1)).getState(DifficultyState.class);
		Mockito.verify(sManager, Mockito.times(2)).getState(PlayState.class);
	}

	/**
	 * test setPhysicsSpace.
	 */
	@Test
	public void testSetPhysicsSpace() {
		final YetiControl yc = new YetiControl(sManager);
		yc.setPhysicsSpace(ps);
		Mockito.verify(ps).addCollisionListener(yc);
	}
	
	/**
	 * test update.
	 * Nothing to test within the update method.
	 */
	@Test
	public void testUpdate() {
		final YetiControl yc = new YetiControl(sManager);
		yc.setSpatial(spatial);
		yc.update(TPF);
	}
	
	/**
	 * test collision.
	 */
	@Test
	public void testCollision() {
		final YetiControl yc = new YetiControl(sManager);
		yc.setSpatial(spatial);
		yc.collision(event);
		Mockito.verify(ds).resetDiff();
	}
	
	/**
	 * test move.
	 */
	@Test
	public void testMove() {
		final YetiControl yc = new YetiControl(sManager);
		yc.setSpatial(spatial);
		yc.update(TPF);
		Mockito.verify(spatial).setLocalTranslation(Mockito.any(Vector3f.class));
		Mockito.verify(spatial, Mockito.times(4)).getLocalTranslation();
	}
}
