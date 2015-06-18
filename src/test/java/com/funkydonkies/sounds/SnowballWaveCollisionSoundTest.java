package com.funkydonkies.sounds;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * tests the snowball wave collision sound class.
 * @author Olivier Dikken
 *
 */
public class SnowballWaveCollisionSoundTest {

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
		new SnowballWaveCollisionSound().play(soundPlayerMock);
		Mockito.verify(soundPlayerMock).snowballWaveCollisionSound();
	}
}
