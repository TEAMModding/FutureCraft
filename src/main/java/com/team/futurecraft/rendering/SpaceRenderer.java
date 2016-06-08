package com.team.futurecraft.rendering;

import com.team.futurecraft.FutureCraft;
import com.team.futurecraft.Mat4f;
import com.team.futurecraft.Vec3f;

import static org.lwjgl.opengl.GL11.*;

import net.minecraft.client.Minecraft;

public class SpaceRenderer {
	private static Minecraft mc = Minecraft.getMinecraft();
	private Mesh skyboxMesh;
	private Mesh mesh;
	
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
	
	public SpaceRenderer() {
		skyboxMesh = new Mesh(vertices, null, skyboxUvs, null);
		mesh = new Mesh(vertices, null, uvs, null);
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
		
		Assets.defaultShader.bind();
		
		Assets.defaultShader.uniformMat4("view", cam.getView());
		Assets.defaultShader.uniformMat4("projection", cam.getProjection());
		Assets.defaultShader.uniformMat4("model", Mat4f.translate(0.0f, 0.5f, -2.0f));
		Textures.loadTexture("textures/blocks/desert_iron_ore.png");
		
		mesh.draw();
		
		Assets.defaultShader.unBind();
		
		FutureCraft.SOL.render(cam, time);
		
		//glEnable(GL_CULL_FACE);
	}
	
	private void renderSkybox(Camera cam) {
		glDepthMask(false);
		Assets.skyboxShader.bind();
		
		Assets.skyboxShader.uniformMat4("view", cam.getView());
		Assets.skyboxShader.uniformMat4("projection", cam.getProjection());
		
		Assets.skyboxShader.uniformMat4("model", Mat4f.rotate(-90.0f, 0.0f, 1.0f, 0.0f));
		Assets.spaceSkybox[0].bind();
		skyboxMesh.draw();
		
		Assets.skyboxShader.uniformMat4("model", Mat4f.rotate(90.0f, 0.0f, 1.0f, 0.0f));
		Assets.spaceSkybox[1].bind();
		skyboxMesh.draw();
		
		Assets.skyboxShader.uniformMat4("model", Mat4f.rotate(180.0f, 0.0f, 0.0f, 1.0f).multiply(Mat4f.rotate(-90.0f, 1.0f, 0.0f, 0.0f)));
		Assets.spaceSkybox[2].bind();
		skyboxMesh.draw();

		Assets.skyboxShader.uniformMat4("model", Mat4f.rotate(180.0f, 0.0f, 0.0f, 1.0f).multiply(Mat4f.rotate(90.0f, 1.0f, 0.0f, 0.0f)));
		Assets.spaceSkybox[3].bind();
		skyboxMesh.draw();
		
		Assets.skyboxShader.uniformMat4("model", Mat4f.rotate(180.0f, 0.0f, 1.0f, 0.0f));
		Assets.spaceSkybox[4].bind();
		skyboxMesh.draw();
		
		Assets.skyboxShader.uniformMat4("model", Mat4f.rotate(0.0f, 0.0f, 1.0f, 0.0f));
		Assets.spaceSkybox[5].bind();
		skyboxMesh.draw();
		
		Assets.skyboxShader.unBind();
		glDepthMask(true);
	}
}