package powerups;

import java.util.Iterator;
import java.util.List;

import com.funkydonkies.controllers.BallController;
import com.funkydonkies.gamestates.PlayState;
import com.funkydonkies.w4v3.Ball;
import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import com.jme3.bullet.collision.shapes.SphereCollisionShape;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

/**
 * Defines the behavior for sizing up the balls. 
 */
public class SuperSizePowerup extends AbstractPowerup {
	private static final float STANDARD_SCALEUP = 2f;
	private AppStateManager stateManager;
	
	/**
	 * Initializes enables to false.
	 * @see com.jme3.app.state.AbstractAppState#initialize(com.jme3.app.state.AppStateManager, 
	 * com.jme3.app.Application)
	 */
	@Override
	public final void initialize(final AppStateManager sManager,
			final Application appl) {
		super.initialize(sManager, appl);
		stateManager = sManager;
	}
	
	/** 
	 * When called scales up or down the balls in the scene.
	 * @see com.jme3.app.state.AbstractAppState#setEnabled(boolean)
	 */
	@Override
	public void setEnabled(final boolean enabled) {
		super.setEnabled(enabled);
		if (enabled) {
			scaleUpAll();
		} else {
			scaleDownAll();
		}
	}
	
	/**
	 * Method for scaling up all balls in the scene.
	 */
	public void scaleUpAll() {
		final Spatial ballNode = stateManager.getState(PlayState.class).getBallNode();
		List<Spatial> balls;
		balls = ((Node) ballNode).getChildren();
		final Iterator<Spatial> ballIterator = balls.iterator();
		// Scale up all existing balls
		while (ballIterator.hasNext()) {
			final Spatial ball = ballIterator.next().scale(STANDARD_SCALEUP);
			final float radius = ((SphereCollisionShape) 
					ball.getControl(BallController.class).getCollisionShape()).getRadius();
			ball.getControl(BallController.class).setCollisionShape(
					new SphereCollisionShape(radius * STANDARD_SCALEUP));
		}
	} 
	
	/**
	 * Method for scaling down all the balls in the scene.
	 */
	public void scaleDownAll() {
		final Spatial ballNode = stateManager.getState(PlayState.class).getBallNode();
		List<Spatial> balls;
		balls = ((Node) ballNode).getChildren();
		final Iterator<Spatial> ballIterator = balls.iterator();
		while (ballIterator.hasNext()) {
			final Spatial ball = ballIterator.next();
			ball.scale(1 / ball.getWorldScale().x);
			ball.getControl(BallController.class).setCollisionShape(
					new SphereCollisionShape(Ball.DEFAULT_RADIUS));
		}
	}
	
	
	/**
	 * Toggles enables by checking the enabled boolean and calling setEnabled.
	 */
	public void toggleEnabled() {
		if (isEnabled()) {
			setEnabled(false);
		} else {
			setEnabled(true);
		}
	}
	
	@Override
	public void update(final float tpf) {
		super.update(tpf);
	}
}
