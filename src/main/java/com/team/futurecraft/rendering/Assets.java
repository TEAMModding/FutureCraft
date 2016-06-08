package com.team.futurecraft.rendering;

public class Assets {
	public static Texture[] spaceSkybox;
	
	public static Shader defaultShader;
	public static Shader skyboxShader;
	
	public static void loadAssets() {
		System.out.println("Loading Futurecraft assets from disk...");
		
		spaceSkybox = new Texture[] {
				new Texture("textures/environment/sky_pos_x.png"),
				new Texture("textures/environment/sky_neg_x.png"),
				new Texture("textures/environment/sky_pos_y.png"),
				new Texture("textures/environment/sky_neg_y.png"),
				new Texture("textures/environment/sky_pos_z.png"),
				new Texture("textures/environment/sky_neg_z.png")
		};
		
		defaultShader = new Shader("default");
		skyboxShader = new Shader("skybox");
		
		System.out.println("Load finished");
	}
}
