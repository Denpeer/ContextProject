package com.funkydonkies.w4v3;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import com.funkydonkies.w4v3.obstacles.Target;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.collision.PhysicsCollisionEvent;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.scene.Spatial;

public class TargetControlTest {
	private static TargetControl control;
	private static CollisionShape shape;
	private static Target target;
	private static Combo combo;
	private static PhysicsCollisionEvent event;
	private static Spatial ballSpatial;
	private static Spatial targetSpatial;
	private static PhysicsSpace space;
	
	@Before
	public void setUp() throws Exception {
		shape = mock(CollisionShape.class);
		target = mock(Target.class);
		combo = mock(Combo.class);
		event = mock(PhysicsCollisionEvent.class);
		control = new TargetControl(shape, target);
		control.setCombo(combo);
		ballSpatial = mock(Spatial.class);
		targetSpatial = mock(Spatial.class);
		space = mock(PhysicsSpace.class);
		when(ballSpatial.getName()).thenReturn("ball");
		when(targetSpatial.getName()).thenReturn("target");
		when(event.getNodeA()).thenReturn(ballSpatial);
		when(event.getNodeB()).thenReturn(targetSpatial);
	}

	@Test
	public void testCollision() {
		control.collision(event);
		verify(target).respawn();
		verify(combo).incCombo();
	}
	
	@Test
	public void testCollision2() {
		when(event.getNodeB()).thenReturn(targetSpatial);
		when(event.getNodeA()).thenReturn(ballSpatial);
		control.collision(event);
		verify(target).respawn();
		verify(combo).incCombo();
	}
	
	@Test
	public void testNotImportantCollision(){
		when(targetSpatial.getName()).thenReturn("nothing");
		control.collision(event);
		verifyNoMoreInteractions(target);
		verifyNoMoreInteractions(combo);
	}
	
	@Test
	public void testSetPhysicsSpace(){
		control.setPhysicsSpace(space);
		verify(space).addCollisionListener(control);
	}
	
	@Test
	public void testDelete(){
		control.setPhysicsSpace(space);
		control.delete(); 
		verify(space).removeCollisionListener(control);
	}

}
