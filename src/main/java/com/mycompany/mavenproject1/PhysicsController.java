package com.mycompany.mavenproject1;

import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.PhysicsTickListener;
import com.jme3.bullet.collision.shapes.SphereCollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.math.Vector3f;

public class PhysicsController extends RigidBodyControl implements
		PhysicsTickListener {
	
	public PhysicsController() {
		// TODO Auto-generated constructor stub
	}

	public PhysicsController(SphereCollisionShape sphereCollisionShape, float f) {
		super(sphereCollisionShape, f);
		
	}
	
	@Override
	public void setPhysicsSpace(PhysicsSpace space) {
		super.setPhysicsSpace(space);
		space.addTickListener(this);
	}

	public void physicsTick(PhysicsSpace arg0, float arg1) {
//		Vector3f velocity = getLinearVelocity();
//		Vector3f loc = getPhysicsLocation();
		Vector3f angularvel = getAngularVelocity();
		if(angularvel.z > 0)
			System.out.println("Angular: x: "+ angularvel.x + "  y : " + angularvel.y + "   z : " + angularvel.z);
//		velocity.z = 0;
//		loc.z = 0;
//		setPhysicsLocation(loc);
//		setLinearVelocity(velocity);
		angularvel.y = 0;

		setAngularVelocity(angularvel);
	}

	public void prePhysicsTick(PhysicsSpace arg0, float arg1) {
//		Vector3f velocity = getLinearVelocity();
//		Vector3f loc = getPhysicsLocation();
		Vector3f angularvel = getAngularVelocity();
		if(angularvel.z > 0)
			System.out.println("Angular: x: "+ angularvel.x + "  y : " + angularvel.y + "   z : " + angularvel.z);
//		velocity.z = 0;
//		loc.z = 0;
//		setPhysicsLocation(loc);
//		setLinearVelocity(velocity);
		angularvel.y = 0;

		setAngularVelocity(angularvel);

	}

}
