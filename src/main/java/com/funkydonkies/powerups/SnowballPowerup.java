package com.funkydonkies.powerups;

import java.util.List;

import com.funkydonkies.controllers.GrowingSnowballControl;
import com.funkydonkies.controllers.PenguinControl;
import com.funkydonkies.core.App;
import com.funkydonkies.exceptions.BadDynamicTypeException;
import com.funkydonkies.gamestates.DisabledState;
import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import com.jme3.bullet.collision.PhysicsCollisionEvent;
import com.jme3.bullet.collision.PhysicsCollisionListener;
import com.jme3.bullet.collision.shapes.SphereCollisionShape;
import com.jme3.material.Material;
import com.jme3.material.RenderState.BlendMode;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue.Bucket;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Sphere;

/**
 * Makes Penguin invincible.
 */
public class SnowballPowerup extends DisabledState implements PhysicsCollisionListener {
	private App app;
	private AppStateManager sManager;

	private boolean enablePowerupNextCycle = false;
	public static final String SNOW_PENGUIN_NAME = "snowBallPenguin";
	private static final int SNOWBALL_SAMPLES = 30;
	private static final float INITIAL_SNOWBALL_RADIUS = 5;

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
			addSnowballControl(penguin);
		}
	}

	/**
	 * Adds a snowballcontrol to the penguin given.
	 * 
	 * @param penguin
	 *            Spatial to attach control to.
	 */
	public void addSnowballControl(final Spatial penguin) {
		final Vector3f speed = ((Node) penguin).getControl(PenguinControl.class)
				.getLinearVelocity();
		((Node) penguin).getControl(PenguinControl.class).setEnabled(false);

		final GrowingSnowballControl sBControl = penguin.getControl(GrowingSnowballControl.class);
		// Check if the penguin already has a snowballcontrol
		if (sBControl == null) {
			((Node) penguin).attachChild(createSnowBall());

			final GrowingSnowballControl snowBallControl = new GrowingSnowballControl(
					new SphereCollisionShape(5), 1f, sManager);
			snowBallControl.init();
			penguin.addControl(snowBallControl);
			snowBallControl.setLinearVelocity(speed);
			penguin.setName(SNOW_PENGUIN_NAME);
		}
	}

	@Override
	public void collision(final PhysicsCollisionEvent event) {

	}

	/**
	 * Creates a snowball spatial and sets the material.
	 * 
	 * @return snowBall spatial.
	 */
	private Spatial createSnowBall() {
		final Sphere snowballMesh = new Sphere(SNOWBALL_SAMPLES, SNOWBALL_SAMPLES,
				INITIAL_SNOWBALL_RADIUS);
		final Geometry snowBall = new Geometry("snowball", snowballMesh);
		snowBall.setMaterial(createSnowMaterial());
		snowBall.setQueueBucket(Bucket.Transparent);
		return snowBall;
	}

	/**
	 * Creates the material for the snowball.
	 * 
	 * @return snow Material.
	 */
	public Material createSnowMaterial() {
		final Material snow = app.getAssetManager().loadMaterial("Materials/ice.j3m");
		snow.getAdditionalRenderState().setBlendMode(BlendMode.Alpha);
		snow.setBoolean("UseAlpha", true);
		return snow;
	}

}
