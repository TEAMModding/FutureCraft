package com.team.futurecraft.block;

import com.team.futurecraft.BlockList;
import com.team.futurecraft.FutureCraft;
import com.team.futurecraft.tileentity.TileEntityGenerator;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class BlockGenerator extends Machine
{
	public BlockGenerator(boolean lit, String name) 
	{
		super(false, name);
		if (!lit)
			this.setCreativeTab(FutureCraft.tabFutureCraft);
	}
	
	public EnumMachineSetting getSide(EnumFacing side)
	{
		return EnumMachineSetting.energyOutput;
	}
	
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ) 
    {
		player.openGui("futurecraft", 102, world, pos.getX(), pos.getY(), pos.getZ());
        return true;
    }
	
	public TileEntity createNewTileEntity(World world, int meta) 
	{
		return new TileEntityGenerator(this.getStateFromMeta(meta));
	}
	
	public static void setState(boolean active, World worldIn, BlockPos pos)
    {
        IBlockState iblockstate = worldIn.getBlockState(pos);
        TileEntity tileentity = worldIn.getTileEntity(pos);

        if (active)
        {
            worldIn.setBlockState(pos, BlockList.generator_lit.getDefaultState().withProperty(FACING, iblockstate.getValue(FACING)), 3);
            worldIn.setBlockState(pos, BlockList.generator_lit.getDefaultState().withProperty(FACING, iblockstate.getValue(FACING)), 3);
        }
        else
        {
            worldIn.setBlockState(pos, BlockList.generator.getDefaultState().withProperty(FACING, iblockstate.getValue(FACING)), 3);
            worldIn.setBlockState(pos, BlockList.generator.getDefaultState().withProperty(FACING, iblockstate.getValue(FACING)), 3);
        }

        if (tileentity != null)
        {
            tileentity.validate();
            worldIn.setTileEntity(pos, tileentity);
        }
    }
}
