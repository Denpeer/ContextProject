package com.funkydonkies.sounds;

/**
 * sound to be triggered at bear penguin collision.
 * 
 * @author Olivier Dikken
 *
 */
public class BearHitSound implements Sound {

	@Override
	public void play(final SoundPlayer sP) {
		sP.bearHitSound();
	}

}
