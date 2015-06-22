package com.funkydonkies.sounds;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * tests the thunder hit sound class.
 * @author Olivier Dikken
 *
 */
public class ThunderHitSoundTest {

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
		new ThunderHitSound().play(soundPlayerMock);
		Mockito.verify(soundPlayerMock).thunderHitSound();
	}

}
