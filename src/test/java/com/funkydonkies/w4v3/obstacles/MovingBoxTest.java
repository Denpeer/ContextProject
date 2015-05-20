package com.funkydonkies.w4v3.obstacles;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import org.junit.BeforeClass;
import org.junit.Test;

import com.funkydonkies.w4v3.Combo;
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
	private static MovingBox mBox;
	private static Material mat;
	private static PhysicsSpace psySpace;
	private static Node rootNode;
	private static final double SPEED = 0.01;
	private static final double MOVEDOWN = 1.01;
	private static final float TIME_PER_FRAME = 0.01f;
	
	private static final int WIDTH = 2;
	private static final int HEIGHT = 4;
	private static final int DEPTH = 1;
	private static AssetManager assetManager;
	private static Combo combo;


	/**
	 * The setup, needed to instantiate and mock objects.
	 */
	@BeforeClass
	public static void setup() {
		obF = new ObstacleFactory();
		assetManager = mock(AssetManager.class);
		combo = mock(Combo.class);
		rootNode = new Node();
		mBox = (MovingBox) obF.makeObstacle("MOVINGBOX", WIDTH, HEIGHT, DEPTH, rootNode, assetManager, combo);
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
