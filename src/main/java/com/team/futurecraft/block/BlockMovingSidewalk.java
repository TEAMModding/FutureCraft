package com.team.futurecraft.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.team.futurecraft.FutureCraft;

public class BlockMovingSidewalk extends Block {
	public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
	public BlockMovingSidewalk() throws StackOverflowError {
	super(Material.rock);
	this.setCreativeTab(FutureCraft.tabFutureCraft);
    this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
    this.setHardness(0.5f);
}

	 @SideOnly(Side.CLIENT)
	 public EnumWorldBlockLayer getBlockLayer()
	 {
	 return EnumWorldBlockLayer.SOLID;
	 }

	 @Override
	 public boolean isOpaqueCube() {
	 return true;
	 }
	 
	 @Override
	 public boolean isFullCube() {
	 return true;
	 }

	 
	 public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state)
	    {
	        this.setDefaultFacing(worldIn, pos, state);
	    }

	    private void setDefaultFacing(World worldIn, BlockPos pos, IBlockState state)
	    {
	        if (!worldIn.isRemote)
	        {
	            Block block = worldIn.getBlockState(pos.north()).getBlock();
	            Block block1 = worldIn.getBlockState(pos.south()).getBlock();
	            Block block2 = worldIn.getBlockState(pos.west()).getBlock();
	            Block block3 = worldIn.getBlockState(pos.east()).getBlock();
	            EnumFacing enumfacing = (EnumFacing)state.getValue(FACING);

	            if (enumfacing == EnumFacing.NORTH && block.isFullBlock() && !block1.isFullBlock())
	            {
	                enumfacing = EnumFacing.SOUTH;
	            }
	            else if (enumfacing == EnumFacing.SOUTH && block1.isFullBlock() && !block.isFullBlock())
	            {
	                enumfacing = EnumFacing.NORTH;
	            }
	            else if (enumfacing == EnumFacing.WEST && block2.isFullBlock() && !block3.isFullBlock())
	            {
	                enumfacing = EnumFacing.EAST;
	            }
	            else if (enumfacing == EnumFacing.EAST && block3.isFullBlock() && !block2.isFullBlock())
	            {
	                enumfacing = EnumFacing.WEST;
	            }

	            worldIn.setBlockState(pos, state.withProperty(FACING, enumfacing), 2);
	        }
	    }
	    
	    public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
	    {
	        return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
	    }

	    public static EnumFacing getFacing(int meta)
	    {
	        return EnumFacing.getHorizontal(meta & 7);
	    }
	    
	    
	    /**
	     * The type of render function that is called for this block
	     */
	    public int getRenderType()
	    {
	        return 3;
	    }

	    /**
	     * Possibly modify the given BlockState before rendering it on an Entity (Minecarts, Endermen, ...)
	     */
	    @SideOnly(Side.CLIENT)
	    public IBlockState getStateForEntityRender(IBlockState state)
	    {
	        return this.getDefaultState().withProperty(FACING, EnumFacing.SOUTH);
	    }

	    /**
	     * Convert the given metadata into a BlockState for this Block
	     */
	    public IBlockState getStateFromMeta(int meta)
	    {
	        EnumFacing enumfacing = EnumFacing.getHorizontal(meta);

	        if (enumfacing.getAxis() == EnumFacing.Axis.Y)
	        {
	            enumfacing = EnumFacing.NORTH;
	        }

	        return this.getDefaultState().withProperty(FACING, enumfacing);
	    }

	    /**
	     * Convert the BlockState into the correct metadata value
	     */
	    public int getMetaFromState(IBlockState state)
	    {
	        return ((EnumFacing)state.getValue(FACING)).getHorizontalIndex();
	    }

	    protected BlockState createBlockState()
	    {
	        return new BlockState(this, new IProperty[] {FACING});
	    }
	    
}