package com.funkydonkies.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.funkydonkies.spatials.Snowball;
import com.jme3.app.state.AppStateManager;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.collision.shapes.SphereCollisionShape;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

/**
 * tests for the growingsnowballcontrol class.
 * @author Olivier Dikken
 *
 */
public class GrowingSnowballControlTest {

	private static final float TPF = 0.01f;
	private static final float SCALE_TIME = 0.25f;
	private SphereCollisionShape sphereCollisionShape;
	private float mass;
	private AppStateManager sManager;
	private PhysicsSpace ps;
	private Node spatial;
	private Vector3f tf;
	private Quaternion quat;
	private Spatial snowball;

	/**
	 * prepare the mocks.
	 * @throws Exception catches exception
	 */
	@Before
	public void setUp() throws Exception {
		sphereCollisionShape = Mockito.mock((SphereCollisionShape.class));
		mass = 1;
		sManager = Mockito.mock(AppStateManager.class);
		ps = Mockito.mock(PhysicsSpace.class);
		spatial = Mockito.mock(Node.class);
		tf = new Vector3f(0, 0, 0);
		quat = new Quaternion();
		snowball = Mockito.mock(Snowball.class);
		Mockito.when(spatial.getLocalTranslation()).thenReturn(tf);
		Mockito.when(spatial.getWorldTranslation()).thenReturn(tf);
		Mockito.when(spatial.getLocalRotation()).thenReturn(quat);
		Mockito.when(spatial.getWorldRotation()).thenReturn(quat);
		Mockito.when(spatial.getChild(GrowingSnowballControl.SNOW_BALL_NAME)).thenReturn(snowball);
		Mockito.when(snowball.getLocalTranslation()).thenReturn(tf);
		Mockito.when(spatial.getWorldScale()).thenReturn(tf);
	}

	/**
	 * test constructor.
	 */
	@Test
	public void testGrowingSnowballControl() {
		assertTrue(new GrowingSnowballControl(sphereCollisionShape, mass, sManager) instanceof PenguinControl);
	}

	/**
	 * test setPhysicsSpace.
	 */
	@Test
	public void testSetPhysicsSpace() {
		final GrowingSnowballControl gsc = new GrowingSnowballControl(sphereCollisionShape, mass,
				sManager);
		gsc.setPhysicsSpace(ps);
		assertEquals(gsc.getPhysicsSpace(), ps);
		Mockito.verify(ps, Mockito.times(2)).addCollisionListener(
				Mockito.any(GrowingSnowballControl.class));
	}

	/**
	 * test update.
	 */
	@Test
	public void testUpdate() {
		final GrowingSnowballControl gsc = new GrowingSnowballControl(sphereCollisionShape, mass,
				sManager);
		gsc.setSpatial(spatial);
		gsc.update(TPF);
		Mockito.verify(spatial).getWorldTranslation();
	}

	/**
	 * test scaleSnowBall.
	 */
	@Test
	public void testScaleSnowBall() {
		final GrowingSnowballControl gsc = new GrowingSnowballControl(sphereCollisionShape, mass,
				sManager);
		gsc.setSpatial(spatial);
		gsc.scaleSnowBall(SCALE_TIME + TPF);
		Mockito.verify(snowball).getLocalTranslation();
	}

	/**
	 * test scaleBack.
	 */
	@Test
	public void testScaleBack() {
		final GrowingSnowballControl gsc = new GrowingSnowballControl(sphereCollisionShape, mass,
				sManager);
		gsc.setSpatial(spatial);
		gsc.scaleBack();
	}

	/**
	 * test physicsTick.
	 */
	@Test
	public void testPhysicsTick() {
		final GrowingSnowballControl gsc = new GrowingSnowballControl(sphereCollisionShape, mass,
				sManager);
		gsc.physicsTick(ps, TPF);
	}

	/**
	 * test prePhysicsTick.
	 */
	@Test
	public void testPrePhysicsTick() {
		final GrowingSnowballControl gsc = new GrowingSnowballControl(sphereCollisionShape, mass,
				sManager);
		gsc.prePhysicsTick(ps, TPF);
	}
}
