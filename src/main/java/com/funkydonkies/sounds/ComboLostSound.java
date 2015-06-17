package com.funkydonkies.sounds;

/**
 * sound to be triggered at combo lost. combo is lost when a penguin collides
 * with an obstacle.
 * 
 * @author Olivier Dikken
 *
 */
public class ComboLostSound implements Sound {

	@Override
	public void play(final SoundPlayer sP) {
		sP.comboLostSound();
	}

}
