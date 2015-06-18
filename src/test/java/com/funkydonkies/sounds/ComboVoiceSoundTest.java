package com.funkydonkies.sounds;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * test the combo voice sound.
 * @author Olivier Dikken
 *
 */
public class ComboVoiceSoundTest {

private SoundPlayer soundPlayerMock;
	
	/**
	 * mock a SoundPlayer class.
	 */
	@Before
	public void setUp() {
		soundPlayerMock = Mockito.mock(SoundPlayer.class);
	}

	/**
	 * call the play method.
	 */
	@Test
	public void testPlay() {
		new ComboVoiceSound().play(soundPlayerMock);
		Mockito.verify(soundPlayerMock).comboVoiceSound();
	}

}
