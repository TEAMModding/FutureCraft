package com.team.futurecraft.block;

import com.team.futurecraft.FutureCraft;
import com.team.futurecraft.tileentity.TileEntityMachine;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * <p>This is an astract class that other blocks should extend to provide core
 * machine functionality. <br>If you do not specify <code>public TileEntity createNewTileEntity(World world, int meta)</code>
 * in your machine class, it will automatically create a TileEntityMachine instead, which has simple energy storage but no
 * inventory slots. To have inventory slots you should override the createNewTileEntity method to return your own TileEntity
 * class which extends TilEntityMachine, see TilEntityMachine.</p>
 * 
 * <p>If you want your machine to provide a gui for it's contents you will need something like this in your class:</p>
 * <code>public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ) 
 *  {
 *		player.openGui("futurecraft", --your gui id--, world, pos.getX(), pos.getY(), pos.getZ());
 *      return true;
 *   }</code> <p>see GuiHandler for more information on making Gui's and Containers for blocks.</p>
 * 
 * @author Joseph
 *
 */
public abstract class Machine extends BlockContainer {
	public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
	
	
	public Machine(String name) {
		super(Material.iron);
		this.setCreativeTab(FutureCraft.tabFutureCraft);
		this.setUnlocalizedName(name);
		GameRegistry.registerBlock(this, name);
	}
	
	/**
	 * Returns settings based on the side. TileEntityMachine calls this to figure out whether to send or recieve
	 * energy, and computes the side even if the machine is turned sideways. Sides returned from here are based on
	 * a machine facing north. so if the machine is facing south, a value returned north from here will be computed
	 * as south in the TilEntityMachine.
	 */
	public EnumMachineSetting getSide(EnumFacing side) {
		return EnumMachineSetting.energyInput;
	}
	
	/**
	 * Internal override fixing BlockContainers render type.
	 */
	public int getRenderType() {
		return 3;
	}

	@Override
	public abstract TileEntity createNewTileEntity(World world, int meta);
	
	/**
	 * You wont usually override this but if you do make sure you call super(worldIn, pos, state) or else the block
	 * wont be able to have different directions.
	 */
	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
    	if (!worldIn.isRemote) {
            Block block = worldIn.getBlockState(pos.north()).getBlock();
            Block block1 = worldIn.getBlockState(pos.south()).getBlock();
            Block block2 = worldIn.getBlockState(pos.west()).getBlock();
            Block block3 = worldIn.getBlockState(pos.east()).getBlock();
            EnumFacing enumfacing = (EnumFacing)state.getValue(FACING);

            if (enumfacing == EnumFacing.NORTH && block.isFullBlock() && !block1.isFullBlock()) {
                enumfacing = EnumFacing.SOUTH;
            }
            else if (enumfacing == EnumFacing.SOUTH && block1.isFullBlock() && !block.isFullBlock()) {
                enumfacing = EnumFacing.NORTH;
            }
            else if (enumfacing == EnumFacing.WEST && block2.isFullBlock() && !block3.isFullBlock()) {
                enumfacing = EnumFacing.EAST;
            }
            else if (enumfacing == EnumFacing.EAST && block3.isFullBlock() && !block2.isFullBlock()) {
                enumfacing = EnumFacing.WEST;
            }
            worldIn.setBlockState(pos, state.withProperty(FACING, enumfacing), 2);
        }
    }
	
	/**
     * Convert the given metadata into a BlockState for this Block
     */
    public IBlockState getStateFromMeta(int meta) {
        EnumFacing enumfacing = EnumFacing.getFront(meta);

        if (enumfacing.getAxis() == EnumFacing.Axis.Y) {
            enumfacing = EnumFacing.NORTH;
        }

        return this.getDefaultState().withProperty(FACING, enumfacing);
    }
    
    /**
     * Convert the BlockState into the correct metadata value
     */
    public int getMetaFromState(IBlockState state) {
        return ((EnumFacing)state.getValue(FACING)).getIndex();
    }

    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] {FACING});
    }
	
    /**
     * don't override this
     */
	public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
    }
    
	/**
	 * or this
	 */
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        worldIn.setBlockState(pos, state.withProperty(FACING, placer.getHorizontalFacing().getOpposite()), 2);

        if (stack.hasDisplayName()) {
            TileEntity tileentity = worldIn.getTileEntity(pos);

            if (tileentity instanceof TileEntityMachine) {
                ((TileEntityMachine)tileentity).setCustomInventoryName(stack.getDisplayName());
            }
        }
    }
    
    /**
     * or especially this
     */
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        
        TileEntity tileentity = worldIn.getTileEntity(pos);

        if (tileentity instanceof TileEntityMachine) {
            InventoryHelper.dropInventoryItems(worldIn, pos, (TileEntityMachine)tileentity);
            worldIn.updateComparatorOutputLevel(pos, this);
        }
        super.breakBlock(worldIn, pos, state);
    }
}