package com.funkydonkies.powerups;


import java.util.List;

import com.funkydonkies.core.App;
import com.funkydonkies.exceptions.BadDynamicTypeException;
import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import com.jme3.bullet.collision.PhysicsCollisionEvent;
import com.jme3.bullet.collision.PhysicsCollisionListener;
import com.jme3.bullet.collision.shapes.SphereCollisionShape;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Sphere;

public class SnowballPowerup extends AbstractPowerup implements PhysicsCollisionListener {
	private App app;
	private AppStateManager sManager;

	@Override
	public void initialize(AppStateManager stateManager, Application appl) {
		super.initialize(stateManager, app);
		if (appl instanceof App) {
			app = (App) appl;
		} else {
			throw new BadDynamicTypeException();
		}
		
		sManager = stateManager;
		
	}
	
	@Override
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
		
		Node penguinNode = app.getPenguinNode();
		List<Spatial> penguins = penguinNode.getChildren();
		
		if (enabled) {
			for (Spatial penguin : penguins) {
				System.out.println(penguin.getName());
//				((Node) penguin).getChild("standardPenguin").getControl(StandardPenguinControl.class).setEnabled(false);
				
				GrowingSnowballControl SBControl = penguin.getControl(GrowingSnowballControl.class);
				//Check if the penguin already has a snowballcontrol
				if (SBControl == null) {
					final Sphere shape = new Sphere(20, 20, 3);
					Geometry snowball = new Geometry("snowball", shape);
					Material snowMaterial = new Material(app.getAssetManager(), 
							"Common/MatDefs/Misc/Unshaded.j3md");
					snowMaterial.setColor("Color", ColorRGBA.White);
					snowball.setMaterial(snowMaterial);
					Vector3f snowballLoc = snowball.getLocalTranslation();
					snowballLoc.z = -4f;
					snowballLoc.x = -1.5f;
					snowballLoc.y = -1;
					snowball.setLocalTranslation(snowballLoc);
					((Node) penguin).attachChild(snowball);
					
					GrowingSnowballControl snowBallControl = new GrowingSnowballControl(
							new SphereCollisionShape(20), 1f);
					penguin.addControl(snowBallControl);
					System.out.println("Added snowballControl");
				} else {
					SBControl.setEnabled(true);
					SBControl.scaleBack();
				}
			}
		} else {
			for (Spatial penguin : penguins) {
				GrowingSnowballControl SBControl = penguin.getControl(GrowingSnowballControl.class);
				if (SBControl != null) {
//					SBControl.scaleBack();
					SBControl.setEnabled(false);
				}
			}
		}
	}
	
	
	@Override
	public void collision(PhysicsCollisionEvent event) {

	}

}
