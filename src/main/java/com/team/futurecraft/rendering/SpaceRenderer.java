package com.team.futurecraft.rendering;

import com.team.futurecraft.FutureCraft;
import com.team.futurecraft.Mat4f;
import com.team.futurecraft.Vec3f;
import com.team.futurecraft.space.SpaceJSONLoader;

import static org.lwjgl.opengl.GL11.*;

import net.minecraft.client.Minecraft;

public class SpaceRenderer {
	private static Minecraft mc = Minecraft.getMinecraft();
	private Mesh skyboxMesh;
	private Mesh mesh;
	private GLFramebuffer buffer;
	
	private static float[] vertices = new float[] {
			// Positions
			-1.0f,  1.0f, -1.0f,
		    -1.0f, -1.0f, -1.0f,
		     1.0f, -1.0f, -1.0f,
		     1.0f, -1.0f, -1.0f,
		     1.0f,  1.0f, -1.0f,
		    -1.0f,  1.0f, -1.0f 
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
		mat.rotate((float)rot.z, 0, 0F, 1f);
		mat.rotate((float)rot.y, 1F, 0F, 0F);
		mat.rotate((float)rot.x, 0F, 1F, 0F);
		mat.translate(-pos.x, -pos.y, -pos.z);
		
		return mat;
	}
	
	public static Mat4f getProjectionMatrix() {
		return Mat4f.perspective(mc.gameSettings.fovSetting, (float)mc.displayWidth / mc.displayHeight, 0.000001F, 10);
	}
	
	public void render(Camera cam, long time, boolean renderOrbits) {
		if (buffer == null) {
			buffer = new GLFramebuffer(mc.displayWidth, mc.displayHeight, 1);
		}
		glEnable(GL_DEPTH_TEST);
		//glDisable(GL_CULL_FACE);
		//glDepthFunc(GL_LEQUAL);
		//glDepthMask(true);
		//glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		
		buffer.bind();
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		renderSkybox(cam);
		
		//glDepthMask(true);
		
		Assets.defaultShader.bind();
		float scaling = 1000000000f;
		
		renderGrid(cam);
		
		Shader.unbind();
		
		SpaceJSONLoader.starSystem.renderOrbits(cam, time);
		SpaceJSONLoader.starSystem.render(cam, time);
		
		//System.out.println(SpaceJSONLoader.starSystem.getChildren()[0].orbit.semiMajorAxis);
		
		//glEnable(GL_CULL_FACE);
		
		buffer.render(Assets.framebufferShader);
	}
	
	private void singleGrid(int x, int y) {
		glVertex3f(0 + x, 0, 0 + y);
		glVertex3f(1 + x, 0, 0 + y);
		
		glVertex3f(0 + x, 0, 0 + y);
		glVertex3f(0 + x, 0, 1 + y);
	}
	
	private void renderGrid(Camera cam) {
		float scaling = 1000000000f;
		
		Assets.gridShader.bind();
		
		Assets.gridShader.uniformMat4("view", cam.getViewSkybox().translate(-cam.position.x / scaling, -cam.position.y / scaling, -cam.position.z / scaling));
		Assets.gridShader.uniformMat4("projection", cam.getProjection(0.01f, 10000));
		Assets.gridShader.uniformMat4("model", new Mat4f().translate((int)(cam.position.x / scaling), 0, (int)(cam.position.z / scaling)));
		
		int size = 20;
		
		glBegin(GL_LINES);
		glColor4f(1.0f, 1.0f, 1.0f, 0.15f);
		for (int x = 0; x < size; x++) {
			for (int y = 0; y < size; y++) {
				singleGrid(x, y);
			}
		}
		
		for (int x = -1; x > -size; x--) {
			for (int y = 0; y < size; y++) {
				singleGrid(x, y);
			}
		}
		
		for (int x = 0; x < size; x++) {
			for (int y = -1; y > -size; y--) {
				singleGrid(x, y);
			}
		}
		
		for (int x = -1; x > -size; x--) {
			for (int y = -1; y > -size; y--) {
				singleGrid(x, y);
			}
		}
		
		
		glEnd();
	}
	
	private void renderSkybox(Camera cam) {
		glDepthMask(false);
		Assets.skyboxShader.bind();
		
		Assets.skyboxShader.uniformMat4("view", cam.getViewSkybox());
		Assets.skyboxShader.uniformMat4("projection", cam.getProjection(0.01f, 10));
		
		Assets.skyboxShader.uniformMat4("model", new Mat4f().rotate(-90.0f, 0.0f, 1.0f, 0.0f));
		Assets.spaceSkybox[0].bind();
		skyboxMesh.draw();
		
		Assets.skyboxShader.uniformMat4("model", new Mat4f().rotate(90.0f, 0.0f, 1.0f, 0.0f));
		Assets.spaceSkybox[1].bind();
		skyboxMesh.draw();
		
		Assets.skyboxShader.uniformMat4("model", new Mat4f().rotate(180.0f, 0.0f, 0.0f, 1.0f).rotate(-90.0f, 1.0f, 0.0f, 0.0f));
		Assets.spaceSkybox[2].bind();
		skyboxMesh.draw();

		Assets.skyboxShader.uniformMat4("model", new Mat4f().rotate(180.0f, 0.0f, 0.0f, 1.0f).rotate(90.0f, 1.0f, 0.0f, 0.0f));
		Assets.spaceSkybox[3].bind();
		skyboxMesh.draw();
		
		Assets.skyboxShader.uniformMat4("model", new Mat4f().rotate(180.0f, 0.0f, 1.0f, 0.0f));
		Assets.spaceSkybox[4].bind();
		skyboxMesh.draw();
		
		Assets.skyboxShader.uniformMat4("model", new Mat4f().rotate(0.0f, 0.0f, 1.0f, 0.0f));
		Assets.spaceSkybox[5].bind();
		skyboxMesh.draw();
		
		Assets.skyboxShader.unbind();
		glDepthMask(true);
	}
}