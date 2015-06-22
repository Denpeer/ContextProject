package com.funkydonkies.sounds;

/**
 * sound to be triggered at powerup start.
 * 
 * @author Olivier Dikken
 *
 */
public class PowerupVoiceSound implements Sound {

	@Override
	public void play(final SoundPlayer sP) {
		sP.powerupVoiceSound();
	}

}
