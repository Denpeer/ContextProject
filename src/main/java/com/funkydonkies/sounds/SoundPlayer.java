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
	//TODO: put new sounds into project and update the paths!
	private static final Path PENGUIN_SPAWN_SOUND_PATH = Paths
			.get("/Sounds/PenguinSpawn_VEH1_Cutted_Sounds_-_021.wav");
	private static final Path PENGUIN_SAVED_SOUND_PATH = Paths
			.get("/Sounds/PenguinSpawn_VEH1_Cutted_Sounds_-_021.wav");
	private static final Path TARGET_SPAWN_SOUND_PATH = Paths
			.get("/Sounds/PenguinSpawn_VEH1_Cutted_Sounds_-_021.wav");
	private static final Path OBSTACLE_SPAWN_SOUND_PATH = Paths
			.get("/Sounds/PenguinSpawn_VEH1_Cutted_Sounds_-_021.wav");
	private static final Path TARGET_COLLISION_SOUND_PATH = Paths
			.get("/Sounds/PenguinSpawn_VEH1_Cutted_Sounds_-_021.wav");
	private static final Path OBSTACLE_COLLISION_SOUND_PATH = Paths
			.get("/Sounds/PenguinSpawn_VEH1_Cutted_Sounds_-_021.wav");
	private static final Path COMBO_NEW_LEVEL_SOUND_PATH = Paths
			.get("/Sounds/PenguinSpawn_VEH1_Cutted_Sounds_-_021.wav");
	private static final Path COMBO_LOST_SOUND_PATH = Paths
			.get("/Sounds/PenguinSpawn_VEH1_Cutted_Sounds_-_021.wav");
	
	private static final String DOUBLE_BACKSLASH = "\\";
	private static final String FRONT_SLASH = "/";

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
	 * set the app and assetManager and call initSounds to prepare the sounds
	 * for launch.
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
		System.out.println("***** LOADING GAME SOUNDS ***** =>");
		
		penguinSpawnAudioNode = new AudioNode(assetManager,
				PENGUIN_SPAWN_SOUND_PATH.toString().replace(DOUBLE_BACKSLASH, FRONT_SLASH), false);
		penguinSavedAudioNode = new AudioNode(assetManager,
				PENGUIN_SAVED_SOUND_PATH.toString().replace(DOUBLE_BACKSLASH, FRONT_SLASH), false);
		targetSpawnAudioNode = new AudioNode(assetManager,
				TARGET_SPAWN_SOUND_PATH.toString().replace(DOUBLE_BACKSLASH, FRONT_SLASH), false);
		obstacleSpawnAudioNode = new AudioNode(assetManager,
				OBSTACLE_SPAWN_SOUND_PATH.toString().replace(DOUBLE_BACKSLASH, FRONT_SLASH), false);
		targetCollisionAudioNode = new AudioNode(assetManager,
				TARGET_COLLISION_SOUND_PATH.toString().replace(DOUBLE_BACKSLASH, FRONT_SLASH), false);
		obstacleCollisionAudioNode = new AudioNode(assetManager,
				OBSTACLE_COLLISION_SOUND_PATH.toString().replace(DOUBLE_BACKSLASH, FRONT_SLASH), false);
		comboNewLevelAudioNode = new AudioNode(assetManager,
				COMBO_NEW_LEVEL_SOUND_PATH.toString().replace(DOUBLE_BACKSLASH, FRONT_SLASH), false);
		comboLostAudioNode = new AudioNode(assetManager,
				COMBO_LOST_SOUND_PATH.toString().replace(DOUBLE_BACKSLASH, FRONT_SLASH), false);
		
		initSingleSound(penguinSpawnAudioNode, PENGUIN_SPAWN_SOUND_PATH);
		initSingleSound(penguinSavedAudioNode, PENGUIN_SAVED_SOUND_PATH);
		initSingleSound(targetSpawnAudioNode, TARGET_SPAWN_SOUND_PATH);
		initSingleSound(obstacleSpawnAudioNode, OBSTACLE_SPAWN_SOUND_PATH);
		initSingleSound(targetCollisionAudioNode, TARGET_COLLISION_SOUND_PATH);
		initSingleSound(obstacleCollisionAudioNode, OBSTACLE_COLLISION_SOUND_PATH);
		initSingleSound(comboNewLevelAudioNode, COMBO_NEW_LEVEL_SOUND_PATH);
		initSingleSound(comboLostAudioNode, COMBO_LOST_SOUND_PATH);
		
		System.out.println("<= ***** DONE LOADING GAME SOUNDS *****");
	}

	/**
	 * initialize an audionode to play the specified sound.
	 * 
	 * @param audioNode
	 *            name of the audionode the sound will be attached to.
	 * @param path
	 *            of the sound file (under assets/Sounds).
	 */
	private void initSingleSound(final AudioNode audioNode, final Path path) {
		audioNode.setPositional(false);
		audioNode.setLooping(false);
		audioNode.setVolume(1);
		app.getRootNode().attachChild(audioNode);
		System.out.println("	=> " + path.toString().replace(DOUBLE_BACKSLASH, FRONT_SLASH)
				+ " sound initialized.");
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
