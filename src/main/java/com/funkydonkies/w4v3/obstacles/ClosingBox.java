package com.funkydonkies.w4v3.obstacles;

import com.jme3.asset.AssetManager;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;

public class ClosingBox extends Obstacle{
	
	private Geometry geom;
	private Box box;
	private boolean moveUp;
	final RigidBodyControl phys;

	public ClosingBox(double width, double height, double depth) {
		super(width, height, depth);
		box = new Box((float)width, (float)height, 2f);
		geom = new Geometry("closingBox", box);
		phys = new RigidBodyControl(0f);
		moveUp = true;
	}

	public void draw(AssetManager assetManager, PhysicsSpace psySpace, Node rootNode) {
		final Material mat = new Material(assetManager,
				"Common/MatDefs/Misc/Unshaded.j3md");
		mat.setColor("Color", ColorRGBA.Red);
		//final Node node = new Node("boxx");
		
		geom.move(20, 0, -0.5f);
		//geom.rotate(15f, , 0);
		geom.setMaterial(mat);
		geom.addControl(phys);
		psySpace.add(phys);
		rootNode.attachChild(geom);
	}

	public void draw() {
		// TODO Auto-generated method stub
		
	}
	
	public void move(){
		Vector3f vec = geom.getLocalTranslation();
		if( moveUp){
			vec.setY(vec.getY() + 0.1f);
			phys.setPhysicsLocation(vec);
			geom.setLocalTranslation(phys.getPhysicsLocation());
			if(vec.getY() > getHeight() * 1.5)
				moveUp = false;
		}else{
			vec.setY(vec.getY() - 0.1f);
			phys.setPhysicsLocation(vec);
			geom.setLocalTranslation(phys.getPhysicsLocation());
			if(vec.getY() < getHeight() * -1.5)
				moveUp = true;
		}

	}

}
