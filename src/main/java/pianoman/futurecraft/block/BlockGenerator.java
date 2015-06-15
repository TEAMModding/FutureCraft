package pianoman.futurecraft.block;

import net.minecraftforge.fml.common.registry.GameRegistry;
import pianoman.futurecraft.BlockList;
import pianoman.futurecraft.FutureCraft;
import pianoman.futurecraft.tileentity.TileEntityGenerator;
import pianoman.futurecraft.tileentity.TileEntityMachine;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

/**
 * The generator class, currently updating.
 * Do not mess with this code until i've finished updating it.
 * 
 * @author Joseph
 *
 */
public class BlockGenerator extends Machine
{
	private boolean isLit;
	private IIcon front;
	
	public BlockGenerator(boolean lit) 
	{
		super();
		this.isLit = lit;
		if (!lit)
			this.setCreativeTab(FutureCraft.tabFutureCraft);
	}
	
	public EnumMachineSetting getSide(int side, int meta)
	{
		return EnumMachineSetting.energyOutput;
	}
	
	public IIcon getFrontIcon(int side, int meta)
	{
		return front;
	}
	
	public void registerBlockIcons(IIconRegister iconRegister)
	{
		super.registerBlockIcons(iconRegister);
		if (isLit)
			front = iconRegister.registerIcon("coal_generator_lit");
		else
			front = iconRegister.registerIcon("coal_generator");
	}
	
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_)
    {
        if (world.isRemote)
        {
        	return true;
        }
        else
        {
        	player.openGui("futurecraft", 102, world, x, y, z);
            return true;
        }
    }
	
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) 
	{
		return new TileEntityGenerator(this);
	}
	
	public static void updateFurnaceBlockState(boolean p_149931_0_, World p_149931_1_, int p_149931_2_, int p_149931_3_, int p_149931_4_)
    {
        int l = p_149931_1_.getBlockMetadata(p_149931_2_, p_149931_3_, p_149931_4_);
        TileEntity tileentity = p_149931_1_.getTileEntity(p_149931_2_, p_149931_3_, p_149931_4_);
        
        if (p_149931_0_)
        {
        	p_149931_1_.setBlock(p_149931_2_, p_149931_3_, p_149931_4_, GameRegistry.findBlock("futurecraft", "generator_lit"));
        }
        else
        {
            p_149931_1_.setBlock(p_149931_2_, p_149931_3_, p_149931_4_, GameRegistry.findBlock("futurecraft", "generator"));
        }

        p_149931_1_.setBlockMetadataWithNotify(p_149931_2_, p_149931_3_, p_149931_4_, l, 2);

        if (tileentity != null)
        {
            tileentity.validate();
            p_149931_1_.setTileEntity(p_149931_2_, p_149931_3_, p_149931_4_, tileentity);
        }
    }
}
