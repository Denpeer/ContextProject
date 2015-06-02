package com.funkydonkies.gamestates;

import java.nio.file.Path;
import java.nio.file.Paths;

import com.funkydonkies.exceptions.BadDynamicTypeException;
import com.funkydonkies.w4v3.App;
import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioNode;
import com.jme3.audio.AudioSource;

/**
 * keeps track of the background music and switches to next song when current
 * song is done playing.
 * 
 * @author Olivier Dikken
 *
 */
public class GameSoundState extends AbstractAppState {
	private static final Path BACKGROUND_MUSIC1_PATH = Paths
			.get("/Sound/Free_The_Robots_-_Yoga_Fire.wav");
	private static final Path BACKGROUND_MUSIC2_PATH = Paths
			.get("/Sound/Free_The_Robots_-_Jupiter.wav");
	private static final Path BACKGROUND_MUSIC3_PATH = Paths
			.get("/Sound/Free_The_Robots_-_Jazzhole.wav");

	private static final Path[] BG_MUSIC_PATHS = { BACKGROUND_MUSIC1_PATH,
			BACKGROUND_MUSIC2_PATH, BACKGROUND_MUSIC3_PATH };
	private static final int DEFAULT_START_MUSIC_ID = 0;

	private AudioNode bgMusic;
	private int playingMusicId;

	private App app;
	private AssetManager assetManager;
	private AppStateManager stateManager;

	@Override
	public final void initialize(final AppStateManager sManager,
			final Application appl) {
		super.initialize(sManager, appl);

		if (appl instanceof App) {
			this.app = (App) appl;
		} else {
			throw new BadDynamicTypeException();
		}
		this.assetManager = this.app.getAssetManager();
		this.stateManager = sManager;
		
		initBgMusic();
	}

	/**
	 * Start playing the bg music.
	 */
	private void initBgMusic() {
		initBackgroundMusic(BG_MUSIC_PATHS[DEFAULT_START_MUSIC_ID]);
		playingMusicId = DEFAULT_START_MUSIC_ID;
	}

	/**
	 * play the background music.
	 * 
	 * @param bgMusicPath
	 *            must point to a mono .wav file
	 */
	private void initBackgroundMusic(final Path bgMusicPath) {
		final String pathString = bgMusicPath.toString().replace("\\", "/");
		// set bg music, streaming set to true
		bgMusic = new AudioNode(assetManager, pathString, true);
		bgMusic.setPositional(false);
		bgMusic.setVolume(1);
		app.getRootNode().attachChild(bgMusic);
		bgMusic.play();
	}

	/**
	 * switches to next BG_MUSIC when previous track is done playing.
	 */
	private void updateBgMusic() {
		if (bgMusic.getStatus() != AudioSource.Status.Playing) {
			if (playingMusicId == BG_MUSIC_PATHS.length - 1) {
				playingMusicId = 0;
			} else {
				playingMusicId++;
			}
			initBackgroundMusic(BG_MUSIC_PATHS[playingMusicId]);
			System.out.println("New BG_MUSIC audio started: "
					+ BG_MUSIC_PATHS[playingMusicId]);
		}
	}
	
	@Override
	public final void update(final float tpf) {
		updateBgMusic();
	}
}