package pianoman.futurecraft.gui;

import pianoman.futurecraft.inventory.ContainerAlloyFurnace;
import pianoman.futurecraft.inventory.ContainerBattery;
import pianoman.futurecraft.inventory.ContainerGenerator;
import pianoman.futurecraft.tileentity.TileEntityAlloyFurnace;
import pianoman.futurecraft.tileentity.TileEntityGenerator;
import pianoman.futurecraft.tileentity.TileEntityMachine;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

/**
 * This is the main gui handler for the whole mod. To add a new gui add a new case statement
 * with the desired ID. Then to open the gui in-game 
 * call: player.openGui("futurecraft", --your id here--, world, pos.getX(), pos.getY(), pos.getZ());.
 * You will need to add two case statements for any kind of container gui: one in the getServerGuiElement
 * method and the getClientGuiElement method.
 * 
 * @author Joseph
 *
 */
public class GuiHandler implements IGuiHandler
{
	/**
     * Returns a Server side Container to be displayed to the user.
     *
     * @param ID The Gui ID Number
     * @param player The player viewing the Gui
     * @param world The current world
     * @param x X Position
     * @param y Y Position
     * @param z Z Position
     * @return A GuiScreen/Container to be displayed to the user, null if none.
     */
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) 
	{
		BlockPos pos = new BlockPos(x, y, z);
		switch (ID)
		{
			case 100:
			{
				return new ContainerAlloyFurnace(player.inventory, (TileEntityAlloyFurnace)world.getTileEntity(pos));
			}
			case 102:
			{
				return new ContainerGenerator(player.inventory, (TileEntityGenerator)world.getTileEntity(pos));
			}
			case 103:
			{
				return new ContainerBattery((TileEntityMachine)world.getTileEntity(pos));
			}
			default:
				return null;
		}
	}

	/**
     * Returns a Container to be displayed to the user. On the client side, this
     * needs to return a instance of GuiScreen On the server side, this needs to
     * return a instance of Container
     *
     * @param ID The Gui ID Number
     * @param player The player viewing the Gui
     * @param world The current world
     * @param x X Position
     * @param y Y Position
     * @param z Z Position
     * @return A GuiScreen/Container to be displayed to the user, null if none.
     */
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) 
	{
		BlockPos pos = new BlockPos(x, y, z);
		switch (ID)
		{
			case 100:
			{
				return new GuiAlloyFurnace(player.inventory, (TileEntityAlloyFurnace)world.getTileEntity(pos));
			}
			case 101:
			{
				return new GuiNavigation(player);
			}
			case 102:
			{
				return new GuiGenerator(player.inventory, (TileEntityGenerator)world.getTileEntity(pos));
			}
			case 103:
			{
				return new GuiBattery((TileEntityMachine)world.getTileEntity(pos));
			}
			default:
				return null;
		}
	}

}
