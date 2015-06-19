package com.funkydonkies.sounds;

/**
 * sound to be triggered at target spawn.
 * 
 * @author Olivier Dikken
 *
 */
public class PowerupInverseSound implements Sound {

	@Override
	public void play(final SoundPlayer sP) {
		sP.powerupInverseSound();
	}

}
