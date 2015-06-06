package com.funkydonkies.powerups;

import com.funkydonkies.controllers.StandardPenguinControl;
import com.funkydonkies.curve.SplineCurve;
import com.funkydonkies.factories.PenguinFactory;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.collision.PhysicsCollisionEvent;
import com.jme3.bullet.collision.PhysicsCollisionListener;
import com.jme3.bullet.collision.shapes.SphereCollisionShape;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

public class GrowingSnowballControl extends StandardPenguinControl implements
		PhysicsCollisionListener {
	private static float THRESHOLD = 1;
	private float timer = 0;
	private boolean canScale = false;
	
	public GrowingSnowballControl(SphereCollisionShape sphereCollisionShape, float f) {
		super(sphereCollisionShape, f);
		
	}
	@Override
	public void setPhysicsSpace(PhysicsSpace space) {
		super.setPhysicsSpace(space);
		space.addCollisionListener(this);
	}
	
	@Override
	public void collision(PhysicsCollisionEvent event) {
		super.collision(event);
		System.out.println("collision");
		if (event.getNodeA().getName().equals(PenguinFactory.STANDARD_PENGUIN_NAME) 
				&& event.getNodeB().getName().equals(SplineCurve.CURVE_NAME)) {
			if (canScale) {
				System.out.println("scaleup");
				canScale = false;
			}
		}
	}
	
	@Override
	public void update(float tpf) {
		super.update(tpf);
		timer += tpf;
		if (timer > 1) {
			((Node) spatial).getChild("snowball").scale(1.1f);
//			spatial.scale(1.1f);
			timer = 0;
			canScale = true;
		}
	}
	
	public void scaleBack() {
		spatial.scale(1 / spatial.getWorldScale().x);
	}
}
