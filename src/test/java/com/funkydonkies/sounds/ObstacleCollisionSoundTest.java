package com.funkydonkies.sounds;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * Play the sound.
 * @author Olivier Dikken
 *
 */
public class ObstacleCollisionSoundTest {
	
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
		new ObstacleCollisionSound().play(soundPlayerMock);
		Mockito.verify(soundPlayerMock).obstacleCollisionSound();
	}

}
