package com.funkydonkies.sounds;

/**
 * sound of combo voice.
 * 
 * @author Olivier Dikken
 *
 */
public class ComboVoiceSound implements Sound {

	@Override
	public void play(final SoundPlayer sP) {
		sP.comboVoiceSound();
	}

}
