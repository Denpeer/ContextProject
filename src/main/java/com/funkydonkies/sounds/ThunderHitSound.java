package com.funkydonkies.sounds;

/**
 * sound to be triggered at target spawn.
 * 
 * @author Olivier Dikken
 *
 */
public class ThunderHitSound implements Sound {

	@Override
	public void play(final SoundPlayer sP) {
		sP.thunderHitSound();
	}

}
