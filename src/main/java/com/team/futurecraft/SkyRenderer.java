package com.team.futurecraft;

import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.glu.Sphere;

import com.team.futurecraft.space.Planet;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;
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
public class SkyRenderer extends IRenderHandler {
	private static final ResourceLocation SKY_POS_X = new ResourceLocation("futurecraft","textures/environment/sky_pos_x.png");
	private static final ResourceLocation SKY_NEG_X = new ResourceLocation("futurecraft","textures/environment/sky_neg_x.png");
	private static final ResourceLocation SKY_POS_Y = new ResourceLocation("futurecraft","textures/environment/sky_pos_y.png");
	private static final ResourceLocation SKY_NEG_Y = new ResourceLocation("futurecraft","textures/environment/sky_neg_y.png");
	private static final ResourceLocation SKY_POS_Z = new ResourceLocation("futurecraft","textures/environment/sky_pos_z.png");
	private static final ResourceLocation SKY_NEG_Z = new ResourceLocation("futurecraft","textures/environment/sky_neg_z.png");
	private Planet planet;
	
	public SkyRenderer(Planet planet) {
		this.planet = planet;
	}
	
	@Override
	public void render(float partialTicks, WorldClient world, Minecraft mc) {
		TextureManager renderEngine = mc.getTextureManager();
		GL11.glDisable(GL11.GL_FOG);
        GL11.glDisable(GL11.GL_ALPHA_TEST);
        GL11.glEnable(GL11.GL_BLEND);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        RenderHelper.disableStandardItemLighting();
        GL11.glDepthMask(false);
        renderEngine.bindTexture(SKY_NEG_Y);
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer renderer = tessellator.getWorldRenderer();

        for (int i = 0; i < 6; ++i) {
            GL11.glPushMatrix();
            GL11.glRotatef(45.0F, 0.0F, 0.0F, 1.0F);

            if (i == 1) {
            	renderEngine.bindTexture(SKY_POS_Z);
                GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
            }

            if (i == 2) {
            	renderEngine.bindTexture(SKY_NEG_Z);
            	GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
                GL11.glRotatef(-90.0F, 1.0F, 0.0F, 0.0F);
            }

            if (i == 3) {
            	renderEngine.bindTexture(SKY_POS_Y);
                GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
            }

            if (i == 4) {
            	renderEngine.bindTexture(SKY_POS_X);
            	GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
                GL11.glRotatef(90.0F, 0.0F, 0.0F, 1.0F);
            }

            if (i == 5) {
            	renderEngine.bindTexture(SKY_NEG_X);
            	GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
                GL11.glRotatef(-90.0F, 0.0F, 0.0F, 1.0F);
            }

            renderer.startDrawingQuads();
            renderer.setColorOpaque_I(0xFFFFFF);
            renderer.addVertexWithUV(-100.0D, -100.0D, -100.0D, 0.0D, 0.0D);
            renderer.addVertexWithUV(-100.0D, -100.0D, 100.0D, 0.0D, 1.0D);
            renderer.addVertexWithUV(100.0D, -100.0D, 100.0D, 1.0D, 1.0D);
            renderer.addVertexWithUV(100.0D, -100.0D, -100.0D, 1.0D, 0.0D);
            tessellator.draw();
            GL11.glPopMatrix();
        }
        renderAtmosphere();
        
        GL11.glTranslatef(0, -100010, 0);
        renderPlanet(new ResourceLocation("futurecraft", "textures/environment/" + this.planet.getTexture()), 100000, mc);
        GL11.glTranslatef(0, 100010, 0);
        
        GL11.glDepthMask(true);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
	}
	
	private void renderAtmosphere() {
		Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer renderer = tessellator.getWorldRenderer();
		Vec3 colors = this.planet.getAtmosphericColor();
        
        for (int i = 0; i < 4; i++) {
        	GL11.glPushMatrix();
        	
        	if (i == 1) GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
        	if (i == 2) GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
        	if (i == 3) GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
        	
        	GlStateManager.disableTexture2D();
        	GlStateManager.enableBlend();
        	GlStateManager.disableAlpha();
        	GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        	GlStateManager.shadeModel(7425);
        	renderer.startDrawingQuads();
        	renderer.setColorRGBA_F((float)colors.xCoord, (float)colors.yCoord, (float)colors.zCoord, this.planet.getAtmosphericDensity());
        	renderer.addVertex(-100.0D, -100.0D, -100.0D);
        	renderer.addVertex(100.0D, -100.0D, -100.0D);
        	renderer.setColorRGBA_F((float)colors.xCoord, (float)colors.yCoord, (float)colors.zCoord, this.planet.getAtmosphericDensity() - 0.5f);
        	renderer.addVertex(100.0D, 100.0D, -100.0D);
        	renderer.addVertex(-100.0D, 100.0D, -100.0D);
        	tessellator.draw();
        	GL11.glPopMatrix();
        }
        renderer.startDrawingQuads();
    	renderer.setColorRGBA_F((float)colors.xCoord, (float)colors.yCoord, (float)colors.zCoord, this.planet.getAtmosphericDensity() - 0.5f);
    	renderer.addVertex(-99.0D, 99.0D, -99.0D);
    	renderer.addVertex(99.0D, 99.0D, -99.0D);
    	renderer.addVertex(99.0D, 99.0D, 99.0D);
    	renderer.addVertex(-99.0D, 99.0D, 99.0D);
    	tessellator.draw();
        
        GlStateManager.enableTexture2D();
	}
	
	/**
	 * Renders the planet with the specified image.
	 */
	private void renderPlanet(ResourceLocation img, float size, Minecraft mc) {
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
	}
}
