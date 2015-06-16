package com.funkydonkies.sounds;

/**
 * sound to be triggered at penguin spawn.
 * 
 * @author Olivier Dikken
 *
 */
public class PenguinSpawnSound implements Sound {

	@Override
	public void play(final SoundPlayer sP) {
		sP.penguinSpawnSound();
	}

}
