package com.team.futurecraft.rendering;

import java.nio.ByteBuffer;

/**
 * Represents image data, used by Textures.
 * 
 * @author Joseph
 */
public class RawImage {
	public ByteBuffer data;
	public int width;
	public int height;
	
	public RawImage(ByteBuffer data, int width, int height) {
		this.data = data;
		this.width = width;
		this.height = height;
	}
}
