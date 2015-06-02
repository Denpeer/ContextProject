package com.funkydonkies.sounds;

/**
 * sound to be triggered at penguin spawn.
 * @author Olivier Dikken
 *
 */
public class PenguinSpawnSound implements Sound {
	
	private SoundPlayer soundPlayer;
	
	/**
	 * Set the SoundPlayer.
	 * @param sP the soundPlayer
	 */
	public PenguinSpawnSound(final SoundPlayer sP) {
		soundPlayer = sP;
	}

	@Override
	public void play(final SoundPlayer sP) {
		sP.penguinSpawnSound();
	}

}
