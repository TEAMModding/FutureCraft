package com.team.futurecraft.block;

import com.team.futurecraft.world.TeleportHandler;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

/**
 * This is a temporary block to get to planets and will in no way be used
 * for the end product.
 * 
 * @author Joseph
 *
 */
public class BlockTeleporter extends SimpleBlock
{
	private int dim;
	
	/**
	 * @param dimension The dimension this block teleports you when right clicking it.
	 * @param name The name of the block item.
	 */
	public BlockTeleporter(int dimension, String name)
	{
		super(Material.rock, name);
		this.dim = dimension;
	}
	
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ) 
    {
		if (player.dimension == dim)
			System.out.println("attempted to teleport in a dimension that you ar allready in!");
		else if (!world.isRemote)
		{
			EntityPlayerMP newplayer = (EntityPlayerMP) player;
			MinecraftServer mServer = MinecraftServer.getServer();
			newplayer.mcServer.getConfigurationManager().transferPlayerToDimension(newplayer, dim, new TeleportHandler(mServer.worldServerForDimension(dim)));
		}
		return true;
    }
}
