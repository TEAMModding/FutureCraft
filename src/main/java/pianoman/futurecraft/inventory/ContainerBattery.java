package pianoman.futurecraft.inventory;

import pianoman.futurecraft.tileentity.TileEntityMachine;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;

/**
 * Currently unused. Need to update the battery.
 * 
 * @author Joseph
 *
 */
public class ContainerBattery extends Container
{
	private TileEntityMachine tileEntity;
	
	public ContainerBattery(TileEntityMachine tileEntity)
	{
		this.tileEntity = tileEntity;
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer p_75145_1_) 
	{
		return this.tileEntity.isUseableByPlayer(p_75145_1_);
	}
	
}
