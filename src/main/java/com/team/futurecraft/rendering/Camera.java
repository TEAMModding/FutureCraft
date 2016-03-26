package com.team.futurecraft.rendering;

import com.team.futurecraft.Vec3f;

public class Camera {
	public Vec3f pos;
	public Vec3f rot;
	
	public Camera(Vec3f pos, Vec3f rot) {
		this.pos = pos;
		this.rot = rot;
	}
}
