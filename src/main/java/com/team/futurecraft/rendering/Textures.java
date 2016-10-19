package com.team.futurecraft.rendering;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.GL_CLAMP_TO_EDGE;
import static org.lwjgl.opengl.GL12.GL_TEXTURE_WRAP_R;
import static org.lwjgl.opengl.GL13.GL_TEXTURE_CUBE_MAP;
import static org.lwjgl.opengl.GL13.GL_TEXTURE_CUBE_MAP_POSITIVE_X;
import static org.lwjgl.opengl.GL30.*;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.HashMap;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

/**
 * Just loads textures with filtering, since minecraft doesn't.
 * This has a built-in texture map and all you have to do is call loadTexture(path) for every 
 * render (no need to store it as a variable). You also don't have to use glBindTexture. 
 * 
 * @author Joseph
 */
public class Textures {
	
	private static HashMap<String, Integer> textures = new HashMap<String, Integer>();
	
	public static void loadTexture(String path) {
		if (textures.containsKey(path)) {
			glBindTexture(GL_TEXTURE_2D, textures.get(path));
		}
		else {
			int handle = glGenTextures();
			//System.out.println(handle);
			glBindTexture(GL_TEXTURE_2D, handle);
			
			textures.put(path, handle);
		
			RawImage img = getRawImage(path);
		
			glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, img.width, img.height, 0, GL_RGBA, GL_UNSIGNED_BYTE, img.data);
		
			//glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
		    //glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
		    
			glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
			glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
			
		    glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
			glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
			glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_WRAP_R, GL_CLAMP_TO_EDGE);
			
		    glGenerateMipmap(GL_TEXTURE_2D);
		}
	}
	
	public static boolean exists(String path) {
		if (textures.containsKey(path))
			return true;
		
		ResourceLocation loc = new ResourceLocation("futurecraft", path);
		
		try {
			Minecraft.getMinecraft().getResourceManager().getResource(loc);
		} catch (IOException e) {
			return false;
		}
		
		return true;
	}
	
	protected static RawImage getRawImage(String path) {
		
		ResourceLocation loc = new ResourceLocation("futurecraft", path);
		
		InputStream in;
		BufferedImage image;
		try {
			in = Minecraft.getMinecraft().getResourceManager().getResource(loc).getInputStream();
			image = ImageIO.read(in);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	
		AffineTransform transform = AffineTransform.getScaleInstance(1f, -1f);
		transform.translate(0, -image.getHeight());
		AffineTransformOp operation = new AffineTransformOp(transform, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
		image = operation.filter(image, null);
	
		int width = image.getWidth();
		int height = image.getHeight();

		int[] pixels = new int[width * height];
		image.getRGB(0, 0, width, height, pixels, 0, width);
	
		ByteBuffer buffer = BufferUtils.createByteBuffer(width * height * 4);

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				/* Pixel as RGBA: 0xAARRGGBB */
				int pixel = pixels[y * width + x];

				/* Red component 0xAARRGGBB >> (4 * 4) = 0x0000AARR */
				buffer.put((byte) ((pixel >> 16) & 0xFF));

				/* Green component 0xAARRGGBB >> (2 * 4) = 0x00AARRGG */
				buffer.put((byte) ((pixel >> 8) & 0xFF));

				/* Blue component 0xAARRGGBB >> 0 = 0xAARRGGBB */
				buffer.put((byte) (pixel & 0xFF));

				/* Alpha component 0xAARRGGBB >> (6 * 4) = 0x000000AA */
				buffer.put((byte) ((pixel >> 24) & 0xFF));
			}
		}
		buffer.flip();
		
		return new RawImage(buffer, width, height);
	}
}
