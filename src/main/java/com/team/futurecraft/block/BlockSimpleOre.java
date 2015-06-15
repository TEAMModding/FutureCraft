package com.team.futurecraft.block;

import java.util.Random;

import com.team.futurecraft.FutureCraft;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;

/**
 * A simple ore class since minecraft's blockOre is private.
 * 
 * @author Joseph
 *
 */
public class BlockSimpleOre extends SimpleBlock
{
    private int xpLow;
    private int xpHigh;

    public BlockSimpleOre(int xpLow, int xpHigh, String name)
    {
        super(Material.rock, name);
        this.setCreativeTab(FutureCraft.tabFutureCraft);
        this.setStepSound(Block.soundTypePiston);
        this.setHardness(3.0f);
        this.setResistance(5.0f);
        this.xpLow = xpLow;
        this.xpHigh = xpHigh;
    }
    
    public BlockSimpleOre(String name) {
    	this(0, 5, name);
    }

    private Random rand = new Random();
    
    @Override
    public int getExpDrop(IBlockAccess world, BlockPos pos, int fortune)
    {
        return MathHelper.getRandomIntegerInRange(rand, xpLow, xpHigh);
    }
}
