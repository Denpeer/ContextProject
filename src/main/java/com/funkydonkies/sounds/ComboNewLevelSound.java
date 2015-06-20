package com.funkydonkies.sounds;

/**
 * sound to be triggered at combo new level. combo new level is reached when the combo count passes
 * a certain threshold.
 * 
 * @author Olivier Dikken
 *
 */
public class ComboNewLevelSound implements Sound {

	@Override
	public void play(final SoundPlayer sP) {
		sP.comboNewLevelSound();
	}

}
