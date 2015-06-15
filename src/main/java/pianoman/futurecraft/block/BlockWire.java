package pianoman.futurecraft.block;

import java.util.ArrayList;

import pianoman.futurecraft.CommonUtils;
import pianoman.futurecraft.FutureCraft;
import pianoman.futurecraft.tileentity.TileEntityMachine;
import pianoman.futurecraft.tileentity.TileEntityWire;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * The wire class, currently updating.
 * Do not mess with this code until i've finished updating it.
 * 
 * @author Joseph
 *
 */
public class BlockWire extends BlockContainer implements IElectric
{

	public BlockWire() 
	{
		super(Material.cloth);
		this.setCreativeTab(FutureCraft.tabFutureCraft);
		this.setStepSound(soundTypeCloth);
	}

	@Override
	public int getRenderType() 
	{
		return 1000;
	}
	
	public boolean isOpaqueCube()
	{
		return false;
	}
	
	public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z)
	{
		float x1 = 6F;
		float y1 = 6F;
		float z1 = 6F;
		float x2 = 10F;
		float y2 = 10F;
		float z2 = 10F;
		
		if (this.canConnectTo(world, x, y, z, ForgeDirection.NORTH))
			z1 = 0F;
		if (this.canConnectTo(world, x, y, z, ForgeDirection.SOUTH))
			z2 = 16F;
		if (this.canConnectTo(world, x, y, z, ForgeDirection.EAST))
			x2 = 16F;
		if (this.canConnectTo(world, x, y, z, ForgeDirection.WEST))
			x1 = 0F;
		if (this.canConnectTo(world, x, y, z, ForgeDirection.UP))
			y2 = 16F;
		if (this.canConnectTo(world, x, y, z, ForgeDirection.DOWN))
			y1 = 0F;
		
		this.setBlockBounds(x1 / 16F, y1 / 16F, z1 / 16F, x2 / 16F, y2 / 16F, z2 / 16F);
	}
	
	public boolean renderAsNormalBlock()
	{
		return false;
	}
	
	public boolean canConnectTo(IBlockAccess world, int x, int y, int z, ForgeDirection side) 
	{
		if (CommonUtils.getBlockNextTo(world, x, y, z, side) instanceof IElectric)
			return true;
		else
			return false;
	}

	@Override
	public int onPowered(World world, int x, int y, int z, int amount, ForgeDirection side) 
	{
		return ((TileEntityWire)world.getTileEntity(x, y, z)).power(amount);
	}
	
	/**
	 * Returns the <code>ForgeDirection</code> equivelants of
	 * the connected blocks.
	 * 
	 * @param world
	 * @param x
	 * @param y
	 * @param z
	 * @return
	 */
	public Object[] getConnectedBlocks(World world, int x, int y, int z)
	{
		ArrayList<ForgeDirection> sides = new ArrayList();
		
		if (this.canConnectTo(world, x, y, z, ForgeDirection.NORTH))
			sides.add(ForgeDirection.NORTH);
		if (this.canConnectTo(world, x, y, z, ForgeDirection.SOUTH))
			sides.add(ForgeDirection.SOUTH);
		if (this.canConnectTo(world, x, y, z, ForgeDirection.EAST))
			sides.add(ForgeDirection.EAST);
		if (this.canConnectTo(world, x, y, z, ForgeDirection.WEST))
			sides.add(ForgeDirection.WEST);
		if (this.canConnectTo(world, x, y, z, ForgeDirection.UP))
			sides.add(ForgeDirection.UP);
		if (this.canConnectTo(world, x, y, z, ForgeDirection.DOWN))
			sides.add(ForgeDirection.DOWN);
		sides.trimToSize();
		return sides.toArray();
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) 
	{
		return new TileEntityWire(100, this);
	}
	
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_)
    {
		System.out.println(((TileEntityWire)world.getTileEntity(x, y, z)).getEnergy());
		return true;
    }

	@Override
	public int getEnergy(World world, int x, int y, int z) {
		return ((TileEntityWire)world.getTileEntity(x, y, z)).energy;
	}
}
