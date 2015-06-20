package com.team.futurecraft.block;

import com.team.futurecraft.FutureCraft;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

/**
 * Currently a work-in-progress of using a gui to teleport to planets instead
 * of individual teleporter's. This'll probably be re-used as the main navigation console for rockets.
 * 
 * @author Joseph
 *
 */
public class BlockNavigator extends SimpleBlock {
	public BlockNavigator(String name) {
		super(Material.iron, name);
		this.setCreativeTab(FutureCraft.tabFutureCraft);
	}

	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ) {
		player.openGui("futurecraft", 101, world, pos.getX(), pos.getY(), pos.getZ());
        return true;
    }
}
