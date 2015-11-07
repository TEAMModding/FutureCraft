package com.team.futurecraft.rendering;

import net.minecraft.util.Vec3;

public class Camera {
	public Vec3 pos;
	public Vec3 rot;
	
	public Camera(Vec3 pos, Vec3 rot) {
		this.pos = pos;
		this.rot = rot;
	}
}
