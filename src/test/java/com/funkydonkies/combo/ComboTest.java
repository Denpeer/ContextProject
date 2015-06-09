package com.funkydonkies.combo;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.reset;

import org.junit.Before;
import org.junit.Test;

import com.jme3.asset.AssetManager;
import com.jme3.font.BitmapText;
import com.jme3.scene.Node;


public class ComboTest {
	private static Combo combo;
	private Combo comboSpy;
	private static BitmapText text;
	private Node node;
	private AssetManager sManager;
	
	/**
	 * Instantiates objects.
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		node = mock(Node.class);
		text = mock(BitmapText.class);
		sManager = mock(AssetManager.class);
		node =mock(Node.class);
		combo = new Combo(node);
		comboSpy = spy(combo);
		doNothing().when(comboSpy).createCurrentComboText(sManager);
		doNothing().when(comboSpy).createHighestComboText(sManager);
	}

	@Test
	public void testCombo() {
		verify(node, times(2)).attachChild(any(BitmapText.class));
	}
	/**
	 * Tests the increasing of the combo.
	 */
	@Test
	public void testIncCombo() {
		assertEquals(comboSpy.getCombo(), 0);
		comboSpy.incCombo();
		assertEquals(comboSpy.getCombo(), 1);
		verify(comboSpy).updateText();
	}

	/**
	 * Tests the combo reset.
	 */
	@Test
	public void testResetCombo() {
		assertEquals(comboSpy.getCombo(), 0);
		comboSpy.incCombo();
		assertEquals(comboSpy.getCombo(), 1);
		comboSpy.resetCombo();
		assertEquals(comboSpy.getCombo(), 0);
		verify(comboSpy, times(2)).updateText();
	}

	/**
	 * Tests the currect displaying of the combo.
	 */
	@Test
	public void testDisplay() {
		reset(node);
		combo.display();
		verify(node, times(2)).attachChild(any(BitmapText.class));
	}

}
