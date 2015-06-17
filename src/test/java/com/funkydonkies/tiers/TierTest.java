package com.funkydonkies.tiers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Test;

import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.shape.Box;

public class TierTest {
	Tier tier;
	private int textSize = 50;
	BitmapText text;
	
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
	
	@Test
	public void setTextTest() {
		tier.createTierText();
		tier.setText("hello");
		assertEquals(text.getText(), "hello");
	}

}
