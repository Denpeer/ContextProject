package com.funkydonkies.controllers;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.funkydonkies.factories.KrillFactory;
import com.funkydonkies.factories.PenguinFactory;
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
 * tests for krillcontrol.
 * @author Olivier Dikken
 *
 */
public class KrillControlTest {

	private CollisionShape shape;
	private AppStateManager sManager;
	private Spatial spatial;
	private Spatial spatial2;
	private Spatial spatial3;
	private Vector3f tf;
	private DifficultyState ds;
	private Quaternion quat;
	private PlayState plays;
	private SoundState ss;
	private PhysicsSpace ps;
	private PhysicsCollisionEvent event1;
	private PhysicsCollisionEvent event2;

	/**
	 * prepare the mocks.
	 * @throws Exception catch exception
	 */
	@Before
	public void setUp() throws Exception {
		shape = Mockito.mock(CollisionShape.class);
		sManager = Mockito.mock(AppStateManager.class);
		spatial = Mockito.mock(Spatial.class);
		spatial2 = Mockito.mock(Spatial.class);
		spatial3 = Mockito.mock(Spatial.class);
		tf = new Vector3f(0, 0, 0);
		ds = Mockito.mock(DifficultyState.class);
		quat = new Quaternion();
		plays = Mockito.mock(PlayState.class);
		ss = Mockito.mock(SoundState.class);
		ps = Mockito.mock(PhysicsSpace.class);
		event1 = Mockito.mock(PhysicsCollisionEvent.class);
		event2 = Mockito.mock(PhysicsCollisionEvent.class);
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
		Mockito.when(spatial.getName()).thenReturn(KrillFactory.KRILL_NAME);
		Mockito.when(spatial2.getName()).thenReturn(PenguinFactory.PENGUIN_NAME);
		Mockito.when(spatial3.getName()).thenReturn(SnowballPowerup.SNOW_PENGUIN_NAME);
	}
	
	/**
	 * test constructor.
	 */
	@Test
	public void testKrillControl() {
		new KrillControl(shape, sManager);
		Mockito.verify(sManager).getState(DifficultyState.class);
	}

	/**
	 * test init.
	 */
	@Test
	public void testInit() {
		final KrillControl kc = new KrillControl(shape, sManager);
		kc.setSpatial(spatial);
		kc.init();
		Mockito.verify(sManager).getState(DifficultyState.class);
		Mockito.verify(sManager, Mockito.times(2)).getState(PlayState.class);
	}

	/**
	 * test setPhysicsSpace.
	 */
	@Test
	public void testSetPhysicsSpace() {
		final KrillControl kc = new KrillControl(shape, sManager);
		kc.setPhysicsSpace(ps);
		assertEquals(kc.getPhysicsSpace(), ps);
		Mockito.verify(ps).addCollisionListener(Mockito.any(KrillControl.class));
	}

	/**
	 * test collision.
	 */
	@Test
	public void testCollision() {
		final KrillControl kc = new KrillControl(shape, sManager);
		final int three = 3;
		final int four = 4;
		kc.setSpatial(spatial);
		kc.init();
		kc.collision(event1);
		Mockito.verify(sManager, Mockito.times(2)).getState(SoundState.class);
		Mockito.verify(ds).activateInvertControls();
		kc.collision(event2);
		Mockito.verify(sManager, Mockito.times(four)).getState(SoundState.class);
		Mockito.verify(ds, Mockito.times(2)).activateInvertControls();
	}
}
