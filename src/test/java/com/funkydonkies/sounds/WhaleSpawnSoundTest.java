package com.funkydonkies.sounds;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * tests the whale spawn class.
 * @author Olivier Dikken
 *
 */
public class WhaleSpawnSoundTest {

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
		new WhaleSpawnSound().play(soundPlayerMock);
		Mockito.verify(soundPlayerMock).whaleSpawnSound();
	}
}
