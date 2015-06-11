package com.funkydonkies.combo;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.startsWith;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doReturn;

import org.junit.Before;
import org.junit.Test;

import com.funkydonkies.core.App;
import com.jme3.asset.AssetManager;
import com.jme3.font.BitmapText;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;


public class ComboTest {
	private static Combo combo;
	private Combo comboSpy;
	private BitmapText text;
	private Node rootNode;
	private Node guiNode;
	private App app;
	private AssetManager sManager;
	
	/**
	 * Instantiates objects.
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		rootNode = mock(Node.class);
		text = mock(BitmapText.class);
		app = mock(App.class);
		sManager = mock(AssetManager.class);
		guiNode =mock(Node.class);
		
		when(app.getRootNode()).thenReturn(rootNode);
		when(app.getGuiNode()).thenReturn(guiNode);
		when(rootNode.getUserData("default text")).thenReturn(text);
		
		combo = new Combo(app);
		comboSpy = spy(combo);
		
		doReturn(text).when(comboSpy).createText(app);
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

	@Test
	public void updateTextTest() {
		reset(text);
		comboSpy.createHighestComboText(app);
		comboSpy.createCurrentComboText(app);

		comboSpy.updateText();
		verify(text).setText(startsWith("Current combo: "));
		verify(text).setText(startsWith("Highest combo: "));
	}
	
	@Test
	public void createCurrentComboTextTest() {
		reset(text);
		comboSpy.createCurrentComboText(app);
		verify(text).setSize(Combo.TEXT_SIZE);
		verify(text).setColor(any(ColorRGBA.class));
		verify(text).setLocalTranslation(Combo.COUNTER_LOCATION);
	}
	
	@Test
	public void createHighestComboTextTest() {
		reset(text);
		comboSpy.createHighestComboText(app);
		verify(text).setSize(Combo.TEXT_SIZE);
		verify(text).setColor(any(ColorRGBA.class));
		Vector3f highestloc = Combo.COUNTER_LOCATION;
		verify(text).setLocalTranslation(highestloc.setX(any(Float.class)));
	}
}
