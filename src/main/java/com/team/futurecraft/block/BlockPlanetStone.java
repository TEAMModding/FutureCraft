package com.team.futurecraft.block;

import java.util.List;
import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockPlanetStone extends SimpleBlock {
	public static final PropertyEnum VARIANT = PropertyEnum.create("variant", EnumType.class);
	
	public BlockPlanetStone(String name) {
		super(Material.rock, name, true);
		this.setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, EnumType.STONE));
	}
	
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return state.getValue(VARIANT) == EnumType.STONE ? Item.getItemFromBlock(Blocks.cobblestone) : Item.getItemFromBlock(Blocks.stone);
    }
	
	 /**
     * returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
     */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@SideOnly(Side.CLIENT)
    public void getSubBlocks(Item itemIn, CreativeTabs tab, List list)
    {
        EnumType[] aenumtype = EnumType.values();
        int i = aenumtype.length;

        for (int j = 0; j < i; ++j)
        {
            EnumType enumtype = aenumtype[j];
            list.add(new ItemStack(itemIn, 1, enumtype.getMetadata()));
        }
    }

    /**
     * Convert the given metadata into a BlockState for this Block
     */
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(VARIANT, EnumType.byMetadata(meta));
    }

    /**
     * Convert the BlockState into the correct metadata value
     */
    public int getMetaFromState(IBlockState state)
    {
        return ((EnumType)state.getValue(VARIANT)).getMetadata();
    }

    protected BlockState createBlockState()
    {
        return new BlockState(this, new IProperty[] {VARIANT});
    }
    
    public String getMetaName(int meta) {
		return EnumType.byMetadata(meta).getName();
	}
    
    public static enum EnumType implements IStringSerializable
    {
        STONE(0, "stone"),
        BRICKS(1, "bricks"),
        COBBLESTONE(2, "cobblestone"),
        MALACHITE(3, "malachite"),
        CASSITERITE(4, "cassiterite"),
        BAUXITE(5, "bauxite");
        
        /** Array of the Block's BlockStates */
        private static final EnumType[] META_LOOKUP = new EnumType[values().length];
        /** The BlockState's metadata. */
        private final int meta;
        /** The EnumType's name. */
        private final String name;

        private EnumType(int meta, String name)
        {
        	this.meta = meta;
            this.name = name;
        }

        /**
         * Returns the EnumType's metadata value.
         */
        public int getMetadata()
        {
            return this.meta;
        }

        public String toString()
        {
            return this.name;
        }

        /**
         * Returns an EnumType for the BlockState from a metadata value.
         */
        public static EnumType byMetadata(int meta)
        {
            if (meta < 0 || meta >= META_LOOKUP.length)
            {
                meta = 0;
            }

            return META_LOOKUP[meta];
        }

        public String getName()
        {
            return this.name;
        }

        static
        {
            EnumType[] var0 = values();
            int var1 = var0.length;

            for (int var2 = 0; var2 < var1; ++var2)
            {
                EnumType var3 = var0[var2];
                META_LOOKUP[var3.getMetadata()] = var3;
            }
        }
    }
}