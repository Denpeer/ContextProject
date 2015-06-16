package com.funkydonkies.sounds;

/**
 * sound to be triggered at penguin saved. penguin saved means penguin passed
 * along the entire wave without hitting any obstacles.
 * 
 * @author Olivier Dikken
 *
 */
public class PenguinSavedSound implements Sound {

	@Override
	public void play(final SoundPlayer sP) {
		sP.penguinSavedSound();
	}

}
