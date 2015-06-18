package com.funkydonkies.sounds;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * test the bear hit sound class.
 * @author Olivier Dikken
 *
 */
public class BearHitSoundTest {

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
		new BearHitSound().play(soundPlayerMock);
		Mockito.verify(soundPlayerMock).bearHitSound();
	}

}
