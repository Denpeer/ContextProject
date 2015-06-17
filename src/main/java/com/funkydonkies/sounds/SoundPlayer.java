package com.funkydonkies.sounds;

import com.funkydonkies.core.App;
import com.funkydonkies.exceptions.BadDynamicTypeException;
import com.jme3.app.Application;
import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioNode;

/**
 * plays the sounds. Sound's play() method calls the appropriate method here to play the specified
 * sound. Sounds are queued in the SoundState class.
 * 
 * @author Olivier Dikken
 *
 */
public class SoundPlayer {
	// TODO: put new sounds into project and update the paths!
	private static final String PENGUIN_SPAWN_SOUND_PATH = "/Sounds/PenguinSpawn_VEH1_Cutted_Sounds_-_021.wav";
	private static final String PENGUIN_SAVED_SOUND_PATH = PENGUIN_SPAWN_SOUND_PATH;
	private static final String TARGET_SPAWN_SOUND_PATH = PENGUIN_SPAWN_SOUND_PATH;
	private static final String OBSTACLE_SPAWN_SOUND_PATH = PENGUIN_SPAWN_SOUND_PATH;
	private static final String TARGET_COLLISION_SOUND_PATH = PENGUIN_SPAWN_SOUND_PATH;
	private static final String OBSTACLE_COLLISION_SOUND_PATH = PENGUIN_SPAWN_SOUND_PATH;
	private static final String COMBO_NEW_LEVEL_SOUND_PATH = PENGUIN_SPAWN_SOUND_PATH;
	private static final String COMBO_LOST_SOUND_PATH = PENGUIN_SPAWN_SOUND_PATH;

	private AudioNode penguinSpawnAudioNode;
	private AudioNode penguinSavedAudioNode;
	private AudioNode targetSpawnAudioNode;
	private AudioNode obstacleSpawnAudioNode;
	private AudioNode targetCollisionAudioNode;
	private AudioNode obstacleCollisionAudioNode;
	private AudioNode comboNewLevelAudioNode;
	private AudioNode comboLostAudioNode;

	private App app;
	private AssetManager assetManager;

	/**
	 * set the app and assetManager and call initSounds to prepare the sounds for launch.
	 * 
	 * @param appl
	 *            the application
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
		penguinSpawnAudioNode = new AudioNode(assetManager, PENGUIN_SPAWN_SOUND_PATH, false);
		penguinSavedAudioNode = new AudioNode(assetManager, PENGUIN_SAVED_SOUND_PATH, false);
		targetSpawnAudioNode = new AudioNode(assetManager, TARGET_SPAWN_SOUND_PATH, false);
		obstacleSpawnAudioNode = new AudioNode(assetManager, OBSTACLE_SPAWN_SOUND_PATH, false);
		targetCollisionAudioNode = new AudioNode(assetManager, TARGET_COLLISION_SOUND_PATH, false);
		obstacleCollisionAudioNode = new AudioNode(assetManager, OBSTACLE_COLLISION_SOUND_PATH,
				false);
		comboNewLevelAudioNode = new AudioNode(assetManager, COMBO_NEW_LEVEL_SOUND_PATH, false);
		comboLostAudioNode = new AudioNode(assetManager, COMBO_LOST_SOUND_PATH, false);

		initSingleSound(penguinSpawnAudioNode, PENGUIN_SPAWN_SOUND_PATH);
		initSingleSound(penguinSavedAudioNode, PENGUIN_SAVED_SOUND_PATH);
		initSingleSound(targetSpawnAudioNode, TARGET_SPAWN_SOUND_PATH);
		initSingleSound(obstacleSpawnAudioNode, OBSTACLE_SPAWN_SOUND_PATH);
		initSingleSound(targetCollisionAudioNode, TARGET_COLLISION_SOUND_PATH);
		initSingleSound(obstacleCollisionAudioNode, OBSTACLE_COLLISION_SOUND_PATH);
		initSingleSound(comboNewLevelAudioNode, COMBO_NEW_LEVEL_SOUND_PATH);
		initSingleSound(comboLostAudioNode, COMBO_LOST_SOUND_PATH);
	}

	/**
	 * initialize an audionode to play the specified sound.
	 * 
	 * @param audioNode
	 *            name of the audionode the sound will be attached to.
	 * @param path
	 *            of the sound file (under assets/Sounds).
	 */
	private void initSingleSound(final AudioNode audioNode, final String path) {
		audioNode.setPositional(false);
		audioNode.setLooping(false);
		audioNode.setVolume(1);
		app.getRootNode().attachChild(audioNode);
	}

	/**
	 * sound of penguin spawning.
	 */
	public void penguinSpawnSound() {
		penguinSpawnAudioNode.playInstance();
	}

	/**
	 * sound of penguin saved.
	 */
	public void penguinSavedSound() {
		penguinSavedAudioNode.playInstance();
	}

	/**
	 * sound of target spawning.
	 */
	public void targetSpawnSound() {
		targetSpawnAudioNode.playInstance();
	}

	/**
	 * sound of obstacle spawning.
	 */
	public void obstacleSpawnSound() {
		obstacleSpawnAudioNode.playInstance();
	}

	/**
	 * sound of ball/target collision.
	 */
	public void targetCollisionSound() {
		targetCollisionAudioNode.playInstance();
	}

	/**
	 * sound of ball/obstacle collision.
	 */
	public void obstacleCollisionSound() {
		obstacleCollisionAudioNode.playInstance();
	}

	/**
	 * sound of new combo level.
	 */
	public void comboNewLevelSound() {
		comboNewLevelAudioNode.playInstance();
	}

	/**
	 * sound of combo lost.
	 */
	public void comboLostSound() {
		comboLostAudioNode.playInstance();
	}
}
