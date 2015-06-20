package com.funkydonkies.tiers;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;

import com.funkydonkies.core.App;
import com.jme3.app.state.AppStateManager;
import com.jme3.font.BitmapText;

/**
 * This test class tests the tier 1.
 */
public class Tier1Test {
	private Tier1 tier;

	
	/**
	 * Do this before the tests.
	 */
	@Before
	public void setUp() {
		tier = spy(Tier1.class);
		doNothing().when(tier).setText(any(String.class));
		doNothing().when(tier).addObstacleArray();
		doReturn(mock(BitmapText.class)).when(tier).createText();
		}
	
	/**
	 * Test the init method.
	 */
	@Test
	public void testInit() {
		tier.initialize(mock(AppStateManager.class), mock(App.class));
		verify(tier).addObstacleArray();
	}
	
	/**
	 * test size of array when nothing added.
	 */
	@Test
	public void testObstacleArray() {
		tier.initialize(mock(AppStateManager.class), mock(App.class));
		assertTrue(tier.getObstacleArray().size() == 0);
	}

	
	/**
	 * test size of array when nothing added.
	 */
	@Test
	public void testEnabled() {
		tier.initialize(mock(AppStateManager.class), mock(App.class));
		tier.setEnabled(true);
		assertTrue(tier.isEnabled());
	}
}
