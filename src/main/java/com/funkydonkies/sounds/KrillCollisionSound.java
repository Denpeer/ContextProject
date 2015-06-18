package com.funkydonkies.sounds;

/**
 * sound to be triggered at krill / penguin collision.
 * 
 * @author Olivier Dikken
 *
 */
public class KrillCollisionSound implements Sound {

	@Override
	public void play(final SoundPlayer sP) {
		sP.krillCollisionSound();
	}

}
