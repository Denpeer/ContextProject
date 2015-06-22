package com.funkydonkies.sounds;

/**
 * sound to be triggered at new tier.
 * 
 * @author Olivier Dikken
 *
 */
public class ComboNewLevelVoiceSound implements Sound {

	@Override
	public void play(final SoundPlayer sP) {
		sP.comboNewLevelVoiceSound();
	}

}
