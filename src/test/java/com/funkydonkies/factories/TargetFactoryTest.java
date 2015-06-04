package com.funkydonkies.factories;

import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.mock;

import org.junit.BeforeClass;
import org.junit.Test;

import com.funkydonkies.factories.TargetFactory;
import com.funkydonkies.geometrys.targets.Fish;
import com.funkydonkies.geometrys.targets.Krill;
import com.funkydonkies.geometrys.targets.Squid;
import com.jme3.asset.AssetManager;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.scene.Node;
/**
 * This class tests the obstacle factory.
 *
 */
public class TargetFactoryTest {
	private static TargetFactory tarFac;

	/**
	 * Do this before the testing begins.
	 */
	@BeforeClass
	public static void setUpBeforeClass() {
		tarFac = new TargetFactory(mock(AssetManager.class),
				mock(Node.class), mock(PhysicsSpace.class));
	}

	/**
	 * Test the collision of the createfish method.
	 */
	@Test
	public void testCreateFish() {
		final Fish fish = tarFac.makeFish();
		assertFalse(fish == null);
	}
	
	/**
	 * Test the collision of the createKrill method.
	 */
	@Test
	public void testCreateKrill() {
		final Krill kr = tarFac.makeKrill();
		assertFalse(kr == null);
	}
	
	/**
	 * Test the collision of the create squids method.
	 */
	@Test
	public void testCreateSquid() {
		final Squid sq = tarFac.makeSquid();
		assertFalse(sq == null);
	}

}
