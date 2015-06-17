package com.team.futurecraft.gui;

import com.team.futurecraft.inventory.ContainerAlloyFurnace;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiAlloyFurnace extends GuiContainer
{
    private static final ResourceLocation furnaceGuiTextures = new ResourceLocation("futurecraft", "textures/gui/alloy_furnace.png");
    /** The player inventory bound to this GUI. */
    private final InventoryPlayer playerInventory;
    private IInventory tileFurnace;

    public GuiAlloyFurnace(InventoryPlayer playerInv, IInventory furnaceInv)
    {
        super(new ContainerAlloyFurnace(playerInv, furnaceInv));
        this.playerInventory = playerInv;
        this.tileFurnace = furnaceInv;
    }

    /**
     * Draw the foreground layer for the GuiContainer (everything in front of the items). Args : mouseX, mouseY
     */
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        String s = this.tileFurnace.getDisplayName().getUnformattedText();
        this.fontRendererObj.drawString(s, this.xSize / 2 - this.fontRendererObj.getStringWidth(s) / 2, 6, 4210752);
        this.fontRendererObj.drawString(this.playerInventory.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2, 4210752);
    }

    /**
     * Args : renderPartialTicks, mouseX, mouseY
     */
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(furnaceGuiTextures);
        int x = (this.width - this.xSize) / 2;
        int y = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(x, y, 0, 0, this.xSize, this.ySize);

        if (TileEntityFurnace.isBurning(this.tileFurnace))
        {
            int cookTime = this.getCookTimeScaled(13);
            this.drawTexturedModalRect(x + 45, y + 36 + 12 - cookTime, 176, 12 - cookTime, 14, cookTime + 1);
        }

        int burnTime = this.getBurnTimeScaled(24);
        this.drawTexturedModalRect(x + 79, y + 34, 176, 14, burnTime + 1, 16);
    }

    private int getBurnTimeScaled(int scale)
    {
        int j = this.tileFurnace.getField(2);
        
        return j * scale / 200;
    }

    private int getCookTimeScaled(int scale)
    {
        int j = this.tileFurnace.getField(1);

        if (j == 0)
        {
            j = 200;
        }

        return this.tileFurnace.getField(0) * scale / j;
    }
}