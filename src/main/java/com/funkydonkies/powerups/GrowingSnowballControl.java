package com.funkydonkies.powerups;

import com.funkydonkies.controllers.StandardPenguinControl;
import com.funkydonkies.curve.SplineCurve;
import com.funkydonkies.factories.PenguinFactory;
import com.jme3.bullet.collision.PhysicsCollisionEvent;
import com.jme3.bullet.collision.PhysicsCollisionListener;
import com.jme3.bullet.collision.shapes.SphereCollisionShape;

public class GrowingSnowballControl extends StandardPenguinControl implements
		PhysicsCollisionListener {
	private static float THRESHOLD = 1;
	private float timer = 0;
	private boolean canScale = false;
	
	public GrowingSnowballControl(SphereCollisionShape sphereCollisionShape, float f) {
		super(sphereCollisionShape, f);
		
	}

	@Override
	public void collision(PhysicsCollisionEvent event) {
		super.collision(event);
		
		if (event.getNodeA().getName().equals(PenguinFactory.STANDARD_PENGUIN_NAME) 
				&& event.getNodeB().getName().equals(SplineCurve.CURVE_NAME)) {
			if (canScale) {
				spatial.scale(1.1f);
				canScale = false;
			}
		}
	}
	
	@Override
	public void update(float tpf) {
		super.update(tpf);
		timer += tpf;
		if (timer > 1) {
			timer = 0;
			canScale = true;
		}
	}
	
	public void scaleBack() {
		spatial.scale(1 / spatial.getWorldScale().x);
	}
}
