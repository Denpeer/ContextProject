package com.funkydonkies.controllers;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.funkydonkies.factories.FishFactory;
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
 * tests for the fishcontrol class.
 * 
 * @author Olivier Dikken
 *
 */
public class FishControlTest {

	private CollisionShape shape;
	private AppStateManager sManager;
	private SoundState ss;
	private Spatial spatial;
	private Vector3f tf;
	private Quaternion quat;
	private PhysicsSpace ps;
	private PlayState plays;
	private PhysicsCollisionEvent event1;
	private PhysicsCollisionEvent event2;
	private Spatial spatial2;
	private Spatial spatial3;
	private DifficultyState ds;

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
		ss = Mockito.mock(SoundState.class);
		spatial = Mockito.mock(Spatial.class);
		spatial2 = Mockito.mock(Spatial.class);
		spatial3 = Mockito.mock(Spatial.class);
		ps = Mockito.mock(PhysicsSpace.class);
		plays = Mockito.mock(PlayState.class);
		event1 = Mockito.mock(PhysicsCollisionEvent.class);
		event2 = Mockito.mock(PhysicsCollisionEvent.class);
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
		Mockito.when(event1.getNodeA()).thenReturn(spatial);
		Mockito.when(event1.getNodeB()).thenReturn(spatial2);
		Mockito.when(spatial.getName()).thenReturn(FishFactory.FISH_NAME);
		Mockito.when(spatial2.getName()).thenReturn(PenguinFactory.PENGUIN_NAME);
		Mockito.when(event2.getNodeA()).thenReturn(spatial);
		Mockito.when(event2.getNodeB()).thenReturn(spatial3);
		Mockito.when(spatial3.getName()).thenReturn(SnowballPowerup.SNOW_PENGUIN_NAME);
	}

	/**
	 * test fishControl.
	 */
	@Test
	public void testFishControl() {
		new FishControl(shape, sManager);
		Mockito.verify(sManager).getState(DifficultyState.class);
	}

	/**
	 * test init.
	 */
	@Test
	public void testInit() {
		final FishControl fc = new FishControl(shape, sManager);
		fc.setSpatial(spatial);
		fc.init();
		Mockito.verify(sManager, Mockito.times(2)).getState(SoundState.class);
		Mockito.verify(sManager, Mockito.times(2)).getState(PlayState.class);
	}

	/**
	 * test setPhysicsSpace.
	 */
	@Test
	public void testSetPhysicsSpace() {
		final FishControl fc = new FishControl(shape, sManager);
		fc.setPhysicsSpace(ps);
		assertEquals(fc.getPhysicsSpace(), ps);
		Mockito.verify(ps).addCollisionListener(Mockito.any(FishControl.class));
	}

	/**
	 * test collision triggering both collision events.
	 */
	@Test
	public void testCollision() {
		final FishControl fc = new FishControl(shape, sManager);
		final int three = 3;
		final int four = 4;
		fc.setSpatial(spatial);
		fc.init();
		fc.collision(event1);
		Mockito.verify(sManager, Mockito.times(three)).getState(SoundState.class);
		fc.collision(event2);
		Mockito.verify(sManager, Mockito.times(four)).getState(SoundState.class);
	}

}
