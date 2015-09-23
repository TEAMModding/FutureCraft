package com.team.futurecraft.rendering;

import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_SMOOTH;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glPopMatrix;

import java.nio.FloatBuffer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.Project;

import com.team.futurecraft.Mat4;
import com.team.futurecraft.space.CelestialObject;
import com.team.futurecraft.space.Sol;

public class SpaceRenderer {
	private Minecraft mc;
	private boolean showOrbit;
	private static FloatBuffer colorBuffer = GLAllocation.createDirectFloatBuffer(16);
	
	public SpaceRenderer(Minecraft mc, boolean showOrbit) {
		this.mc = mc;
		this.showOrbit = showOrbit;
	}
	
	public static Mat4 getViewMatrix(Vec3 pos, Vec3 rot) {
		return Mat4.rotate((float)rot.yCoord, 1F, 0F, 0F).multiply(Mat4.rotate((float)rot.xCoord, 0F, 1F, 0F)).multiply(Mat4.translate(pos.xCoord, pos.yCoord, pos.zCoord));
	}
	
	public static Mat4 getProjectionMatrix(Minecraft mc) {
		return Mat4.perspective(70f, (float)mc.displayWidth / mc.displayHeight, 0.001F, 1000000);
	}
	
	public void render(Vec3 rot, Vec3 pos, CelestialObject following, float time, boolean onPlanet) {
		setupRendering(time, rot, pos, following, onPlanet);
		
		Sol sol = new Sol(null);
		if (onPlanet) {
			Vec3 newRot = rot.subtract(new Vec3(time * following.getOrbit().rotationPeriod, 0, 45));
			Vec3 PlanetPos = following.getPosition(time);
			sol.render(newRot, new Vec3(-PlanetPos.xCoord, -PlanetPos.yCoord, -PlanetPos.zCoord), this.mc, time, showOrbit);
		}
		else {
			sol.renderOrbits(time);
			Vec3 PlanetPos = following.getPosition(time);
			sol.render(rot, new Vec3(-PlanetPos.xCoord + pos.xCoord, -PlanetPos.yCoord + pos.yCoord, -PlanetPos.zCoord + pos.zCoord), this.mc, time, showOrbit);
		}
		revertRendering();
	}
	
	/**
	 * Sets up 3d projection, shadowing, skybox, all that good stuff.
	 */
	private void setupRendering(float time, Vec3 rot, Vec3 pos, CelestialObject following, boolean onPlanet) {
		//sets up the camera projection and position
		GlStateManager.matrixMode(GL_PROJECTION);
        GlStateManager.pushMatrix();
        GlStateManager.loadIdentity();
        Project.gluPerspective(70f, (float)mc.displayWidth / mc.displayHeight, 0.001F, 1000000);
        
        GL11.glRotatef((float)rot.yCoord, 1F, 0F, 0F);
        GL11.glRotatef((float)rot.xCoord, 0F, 1F, 0F);
        GL11.glRotatef((float)rot.zCoord, 0F, 0F, 1F);
        
        if (onPlanet) {
        	GL11.glRotatef(-45, 0, 0, 1);
        	GL11.glRotatef(-time * following.getOrbit().rotationPeriod, 0, 1, 0);
        }
        
        Vec3 planetPos = following.getPosition(time);
        GL11.glTranslatef((float)-planetPos.xCoord, (float)-planetPos.yCoord, (float)-planetPos.zCoord);
        GL11.glTranslatef((float)pos.xCoord, (float)pos.yCoord, (float)pos.zCoord);
        //sets up the model space
        GlStateManager.matrixMode(GL_MODELVIEW);
        GlStateManager.pushMatrix();
        GlStateManager.loadIdentity();
        //glScalef(0.6f, 1f, 0.6f);
        //GL11.glRotatef(-time * following.getOrbit().getRotation(), 0, 1, 0);
        
        //enables depth
        glEnable(GL_DEPTH_TEST);
        GlStateManager.enableAlpha();
        GlStateManager.enableBlend();
        
        GlStateManager.pushMatrix();
        
        renderSkybox(pos.add(planetPos));
        
        //sets up shadowing
        GlStateManager.enableLighting();
        GlStateManager.enableLight(0);
        
        GL11.glLight(GL11.GL_LIGHT0, GL11.GL_POSITION, setColorBuffer(0f, 0f, 0f, 1f));
        GL11.glLight(GL11.GL_LIGHT0, GL11.GL_DIFFUSE, setColorBuffer(0.6f, 0.6f, 0.6f, 1.0F));
        GL11.glLight(GL11.GL_LIGHT0, GL11.GL_AMBIENT, setColorBuffer(0.0F, 0.0F, 0.0F, 1.0F));
        GL11.glLight(GL11.GL_LIGHT0, GL11.GL_SPECULAR, setColorBuffer(0f, 0f, 0f, 1.0F));
        
        GlStateManager.shadeModel(GL_SMOOTH);
        GL11.glLightModel(GL11.GL_LIGHT_MODEL_AMBIENT, setColorBuffer(0f, 0f, 0f, 1.0F));
	}
	
	/**
	 * Gets rid of all our good stuff so minecraft can go happilly along with bad graphics.
	 */
	private void revertRendering() {
		GlStateManager.disableLighting();
		glPopMatrix();
        GlStateManager.matrixMode(GL_PROJECTION);
        GlStateManager.popMatrix();
        GlStateManager.matrixMode(GL_MODELVIEW);
        GlStateManager.popMatrix();
	}
	
	/**
	 * Renders the skybox.....
	 */
	private void renderSkybox(Vec3 pos) {
		ResourceLocation SKY_POS_X = new ResourceLocation("futurecraft","textures/environment/sky_pos_x.png");
		ResourceLocation SKY_NEG_X = new ResourceLocation("futurecraft","textures/environment/sky_neg_x.png");
		ResourceLocation SKY_POS_Y = new ResourceLocation("futurecraft","textures/environment/sky_pos_y.png");
		ResourceLocation SKY_NEG_Y = new ResourceLocation("futurecraft","textures/environment/sky_neg_y.png");
		ResourceLocation SKY_POS_Z = new ResourceLocation("futurecraft","textures/environment/sky_pos_z.png");
		ResourceLocation SKY_NEG_Z = new ResourceLocation("futurecraft","textures/environment/sky_neg_z.png");
		
		this.mc.getTextureManager().bindTexture(SKY_NEG_Y);
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer renderer = tessellator.getWorldRenderer();
        
        GL11.glPushMatrix();
        GL11.glTranslated(pos.xCoord, pos.yCoord, pos.zCoord);

        for (int i = 0; i < 6; ++i) {
            GL11.glPushMatrix();
            GL11.glRotatef(45.0F, 0.0F, 0.0F, 1.0F);

            if (i == 1) {
            	this.mc.getTextureManager().bindTexture(SKY_POS_Z);
                GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
            }

            if (i == 2) {
            	this.mc.getTextureManager().bindTexture(SKY_NEG_Z);
            	GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
                GL11.glRotatef(-90.0F, 1.0F, 0.0F, 0.0F);
            }

            if (i == 3) {
            	this.mc.getTextureManager().bindTexture(SKY_POS_Y);
                GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
            }

            if (i == 4) {
            	this.mc.getTextureManager().bindTexture(SKY_POS_X);
            	GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
                GL11.glRotatef(90.0F, 0.0F, 0.0F, 1.0F);
            }

            if (i == 5) {
            	this.mc.getTextureManager().bindTexture(SKY_NEG_X);
            	GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
                GL11.glRotatef(-90.0F, 0.0F, 0.0F, 1.0F);
            }

            renderer.startDrawingQuads();
            renderer.setColorOpaque_I(0xFFFFFF);
            renderer.addVertexWithUV(-10000.0D, -10000.0D, -10000.0D, 0.0D, 0.0D);
            renderer.addVertexWithUV(-10000.0D, -10000.0D, 10000.0D, 0.0D, 1.0D);
            renderer.addVertexWithUV(10000.0D, -10000.0D, 10000.0D, 1.0D, 1.0D);
            renderer.addVertexWithUV(10000.0D, -10000.0D, -10000.0D, 1.0D, 0.0D);
            tessellator.draw();
            GL11.glPopMatrix();
        }
        GL11.glPopMatrix();
	}
	
	/**
	 * Turns an RGBA color into a buffer for use in shadowing.
	 */
	public static FloatBuffer setColorBuffer(float p_74521_0_, float p_74521_1_, float p_74521_2_, float p_74521_3_)
    {
        colorBuffer.clear();
        colorBuffer.put(p_74521_0_).put(p_74521_1_).put(p_74521_2_).put(p_74521_3_);
        colorBuffer.flip();
        /** Float buffer used to set OpenGL material colors */
        return colorBuffer;
    }
}
