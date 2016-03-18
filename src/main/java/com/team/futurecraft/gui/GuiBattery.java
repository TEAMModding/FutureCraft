package com.team.futurecraft.gui;

import org.lwjgl.opengl.GL11;

import com.team.futurecraft.inventory.ContainerBattery;
import com.team.futurecraft.tileentity.TileEntityMachine;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;

public class GuiBattery extends GuiContainer
{
	private static final ResourceLocation guiTexture = new ResourceLocation("futurecraft", "textures/gui/battery.png");
	private TileEntityMachine tileEntity;
	
	public GuiBattery(TileEntityMachine tileEntity) {
		super(new ContainerBattery(tileEntity));
		this.tileEntity = tileEntity; 
	}
	
	protected void drawGuiContainerForegroundLayer(int p_146979_1_, int p_146979_2_) {
        this.fontRendererObj.drawString(I18n.format("container.battery", new Object[0]), this.xSize / 2 - this.fontRendererObj.getStringWidth("container.battery") / 2, 6, 4210752);
        this.fontRendererObj.drawString(I18n.format("container.inventory", new Object[0]), 8, this.ySize - 96 + 2, 4210752);
    }

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(guiTexture);
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
		
		if (this.tileEntity.energy != 0) {
        	int i2 = this.tileEntity.getEnergyAmountScaled(47);
        	this.drawTexturedModalRect(k + 79, l + 66 - i2, 176, 61 - i2, 18, i2 + 1);
        }
	}
}
