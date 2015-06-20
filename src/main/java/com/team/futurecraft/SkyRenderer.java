package com.team.futurecraft;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IRenderHandler;

/**
 * The SkyRendering class used for rendering the sky of planets.
 * Currently only used for the moon. This needs alot of work for making
 * atmospheres and moons and daylight cycles dependent on the planet's physical
 * and orbital parameters.
 * 
 * @author Joseph
 *
 */
public class SkyRenderer extends IRenderHandler {
	private static final ResourceLocation locationSkyPng = new ResourceLocation("futurecraft","textures/environment/stars.png");
	
	@Override
	public void render(float partialTicks, WorldClient world, Minecraft mc) {
		TextureManager renderEngine = mc.getTextureManager();
		GL11.glDisable(GL11.GL_FOG);
        GL11.glDisable(GL11.GL_ALPHA_TEST);
        GL11.glEnable(GL11.GL_BLEND);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        RenderHelper.disableStandardItemLighting();
        GL11.glDepthMask(false);
        renderEngine.bindTexture(locationSkyPng);
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer renderer = tessellator.getWorldRenderer();

        for (int i = 0; i < 6; ++i) {
            GL11.glPushMatrix();

            if (i == 1) {
                GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
            }

            if (i == 2) {
                GL11.glRotatef(-90.0F, 1.0F, 0.0F, 0.0F);
            }

            if (i == 3) {
                GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
            }

            if (i == 4) {
                GL11.glRotatef(90.0F, 0.0F, 0.0F, 1.0F);
            }

            if (i == 5) {
                GL11.glRotatef(-90.0F, 0.0F, 0.0F, 1.0F);
            }

            renderer.startDrawingQuads();
            renderer.setColorOpaque_I(2631720);
            renderer.addVertexWithUV(-100.0D, -100.0D, -100.0D, 0.0D, 0.0D);
            renderer.addVertexWithUV(-100.0D, -100.0D, 100.0D, 0.0D, 8.0D);
            renderer.addVertexWithUV(100.0D, -100.0D, 100.0D, 8.0D, 8.0D);
            renderer.addVertexWithUV(100.0D, -100.0D, -100.0D, 8.0D, 0.0D);
            tessellator.draw();
            GL11.glPopMatrix();
        }
        GL11.glDepthMask(true);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
	}
}
