package com.funkydonkies.controllers;

import com.funkydonkies.curve.SplineCurve;
import com.funkydonkies.factories.PenguinFactory;
import com.funkydonkies.geometrys.penguins.Snowball;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.PhysicsTickListener;
import com.jme3.bullet.collision.PhysicsCollisionEvent;
import com.jme3.bullet.collision.PhysicsCollisionListener;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.collision.shapes.SphereCollisionShape;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

public class GrowingSnowballControl extends StandardPenguinControl implements
		PhysicsCollisionListener, PhysicsTickListener {
	private static final float SCALE_UP_FACTOR = 0.3f;
	private static float THRESHOLD = 0.5f;
	private float timer = 0;
	private boolean canScale = false;
	
	public GrowingSnowballControl(SphereCollisionShape sphereCollisionShape, float f) {
		super(sphereCollisionShape, f);
		
	}
	@Override
	public void setPhysicsSpace(PhysicsSpace space) {
		super.setPhysicsSpace(space);
		space.addCollisionListener(this);
		space.addTickListener(this);
	}
	
	@Override
	public void collision(PhysicsCollisionEvent event) {
		if(event.getNodeA() != null && event.getNodeB() != null){
			if (event.getNodeA().getName().equals(PenguinFactory.STANDARD_PENGUIN_NAME) 
					&& event.getNodeB().getName().equals(SplineCurve.CURVE_NAME)
					|| event.getNodeB().getName().equals(PenguinFactory.STANDARD_PENGUIN_NAME) 
					&&  event.getNodeA().getName().equals(SplineCurve.CURVE_NAME)) {
				if (canScale) {
					canScale = false;
					//TODO only scale here
				}
			}
		}
	}
	
	@Override
	public void update(float tpf) {
		super.update(tpf);
		timer += tpf;
		if (timer > THRESHOLD) {
			timer = 0;
			canScale = true;
			Spatial snowBall = ((Node) spatial).getChild("snowball");
			((Snowball) snowBall).setRadius(((Snowball) snowBall).getRadius() + SCALE_UP_FACTOR);
			Vector3f loc = snowBall.getLocalTranslation();
			loc.x = loc.x - 0.5f * SCALE_UP_FACTOR;
			loc.y = loc.y - 0.5f * SCALE_UP_FACTOR;
			CollisionShape s = getCollisionShape();
			
			float radius = ((SphereCollisionShape) s).getRadius();
			setCollisionShape(new SphereCollisionShape(radius + SCALE_UP_FACTOR * 0.5f));
			snowBall.setLocalTranslation(loc);
		}
	}
	
	public void scaleBack() {
		spatial.scale(1 / spatial.getWorldScale().x);
	}
	
	@Override
	public void physicsTick(PhysicsSpace space, float tpf) {
		super.physicsTick(space, tpf);
	}
	
	/**
	 * Performed before each physics tick.
	 * Sets the z location to 0 to restrict the object from moving on the 
	 * z-axis.
	 * @param space The physics space 
	 * @param tpf time per frame in seconds (time since last frame) 
	 * 	for normalizing in faster computers 
	 */
	public void prePhysicsTick(final PhysicsSpace space, 
			final float tpf) {
		super.prePhysicsTick(space, tpf);
		Vector3f a = getAngularVelocity();
		a.x = 0;
		a.y = 0;
		setAngularVelocity(a);
	}
	
}
