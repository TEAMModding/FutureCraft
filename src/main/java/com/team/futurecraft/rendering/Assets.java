package com.team.futurecraft.rendering;

public class Assets {
	public static Texture[] spaceSkybox;
	
	public static Shader defaultShader;
	public static Shader skyboxShader;
	public static Shader framebufferShader;
	public static Shader starSurfaceShader;
	public static Shader planetSurfaceShader;
	public static Shader orbitShader;
	public static Shader gridShader;
	
	public static Mesh framebufferMesh;
	
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
		framebufferShader = new Shader("framebuffer");
		starSurfaceShader = new Shader("star_surface");
		planetSurfaceShader = new Shader("planet_surface");
		orbitShader = new Shader("orbit");
		gridShader = new Shader("grid");
		
		framebufferMesh = new Mesh(
			new float[] {
				-1.0f,  1.0f, 0.0f,
			    -1.0f, -1.0f, 0.0f,
			     1.0f, -1.0f, 0.0f,
			     1.0f, -1.0f, 0.0f,
			     1.0f,  1.0f, 0.0f,
			    -1.0f,  1.0f, 0.0f
			},
			null, new float[] {
				0.0f, 1.0f,
				0.0f, 0.0f,
				1.0f, 0.0f,
				1.0f, 0.0f,
				1.0f, 1.0f,
				0.0f, 1.0f
			}, null);
		
		System.out.println("Load finished");
	}
}
