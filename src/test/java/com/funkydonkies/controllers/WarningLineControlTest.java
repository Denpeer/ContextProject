package com.funkydonkies.controllers;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;

/**
 * tests for the WarningLineControl class.
 * 
 * @author Olivier Dikken
 *
 */
public class WarningLineControlTest {

	private static final float TPF = 0.01f;
	private static final float TIME_TWO = 2f;
	private Spatial spatial;

	/**
	 * prepare the mocks.
	 * 
	 * @throws Exception
	 *             catch exception
	 */
	@Before
	public void setUp() throws Exception {
		spatial = Mockito.mock(Spatial.class);
	}

	/**
	 * test constructor. nothing to test in the constructor method.
	 */
	@Test
	public void testWarningLineControl() {
		new WarningLineControl(0, 0);
	}

	/**
	 * test init.
	 */
	@Test
	public void testInit() {
		final WarningLineControl wlc = new WarningLineControl(-1, 1);
		wlc.setSpatial(spatial);
		wlc.init();
		Mockito.verify(spatial, Mockito.times(2)).setLocalTranslation(new Vector3f(-1, 1, 0));
	}

	/**
	 * test controlUpdate.
	 */
	@Test
	public void testControlUpdate() {
		final WarningLineControl wlc = new WarningLineControl(0, 0);
		wlc.setSpatial(spatial);
		wlc.controlUpdate(TPF);
		Mockito.verify(spatial, Mockito.times(0)).removeFromParent();
		wlc.controlUpdate(TIME_TWO);
		Mockito.verify(spatial).removeFromParent();
	}
}
