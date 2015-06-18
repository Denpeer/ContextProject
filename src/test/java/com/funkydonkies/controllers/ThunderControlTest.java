package com.funkydonkies.controllers;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.funkydonkies.factories.PenguinFactory;
import com.funkydonkies.factories.ThunderFactory;
import com.funkydonkies.gamestates.CurveState;
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
 * tests for ThunderControl class.
 * 
 * @author Olivier Dikken
 *
 */
public class ThunderControlTest {

	private static final float TIME_TWO_HALF = 2.5f;
	private CollisionShape shape;
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
	private CurveState cs;

	/**
	 * prepare mocks.
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
		ps = Mockito.mock(PhysicsSpace.class);
		plays = Mockito.mock(PlayState.class);
		event = Mockito.mock(PhysicsCollisionEvent.class);
		tf = new Vector3f(0, 0, 0);
		ds = Mockito.mock(DifficultyState.class);
		quat = new Quaternion();
		cs = Mockito.mock(CurveState.class);
		Mockito.when(sManager.getState(SoundState.class)).thenReturn(ss);
		Mockito.when(spatial.getLocalTranslation()).thenReturn(tf);
		Mockito.when(spatial.getWorldTranslation()).thenReturn(tf);
		Mockito.when(spatial.getLocalRotation()).thenReturn(quat);
		Mockito.when(spatial.getWorldRotation()).thenReturn(quat);
		Mockito.when(sManager.getState(PlayState.class)).thenReturn(plays);
		Mockito.when(sManager.getState(DifficultyState.class)).thenReturn(ds);
		Mockito.when(sManager.getState(CurveState.class)).thenReturn(cs);
		Mockito.when(plays.getPhysicsSpace()).thenReturn(ps);
		Mockito.when(event.getNodeA()).thenReturn(spatial);
		Mockito.when(event.getNodeB()).thenReturn(spatial2);
		Mockito.when(spatial.getName()).thenReturn(ThunderFactory.THUNDER_NAME);
		Mockito.when(spatial2.getName()).thenReturn(PenguinFactory.PENGUIN_NAME);
		Mockito.when(cs.getHighestPointX()).thenReturn(1.0f);
	}

	/**
	 * test ThunderControl.
	 */
	@Test
	public void testThunderControl() {
		new ThunderControl(shape, sManager, 0);
		Mockito.verify(sManager).getState(DifficultyState.class);
	}

	/**
	 * test init.
	 */
	@Test
	public void testInit() {
		final ThunderControl tc = new ThunderControl(shape, sManager, 0);
		tc.setSpatial(spatial);
		tc.init();
		Mockito.verify(sManager, Mockito.times(2)).getState(SoundState.class);
		Mockito.verify(sManager, Mockito.times(2)).getState(PlayState.class);
	}

	/**
	 * test setPhysicsSpace.
	 */
	@Test
	public void testSetPhysicsSpace() {
		final ThunderControl tc = new ThunderControl(shape, sManager, 0);
		tc.setPhysicsSpace(ps);
		assertEquals(tc.getPhysicsSpace(), ps);
		Mockito.verify(ps).addCollisionListener(Mockito.any(ThunderControl.class));
	}

	/**
	 * test update.
	 */
	@Test
	public void testUpdate() {
		final ThunderControl tc = new ThunderControl(shape, sManager, 0);
		tc.setSpatial(spatial);
		tc.init();
		tc.update(TIME_TWO_HALF);
		tc.update(TIME_TWO_HALF);
		Mockito.verify(spatial).removeFromParent();
	}

	/**
	 * test moveToX.
	 */
	@Test
	public void testMoveToX() {
		final ThunderControl tc = new ThunderControl(shape, sManager, 0);
		tc.setSpatial(spatial);
		tc.init();
		tc.update(TIME_TWO_HALF);
		Mockito.verify(spatial, Mockito.times(2)).setLocalTranslation(Mockito.any(Vector3f.class));
	}

	/**
	 * test collision.
	 */
	@Test
	public void testCollision() {
		final ThunderControl tc = new ThunderControl(shape, sManager, 0);
		final int three = 3;
		tc.setSpatial(spatial);
		tc.init();
		tc.collision(event);
		Mockito.verify(sManager, Mockito.times(three)).getState(SoundState.class);
		Mockito.verify(ds).resetDiff();
	}
}
