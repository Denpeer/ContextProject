package com.funkydonkies.sounds;

/**
 * sound to be triggered at target collision.
 * 
 * @author Olivier Dikken
 *
 */
public class TargetCollisionSound implements Sound {

	@Override
	public void play(final SoundPlayer sP) {
		sP.targetCollisionSound();
	}

}
