package com.funkydonkies.tiers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;

import com.funkydonkies.core.App;
import com.funkydonkies.exceptions.BadDynamicTypeException;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppStateManager;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.math.ColorRGBA;

/**
 * This class tests the tier.
 */
public class TierTest {
	private Tier tier;
	private final int textSize = 50;
	private BitmapText text;
	
	/**
	 * Do this before every test.
	 */
	@Before
	public void setUp() {
		text = new BitmapText(mock(BitmapFont.class));
		tier = spy(Tier.class);
	    doReturn(text).when(tier).createText();
	}
	
	/**
	 * This method tests the createTierText method.
	 */
	@Test
	public void createTierTexttest() {
		tier.createTierText();
		assertEquals(text.getColor(), ColorRGBA.Red);
		assertTrue(text.getSize() == textSize);
	}
	 
	/**
	 * Test initialize with wrong methods.
	 */
	@Test(expected = BadDynamicTypeException.class)
	public void testInitFail() {
		tier.initialize(mock(AppStateManager.class), mock(SimpleApplication.class));
	}
	
	/**
	 * Test init.
	 */
	@Test
	public void initTest() {
		tier.initialize(mock(AppStateManager.class), mock(App.class));
		verify(tier).createTierText();
	}

	/**
	 * Test init.
	 */
	@Test
	public void getObstaclesTest() {
		tier.initialize(mock(AppStateManager.class), mock(App.class));
		assertTrue(tier.getObstacleArray() != null);
	}
}
