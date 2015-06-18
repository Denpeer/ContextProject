package com.funkydonkies.sounds;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * tests the krill collision sound.
 * @author Olivier Dikken
 *
 */
public class KrillCollisionSoundTest {

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
		new KrillCollisionSound().play(soundPlayerMock);
		Mockito.verify(soundPlayerMock).krillCollisionSound();
	}

}
