package com.funkydonkies.controllers;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.funkydonkies.factories.KillerWhaleFactory;
import com.funkydonkies.factories.PenguinFactory;
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
 * tests for killerwhalecontrol class.
 * @author Olivier Dikken
 *
 */
public class KillerWhaleControlTest {
	
	private static final float TPF = 0.01f;
	private static final float TIME_MORE_THAN_TWO = 3.0f;
	private static final float SMALLER_DESTROY_HEIGHT = -1000;
	private AppStateManager sManager;
	private CollisionShape colShape;
	private Vector3f vec;
	private PhysicsSpace ps;
	private Spatial spatial;
	private Spatial spatial2;
	private Spatial spatial3;
	private SoundState ss;
	private PlayState plays;
	private Vector3f tf;
	private Vector3f tf2;
	private Quaternion quat;
	private PhysicsCollisionEvent event;
	private DifficultyState ds;
	

	/**
	 * prepare mocks.
	 * @throws Exception catch exception
	 */
	@Before
	public void setUp() throws Exception {
		sManager = Mockito.mock(AppStateManager.class);
		colShape = Mockito.mock(CollisionShape.class);
		vec  = new Vector3f(0, 0, 0);
		ps = Mockito.mock(PhysicsSpace.class);
		ss = Mockito.mock(SoundState.class);
		plays = Mockito.mock(PlayState.class);
		tf = new Vector3f(0, SMALLER_DESTROY_HEIGHT, 0);
		tf2 = new Vector3f(0, 0, 0);
		spatial = Mockito.mock(Spatial.class);
		spatial2 = Mockito.mock(Spatial.class);
		spatial3 = Mockito.mock(Spatial.class);
		quat = new Quaternion();
		event = Mockito.mock(PhysicsCollisionEvent.class);
		ds = Mockito.mock(DifficultyState.class);
		Mockito.when(sManager.getState(SoundState.class)).thenReturn(ss);
		Mockito.when(sManager.getState(PlayState.class)).thenReturn(plays);
		Mockito.when(sManager.getState(DifficultyState.class)).thenReturn(ds);
		Mockito.when(spatial.getLocalTranslation()).thenReturn(tf);
		Mockito.when(spatial.getWorldTranslation()).thenReturn(tf);
		Mockito.when(spatial.getLocalRotation()).thenReturn(quat);
		Mockito.when(spatial.getWorldRotation()).thenReturn(quat);
		Mockito.when(plays.getPhysicsSpace()).thenReturn(ps);
		Mockito.when(event.getNodeA()).thenReturn(spatial);
		Mockito.when(event.getNodeB()).thenReturn(spatial2);
		Mockito.when(spatial.getName()).thenReturn(KillerWhaleFactory.WHALE_NAME);
		Mockito.when(spatial2.getName()).thenReturn(PenguinFactory.PENGUIN_NAME);
		Mockito.when(spatial3.getLocalTranslation()).thenReturn(tf2);
		Mockito.when(spatial3.getWorldTranslation()).thenReturn(tf2);
		Mockito.when(spatial3.getLocalRotation()).thenReturn(quat);
		Mockito.when(spatial3.getWorldRotation()).thenReturn(quat);
	}

	/**
	 * test constructor.
	 */
	@Test
	public void testKillerWhaleControl() {
		new KillerWhaleControl(colShape, sManager, vec);
		Mockito.verify(sManager).getState(DifficultyState.class);
	}
	
	/**
	 * test setPhysicsSpace.
	 */
	@Test
	public void testSetPhysicsSpace() {
		final KillerWhaleControl kwc = new KillerWhaleControl(colShape, sManager, vec);
		kwc.setPhysicsSpace(ps);
		assertEquals(kwc.getPhysicsSpace(), ps);
		Mockito.verify(ps).addCollisionListener(
				Mockito.any(KillerWhaleControl.class));
	}

	/**
	 * test init.
	 */
	@Test
	public void testInit() {
		final KillerWhaleControl kwc = new KillerWhaleControl(colShape, sManager, vec);
		kwc.setSpatial(spatial);
		kwc.init();
		Mockito.verify(sManager, Mockito.times(2)).getState(SoundState.class);
		Mockito.verify(sManager, Mockito.times(2)).getState(PlayState.class);
	}
	
	/**
	 * test update.
	 */
	@Test
	public void testUpdate() {
		final KillerWhaleControl kwc = new KillerWhaleControl(colShape, sManager, vec);
		kwc.setSpatial(spatial);
		kwc.update(TPF);
		Mockito.verify(spatial).getWorldTranslation();
	}
	
	/**
	 * test removeCheck.
	 */
	@Test
	public void testRemoveCheck() {
		final KillerWhaleControl kwc = new KillerWhaleControl(colShape, sManager, vec);
		kwc.setSpatial(spatial);
		kwc.removeCheck();
		Mockito.verify(spatial).removeFromParent();
	}
	
	/**
	 * TODO: test moveSpatial.
	 */
	@Test
	public void testMoveSpatial() {
		final KillerWhaleControl kwc = new KillerWhaleControl(colShape, sManager, vec);
		kwc.setSpatial(spatial);
		kwc.update(TIME_MORE_THAN_TWO);
		kwc.setSpatial(spatial3);
		kwc.update(TIME_MORE_THAN_TWO);
		Mockito.verify(spatial, Mockito.times(2)).setLocalTranslation(Mockito.any(Vector3f.class));
	}
	
	/**
	 * test collision.
	 */
	@Test
	public void testCollision() {
		final KillerWhaleControl kwc = new KillerWhaleControl(colShape, sManager, vec);
		final int three = 3;
		kwc.setSpatial(spatial);
		kwc.init();
		kwc.collision(event);
		Mockito.verify(sManager, Mockito.times(three)).getState(SoundState.class);
	}
}
