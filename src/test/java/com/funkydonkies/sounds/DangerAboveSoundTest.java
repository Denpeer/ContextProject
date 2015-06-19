package com.funkydonkies.sounds;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * tests the danger above sound.
 * @author Olivier Dikken
 *
 */
public class DangerAboveSoundTest {

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
		new DangerAboveSound().play(soundPlayerMock);
		Mockito.verify(soundPlayerMock).dangerAboveSound();
	}

}
