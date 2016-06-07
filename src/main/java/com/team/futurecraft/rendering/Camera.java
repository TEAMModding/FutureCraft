package com.team.futurecraft.rendering;

import com.team.futurecraft.Mat4f;
import com.team.futurecraft.Vec3f;

import net.minecraft.client.Minecraft;

public class Camera {
	public Vec3f position = new Vec3f(0.0f, 0.0f, 0.0f);
	public Vec3f front = new Vec3f(0.0f, 0.0f, 0.0f);
	public float pitch = 0.0f;
	public float yaw = -90.0f;
	public float roll = 0;
	public Vec3f up = new Vec3f(0.0f, 1.0f, 0.0f);
	
	private static float lastX = 400;
	private static float lastY = 300;
	
	/**
	 * Called by the game loop every frame. When we make GameObjects they'll all have this functionality for multiple objects. For now it's called directly
	 * from the loop.
	 * 
	 * TODO: this update it's own object instead of updating Engine.camera
	 */
	public void update() {
		
	}
	
	public Mat4f getView() {
		return Mat4f.LookAt(this.position, this.position.add(this.front), this.up);
	}

	public Mat4f getProjection() {
		Minecraft mc = Minecraft.getMinecraft();
		return Mat4f.perspective(mc.gameSettings.fovSetting, (float)mc.displayWidth / mc.displayHeight, 0.001f, 1000.0f);
	}

	public Vec3f getPosition() {
		return this.position;
	}
}