package com.funkydonkies.w4v3.obstacles;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import com.jme3.bullet.PhysicsSpace;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;

/**
 * This class
 * @author SDumasy
 *
 */
public class MovingBoxTest {
	private static ObstacleFactory obF;
	private static MovingBox mBox;
	private static Material mat;
	private static PhysicsSpace psySpace;
	private static Node rootNode;


	@BeforeClass
	public static void setup() {
		obF = new ObstacleFactory();
		mBox = (MovingBox) obF.makeObstacle("MOVINGBOX", 2, 4, 1);
		mat = mock(Material.class);
		psySpace = mock(PhysicsSpace.class); 
		rootNode = new Node();
	}
	
	@Test
	public void drawTest(){		
		mBox.draw(mat, psySpace, rootNode);
		assertEquals(rootNode.getChildren().size(), 1);
	}
	
	@Test
	public void moveUpTest(){
		Geometry geom = mBox.getGeometry();
		Vector3f vec = geom.getLocalTranslation();
		mBox.draw(mat, psySpace, rootNode);
		mBox.move();
		Vector3f vec2 = geom.getLocalTranslation();
		vec2.setY((float)(vec.getY() - 0.01));
		assertEquals(vec, vec2);	
	}
	
	@Test
	public void changeMoveUpTest(){
		Geometry geom = mBox.getGeometry();
		Vector3f vec = geom.getLocalTranslation();
		while(vec.getY() <= 1.5* mBox.getHeight())
			mBox.move();
		Vector3f vec2 = geom.getLocalTranslation();
		vec2.setY((float)(vec.getY() - 1.51));
		assertEquals(vec, vec2);	
	}
	
	@Test
	public void moveDownTest(){
		Geometry geom = mBox.getGeometry();
		Vector3f vec = geom.getLocalTranslation();
		while(vec.getY() <= 1.5* mBox.getHeight())
			mBox.move();
		mBox.move();
		Vector3f vec2 = geom.getLocalTranslation();
		vec2.setY((float)(vec.getY() - 1.50));
		assertEquals(vec, vec2);	
	}

}
