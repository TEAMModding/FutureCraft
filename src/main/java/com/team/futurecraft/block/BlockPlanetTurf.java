package com.team.futurecraft.block;

import com.team.futurecraft.biome.BiomePlanet;
import com.team.futurecraft.world.WorldProviderPlanet;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.RegionRenderCache;
import net.minecraft.util.BlockPos;
import net.minecraft.world.ColorizerGrass;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockPlanetTurf extends SimpleBlock {

	public BlockPlanetTurf(String name) {
		super(Material.rock, name);
	}
	
	@SideOnly(Side.CLIENT)
    public int getBlockColor()
    {
		return 0xFFFFFF;
    }

    @SideOnly(Side.CLIENT)
    public int getRenderColor(IBlockState state)
    {
        return this.getBlockColor();
    }

    @SideOnly(Side.CLIENT)
    public int colorMultiplier(IBlockAccess worldIn, BlockPos pos, int renderPass)
    {
    	BiomeGenBase biome = worldIn.getBiomeGenForCoords(pos);
    	if (biome instanceof BiomePlanet) {
    		BiomePlanet biomePlanet = (BiomePlanet)biome;
    		return biomePlanet.getTurfColor(pos);
    	}
        return 0xFFFFFF;
    }
}
