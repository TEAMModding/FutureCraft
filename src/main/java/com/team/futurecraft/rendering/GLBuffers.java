package com.team.futurecraft.rendering;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;

public class GLBuffers {
	public static FloatBuffer StaticBuffer(float[] contents) {
		FloatBuffer buffer = BufferUtils.createFloatBuffer(contents.length);
		for (float i : contents) {
			buffer.put(i);
		}
		buffer.flip();
		
		return buffer;
	}
	
	public static IntBuffer StaticBuffer(int[] contents) {
		IntBuffer buffer = BufferUtils.createIntBuffer(contents.length);
		for (int i : contents) {
			buffer.put(i);
		}
		buffer.flip();
		
		return buffer;
	}
}
