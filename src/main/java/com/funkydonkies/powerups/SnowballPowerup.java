package com.funkydonkies.powerups;


import java.awt.Color;
import java.util.List;

import com.funkydonkies.controllers.StandardPenguinControl;
import com.funkydonkies.core.App;
import com.funkydonkies.exceptions.BadDynamicTypeException;
import com.funkydonkies.gamestates.PlayState;
import com.funkydonkies.geometrys.penguins.Snowball;
import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import com.jme3.bullet.collision.PhysicsCollisionEvent;
import com.jme3.bullet.collision.PhysicsCollisionListener;
import com.jme3.bullet.collision.shapes.SphereCollisionShape;
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
				((Node) penguin).getControl(StandardPenguinControl.class).setEnabled(false);
				
				GrowingSnowballControl SBControl = penguin.getControl(GrowingSnowballControl.class);
				//Check if the penguin already has a snowballcontrol
				if (SBControl == null) {
					Snowball sball = new Snowball(app.getAssetManager(), 10
							, 0,Color.white,0,Color.white,360);
					sball.setLocalTranslation(-5, -5, 0);
					((Node) penguin).attachChild(sball);
					
					GrowingSnowballControl snowBallControl = new GrowingSnowballControl(
							new SphereCollisionShape(5), 1f);
					penguin.addControl(snowBallControl);
					sManager.getState(PlayState.class).getPhysicsSpace().add(snowBallControl);
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
