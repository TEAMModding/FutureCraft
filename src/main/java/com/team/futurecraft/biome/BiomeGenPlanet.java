package com.team.futurecraft.biome;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSand;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.ChunkPrimer;

/**
 * This is the base Biome gen class that all planets inherit.
 * The only use for it so far is to allow top blocks to generate on
 * top of the planet's stone type, instead of testing for normal stone and never
 * generating top blocks (grass, sand, etc).
 * 
 * @author Joseph
 *
 */
public class BiomeGenPlanet extends BiomeGenBase {
	private Block stoneBlock;
	
	public BiomeGenPlanet(int p_i1971_1_, Block stoneBlock) {
		super(p_i1971_1_);
		this.stoneBlock = stoneBlock;
	}
	
	public void genTerrainBlocks(World worldIn, Random p_180622_2_, ChunkPrimer p_180622_3_, int p_180622_4_, int p_180622_5_, double p_180622_6_) {
        this.generateBiomeTerrainNew(worldIn, p_180622_2_, p_180622_3_, p_180622_4_, p_180622_5_, p_180622_6_);
    }
	
	public void generateBiomeTerrainNew(World worldIn, Random p_180628_2_, ChunkPrimer p_180628_3_, int p_180628_4_, int p_180628_5_, double p_180628_6_) {
        IBlockState iblockstate = this.topBlock;
        IBlockState iblockstate1 = this.fillerBlock;
        int k = -1;
        int l = (int)(p_180628_6_ / 3.0D + 3.0D + p_180628_2_.nextDouble() * 0.25D);
        int i1 = p_180628_4_ & 15;
        int j1 = p_180628_5_ & 15;

        for (int k1 = 255; k1 >= 0; --k1) {
            if (k1 <= p_180628_2_.nextInt(5)) {
                p_180628_3_.setBlockState(j1, k1, i1, Blocks.bedrock.getDefaultState());
            }
            else {
                IBlockState iblockstate2 = p_180628_3_.getBlockState(j1, k1, i1);

                if (iblockstate2.getBlock().getMaterial() == Material.air) {
                    k = -1;
                }
                else if (iblockstate2.getBlock() == stoneBlock) {
                    if (k == -1) {
                        if (l <= 0) {
                            iblockstate = null;
                            iblockstate1 = stoneBlock.getDefaultState();
                        }
                        else if (k1 >= 59 && k1 <= 64) {
                            iblockstate = this.topBlock;
                            iblockstate1 = this.fillerBlock;
                        }

                        if (k1 < 63 && (iblockstate == null || iblockstate.getBlock().getMaterial() == Material.air)) {
                            if (this.getFloatTemperature(new BlockPos(p_180628_4_, k1, p_180628_5_)) < 0.15F) {
                                iblockstate = Blocks.ice.getDefaultState();
                            }
                            else {
                                iblockstate = Blocks.water.getDefaultState();
                            }
                        }

                        k = l;

                        if (k1 >= 62) {
                            p_180628_3_.setBlockState(j1, k1, i1, iblockstate);
                        }
                        else if (k1 < 56 - l) {
                            iblockstate = null;
                            iblockstate1 = stoneBlock.getDefaultState();
                            p_180628_3_.setBlockState(j1, k1, i1, Blocks.gravel.getDefaultState());
                        }
                        else {
                            p_180628_3_.setBlockState(j1, k1, i1, iblockstate1);
                        }
                    }
                    else if (k > 0) {
                        --k;
                        p_180628_3_.setBlockState(j1, k1, i1, iblockstate1);

                        if (k == 0 && iblockstate1.getBlock() == Blocks.sand) {
                            k = p_180628_2_.nextInt(4) + Math.max(0, k1 - 63);
                            iblockstate1 = iblockstate1.getValue(BlockSand.VARIANT) == BlockSand.EnumType.RED_SAND ? Blocks.red_sandstone.getDefaultState() : Blocks.sandstone.getDefaultState();
                        }
                    }
                }
            }
        }
    }
}
