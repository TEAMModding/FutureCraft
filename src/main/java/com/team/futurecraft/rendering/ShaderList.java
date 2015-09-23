package com.team.futurecraft.rendering;

public class ShaderList {
	public static int planetShader;
	public static int cloudShader;
	
	public static void loadShaders() {
		planetShader = ShaderUtil.loadShader("planet.vsh", "planet.fsh");
		cloudShader = ShaderUtil.loadShader("clouds.vsh", "clouds.fsh");
	}
}
