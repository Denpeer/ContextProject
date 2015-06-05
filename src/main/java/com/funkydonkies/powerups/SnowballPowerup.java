package com.funkydonkies.powerups;


import java.util.List;

import com.funkydonkies.controllers.StandardPenguinControl;
import com.funkydonkies.core.App;
import com.funkydonkies.exceptions.BadDynamicTypeException;
import com.funkydonkies.factories.PenguinFactory;
import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import com.jme3.bullet.collision.PhysicsCollisionEvent;
import com.jme3.bullet.collision.PhysicsCollisionListener;
import com.jme3.bullet.collision.shapes.SphereCollisionShape;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.Control;
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
				penguin.getControl(StandardPenguinControl.class).setEnabled(false);
				
				GrowingSnowballControl SBControl = penguin.getParent()
						.getControl(GrowingSnowballControl.class);
				//Check if the penguin already has a snowballcontrol
				if (SBControl == null) {
					Sphere shape = new Sphere(20, 20, 1);
					Geometry snowball = new Geometry("snowball", shape);
					Material snowMaterial = new Material(app.getAssetManager(), 
							"Common/MatDefs/Misc/Unshaded.j3md");
					snowMaterial.setColor("Color", ColorRGBA.White);
					snowball.setMaterial(snowMaterial);
					
					Node parentNode = penguin.getParent();
					parentNode.attachChild(snowball);
					
					GrowingSnowballControl snowBallControl = new GrowingSnowballControl(
							new SphereCollisionShape(penguin.getWorldScale().x 
									* PenguinFactory.DEFAULT_RADIUS), 20f);
					penguinNode.addControl(snowBallControl);
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
					SBControl.setEnabled(false);
				}
			}
		}
	}
	
	
	@Override
	public void collision(PhysicsCollisionEvent event) {

	}

}
