package com.funkydonkies.sounds;

/**
 * sound to be triggered at speed power up started.
 * 
 * @author Olivier Dikken
 *
 */
public class PowerupSpeedSound implements Sound {

	@Override
	public void play(final SoundPlayer sP) {
		sP.powerupSpeedSound();
	}

}
