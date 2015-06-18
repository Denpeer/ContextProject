package com.funkydonkies.sounds;

/**
 * sound to be triggered at target spawn.
 * 
 * @author Olivier Dikken
 *
 */
public class SpearThrowSound implements Sound {

	@Override
	public void play(final SoundPlayer sP) {
		sP.spearThrowSound();
	}

}
