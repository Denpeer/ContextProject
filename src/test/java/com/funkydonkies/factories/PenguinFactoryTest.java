package com.funkydonkies.factories;

import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.mock;

import org.junit.BeforeClass;
import org.junit.Test;

import com.funkydonkies.geometrys.penguins.FatPenguin;
import com.funkydonkies.geometrys.penguins.ShinyPenguin;
import com.funkydonkies.geometrys.penguins.StandardPenguin;
import com.jme3.asset.AssetManager;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.material.Material;
import com.jme3.scene.Node;


/**
 * This class tests the obstacle factory.
 *
 */
public class PenguinFactoryTest {
	private static PenguinFactory penguinFac;

	/**
	 * Do this before the testing begins.
	 */
	@BeforeClass
	public static void setUpBeforeClass() {
		penguinFac = new PenguinFactory(mock(AssetManager.class),
				mock(Node.class), mock(PhysicsSpace.class));
	}

	/**
	 * Test the collision of the createKillerWhale method.
	 */
	@Test
	public void testCreateFatPenguin() {
		penguinFac.setFatPenguinMaterial(mock(Material.class));
		final FatPenguin peng = penguinFac.makeFatPenguin();
		assertFalse(peng == null);
	}
	
	/**
	 * Test the collision of the createPolarBear method.
	 */
	@Test
	public void testCreateShinyPenguin() {
		penguinFac.setShinyPenguinMaterial(mock(Material.class));
		final ShinyPenguin peng = penguinFac.makeShinyPenguin();
		assertFalse(peng == null);
	}
	
	/**
	 * Test the collision of the createSeaLion method.
	 */
	@Test
	public void testCreateStandardPenguin() {
		penguinFac.setStandardPenguinMaterial(mock(Material.class));
		final StandardPenguin peng = penguinFac.makeStandardPenguin();
		assertFalse(peng == null);
	}

}
