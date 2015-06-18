package com.funkydonkies.controllers;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.funkydonkies.factories.PenguinFactory;
import com.funkydonkies.factories.PolarBearFactory;
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
 * tests for the PolarBearClass.
 * @author Olivier Dikken
 *
 */
public class PolarBearControlTest {

	private static final float SPEED = 1;
	private static final float TPF = 0.01f;
	private static final int DESTROY_TIME = 3;
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
	private float stopX = 1;
	private Vector3f iLoc = new Vector3f(0, 0, 0);
	private Vector3f iLocStop = new Vector3f(stopX, 0, 0);

	/**
	 * prepare the mocks.
	 * @throws Exception catch exception.
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
		Mockito.when(spatial2.getLocalTranslation()).thenReturn(new Vector3f(stopX, 0, 0));
		Mockito.when(spatial2.getWorldTranslation()).thenReturn(new Vector3f(stopX, 0, 0));
		Mockito.when(spatial2.getLocalRotation()).thenReturn(quat);
		Mockito.when(spatial2.getWorldRotation()).thenReturn(quat);
		Mockito.when(sManager.getState(PlayState.class)).thenReturn(plays);
		Mockito.when(sManager.getState(DifficultyState.class)).thenReturn(ds);
		Mockito.when(plays.getPhysicsSpace()).thenReturn(ps);
		Mockito.when(event.getNodeA()).thenReturn(spatial);
		Mockito.when(event.getNodeB()).thenReturn(spatial2);
		Mockito.when(spatial.getName()).thenReturn(PolarBearFactory.POLAR_BEAR_NAME);
		Mockito.when(spatial2.getName()).thenReturn(PenguinFactory.PENGUIN_NAME);
	}

	/**
	 * test constructor.
	 */
	@Test
	public void testPolarBearControl() {
		new PolarBearControl(shape, sManager, stopX, iLoc);
		Mockito.verify(sManager).getState(DifficultyState.class);
	}

	/**
	 * test init.
	 */
	@Test
	public void testInit() {
		final PolarBearControl pc = new PolarBearControl(shape, sManager, stopX, iLoc);
		pc.setSpatial(spatial);
		pc.init();
		Mockito.verify(sManager, Mockito.times(2)).getState(SoundState.class);
		Mockito.verify(sManager, Mockito.times(2)).getState(PlayState.class);
		assertEquals(pc.getPhysicsLocation(), iLoc);
	}

	/**
	 * test setPhysicsSpace.
	 */
	@Test
	public void testSetPhysicsSpace() {
		final PolarBearControl pc = new PolarBearControl(shape, sManager, stopX, iLoc);
		pc.setPhysicsSpace(ps);
		assertEquals(pc.getPhysicsSpace(), ps);
		Mockito.verify(ps).addCollisionListener(pc);
	}

	/**
	 * test update.
	 */
	@Test
	public void testUpdate() {
		final PolarBearControl pc = new PolarBearControl(shape, sManager, stopX, iLocStop);
		pc.setSpatial(spatial);
		pc.init();
		pc.update(TPF);
		pc.update(DESTROY_TIME);
		pc.update(TPF);
	}
	
	/**
	 * test if movement works as expected.
	 */
	@Test
	public void testMoveSpatial() {
		final PolarBearControl pc = new PolarBearControl(shape, sManager, stopX, iLoc);
		pc.setSpatial(spatial);
		pc.init();
		pc.update(TPF);
		final Vector3f vec = spatial.getLocalTranslation();
		final float offset = 0.5f;
		final Vector3f loc = new Vector3f((float) (vec.getX() + SPEED + offset), vec.getY(), vec.getZ());
		assertEquals(pc.getPhysicsLocation(), loc);
		final PolarBearControl pc2 = new PolarBearControl(shape, sManager, stopX, iLocStop);
		pc2.setSpatial(spatial2);
		pc2.init();
		pc2.update(TPF);
		assertEquals(pc.getPhysicsLocation(), loc);
	}

	/**
	 * test collision triggering.
	 */
	@Test
	public void testCollision() {
		final int three = 3;
		final PolarBearControl pc = new PolarBearControl(shape, sManager, stopX, iLocStop);
		pc.setSpatial(spatial);
		pc.init();
		pc.update(TPF);
		pc.collision(event);
		Mockito.verify(sManager, Mockito.times(three)).getState(SoundState.class);
		Mockito.verify(ds).resetDiff();
	}

}
