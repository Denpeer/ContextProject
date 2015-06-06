package com.funkydonkies.controllers;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import com.funkydonkies.geometries.targets.Fish;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.collision.PhysicsCollisionEvent;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.scene.Spatial;

/**
 * This class tests the controller of the fish targets.
 * @author SDumasy
 *
 */
public class FishControlTest {
	private static FishControl control;
	private static CollisionShape shape;
	private static Fish fish;
	private static PhysicsCollisionEvent event;
	private static Spatial ballSpatial;
	private static Spatial targetSpatial;
	private static PhysicsSpace space;
	
	/**
	 * Do this prior to testing.
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		shape = mock(CollisionShape.class);
		fish = mock(Fish.class);
		event = mock(PhysicsCollisionEvent.class);
		control = new FishControl(shape);
		ballSpatial = mock(Spatial.class);
		targetSpatial = mock(Spatial.class);
		space = mock(PhysicsSpace.class);
		when(ballSpatial.getName()).thenReturn("ball");
		when(targetSpatial.getName()).thenReturn("target");
		when(event.getNodeA()).thenReturn(ballSpatial);
		when(event.getNodeB()).thenReturn(targetSpatial);
	}

	/**
	 * Test the target respawn when collision occurs.
	 */
	@Test
	public void testCollision() {
		control.collision(event);
		verify(control).respawn();
	}
	
	/**
	 * Test the target respawn when collision occurs.
	 */
	@Test
	public void testCollision2() {
		when(event.getNodeB()).thenReturn(targetSpatial);
		when(event.getNodeA()).thenReturn(ballSpatial);
		control.collision(event);
		verify(control).respawn();
	}
	
	/**
	 * No interaction when no collision.
	 */
	@Test
	public void testNotImportantCollision() {
		when(targetSpatial.getName()).thenReturn("nothing");
		control.collision(event);
		verifyNoMoreInteractions(control);
	}
	
	/**
	 * 
	 */
	@Test
	public void testSetPhysicsSpace() {
		control.setPhysicsSpace(space);
		verify(space).addCollisionListener(control);
	}
	
	/**
	 * 
	 */
	@Test
	public void testDelete() {
		control.setPhysicsSpace(space);
		control.delete(); 
		verify(space).removeCollisionListener(control);
	}

}
