package pianoman.futurecraft.gui;

import org.lwjgl.opengl.GL11;

import pianoman.futurecraft.inventory.ContainerGenerator;
import pianoman.futurecraft.tileentity.TileEntityGenerator;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

/**
 * Currently not in use.
 * 
 * @author Joseph
 *
 */
public class GuiGenerator extends GuiContainer
{
	private static final ResourceLocation guiTexture = new ResourceLocation("textures/gui/generator.png");
	private TileEntityGenerator tileGenerator;
	
	public GuiGenerator(InventoryPlayer p_i1091_1_, TileEntityGenerator tileEntityGenerator)
    {
        super(new ContainerGenerator(p_i1091_1_, tileEntityGenerator));
        this.tileGenerator = tileEntityGenerator;
    }
	
	protected void drawGuiContainerForegroundLayer(int p_146979_1_, int p_146979_2_)
    {
        String s = this.tileGenerator.hasCustomInventoryName() ? this.tileGenerator.getInventoryName() : I18n.format(this.tileGenerator.getInventoryName(), new Object[0]);
        this.fontRendererObj.drawString(s, this.xSize / 2 - this.fontRendererObj.getStringWidth(s) / 2, 6, 4210752);
        this.fontRendererObj.drawString(I18n.format("container.inventory", new Object[0]), 8, this.ySize - 96 + 2, 4210752);
    }
	
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(guiTexture);
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
        
        if (this.tileGenerator.isBurning())
        {
            int i1 = this.tileGenerator.getBurnTimeRemainingScaled(13);
            this.drawTexturedModalRect(k + 80, l + 36 - i1, 176, 12 - i1, 14, i1 + 1);
        }
        if (this.tileGenerator.energy != 0)
        {
        	int i2 = this.tileGenerator.getEnergyAmountScaled(47);
        	this.drawTexturedModalRect(k + 7, l + 66 - i2, 176, 61 - i2, 18, i2 + 1);
        }
    }
}
