package com.funkydonkies.w4v3;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;

import com.funkydonkies.gamestates.Combo;
import com.funkydonkies.gamestates.ComboState;
import com.jme3.font.BitmapText;
import com.jme3.scene.Node;

public class ComboTest {
	private static Combo combo;
	private static BitmapText text;
	private static Node node;
	
	/**
	 * Instantiates objects.
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		node = mock(Node.class);
		text = mock(BitmapText.class);
		combo = new Combo(node, text);
	}

	/**
	 * Tests the increasing of the combo.
	 */
	@Test
	public void testIncCombo() {
		verify(text).setText(("Current combo: 0"));
		assertEquals(combo.getCombo(), 0);
		combo.incCombo();
		assertEquals(combo.getCombo(), 1);
		verify(text).setText(("Current combo: 1"));
	}

	/**
	 * Tests the combo reset.
	 */
	@Test
	public void testResetCombo() {
		assertEquals(combo.getCombo(), 0);
		combo.incCombo();
		assertEquals(combo.getCombo(), 1);
		combo.resetCombo();
		assertEquals(combo.getCombo(), 0);
		verify(text, times(2)).setText(("Current combo: 0"));
		verify(text).setText(("Current combo: 1"));

	}

	/**
	 * Tests the currect displaying of the combo.
	 */
	@Test
	public void testDisplay() {
		combo.display();
		verify(node).attachChild(text);
	}

}
