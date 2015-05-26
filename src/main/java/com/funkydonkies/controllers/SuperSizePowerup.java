package com.funkydonkies.controllers;

import java.util.Iterator;
import java.util.List;

import com.funkydonkies.exceptions.BadDynamicTypeException;
import com.funkydonkies.w4v3.App;
import com.funkydonkies.w4v3.Ball;
import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.bullet.collision.shapes.SphereCollisionShape;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;

public class SuperSizePowerup extends AbstractAppState {
	private static final float STANDARD_SCALEUP = 2f;
	private static final float STANDARD_RADIUS = 0.5f;
	private static final float TIME_TO_SCALE = 1f;
	private float counter;
	
	private App app;
	
	@Override
	public final void initialize(final AppStateManager sManager,
			final Application appl) {
		super.initialize(sManager, appl);
		
		if (appl instanceof App) {
			this.app = (App) appl;
		} else {
			throw new BadDynamicTypeException();
		}
		setEnabled(false);
	}
	
	@Override
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
		Spatial ballNode = app.getBallNode();
		List<Spatial> balls;
		balls = ((Node) ballNode).getChildren();
		Iterator<Spatial> ballIterator = balls.iterator();
		
		if (enabled) {
			// Scale up all existing balls
			while (ballIterator.hasNext()) {
				Spatial ball = ballIterator.next().scale(STANDARD_SCALEUP);
				float radius = ((SphereCollisionShape) ball.getControl(BallController.class).getCollisionShape()).getRadius();
				ball.getControl(BallController.class).setCollisionShape(new SphereCollisionShape(radius*STANDARD_SCALEUP));//
			}
		} else {
			// Scale down balls to default
			while (ballIterator.hasNext()) {
				Spatial ball = ballIterator.next();
				ball.scale(1 / ball.getWorldScale().x);
				ball.getControl(BallController.class).setCollisionShape(new SphereCollisionShape(STANDARD_RADIUS));//
			}
		}
	}
	
	public void toggleEnabled() {
		if (isEnabled()) {
			setEnabled(false);
		} else {
			setEnabled(true);
		}
	}
	
	@Override
	public void update(float tpf) {
		super.update(tpf);

	}
}
