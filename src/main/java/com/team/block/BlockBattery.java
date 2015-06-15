package com.team.block;

/**
 * The battery block class, currently updating.
 * Do not mess with this code until i've finished updating it.
 * 
 * @author Joseph
 *
 */
import com.team.tileentity.TileEntityMachine;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockBattery extends Machine
{
	private boolean isCreative;
	
	public BlockBattery(boolean full) 
	{
		super(full, "power_output");
		isCreative = full;
	}
	
	public EnumMachineSetting getSide(int side, int meta)
	{
		return side == 1 ? EnumMachineSetting.energyInput : (side == 0 ? EnumMachineSetting.energyInput : (side != meta ? EnumMachineSetting.energyInput : EnumMachineSetting.energyOutput));
	}
	
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_)
    {
		if (world.isRemote)
        {
        	return true;
        }
        else
        {
        	player.openGui("futurecraft", 103, world, x, y, z);
            return true;
        }
    }
	
	public void registerBlockIcons(IIconRegister iconRegister)
	{
		super.registerBlockIcons(iconRegister);
		if (!isCreative)
			this.blockIcon = iconRegister.registerIcon("battery");
		else
			this.blockIcon = iconRegister.registerIcon("creative_battery");
	}
}
