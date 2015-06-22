package com.funkydonkies.tiers;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import com.funkydonkies.core.App;
import com.funkydonkies.sounds.SoundState;
import com.jme3.app.state.AppStateManager;
import com.jme3.font.BitmapText;

/**
 * This test class tests the tier 1.
 */
public class Tier5Test {
	private Tier5 tier;
	private AppStateManager mockStateManager;
	
	/**
	 * Do this before the tests.
	 */
	@Before
	public void setUp() {
		tier = spy(Tier5.class);
		mockStateManager = mock(AppStateManager.class);
		when(mockStateManager.getState(SoundState.class)).thenReturn(mock(SoundState.class));
		doNothing().when(tier).setText(any(String.class));
		doNothing().when(tier).addObstacleArray();
		doReturn(mock(BitmapText.class)).when(tier).createText();
		}
	
	/**
	 * Test the init method.
	 */
	@Test
	public void testInit() {
		tier.initialize(mockStateManager, mock(App.class));
		verify(tier).addObstacleArray();
	}
	
	/**
	 * test size of array when nothing added.
	 */
	@Test
	public void testObstacleArray() {
		tier.initialize(mockStateManager, mock(App.class));
		assertTrue(tier.getObstacleArray().size() == 0);
	}

	
	/**
	 * test size of array when nothing added.
	 */
	@Test
	public void testEnabled() {
		tier.initialize(mockStateManager, mock(App.class));
		tier.setEnabled(true);
		assertTrue(tier.isEnabled());
	}
}
