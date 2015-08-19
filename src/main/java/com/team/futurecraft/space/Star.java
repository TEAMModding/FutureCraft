package com.team.futurecraft.space;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.glu.Sphere;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;

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
	
	/**
	 * This will determine the star's color in the future,
	 * for now stars are just white, and this is useless.
	 */
	public abstract int getTemperature();
	
	/**
	 * Render's an awesome star.
	 */
	public void render(Vec3 rotation, Vec3 pos, Minecraft mc, float time, boolean showOrbit) {
		Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer renderer = tessellator.getWorldRenderer();
        ResourceLocation img = new ResourceLocation("futurecraft", "textures/environment/star_glow.png");
        
        //draw the star itself
		glPushMatrix();
		GlStateManager.disableLighting();
		GlStateManager.disableTexture2D();
		glTranslatef(0, 0, 0);
		glColor3f(2.0f, 2.0f, 2.0f);
        Sphere sphere = new Sphere();
        sphere.setTextureFlag(false);
        sphere.setNormals(GLU.GLU_SMOOTH);
        sphere.draw(this.getDiameter(), 100, 100);
        glPopMatrix();
        
        //draw the star glow
        glPushMatrix();
        GlStateManager.enableTexture2D();
        glBlendFunc(GL_ONE, GL_ONE);
        mc.getTextureManager().bindTexture(img);
        glRotatef((float)-rotation.xCoord, 0, 1, 0);
        glRotatef((float)-rotation.yCoord, 1, 0, 0);
        float glowSize = this.getDiameter() * 20;
        
        renderer.startDrawingQuads();
        renderer.setColorRGBA(255, 255, 255, 255);
        renderer.addVertexWithUV(-glowSize, -glowSize, 0, 0, 1);
        renderer.addVertexWithUV(glowSize, -glowSize, 0, 1, 1);
        renderer.addVertexWithUV(glowSize, glowSize, 0, 1, 0);
        renderer.addVertexWithUV(-glowSize, glowSize, 0, 0, 0);
        tessellator.draw();
        
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glPopMatrix();
        
        GlStateManager.enableLighting();
        this.renderChildren(rotation, pos, mc, time, showOrbit);
	}
}
