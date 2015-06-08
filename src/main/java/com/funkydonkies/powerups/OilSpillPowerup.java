package com.funkydonkies.powerups;

import com.funkydonkies.controllers.OilPenguinControl;
import com.funkydonkies.controllers.StandardPenguinControl;
import com.funkydonkies.core.App;
import com.funkydonkies.factories.PenguinFactory;
import com.funkydonkies.gamestates.CurveState;
import com.funkydonkies.gamestates.DisabledState;
import com.funkydonkies.gamestates.PlayState;
import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import com.jme3.bullet.collision.shapes.SphereCollisionShape;
import com.jme3.scene.Node;

public class OilSpillPowerup extends DisabledState {
	private static final int START_X = 200;
	private static final int END_X = 250;
	private static int startx = START_X;
	private static int endx = END_X;
	public static final float DEFAULT_RADIUS = 4f;
	private AppStateManager sm;
	private Application app;
	private Node node;
	private boolean enabled = false;
	private boolean performEnabled = false;
	private OilPenguinControl controller;

	@Override
	public void initialize(AppStateManager stateManager, Application app) {
		super.initialize(stateManager, app);
		this.sm = stateManager;
		this.app = app;
		controller = new OilPenguinControl(
				new SphereCollisionShape(DEFAULT_RADIUS), 1f);
		controller.setRestitution(1);
		sm.getState(PlayState.class).getPhysicsSpace().add(controller);
		node = (Node) ((App) app).getPenguinNode().getChild(PenguinFactory.STANDARD_PENGUIN_NAME);
		
	}

	@Override
	public void setEnabled(boolean bool) {
		super.setEnabled(bool);
		this.enabled = bool;
		performEnabled = true;
	}

	public void setRange(final int sx, final int ex) {
		this.startx = sx;
		this.endx = ex;
	}

	public void setOilSpilt(boolean isenabled, int sx, int ex) {
		if (isenabled) {
			System.out.println("Oil split from " + startx + "x till " + endx
					+ "x. Take care! Penguins will be slowed upon contact!");
		} else {
			System.out.println("Cleared oil spill!");
		}
		startx = sx;
		endx = ex;
	}

	public static int getOilStartX() {
		return startx;
	}

	public static int getOilEndX() {
		return endx;
	}

	@Override
	public void update(float tpf) {
		super.update(tpf);
		if (performEnabled) {
			performEnabled = false;
			node.addControl(controller);
			controller.init();
			setOilSpilt(enabled, startx, endx);
			if (enabled) {
				node.getControl(StandardPenguinControl.class).setEnabled(false);
				node.getControl(OilPenguinControl.class).setEnabled(true);
			} else {
				node.getControl(StandardPenguinControl.class).setEnabled(true);
				node.getControl(OilPenguinControl.class).setEnabled(false);
			}
		}
	}

}
