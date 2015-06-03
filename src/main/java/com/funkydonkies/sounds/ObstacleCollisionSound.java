package com.funkydonkies.sounds;

/**
 * sound to be triggered at obstacle collision.
 * 
 * @author Olivier Dikken
 *
 */
public class ObstacleCollisionSound implements Sound {

	@Override
	public void play(final SoundPlayer sP) {
		sP.obstacleCollisionSound();
	}

}
