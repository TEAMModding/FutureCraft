package com.team.futurecraft.space;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.glu.Sphere;

import com.team.futurecraft.rendering.Camera;
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
public abstract class Star extends CelestialObject {
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
		Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer renderer = tessellator.getWorldRenderer();
        
        //draw the star itself
		glPushMatrix();
		GlStateManager.disableLighting();
		GlStateManager.disableTexture2D();
		glTranslatef(0, 0, 0);
		glColor3f(2.0f, 2.0f, 2.0f);
        Sphere sphere = new Sphere();
        sphere.setTextureFlag(false);
        sphere.setNormals(GLU.GLU_SMOOTH);
        sphere.draw((this.physical.diameter / 1000000) * 2, 100, 100);
        glPopMatrix();
        
        //draw the star glow
        /*glPushMatrix();
        GlStateManager.enableTexture2D();
        glBlendFunc(GL_ONE, GL_ONE);
        Textures.loadTexture("textures/environment/star_glow.png");
        //glRotatef((float)-cam.rot.x, 0, 1, 0);
        //glRotatef((float)-cam.rot.y, 1, 0, 0);
        float glowSize = ((this.physical.diameter / 1000000) * 2) * 20;
        
        renderer.startDrawingQuads();
        renderer.setColorRGBA(255, 255, 255, 255);
        renderer.addVertexWithUV(-glowSize, -glowSize, 0, 0, 1);
        renderer.addVertexWithUV(glowSize, -glowSize, 0, 1, 1);
        renderer.addVertexWithUV(glowSize, glowSize, 0, 1, 0);
        renderer.addVertexWithUV(-glowSize, glowSize, 0, 0, 0);
        tessellator.draw();
        
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glPopMatrix();*/
        
        GlStateManager.enableLighting();
        GlStateManager.enableTexture2D();
        this.renderChildren(cam, time);
	}
}
