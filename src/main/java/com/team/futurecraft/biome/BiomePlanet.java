package com.team.futurecraft.biome;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

import javax.imageio.ImageIO;

import com.team.futurecraft.space.Planet;
import com.team.futurecraft.space.PlanetType;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSand;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.ChunkPrimer;

import com.team.futurecraft.world.ChunkProviderPlanet;
import com.team.futurecraft.world.WorldProviderPlanet;

/**
 * This is the base Biome gen class that all planets inherit.
 * The only use for it so far is to allow top blocks to generate on
 * top of the planet's stone type, instead of testing for normal stone and never
 * generating top blocks (grass, sand, etc).
 * 
 * @author Joseph
 *
 */
public class BiomePlanet extends BiomeGenBase {
	public IBlockState underwaterBlock;
	public Planet planet;
	
	public BiomePlanet(int id, Planet planet) {
		super(id);
		this.setDisableRain();
		this.planet = planet;
		this.underwaterBlock = Blocks.sand.getDefaultState();
	}
	
	public int getTurfColor(BlockPos pos) {
		return this.planet.turfmap[pos.getY() * this.planet.turfmap.length / 255];
	}
	
	public int getStoneColor(BlockPos pos) {
		return this.planet.stonemap[pos.getY() * this.planet.stonemap.length / 255];
	}
	
	public void genTerrainBlocks(World worldIn, Random p_180622_2_, ChunkPrimer p_180622_3_, int p_180622_4_, int p_180622_5_, double p_180622_6_) {
        this.generateBiomeTerrainNew(worldIn, p_180622_2_, p_180622_3_, p_180622_4_, p_180622_5_, p_180622_6_);
    }
	
	public void generateBiomeTerrainNew(World worldIn, Random p_180628_2_, ChunkPrimer primer, int p_180628_4_, int p_180628_5_, double p_180628_6_) {
		WorldProviderPlanet provider = (WorldProviderPlanet)worldIn.provider;
		PlanetType type = provider.planet.getWorldType();
		
        IBlockState topState = this.topBlock;
        IBlockState fillState = this.fillerBlock;
        int x = p_180628_4_ & 15;
        int z = p_180628_5_ & 15;

        for (int y = 250; y >= 0; --y) {
        	if (primer.getBlockState(x, y, z).getBlock() == type.getStoneBlock() && primer.getBlockState(x, y + 1, z) == Blocks.air.getDefaultState()) {
        		if (y > 140 && type.hasWater()) {
        			primer.setBlockState(x, y, z, Blocks.snow.getDefaultState());
        			primer.setBlockState(x, y - 1, z, Blocks.snow.getDefaultState());
        			primer.setBlockState(x, y - 2, z, Blocks.snow.getDefaultState());
        		}
        		else {
        			primer.setBlockState(x, y, z, topState);
        			primer.setBlockState(x, y - 1, z, fillState);
        			primer.setBlockState(x, y - 2, z, fillState);
        		}
        	}
        	else if (primer.getBlockState(x, y, z).getBlock() == type.getStoneBlock() && primer.getBlockState(x, y + 1, z).getBlock() == Blocks.water) {
        		primer.setBlockState(x, y, z, this.underwaterBlock);
    			primer.setBlockState(x, y - 1, z, this.underwaterBlock);
    			primer.setBlockState(x, y - 2, z, this.underwaterBlock);
        	}
        	
        	if (y <= 80 && type.hasWater()) {
        		if (primer.getBlockState(x, y, z) == Blocks.air.getDefaultState()) {
        			primer.setBlockState(x, y, z, Blocks.water.getDefaultState());
        		}
        	}
        }
    }
}
