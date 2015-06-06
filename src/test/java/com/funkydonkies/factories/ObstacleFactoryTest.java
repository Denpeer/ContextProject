package com.funkydonkies.factories;

import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.doNothing;





import org.junit.BeforeClass;
import org.junit.Test;

import com.funkydonkies.geometries.obstacles.KillerWhale;
import com.funkydonkies.geometries.obstacles.PolarBear;
import com.funkydonkies.geometries.obstacles.SeaLion;
import com.jme3.asset.AssetManager;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.material.Material;
import com.jme3.scene.Node;

/**
 * This class tests the obstacle factory.
 *
 */
public class ObstacleFactoryTest {
	private static ObstacleFactory obf;

	/**
	 * Do this before the testing begins.
	 */
	@BeforeClass
	public static void setUpBeforeClass() {
		obf = new ObstacleFactory(mock(AssetManager.class),
				mock(Node.class), mock(PhysicsSpace.class));		
	}

	/**
	 * Test the collision of the createKillerWhale method.
	 */
	@Test
	public void testCreateKillerWhale() {
		obf.setKillerWhaleMaterial(mock(Material.class));
		final KillerWhale kWhale = obf.makeKillerWhale();
		assertFalse(kWhale == null);
	}
	
	/**
	 * Test the collision of the createPolarBear method.
	 */
	@Test
	public void testCreatePolarBear() {
		obf.setPolarBearMaterial(mock(Material.class));
		final PolarBear pBear = obf.makePolarBear();
		assertFalse(pBear == null);
	}
	
	/**
	 * Test the collision of the createSeaLion method.
	 */
	@Test
	public void testCreateSeaLion() {
		obf.setSeaLionMaterial(mock(Material.class));
		final SeaLion sLion = obf.makeSeaLion();
		assertFalse(sLion == null);
	}
	 

  }
