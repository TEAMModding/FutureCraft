package com.team.futurecraft.rendering;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.*;
import static org.lwjgl.opengl.GL13.*;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

public class Cubemap {
	public int id;
	
	public Cubemap(String[] images) {
		this.id = id;
		glBindTexture(GL_TEXTURE_CUBE_MAP, this.id);
		
		for (int i = 0; i < 6; i++) {
			RawImage image = getRawImage(images[i]);
			glTexImage2D(GL_TEXTURE_CUBE_MAP_POSITIVE_X + i, 0, GL_RGBA8, image.width, image.height, 0, GL_RGBA, GL_UNSIGNED_BYTE, image.data);
		}
		
		glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
		glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
		glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
		glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
		glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_WRAP_R, GL_CLAMP_TO_EDGE);
	}
	
	/**
	 * Binds the texture for rendering in the specified texture location. Multiple textures can be sent
	 * to the same shader if they are in different locations.
	 */
	public void bind(int num) {
		glActiveTexture(GL_TEXTURE0 + num);
		glBindTexture(GL_TEXTURE_CUBE_MAP, id);
		glActiveTexture(GL_TEXTURE0);
	}
	
	public static void unBind(int num) {
		glActiveTexture(GL_TEXTURE0 + num);
		glBindTexture(GL_TEXTURE_CUBE_MAP, 0);
		glActiveTexture(GL_TEXTURE0);
	}
	
	/**
	 * Binds the texture for rendering in the first texture location.
	 */
	public void bind() {
		glBindTexture(GL_TEXTURE_CUBE_MAP, id);
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
	
		//AffineTransform transform = AffineTransform.getScaleInstance(-1f, 1f);
		//transform.translate(-image.getWidth(), 0);
		//AffineTransformOp operation = new AffineTransformOp(transform, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
		//image = operation.filter(image, null);
	
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


