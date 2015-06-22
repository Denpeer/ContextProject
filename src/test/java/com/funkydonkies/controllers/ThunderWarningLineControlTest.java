package com.funkydonkies.controllers;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.funkydonkies.gamestates.CurveState;
import com.funkydonkies.gamestates.DifficultyState;
import com.funkydonkies.gamestates.PlayState;
import com.funkydonkies.sounds.SoundState;
import com.jme3.app.state.AppStateManager;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;

/**
 * tests for ThunderWarningLineControl class.
 * 
 * @author Olivier Dikken
 *
 */
public class ThunderWarningLineControlTest {

	private static final float TPF = 0.01f;
	private static final float TIME_ONE = 2f;
	private AppStateManager sManager;
	private SoundState ss;
	private Spatial spatial;
	private Vector3f tf;
	private Quaternion quat;
	private PhysicsSpace ps;
	private PlayState plays;
	private DifficultyState ds;
	private CurveState cs;

	/**
	 * prepare the mocks.
	 * 
	 * @throws Exception
	 *             catch exception
	 */
	@Before
	public void setUp() throws Exception {
		sManager = Mockito.mock(AppStateManager.class);
		ss = Mockito.mock(SoundState.class);
		spatial = Mockito.mock(Spatial.class);
		ps = Mockito.mock(PhysicsSpace.class);
		plays = Mockito.mock(PlayState.class);
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
		Mockito.when(plays.getPhysicsSpace()).thenReturn(ps);
		Mockito.when(sManager.getState(CurveState.class)).thenReturn(cs);
		Mockito.when(cs.getHighestPointX()).thenReturn(1f);
	}

	/**
	 * test constructor.
	 */
	@Test
	public void testThuunderWarningLineControl() {
		new ThunderWarningLineControl(sManager, 0, 0);
		Mockito.verify(sManager).getState(CurveState.class);
	}

	/**
	 * test init.
	 */
	@Test
	public void testInit() {
		final ThunderWarningLineControl twlc = new ThunderWarningLineControl(sManager, 0, 0);
		final int three = 3;
		twlc.setSpatial(spatial);
		twlc.init();
		Mockito.verify(spatial, Mockito.times(three)).setLocalTranslation(
				Mockito.any(Vector3f.class));
	}

	/**
	 * test controlUpdate.
	 */
	@Test
	public void testControlUpdate() {
		final ThunderWarningLineControl twlc = new ThunderWarningLineControl(sManager, 0, 0);
		twlc.setSpatial(spatial);
		twlc.controlUpdate(TPF);
		twlc.controlUpdate(TIME_ONE);
		Mockito.verify(spatial).removeFromParent();
	}

	/**
	 * test moveToX.
	 */
	@Test
	public void testMoveToX() {
		final ThunderWarningLineControl twlc = new ThunderWarningLineControl(sManager, 0, 0);
		twlc.setSpatial(spatial);
		twlc.controlUpdate(TPF);
		twlc.controlUpdate(TIME_ONE);
		Mockito.verify(spatial, Mockito.times(2)).setLocalTranslation(1f, 0f, 0f);
		Mockito.verify(spatial, Mockito.times(2 * 2)).getLocalTranslation();
	}
}
