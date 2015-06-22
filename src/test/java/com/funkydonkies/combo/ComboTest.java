package com.funkydonkies.combo;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.startsWith;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import com.funkydonkies.core.App;
import com.funkydonkies.gamestates.DifficultyState;
import com.jme3.app.state.AppStateManager;
import com.jme3.font.BitmapText;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;


public class ComboTest {
	private static ComboDisplay combo;
	private ComboDisplay comboSpy;
	private BitmapText text;
	private Node rootNode;
	private Node guiNode;
	private App app;
	private AppStateManager sManager;
	private DifficultyState diffState;
	
	/**
	 * Instantiates objects.
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		rootNode = mock(Node.class);
		text = mock(BitmapText.class);
		app = mock(App.class);
		sManager = mock(AppStateManager.class);
		guiNode =mock(Node.class);
		diffState = mock(DifficultyState.class);
		
		when(app.getRootNode()).thenReturn(rootNode);
		when(app.getGuiNode()).thenReturn(guiNode);
		when(rootNode.getUserData("default text")).thenReturn(text);
		
		doReturn(sManager).when(app).getStateManager();
		doReturn(diffState).when(sManager).getState(DifficultyState.class);
		
		combo = new ComboDisplay(app);
		comboSpy = spy(combo);
		
		doReturn(text).when(comboSpy).createText();
	}


	@Test
	public void updateTextTest() {
		comboSpy.init();
		reset(text);

		comboSpy.updateText();
		verify(text).setText(startsWith("Current combo: "));
		verify(text).setText(startsWith("Highest combo: "));
	}
	
	@Test
	public void updateTest() {
		comboSpy.update(diffState, null);
		
		verify(comboSpy).updateText();
	}
	
	@Test
	public void createCurrentComboTextTest() {
		reset(text);
		comboSpy.createCurrentComboText();
		verify(text).setSize(ComboDisplay.TEXT_SIZE);
		verify(text).setColor(any(ColorRGBA.class));
		verify(text).setLocalTranslation(ComboDisplay.COUNTER_LOCATION);
	}
	
	@Test
	public void createHighestComboTextTest() {
		reset(text);
		comboSpy.createHighestComboText();
		verify(text).setSize(ComboDisplay.TEXT_SIZE);
		verify(text).setColor(any(ColorRGBA.class));
		Vector3f highestloc = ComboDisplay.COUNTER_LOCATION;
		verify(text).setLocalTranslation(highestloc.setX(any(Float.class)));
	}
}
