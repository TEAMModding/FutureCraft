package com.team.futurecraft.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;

/**
 * Overrided version of GuiButton just with a space-y texture.
 * 
 * @author Joseph
 *
 */
public class GuiSpaceButton extends GuiButton
{

	protected static final ResourceLocation buttonTextures = new ResourceLocation("futurecraft", "textures/gui/space_widgets.png");
	
	public GuiSpaceButton(int buttonId, int x, int y, int width, int height, String text) {
		super(buttonId, x, y, width, height, text);
	}
	
	public void drawButton(Minecraft mc, int mouseX, int mouseY)
    {
        if (this.visible)
        {
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
        	this.drawCenteredString(fontrenderer, this.displayString, this.xPosition + this.width / 2, this.yPosition + (this.height - 8) / 2, 0xFFAAAAAA);
        }
    }
}
