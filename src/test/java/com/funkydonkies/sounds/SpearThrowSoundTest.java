package com.funkydonkies.sounds;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * tests the spear throw sound class.
 * @author Olivier Dikken
 *
 */
public class SpearThrowSoundTest {

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
		new SpearThrowSound().play(soundPlayerMock);
		Mockito.verify(soundPlayerMock).spearThrowSound();
	}

}
