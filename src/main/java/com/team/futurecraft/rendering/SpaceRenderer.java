package com.team.futurecraft.rendering;

import com.team.futurecraft.FutureCraft;
import com.team.futurecraft.Mat4f;
import com.team.futurecraft.Vec3f;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;

import net.minecraft.client.Minecraft;

public class SpaceRenderer {
	private static Minecraft mc = Minecraft.getMinecraft();
	private Shader standardShader;
	private Shader skyboxShader;
	private Mesh skyboxMesh;
	private Mesh mesh;
	private Cubemap skybox;
	
	private static float[] vertices = new float[] {
			// Positions
			-1.0f,  1.0f, -1.0f,
		    -1.0f, -1.0f, -1.0f,
		     1.0f, -1.0f, -1.0f,
		     1.0f, -1.0f, -1.0f,
		     1.0f,  1.0f, -1.0f,
		    -1.0f,  1.0f, -1.0f
	};
	
	private static float[] colors = new float[] {
			1.0f, 0.0f, 0.0f,
			0.0f, 1.0f, 0.0f,
			0.0f, 0.0f, 1.0f,
			0.0f, 0.0f, 1.0f,
			1.0f, 1.0f, 0.0f,
			1.0f, 0.0f, 0.0f
	};
	
	private static float[] skyboxUvs = new float[] {
			1.0f, 1.0f,
			1.0f, 0.0f,
			0.0f, 0.0f,
			0.0f, 0.0f,
			0.0f, 1.0f,
			1.0f, 1.0f
	};
	
	private static float[] uvs = new float[] {
			0.0f, 1.0f,
			0.0f, 0.0f,
			1.0f, 0.0f,
			1.0f, 0.0f,
			1.0f, 1.0f,
			0.0f, 1.0f
	};
	
	private static String[] cubemapTextures = new String[] {
			"textures/environment/sky_pos_x.png",
			"textures/environment/sky_neg_x.png",
			"textures/environment/sky_pos_y.png",
			"textures/environment/sky_neg_y.png",
			"textures/environment/sky_pos_z.png",
			"textures/environment/sky_neg_z.png"
			/*"textures/environment/right.jpg",
			"textures/environment/left.jpg",
			"textures/environment/top.jpg",
			"textures/environment/bottom.jpg",
			"textures/environment/back.jpg",
			"textures/environment/front.jpg",*/
	};
	
	public SpaceRenderer() {
		standardShader = new Shader("default");
		skyboxShader = new Shader("skybox");
		skyboxMesh = new Mesh(vertices, null, skyboxUvs, null);
		mesh = new Mesh(vertices, null, uvs, null);
		//skybox = new Cubemap(cubemapTextures);
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
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		//glDisable(GL_CULL_FACE);
		renderSkybox(cam);
		
		standardShader.bind();
		
		standardShader.uniformMat4("view", cam.getView());
		standardShader.uniformMat4("projection", cam.getProjection());
		standardShader.uniformMat4("model", Mat4f.translate(0.0f, 0.5f, -2.0f));
		Textures.loadTexture("textures/blocks/desert_iron_ore.png");
		
		mesh.draw();
		
		standardShader.unBind();
		
		FutureCraft.SOL.render(cam, time);
		
		//glEnable(GL_CULL_FACE);
	}
	
	private void renderSkybox(Camera cam) {
		glDepthMask(false);
		skyboxShader.bind();
		
		skyboxShader.uniformMat4("view", cam.getView());
		skyboxShader.uniformMat4("projection", cam.getProjection());
		
		skyboxShader.uniformMat4("model", Mat4f.rotate(-90.0f, 0.0f, 1.0f, 0.0f));
		Textures.loadTexture(cubemapTextures[0]);
		skyboxMesh.draw();
		
		skyboxShader.uniformMat4("model", Mat4f.rotate(90.0f, 0.0f, 1.0f, 0.0f));
		Textures.loadTexture(cubemapTextures[1]);
		skyboxMesh.draw();
		
		skyboxShader.uniformMat4("model", Mat4f.rotate(180.0f, 0.0f, 0.0f, 1.0f).multiply(Mat4f.rotate(-90.0f, 1.0f, 0.0f, 0.0f)));
		Textures.loadTexture(cubemapTextures[2]);
		skyboxMesh.draw();
		
		skyboxShader.uniformMat4("model", Mat4f.rotate(180.0f, 0.0f, 0.0f, 1.0f).multiply(Mat4f.rotate(90.0f, 1.0f, 0.0f, 0.0f)));
		Textures.loadTexture(cubemapTextures[3]);
		skyboxMesh.draw();
		
		skyboxShader.uniformMat4("model", Mat4f.rotate(180.0f, 0.0f, 1.0f, 0.0f));
		Textures.loadTexture(cubemapTextures[4]);
		skyboxMesh.draw();
		
		skyboxShader.uniformMat4("model", Mat4f.rotate(0.0f, 0.0f, 1.0f, 0.0f));
		Textures.loadTexture(cubemapTextures[5]);
		skyboxMesh.draw();
		
		skyboxShader.unBind();
		glDepthMask(true);
	}
}