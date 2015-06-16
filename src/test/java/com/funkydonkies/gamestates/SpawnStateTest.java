package com.funkydonkies.gamestates;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;

import com.funkydonkies.core.App;
import com.funkydonkies.interfaces.FactoryInterface;
import com.jme3.app.state.AppStateManager;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.scene.Node;

public class SpawnStateTest {
	private SpawnState spawnState;
	private SpawnState spy;
	private PlayState spy2;
	private PhysicsSpace ps;
	private App app;
	private AppStateManager sManager;
	
	
	/**
	 * Do this before the testing begins.
	 */
	@Before
	public void setUp() {
		app = new App();
		sManager = new AppStateManager(app);
		spawnState = new SpawnState();
		spy = spy(spawnState);
		spy2 = spy(new PlayState());
		ps = mock(PhysicsSpace.class);
		doNothing().when(spy).spawn(any(FactoryInterface.class), any(Node.class));
		doNothing().when(spy).initRootNodeMat(app);
	}
	
	@Test
	public void initializeTest(){
		spy.initialize(sManager, app);
		
		verify(spy).initFactories();
		verify(spy).setSpawnAbleObjects();
		verify(spy).spawn(any(FactoryInterface.class), any(Node.class));
		verify(spy).initRootNodeMat(app);
	}
}
