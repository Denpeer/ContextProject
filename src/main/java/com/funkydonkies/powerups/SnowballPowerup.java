package com.funkydonkies.powerups;

import java.awt.Color;
import java.util.List;

import com.funkydonkies.controllers.GrowingSnowballControl;
import com.funkydonkies.controllers.PenguinControl;
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

/**
 * Makes Penguin invincible.
 */
public class SnowballPowerup extends DisabledState implements PhysicsCollisionListener {
	private App app;
	private AppStateManager sManager;

	private boolean enablePowerupNextCycle = false;
	public static final String SNOW_PENGUIN_NAME = "snowBallPenguin";

	@Override
	public void initialize(final AppStateManager stateManager, final Application appl) {
		super.initialize(stateManager, app);
		if (appl instanceof App) {
			app = (App) appl;
		} else {
			throw new BadDynamicTypeException();
		}

		sManager = stateManager;

	}

	@Override
	public void setEnabled(final boolean enabled) {
		super.setEnabled(enabled);
		enablePowerupNextCycle = true;
	}

	@Override
	public void update(final float tpf) {
		super.update(tpf);
		if (enablePowerupNextCycle) {
			activate();
			enablePowerupNextCycle = false;
		}
	}

	/**
	 * Activates this power-up.
	 */
	public void activate() {
		// TODO clean...
		final Node penguinNode = app.getPenguinNode();
		final List<Spatial> penguins = penguinNode.getChildren();
		for (Spatial penguin : penguins) {
			final Vector3f speed = ((Node) penguin).getControl(PenguinControl.class).getLinearVelocity();
			((Node) penguin).getControl(PenguinControl.class).setEnabled(false);

			final GrowingSnowballControl sBControl = penguin
					.getControl(GrowingSnowballControl.class);
			// Check if the penguin already has a snowballcontrol
			if (sBControl == null) {
				final Snowball sball = new Snowball(app.getAssetManager(), 10, 0, Color.white, 0,
						Color.white, 360);
				sball.setLocalTranslation(-5, -5, 0);
				((Node) penguin).attachChild(sball);
				final GrowingSnowballControl snowBallControl = new GrowingSnowballControl(
						new SphereCollisionShape(5), 1f, sManager);
				penguin.addControl(snowBallControl);
				sManager.getState(PlayState.class).getPhysicsSpace().add(snowBallControl);
				snowBallControl.setLinearVelocity(speed);
				penguin.setName(SNOW_PENGUIN_NAME);
			} else {
				sBControl.setEnabled(true);
				sBControl.scaleBack();
			}
		}
	}

	@Override
	public void collision(final PhysicsCollisionEvent event) {

	}

}
