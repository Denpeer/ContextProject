package com.funkydonkies.gamestates;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;

import org.junit.Before;
import org.junit.Test;

import com.funkydonkies.core.App;
import com.jme3.app.state.AppStateManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.input.FlyByCamera;
import com.jme3.renderer.Camera;
import com.jme3.scene.Node;

public class PlayStateTest {
	private App app;
	private AppStateManager sManager;
	private BulletAppState bullet;
	private PlayState playState;
	private PlayState playSpy;
	private FlyByCamera flyCam;
	private Camera cam;
	private PhysicsSpace phySpace;
	private Node penguinNode;
	
	@Before
	public void setUp() throws Exception {
		app = mock(App.class);
		sManager = mock(AppStateManager.class);
		flyCam = mock(FlyByCamera.class);
		cam = mock(Camera.class);
		phySpace = mock(PhysicsSpace.class);
		bullet = mock(BulletAppState.class);
		penguinNode = mock(Node.class);
		
		playState = new PlayState();
		playSpy = spy(playState);
		
		doReturn(flyCam).when(app).getFlyByCamera();
		doReturn(cam).when(app).getCamera();
		doReturn(bullet).when(playSpy).makeBulletAppState();
		when(bullet.getPhysicsSpace()).thenReturn(phySpace);
		when(app.getPenguinNode()).thenReturn(penguinNode);
	}

	@Test
	public void testInitializeAppStateManagerApplication() {
		playSpy.initialize(sManager, app);
		
		verify(playSpy).handleBulletAppState();
		verify(playSpy).initStates();
//		verify(flyCam).setEnabled(false);
		verify(cam).setLocation(PlayState.CAM_LOCATION);
	}

	@Test
	public void testInitStates() {
		playSpy.initialize(sManager, app);
		
		verify(sManager, times(9)).attach(any(DisabledState.class));
	}

	@Test
	public void testHandleBulletAppState() {
		playSpy.initialize(sManager, app);
		
		verify(sManager).attach(bullet);
		verify(phySpace).setGravity(PlayState.GRAVITY);
	}

	@Test
	public void testGetPhysicsSpace() {
		playSpy.initialize(sManager, app);

		assertEquals(playSpy.getPhysicsSpace(), phySpace);
	}
}
