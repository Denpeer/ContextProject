package com.funkydonkies.sounds;

/**
 * sound to be triggered at snowball wave collision.
 * 
 * @author Olivier Dikken
 *
 */
public class SnowballWaveCollisionSound implements Sound {

	@Override
	public void play(final SoundPlayer sP) {
		sP.snowballWaveCollisionSound();
	}

}
