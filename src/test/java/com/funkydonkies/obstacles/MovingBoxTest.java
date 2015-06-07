package com.funkydonkies.obstacles;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import org.junit.BeforeClass;
import org.junit.Test;

import com.funkydonkies.factories.ObstacleFactory;
import com.funkydonkies.geometrys.obstacles.Spear;
import com.jme3.asset.AssetManager;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;

/**
 * This class test the moving box.
 * @author SDumasy
 *
 */
public class MovingBoxTest {
	private static ObstacleFactory obF;
	private static Spear mBox;
	private static Material mat;
	private static PhysicsSpace psySpace;
	private static Node rootNode;
	private static final float SPEED = 0.01f;
	private static final float MOVEDOWN = 1.01f;
	private static final float TIME_PER_FRAME = 0.01f;
	private static AssetManager assetManager;

	/**
	 * The setup, needed to instantiate and mock objects.
	 */
	@BeforeClass
	public static void setup() {
		obF = new ObstacleFactory();
		assetManager = mock(AssetManager.class);
		rootNode = new Node();
		mBox = obF.makeMovingBox(rootNode, assetManager);
		mat = mock(Material.class);
		psySpace = mock(PhysicsSpace.class); 
	}
	
	/**
	 * Tests if the box is succesfully attached to the rootnode.
	 */
	@Test
	public final void drawTest() {
		mBox.draw(mat, psySpace);
		assertEquals(rootNode.getChildren().size(), 1);
	}
	
	/**
	 * tests if the box moves up correctly.
	 */
	@Test
	public final void moveUpTest() {
		final Geometry geom = mBox.getGeometry();
		final Vector3f vec = geom.getLocalTranslation();
		mBox.draw(mat, psySpace);
		mBox.move(TIME_PER_FRAME);
		final Vector3f vec2 = geom.getLocalTranslation();
		vec2.setY((float) (vec.getY() - SPEED));
		assertEquals(vec, vec2);	
	}
	
	/**
	 * test if the move direction changes accordingly.
	 */
	@Test
	public final void changeMoveUpTest() {
		final Geometry geom = mBox.getGeometry();
		final Vector3f vec = geom.getLocalTranslation();
		while (vec.getY() <= mBox.getHeight()) {
			mBox.move(TIME_PER_FRAME);
		}
		
		final Vector3f vec2 = geom.getLocalTranslation();
		vec2.setY((float) (vec.getY() - MOVEDOWN));
		assertEquals(vec, vec2);	
	}

	/**
	 * test if the box moves down correctly.
	 */
	@Test
	public final void moveDownTest() {
		final Geometry geom = mBox.getGeometry();
		final Vector3f vec = geom.getLocalTranslation();
		while (vec.getY() <= mBox.getHeight()) {
			mBox.move(TIME_PER_FRAME);
		}
		mBox.move(TIME_PER_FRAME);
		final Vector3f vec2 = geom.getLocalTranslation();
		vec2.setY(vec.getY() - 1);
		assertEquals(vec, vec2);	
	}

}
