package com.team.futurecraft.space;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.glu.Sphere;

import com.team.futurecraft.Mat4f;
import com.team.futurecraft.rendering.Assets;
import com.team.futurecraft.rendering.Camera;
import com.team.futurecraft.rendering.Shader;
import com.team.futurecraft.rendering.Textures;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;

/**
 * The class that all stars should extend.
 * 
 * @author Joseph
 */
public class Star extends CelestialObject {
	public Star(CelestialObject parent) {
		super(parent);
	}

	/**
	 * This is definitely a star.
	 */
	public EnumCelestialType getType() {
		return EnumCelestialType.STAR;
	}
	
	/**
	 * No way we're landing on it.
	 */
	public boolean isLandable() {
		return true;
	}
	
	public void renderStatic(Minecraft mc) {
		
	}
	
	/**
	 * Render's an awesome star.
	 */
	public void render(Camera cam, float time) {
        Assets.starSurfaceShader.bind();
        float scaling = 1000000000f;
        //we translate the model by the camera position, and letthe actual view matrix stay at 0. This way the camera is the center
        //of the coordinate space and we won't get accuracy problems. We also scale down space so that 1 unit = 1,000,000,000 meters.
        Assets.starSurfaceShader.uniformMat4("model", new Mat4f().translate(-cam.position.x / scaling, -cam.position.y / scaling, -cam.position.z / scaling));
        Assets.starSurfaceShader.uniformMat4("view", cam.getViewSkybox());
        Assets.starSurfaceShader.uniformMat4("projection", cam.getProjection(0.1f, 1000f));
        
        Sphere sphere = new Sphere();
        sphere.setTextureFlag(false);
        sphere.setNormals(GLU.GLU_SMOOTH);
        sphere.draw((this.physical.diameter / 2) / 1000000000f, 100, 100);
        
        Shader.unbind();
        
        this.renderChildren(cam, time);
	}
}
