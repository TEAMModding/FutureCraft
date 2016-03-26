package com.team.futurecraft.rendering;

import static org.lwjgl.opengl.GL11.*;

import com.team.futurecraft.Vec4f;
import com.team.futurecraft.Vec3f;
import com.team.futurecraft.space.Planet;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraftforge.client.IRenderHandler;

/**
 * The SkyRendering class used for rendering the sky of planets.
 * 
 * @author Joseph
 *
 */
public class PlanetSkyRenderer extends IRenderHandler {
	private Planet planet;
	private long time = 62755776000L;
	
	public PlanetSkyRenderer(Planet planet) {
		this.planet = planet;
	}
	
	@Override
	public void render(float partialTicks, WorldClient world, Minecraft mc) {
		glDepthMask(false);
		glDisable(GL_FOG);
		GlStateManager.enableAlpha();
		
		SpaceRenderer render = new SpaceRenderer();
		Camera cam = new Camera(this.planet.getPosition(time), new Vec3f(mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch, 0));
		render.render(cam, time, false);
		renderAtmosphere(mc);
		glDepthMask(true);
	}
	
	private void renderAtmosphere(Minecraft mc) {
		Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer renderer = tessellator.getWorldRenderer();
		Vec4f colors = this.planet.atmosphere;
        
        for (int i = 0; i < 2; i++) {
        	glPushMatrix();
        	glRotatef(-mc.thePlayer.rotationYaw, 0, 1, 0);
        	
        	//if (i == 1) GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
        	if (i == 1) glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
        	//if (i == 3) GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
        	
        	GlStateManager.disableTexture2D();
        	GlStateManager.enableBlend();
        	GlStateManager.disableAlpha();
        	GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        	GlStateManager.shadeModel(7425);
        	renderer.startDrawingQuads();
        	renderer.setColorRGBA_F((float)colors.x, (float)colors.y, (float)colors.z, (float)colors.w);
        	renderer.addVertex(-500.0D, -100.0D, -100.0D);
        	renderer.addVertex(500.0D, -100.0D, -100.0D);
        	renderer.setColorRGBA_F((float)colors.x, (float)colors.y, (float)colors.z, (float)colors.w - 0.5f);
        	renderer.addVertex(500.0D, 100.0D, -100.0D);
        	renderer.addVertex(-500.0D, 100.0D, -100.0D);
        	tessellator.draw();
        	glPopMatrix();
        }
        glPushMatrix();
        glRotatef(-mc.thePlayer.rotationYaw, 0, 1, 0);
        renderer.startDrawingQuads();
    	renderer.setColorRGBA_F((float)colors.x, (float)colors.y, (float)colors.z, (float)colors.w - 0.5f);
    	renderer.addVertex(-500.0D, 100.0D, -100.0D);
    	renderer.addVertex(500.0D, 100.0D, -100.0D);
    	renderer.addVertex(500.0D, 100.0D, 100.0D);
    	renderer.addVertex(-500.0D, 100.0D, 100.0D);
    	tessellator.draw();
    	glPopMatrix();
        
        GlStateManager.enableTexture2D();
	}
}
