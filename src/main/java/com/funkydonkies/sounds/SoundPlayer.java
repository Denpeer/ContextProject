package com.funkydonkies.sounds;

import java.nio.file.Path;
import java.nio.file.Paths;

import com.funkydonkies.exceptions.BadDynamicTypeException;
import com.funkydonkies.w4v3.App;
import com.jme3.app.Application;
import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioNode;

/**
 * plays the sounds. Sound's play() method calls the appropriate method here to
 * play the specified sound. Sounds are queued in the SoundState class.
 * 
 * @author Olivier Dikken
 *
 */
public class SoundPlayer {

	private final Path PENGUIN_SPAWN_SOUND_PATH = Paths
			.get("/Sounds/PenguinSpawn_VEH1_Cutted_Sounds_-_021.wav");

	private AudioNode penguinSpawnAudioNode;

	private App app;
	private AssetManager assetManager;

	/**
	 * set the app and assetManager and call initSounds to prepare the sounds
	 * for launch.
	 * 
	 * @param appl the application
	 */
	public SoundPlayer(final Application appl) {
		if (appl instanceof App) {
			this.app = (App) appl;
		} else {
			throw new BadDynamicTypeException();
		}
		this.assetManager = appl.getAssetManager();
		initSounds();
	}

	/**
	 * 
	 */
	private void initSounds() {
		//initSingleSound(penguinSpawnAudioNode, PENGUIN_SPAWN_SOUND_PATH);
		penguinSpawnAudioNode = new AudioNode(assetManager, PENGUIN_SPAWN_SOUND_PATH.toString().replace("\\",
				"/"), false);
		penguinSpawnAudioNode.setPositional(false);
		penguinSpawnAudioNode.setLooping(false);
		penguinSpawnAudioNode.setVolume(1);
		app.getRootNode().attachChild(penguinSpawnAudioNode);
	}

	/**
	 * initialize an audionode to play the specified sound.
	 * 
	 * @param audioNode
	 *            name of the audionode the sound will be attached to.
	 * @param path
	 *            of the sound file (under assets/Sounds).
	 */
	private void initSingleSound(AudioNode audioNode, final Path path) {
//		audioNode = new AudioNode(assetManager, path.toString().replace("\\",
//				"/"), false);
//		audioNode.setPositional(false);
//		audioNode.setLooping(false);
//		audioNode.setVolume(1);
//		app.getRootNode().attachChild(audioNode);
//		System.out.println("=> " + path.toString().replace("\\",
//				"/") + " sound initialized.");
	}

	/**
	 * sound of penguin spawning.
	 */
	public void penguinSpawnSound() {
		penguinSpawnAudioNode.playInstance();
	}

	public void penguinSavedSound() {

	}

	public void targetSpawnSound() {

	}

	public void obstacleSpawnSound() {

	}

	public void targetCollisionSound() {

	}

	public void obstacleCollisionSound() {

	}

	public void comboNewLevelSound() {

	}

	public void comboLostSound() {

	}
}
