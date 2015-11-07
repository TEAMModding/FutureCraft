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
	private static Minecraft mc = Minecraft.getMinecraft();
	private static FloatBuffer colorBuffer = GLAllocation.createDirectFloatBuffer(16);
	
	public static Mat4 getViewMatrix(Vec3 pos, Vec3 rot) {
		Mat4 mat = new Mat4();
		mat = mat.multiply(Mat4.rotate((float)rot.zCoord, 0, 0F, 1f));
		mat = mat.multiply(Mat4.rotate((float)rot.yCoord, 1F, 0F, 0F));
		mat = mat.multiply(Mat4.rotate((float)rot.xCoord, 0F, 1F, 0F));
		mat = mat.multiply(Mat4.translate(-pos.xCoord, -pos.yCoord, -pos.zCoord));
		
		return mat;
	}
	
	public static Mat4 getProjectionMatrix() {
		return Mat4.perspective(mc.gameSettings.fovSetting, (float)mc.displayWidth / mc.displayHeight, 0.0001F, 10);
	}
	
	public void render(Camera cam, long time, boolean renderOrbits) {
		setupRendering(cam, time);
		
		Sol sol = new Sol(null);
		if (renderOrbits) {
			sol.renderOrbits(time);
			sol.render(cam, time);
		}
		else {
			sol.render(cam, time);
		}
		revertRendering();
	}
	
	/**
	 * Sets up 3d projection, shadowing, skybox, all that good stuff.
	 */
	private void setupRendering(Camera cam, long time) {
		//sets up the camera projection and position
		GlStateManager.matrixMode(GL_PROJECTION);
        GlStateManager.pushMatrix();
        GlStateManager.loadIdentity();
        Project.gluPerspective(mc.gameSettings.fovSetting, (float)mc.displayWidth / mc.displayHeight, 0.001F, 50000);
        
        GL11.glRotatef((float)cam.rot.yCoord, 1F, 0F, 0F);
        GL11.glRotatef((float)cam.rot.xCoord, 0F, 1F, 0F);
        GL11.glRotatef((float)cam.rot.zCoord, 0F, 0F, 1F);
        
        GL11.glTranslatef((float)-cam.pos.xCoord, (float)-cam.pos.yCoord, (float)-cam.pos.zCoord);
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
        
        renderSkybox(cam.pos);
        
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
	
	private void renderSkybox(Vec3 pos) {
		ResourceLocation SKY_POS_X = new ResourceLocation("futurecraft","textures/environment/sky_pos_x.png");
		ResourceLocation SKY_NEG_X = new ResourceLocation("futurecraft","textures/environment/sky_neg_x.png");
		ResourceLocation SKY_POS_Y = new ResourceLocation("futurecraft","textures/environment/sky_pos_y.png");
		ResourceLocation SKY_NEG_Y = new ResourceLocation("futurecraft","textures/environment/sky_neg_y.png");
		ResourceLocation SKY_POS_Z = new ResourceLocation("futurecraft","textures/environment/sky_pos_z.png");
		ResourceLocation SKY_NEG_Z = new ResourceLocation("futurecraft","textures/environment/sky_neg_z.png");
		
		mc.getTextureManager().bindTexture(SKY_NEG_Y);
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer renderer = tessellator.getWorldRenderer();
        
        GL11.glPushMatrix();
        GL11.glTranslated(pos.xCoord, pos.yCoord, pos.zCoord);

        for (int i = 0; i < 6; ++i) {
            GL11.glPushMatrix();
            GL11.glRotatef(45.0F, 0.0F, 0.0F, 1.0F);

            if (i == 1) {
            	mc.getTextureManager().bindTexture(SKY_POS_Z);
                GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
            }

            if (i == 2) {
            	mc.getTextureManager().bindTexture(SKY_NEG_Z);
            	GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
                GL11.glRotatef(-90.0F, 1.0F, 0.0F, 0.0F);
            }

            if (i == 3) {
            	mc.getTextureManager().bindTexture(SKY_POS_Y);
                GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
            }

            if (i == 4) {
            	mc.getTextureManager().bindTexture(SKY_POS_X);
            	GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
                GL11.glRotatef(90.0F, 0.0F, 0.0F, 1.0F);
            }

            if (i == 5) {
            	mc.getTextureManager().bindTexture(SKY_NEG_X);
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
