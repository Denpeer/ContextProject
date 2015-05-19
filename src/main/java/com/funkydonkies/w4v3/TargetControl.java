package com.funkydonkies.w4v3;

import com.funkydonkies.w4v3.obstacles.Target;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.collision.PhysicsCollisionEvent;
import com.jme3.bullet.collision.PhysicsCollisionListener;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.GhostControl;

public class TargetControl extends GhostControl implements PhysicsCollisionListener {
	private Target target;
	private Combo combo;

	public TargetControl(CollisionShape shape, Target t, Combo c) {
		super(shape);
		target = t;
		combo = c;
	}
	
	/**
	 * Set the physics space and add this controller as tick listener.
	 * @param space takes a pre-defined jme3 physicsSpace
	 */
	@Override
	public void setPhysicsSpace(final PhysicsSpace space) {
		super.setPhysicsSpace(space);
		space.addCollisionListener(this);
	}
	
	public void delete(){
		space.removeCollisionListener(this);
	}

	public void collision(PhysicsCollisionEvent event) {
		if(("target".equals(event.getNodeA().getName()) 
				&& "ball".equals(event.getNodeB().getName()))
				|| ("ball".equals(event.getNodeA().getName()) 
						&& "target".equals(event.getNodeB().getName()))){
			combo.incCombo();
			target.respawn();
		}
	}

}
