package com.funkydonkies.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import com.funkydonkies.gamestates.PlayState;
import com.jme3.app.state.AppStateManager;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.collision.shapes.SphereCollisionShape;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;

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
	private Geometry snowball;
	private PlayState playState;
	private PhysicsSpace phySpace;
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
		snowball = Mockito.mock(Geometry.class);
		playState = Mockito.mock(PlayState.class);
		phySpace = Mockito.mock(PhysicsSpace.class);
		tf = new Vector3f(0, 0, 0);
		quat = new Quaternion();
		Mockito.when(spatial.getLocalTranslation()).thenReturn(tf);
		Mockito.when(spatial.getWorldTranslation()).thenReturn(tf);
		Mockito.when(spatial.getLocalRotation()).thenReturn(quat);
		Mockito.when(spatial.getWorldRotation()).thenReturn(quat);
		Mockito.when(spatial.getChild(GrowingSnowballControl.SNOW_BALL_NAME)).thenReturn(snowball);
		Mockito.when(spatial.getWorldScale()).thenReturn(tf);
		Mockito.doReturn(playState).when(sManager).getState(PlayState.class);
		Mockito.doReturn(phySpace).when(playState).getPhysicsSpace();
		Mockito.doNothing().when(phySpace).add(Mockito.any());
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
		final float radius = ((SphereCollisionShape) gsc.getCollisionShape()).getRadius();
		gsc.setSpatial(spatial);
		gsc.scaleSnowBall(SCALE_TIME + TPF);
		assertTrue(((SphereCollisionShape) gsc.getCollisionShape()).getRadius() > radius);
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
	
	@Test
	public void testInit() {
		final GrowingSnowballControl gsc = new GrowingSnowballControl(sphereCollisionShape, mass,
				sManager);
		GrowingSnowballControl spy = Mockito.spy(gsc);
		spy.init();
		Mockito.verify(spy).disableMeshRotation();
		Mockito.verify(phySpace).add(spy);
	}
}
