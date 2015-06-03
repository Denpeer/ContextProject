package com.funkydonkies.sounds;

/**
 * sound to be triggered at obstacle spawn.
 * 
 * @author Olivier Dikken
 *
 */
public class ObstacleSpawnSound implements Sound {

	@Override
	public void play(final SoundPlayer sP) {
		sP.obstacleSpawnSound();
	}

}
