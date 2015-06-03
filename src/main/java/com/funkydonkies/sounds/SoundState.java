package com.funkydonkies.sounds;

import java.util.ArrayList;
import java.util.List;

import com.funkydonkies.exceptions.BadDynamicTypeException;
import com.funkydonkies.w4v3.App;
import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;

/**
 * gets, queues and calls play() on all sounds that need to be triggered.
 * 
 * @author Olivier Dikken
 */
public class SoundState extends AbstractAppState {

	private App app;
	private List<Sound> soundList = new ArrayList<Sound>();
	private SoundPlayer soundPlayer;

	@Override
	public final void initialize(final AppStateManager sManager,
			final Application appl) {
		super.initialize(sManager, appl);

		if (appl instanceof App) {
			this.app = (App) appl;
		} else {
			throw new BadDynamicTypeException();
		}
		this.app.getAssetManager();
		this.soundPlayer = new SoundPlayer(appl);
	}

	/**
	 * add sound to queue to be played next.
	 * 
	 * @param sound
	 *            to play next.
	 */
	public void queueSound(final Sound sound) {
		soundList.add(sound);
	}

	/**
	 * plays all sounds in queue.
	 */
	@Override
	public final void update(final float tpf) {
		for (Sound sound : soundList) {
			sound.play(soundPlayer);
		}
		soundList.clear();
	}
}
