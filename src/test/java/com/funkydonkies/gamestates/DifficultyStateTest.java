package com.funkydonkies.gamestates;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import com.funkydonkies.combo.ComboDisplay;
import com.funkydonkies.core.App;
import com.funkydonkies.powerups.InvertControlsPowerup;
import com.funkydonkies.powerups.SnowballPowerup;
import com.funkydonkies.tiers.Tier1;
import com.funkydonkies.tiers.Tier2;
import com.jme3.app.state.AppStateManager;

public class DifficultyStateTest {
	private App app;
	private AppStateManager sManager;
	private DifficultyState diffState;
	private DifficultyState diffSpy;
	private Tier1 tier1;
	private Tier2 tier2;
	private ComboDisplay combo;
	private SnowballPowerup snowBall;
	private InvertControlsPowerup invertControls;
	
	@Before
	public void setUp() throws Exception {
		app = mock(App.class);
		sManager = mock(AppStateManager.class);
		tier1 = mock(Tier1.class);
		tier2 = mock(Tier2.class);
		combo = mock(ComboDisplay.class);
		snowBall = mock(SnowballPowerup.class);
		invertControls = mock(InvertControlsPowerup.class);
		
		diffState = new DifficultyState();
		diffSpy = spy(diffState);
		doReturn(combo).when(diffSpy).makeCombo();
		doReturn(tier1).when(diffSpy).makeTier1();
		doReturn(tier2).when(diffSpy).makeTier2();
		doReturn(snowBall).when(diffSpy).makeSnowBallPowerup();
		doReturn(invertControls).when(diffSpy).makeInvertControlsPowerup();
	}

	@Test
	public void testInitialize() {
		diffSpy.initialize(sManager, app);
		
		verify(sManager, times(4)).attach(any(DisabledState.class));
		verify(diffSpy).initCombo();
	}
	
	@Test
	public void testUpdate() {
		//TODO test update method here
	}

	@Test
	public void testSetTier1() {
		diffSpy.initialize(sManager, app);
		
		verify(tier1, times(0)).setEnabled(true);
		verify(tier2, times(0)).setEnabled(true);

		diffSpy.setTier1();
		
		verify(tier1).setEnabled(true);
		verify(tier2, times(0)).setEnabled(true);
	}

	@Test
	public void testSetTier2() {
		diffSpy.initialize(sManager, app);
		
		verify(tier1, times(0)).setEnabled(true);
		verify(tier2, times(0)).setEnabled(true);

		diffSpy.setTier2();
		
		verify(tier2).setEnabled(true);
		verify(tier1, times(0)).setEnabled(true);
	}
	
	@Test
	public void testSetTier2_2() {
		diffSpy.initialize(sManager, app);
		
		verify(tier1, times(0)).setEnabled(true);
		verify(tier2, times(0)).setEnabled(true);
		
		when(tier1.isEnabled()).thenReturn(true);
		diffSpy.setTier1();
		diffSpy.setTier2();
		
		verify(tier2).setEnabled(true);
		verify(tier1, times(1)).setEnabled(false);
	}

	@Test
	public void testUpdateObservable() {
		diffSpy.initialize(sManager, app);
		
		diffSpy.update(combo, null);
		verify(combo).getCombo();
	}

	@Test
	public void testIncDiff() {
		diffSpy.initialize(sManager, app);
		
		diffSpy.incDiff();
		verify(combo).incCombo();
	}

	@Test
	public void testResetDiff() {
		diffSpy.initialize(sManager, app);
		
		diffSpy.resetDiff();
		verify(combo).resetCombo();
	}
	
	@Test
	public void testActivateInvertControls() {
		diffSpy.initialize(sManager, app);
		
		diffSpy.activateInvertControls();
		verify(invertControls).setEnabled(true);
	}
	
	@Test
	public void testActivateSnowBallPowerup() {
		diffSpy.initialize(sManager, app);
		
		diffSpy.activateSnowBallPowerup();
		verify(snowBall).setEnabled(true);
	}
	
	@Test
	public void testInitCombo() {
		diffSpy.initialize(sManager, app);

		verify(combo).createCurrentComboText(app);
		verify(combo).createHighestComboText(app);
		verify(combo).addObserver(diffSpy);
		verify(combo).updateText();
	}
}
