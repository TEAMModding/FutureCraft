package com.team.futurecraft.rendering;

import org.lwjgl.opengl.GL11;

import com.team.futurecraft.FutureCraft;
import com.team.futurecraft.space.Planet;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.util.Vec3;
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
public class PlanetSkyRenderer extends IRenderHandler {
	private Planet planet;
	private static final float TIME_SCALE = 0.0003f;
	private float time;
	
	public PlanetSkyRenderer(Planet planet) {
		this.planet = planet;
	}
	
	@Override
	public void render(float partialTicks, WorldClient world, Minecraft mc) {
		GL11.glDepthMask(false);
		GL11.glDisable(GL11.GL_FOG);
		GlStateManager.enableAlpha();
		time = (float) (System.nanoTime() * 0.000000001 * TIME_SCALE) + FutureCraft.timeOffset;
		SpaceRenderer render = new SpaceRenderer(mc, false);
		render.render(new Vec3(mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch, 0), new Vec3(0, 0, 0), this.planet, time, true);
		renderAtmosphere(mc);
		//System.out.println(time);
		GL11.glDepthMask(true);
	}
	
	private void renderAtmosphere(Minecraft mc) {
		Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer renderer = tessellator.getWorldRenderer();
		Vec3 colors = this.planet.getAtmosphericColor();
        
        for (int i = 0; i < 2; i++) {
        	GL11.glPushMatrix();
        	GL11.glRotatef(-mc.thePlayer.rotationYaw, 0, 1, 0);
        	
        	//if (i == 1) GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
        	if (i == 1) GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
        	//if (i == 3) GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
        	
        	GlStateManager.disableTexture2D();
        	GlStateManager.enableBlend();
        	GlStateManager.disableAlpha();
        	GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        	GlStateManager.shadeModel(7425);
        	renderer.startDrawingQuads();
        	renderer.setColorRGBA_F((float)colors.xCoord, (float)colors.yCoord, (float)colors.zCoord, this.planet.getAtmosphericDensity());
        	renderer.addVertex(-500.0D, -100.0D, -100.0D);
        	renderer.addVertex(500.0D, -100.0D, -100.0D);
        	renderer.setColorRGBA_F((float)colors.xCoord, (float)colors.yCoord, (float)colors.zCoord, this.planet.getAtmosphericDensity() - 0.5f);
        	renderer.addVertex(500.0D, 100.0D, -100.0D);
        	renderer.addVertex(-500.0D, 100.0D, -100.0D);
        	tessellator.draw();
        	GL11.glPopMatrix();
        }
        GL11.glPushMatrix();
        GL11.glRotatef(-mc.thePlayer.rotationYaw, 0, 1, 0);
        renderer.startDrawingQuads();
    	renderer.setColorRGBA_F((float)colors.xCoord, (float)colors.yCoord, (float)colors.zCoord, this.planet.getAtmosphericDensity() - 0.5f);
    	renderer.addVertex(-500.0D, 100.0D, -100.0D);
    	renderer.addVertex(500.0D, 100.0D, -100.0D);
    	renderer.addVertex(500.0D, 100.0D, 100.0D);
    	renderer.addVertex(-500.0D, 100.0D, 100.0D);
    	tessellator.draw();
    	GL11.glPopMatrix();
        
        GlStateManager.enableTexture2D();
	}
	
	/**
	 * Renders the planet with the specified image.
	 */
	/*private void renderPlanet(ResourceLocation img, float size, Minecraft mc) {
		glPushMatrix();
        mc.getTextureManager().bindTexture(img);
		glColor3f(1, 1, 1);
        
        
        mc.getTextureManager().bindTexture(img);
        glColor3f(1, 1, 1);
        Sphere sphere = new Sphere();
        sphere.setTextureFlag(true);
        sphere.setNormals(GLU.GLU_SMOOTH);
        sphere.draw(size, 100, 100);
        glPopMatrix();
	}*/
}
