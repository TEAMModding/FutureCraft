package com.team.futurecraft.rendering;

import com.team.futurecraft.Mat4f;
import com.team.futurecraft.Vec3f;

import net.minecraft.client.Minecraft;

public class SpaceRenderer {
	private static Minecraft mc = Minecraft.getMinecraft();
	private Mesh triangle;
	private Shader standardShader;
	
	float[] vertices = {
		    -0.5f, -0.5f, 0.0f,
		     0.5f, -0.5f, 0.0f,
		     0.0f,  0.5f, 0.0f
	}; 
	
	public SpaceRenderer() {
		triangle = new Mesh(vertices);
		standardShader = new Shader("default");
	}
	
	public static Mat4f getViewMatrix(Vec3f pos, Vec3f rot) {
		Mat4f mat = new Mat4f();
		mat = mat.multiply(Mat4f.rotate((float)rot.z, 0, 0F, 1f));
		mat = mat.multiply(Mat4f.rotate((float)rot.y, 1F, 0F, 0F));
		mat = mat.multiply(Mat4f.rotate((float)rot.x, 0F, 1F, 0F));
		mat = mat.multiply(Mat4f.translate(-pos.x, -pos.y, -pos.z));
		
		return mat;
	}
	
	public static Mat4f getProjectionMatrix() {
		return Mat4f.perspective(mc.gameSettings.fovSetting, (float)mc.displayWidth / mc.displayHeight, 0.000001F, 10);
	}
	
	public void render(Camera cam, long time, boolean renderOrbits) {
		//glClear(GL_COLOR_BUFFER_BIT);
		
		standardShader.bind();
		
		triangle.bind();
		
		triangle.draw();
		
		triangle.unBind();
		
		standardShader.unBind();
	}
}
