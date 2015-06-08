package com.funkydonkies.powerups;


import java.awt.Color;
import java.util.List;

import com.funkydonkies.controllers.GrowingSnowballControl;
import com.funkydonkies.controllers.StandardPenguinControl;
import com.funkydonkies.core.App;
import com.funkydonkies.exceptions.BadDynamicTypeException;
import com.funkydonkies.gamestates.DisabledState;
import com.funkydonkies.gamestates.PlayState;
import com.funkydonkies.geometrys.penguins.Snowball;
import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import com.jme3.bullet.collision.PhysicsCollisionEvent;
import com.jme3.bullet.collision.PhysicsCollisionListener;
import com.jme3.bullet.collision.shapes.SphereCollisionShape;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

public class SnowballPowerup extends DisabledState implements PhysicsCollisionListener {
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
				Vector3f speed = ((Node) penguin).getControl(StandardPenguinControl.class).getLinearVelocity();
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
					snowBallControl.setLinearVelocity(speed);
					penguin.setName("snowBallPenguin");
				} else {
					SBControl.setEnabled(true);
					SBControl.scaleBack();
				}
			}
		} else {
//			for (Spatial penguin : penguins) {
//				GrowingSnowballControl SBControl = penguin.getControl(GrowingSnowballControl.class);
//				if (SBControl != null) {
//					SBControl.scaleBack();
//					((Node) penguin).getControl(StandardPenguinControl.class).setEnabled(true);
//					penguin.setName("standardPenguin");
//					SBControl.setEnabled(false);
//				}
//			}
		}
	}
	
	@Override
	public void update(float tpf) {
		super.update(tpf);
	}
	
	
	@Override
	public void collision(PhysicsCollisionEvent event) {

	}

}
