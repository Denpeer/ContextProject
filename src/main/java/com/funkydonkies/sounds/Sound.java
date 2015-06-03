package com.funkydonkies.sounds;

/**
 * all sounds must extend this class. the play method should call the
 * appropriate method of the SoundPlayer class which will playInstance() the
 * sound.
 * 
 * @author Olivier Dikken
 *
 */
public interface Sound {
	/**
	 * calls the appropriate method of the SoundPlayer to play the sound.
	 * 
	 * @param sP
	 *            the SoundPlayer
	 */
	void play(SoundPlayer sP);
}
