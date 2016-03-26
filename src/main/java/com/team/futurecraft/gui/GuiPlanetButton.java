package com.team.futurecraft.gui;

import org.lwjgl.opengl.GL11;

import com.team.futurecraft.space.CelestialObject;

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
		super(buttonId, x, y, 150, 50, planet.name);
		this.planet = planet;
	}
	
	public void drawButton(Minecraft mc, int mouseX, int mouseY)
    {
        if (this.visible)
        {
        	GL11.glPushMatrix();
        	GL11.glTranslatef(this.xPosition + 25, this.yPosition + 25, 30);
        	GL11.glRotatef(-90, 1, 0, 0);
        	GL11.glScalef(2, 2, 2);
        	this.planet.renderStatic(mc);
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
