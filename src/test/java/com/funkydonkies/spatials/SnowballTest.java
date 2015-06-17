package com.funkydonkies.spatials;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import java.awt.Color;

import org.junit.Before;
import org.junit.Test;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.texture.Texture;

public class SnowballTest {
	private Snowball snowball;
	private Snowball snowballSpy;
	private AssetManager assetManager;
	private Material material;
	@Before
	public void setUp() throws Exception {
		assetManager = mock(AssetManager.class);
		material = mock(Material.class);
		
		int radius = 5;
		
		float borderWidth = 0;
		Color color = new Color(0, 0, 0);
		int borderAngle = 0;
		int angle = 360;
		snowball = new Snowball(assetManager, radius, borderWidth , color, borderAngle, color, 
				angle );
		snowballSpy = spy(snowball);
		doReturn(material).when(snowballSpy).createMaterial();
		doNothing().when(material).setTexture(eq("ColorMap"), any(Texture.class));
		snowballSpy.init();
	}

	@Test
	public void testGetRadius() {
		assertTrue(snowball.getRadius() == 5);
	}

	@Test
	public void testGenerateImage() {
		reset(material);
		snowballSpy.generateImage();
		verify(material).setTexture(eq("ColorMap"), any(Texture.class));
	}

	@Test
	public void testSetRadius() {
		reset(snowballSpy);
		snowballSpy.setRadius(10);
		assertTrue(snowballSpy.getRadius() == 10);
		verify(snowballSpy).updateSpatial();
	}

	@Test
	public void testSetFillColor() {
		Color fill = new Color(2);
		snowball.setFillColor(fill);
		assertEquals(snowball.getFillColor(), fill);
	}

	@Test
	public void testSetColor() {
		Color c = new Color(2);
		snowball.setColor(c);
		assertEquals(snowball.getColor(), c);
	}

	@Test
	public void testSetResolution() {
		snowball.setResolution(10);
		assertTrue(snowball.getResulolution() == 10);
	}

}
