package com.team.futurecraft.gui;

import static org.lwjgl.opengl.GL11.GL_SMOOTH;

import org.lwjgl.opengl.GL11;

import com.team.futurecraft.rendering.SpaceRenderer;
import com.team.futurecraft.space.CelestialObject;
import com.team.futurecraft.space.Planet;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

/**
 * Overrided version of GuiButton which renders a planet on it.
 * 
 * @author Joseph
 *
 */
public class GuiPlanetButton extends GuiButton
{

	protected static final ResourceLocation buttonTextures = new ResourceLocation("futurecraft", "textures/gui/space_widgets.png");
	private CelestialObject planet;
	
	public GuiPlanetButton(int buttonId, int x, int y, CelestialObject planet) {
		super(buttonId, x, y, 150, 50, planet.getName());
		this.planet = planet;
	}
	
	public void drawButton(Minecraft mc, int mouseX, int mouseY)
    {
        if (this.visible)
        {
        	GlStateManager.enableLighting();
            GlStateManager.enableLight(0);
            
            GL11.glLight(GL11.GL_LIGHT0, GL11.GL_POSITION, SpaceRenderer.setColorBuffer(1f, 0f, -1f, 0f));
            GL11.glLight(GL11.GL_LIGHT0, GL11.GL_DIFFUSE, SpaceRenderer.setColorBuffer(1f, 1f, 1f, 1.0F));
            GL11.glLight(GL11.GL_LIGHT0, GL11.GL_AMBIENT, SpaceRenderer.setColorBuffer(0.0F, 0.0F, 0.0F, 1.0F));
            GL11.glLight(GL11.GL_LIGHT0, GL11.GL_SPECULAR, SpaceRenderer.setColorBuffer(1f, 1f, 1f, 1.0F));
            
            GlStateManager.shadeModel(GL_SMOOTH);
            GL11.glLightModel(GL11.GL_LIGHT_MODEL_AMBIENT, SpaceRenderer.setColorBuffer(0f, 0f, 0f, 1.0F));
        	
        	GL11.glPushMatrix();
        	GL11.glTranslatef(this.xPosition + 25, this.yPosition + 25, 30);
        	GL11.glRotatef(-90, 1, 0, 0);
        	GL11.glScalef(2, 2, 2);
        	((Planet)this.planet).renderStatic(mc);
        	GL11.glPopMatrix();
        	
        	GlStateManager.disableLighting();
        	
        	this.hovered = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
        	
        	if (this.hovered)
        		Gui.drawRect(this.xPosition, this.yPosition, this.xPosition + this.width, this.yPosition + this.height, 0xFF5555FF);
        	else 
        		Gui.drawRect(this.xPosition, this.yPosition, this.xPosition + this.width, this.yPosition + this.height, 0xAA555555);
        	this.drawHorizontalLine(this.xPosition, this.xPosition + this.width, this.yPosition, 0xAA5555FF);
        	this.drawHorizontalLine(this.xPosition, this.xPosition + this.width, this.yPosition + this.height, 0xAA5555FF);
        	this.drawVerticalLine(this.xPosition, this.yPosition, this.yPosition + this.height, 0xAA5555FF);
        	this.drawVerticalLine(this.xPosition + this.width, this.yPosition, this.yPosition + this.height, 0xAA5555FF);
        	
        	FontRenderer fontrenderer = mc.fontRendererObj;
        	this.drawCenteredString(fontrenderer, this.displayString, this.xPosition + 100, this.yPosition + (this.height - 8) / 2, 0xFFAAAAAA);
        }
    }
}
