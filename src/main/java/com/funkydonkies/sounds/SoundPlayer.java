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
	private static final String PENGUIN_SPAWN_SOUND_PATH = "/Sounds/241809__suntemple__magic-sfx-for-games.wav";
	private static final String KRILL_COLLISION_SOUND_PATH = "/Sounds/56229__pera__3beeps.wav";
	private static final String OBSTACLE_SPAWN_SOUND_PATH = "/Sounds/186896__mrmacross__negativebuzz.wav";
	private static final String TARGET_COLLISION_SOUND_PATH = "/Sounds/263133__pan14__tone-beep.wav";
	private static final String OBSTACLE_COLLISION_SOUND_PATH = "/Sounds/87535__flasher21__splat.wav";
	private static final String COMBO_NEW_LEVEL_SOUND_PATH = "/Sounds/126422__cabeeno-rossley__level-up.wav";
	private static final String COMBO_VOICE_SOUND_PATH = "/Sounds/93370__corsica-s__combo.wav";
	private static final String COMBO_LOST_SOUND_PATH = "/Sounds/135831__davidbain__end-game-fail.wav";
	private static final String POWERUP_INVERSE_SOUND_PATH = "/Sounds/48168__larsyyy__alien.wav";
	private static final String POWERUP_INVERSE_END_SOUND_PATH = "/Sounds/267561__alienxxx__beep-sequence-10.wav";
	private static final String POWERUP_SPEED_SOUND_PATH = "/Sounds/257225__javierzumer__8bit-powerup.wav";
	private static final String POWERUP_SPEED_END_SOUND_PATH = "/Sounds/257225__javierzumer__8bit-powerup_reversed.wav";
	private static final String SPEAR_THROW_SOUND_PATH = "/Sounds/195937__michimuc2__short-wind-noise.wav";
	private static final String THUNDER_HIT_SOUND_PATH = "/Sounds/34169__glaneur-de-sons__electric-wire-02.wav";
	private static final String WHALE_SPAWN_SOUND_PATH = "/Sounds/8611__hanstimm__tune606467712448.wav";
	private static final String SNOWBALL_WAVE_COLLISION_SOUND_PATH = "/Sounds/164585__adam-n__thump-1.wav";
	private static final String BEAR_HIT_SOUND_PATH = "/Sounds/144003__arrigd__zombie-roar-7.wav";
	private static final String DANGER_ABOVE_SOUND_PATH = "/Sounds/danger_from_above.wav";
	private static final String POWERUP_VOICE_SOUND_PATH = "/Sounds/powerup_voice.wav";

	private AudioNode penguinSpawnAudioNode;
	private AudioNode krillCollisionAudioNode;
	private AudioNode obstacleSpawnAudioNode;
	private AudioNode targetCollisionAudioNode;
	private AudioNode obstacleCollisionAudioNode;
	private AudioNode comboNewLevelAudioNode;
	private AudioNode comboLostAudioNode;
	private AudioNode powerupInverseAudioNode;
	private AudioNode powerupInverseEndAudioNode;
	private AudioNode comboVoiceAudioNode;
	private AudioNode spearThrowAudioNode;
	private AudioNode thunderHitAudioNode;
	private AudioNode whaleSpawnAudioNode;
	private AudioNode snowballWaveCollisionAudioNode;
	private AudioNode bearHitAudioNode;
	private AudioNode dangerAboveAudioNode;
	private AudioNode powerupSpeedAudioNode;
	private AudioNode powerupSpeedEndAudioNode;
	private AudioNode powerupVoiceAudioNode;

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
		krillCollisionAudioNode = new AudioNode(assetManager, KRILL_COLLISION_SOUND_PATH, false);
		obstacleSpawnAudioNode = new AudioNode(assetManager, OBSTACLE_SPAWN_SOUND_PATH, false);
		targetCollisionAudioNode = new AudioNode(assetManager, TARGET_COLLISION_SOUND_PATH, false);
		obstacleCollisionAudioNode = new AudioNode(assetManager, OBSTACLE_COLLISION_SOUND_PATH,
				false);
		comboNewLevelAudioNode = new AudioNode(assetManager, COMBO_NEW_LEVEL_SOUND_PATH, false);
		comboLostAudioNode = new AudioNode(assetManager, COMBO_LOST_SOUND_PATH, false);
		powerupInverseAudioNode = new AudioNode(assetManager, POWERUP_INVERSE_SOUND_PATH, false);
		powerupInverseEndAudioNode = new AudioNode(assetManager, POWERUP_INVERSE_END_SOUND_PATH, false);
		comboVoiceAudioNode = new AudioNode(assetManager, COMBO_VOICE_SOUND_PATH, false);
		spearThrowAudioNode = new AudioNode(assetManager, SPEAR_THROW_SOUND_PATH, false);
		thunderHitAudioNode = new AudioNode(assetManager, THUNDER_HIT_SOUND_PATH, false);
		whaleSpawnAudioNode = new AudioNode(assetManager, WHALE_SPAWN_SOUND_PATH, false);
		snowballWaveCollisionAudioNode = new AudioNode(assetManager, SNOWBALL_WAVE_COLLISION_SOUND_PATH, false);
		bearHitAudioNode = new AudioNode(assetManager, BEAR_HIT_SOUND_PATH, false);
		dangerAboveAudioNode = new AudioNode(assetManager, DANGER_ABOVE_SOUND_PATH, false);
		powerupSpeedAudioNode = new AudioNode(assetManager, POWERUP_SPEED_SOUND_PATH, false);
		powerupSpeedEndAudioNode = new AudioNode(assetManager, POWERUP_SPEED_END_SOUND_PATH, false);
		powerupVoiceAudioNode = new AudioNode(assetManager, POWERUP_VOICE_SOUND_PATH, false);
		
		initSingleSound(penguinSpawnAudioNode, PENGUIN_SPAWN_SOUND_PATH);
		initSingleSound(krillCollisionAudioNode, KRILL_COLLISION_SOUND_PATH);
		initSingleSound(obstacleSpawnAudioNode, OBSTACLE_SPAWN_SOUND_PATH);
		initSingleSound(targetCollisionAudioNode, TARGET_COLLISION_SOUND_PATH);
		initSingleSound(obstacleCollisionAudioNode, OBSTACLE_COLLISION_SOUND_PATH);
		initSingleSound(comboNewLevelAudioNode, COMBO_NEW_LEVEL_SOUND_PATH);
		initSingleSound(comboLostAudioNode, COMBO_LOST_SOUND_PATH);
		initSingleSound(powerupInverseAudioNode, POWERUP_INVERSE_SOUND_PATH);
		initSingleSound(powerupInverseEndAudioNode, POWERUP_INVERSE_END_SOUND_PATH);
		initSingleSound(comboVoiceAudioNode, COMBO_VOICE_SOUND_PATH);
		initSingleSound(spearThrowAudioNode, SPEAR_THROW_SOUND_PATH);
		initSingleSound(thunderHitAudioNode, THUNDER_HIT_SOUND_PATH);
		initSingleSound(whaleSpawnAudioNode, WHALE_SPAWN_SOUND_PATH);
		initSingleSound(snowballWaveCollisionAudioNode, SNOWBALL_WAVE_COLLISION_SOUND_PATH);
		initSingleSound(bearHitAudioNode, BEAR_HIT_SOUND_PATH);
		initSingleSound(dangerAboveAudioNode, DANGER_ABOVE_SOUND_PATH);
		initSingleSound(powerupSpeedAudioNode, POWERUP_SPEED_SOUND_PATH);
		initSingleSound(powerupSpeedEndAudioNode, POWERUP_SPEED_END_SOUND_PATH);
		initSingleSound(powerupVoiceAudioNode, POWERUP_VOICE_SOUND_PATH);
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
	 * sound of target spawning.
	 */
	public void krillCollisionSound() {
		krillCollisionAudioNode.playInstance();
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
	
	/**
	 * sound of inverse controls power up started.
	 */
	public void powerupInverseSound() {
		powerupInverseAudioNode.playInstance();
	}
	
	/**
	 * sound of end of inverse controls power up.
	 */
	public void powerupInverseEndSound() {
		powerupInverseEndAudioNode.playInstance();
	}
	
	/**
	 * sound of combo voice saying 'combo'.
	 */
	public void comboVoiceSound() {
		comboVoiceAudioNode.playInstance();
	}
	
	/**
	 * sound of spear throw.
	 */
	public void spearThrowSound() {
		spearThrowAudioNode.playInstance();
	}
	
	/**
	 * sound of thunder hit.
	 */
	public void thunderHitSound() {
		thunderHitAudioNode.playInstance();
	}
	
	/**
	 * sound of whale spawn.
	 */
	public void whaleSpawnSound() {
		whaleSpawnAudioNode.playInstance();
	}
	
	/**
	 * sound of snowball thumping into the wave.
	 */
	public void snowballWaveCollisionSound() {
		snowballWaveCollisionAudioNode.playInstance();
	}
	
	/**
	 * sound of bear roaring when it makes the hit.
	 */
	public void bearHitSound() {
		bearHitAudioNode.playInstance();
	}
	
	/**
	 * sound of spikey ball spawn.
	 */
	public void dangerAboveSound() {
		dangerAboveAudioNode.playInstance();
	}
	
	/**
	 * sound of speed power up kicking in.
	 */
	public void powerupSpeedSound() {
		powerupSpeedAudioNode.playInstance();
	}
	
	/**
	 * sound of speed power up end.
	 */
	public void powerupSpeedEndSound() {
		powerupSpeedEndAudioNode.playInstance();
	}
	
	/**
	 * sound of power up trigger.
	 */
	public void powerupVoiceSound() {
		powerupVoiceAudioNode.playInstance();
	}
}
