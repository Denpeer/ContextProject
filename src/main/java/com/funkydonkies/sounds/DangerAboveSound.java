package com.funkydonkies.sounds;

/**
 * sound to be triggered at spikey ball spawn.
 * 
 * @author Olivier Dikken
 *
 */
public class DangerAboveSound implements Sound {

	@Override
	public void play(final SoundPlayer sP) {
		sP.dangerAboveSound();
	}

}
