//package com.funkydonkies.gamestates;
//
//import static org.mockito.Mockito.spy;
//import static org.mockito.Mockito.when;
//
//import org.junit.BeforeClass;
//import org.junit.Test;
//
//import com.funkydonkies.core.App;
//import com.jme3.app.state.AppStateManager;
//import com.jme3.bullet.PhysicsSpace;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.doNothing;
//
//public class SpawnStateTest {
//	private static SpawnState ss;
//	private static SpawnState spy;
//	private static PlayState spy2;
//	private static App app;
//	private static AppStateManager sm;
//	private static PhysicsSpace ps;
//	
//	/**
//	 * Do this before the testing begins.
//	 */
//	@BeforeClass
//	public static void setUpBeforeClass() {
//		app = new App();
//		sm = new AppStateManager(app);
//		ss = new SpawnState();
//		spy = spy(ss);
//		spy2 = spy(new PlayState());
//		ps = mock(PhysicsSpace.class);
//		doNothing().when((spy2).getPhysicsSpace());
//	}
//	
//	@Test
//	public void initializeTest(){
//		ss.initialize(sm, app);
//	}
//}
