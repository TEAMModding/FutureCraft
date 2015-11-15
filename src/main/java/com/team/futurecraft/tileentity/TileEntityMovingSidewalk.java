package com.team.futurecraft.tileentity;

import java.util.List;

import com.team.futurecraft.BlockList;

import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;


public class TileEntityMovingSidewalk extends TileEntity implements IUpdatePlayerListBox {
	// Create and initialize the items variable that will store store the items

	
	//Check for items
	@Override
	public void update() {
		
		if (!worldObj.isRemote) {

			List<?> list = worldObj.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(this.pos.getX(), this.pos.getY() + 1.0D, this.pos.getZ(), this.pos.getX() + 1.0D, this.pos.getY() + 1.1D, this.pos.getZ() + 1.0D), IEntitySelector.selectAnything);
			
			for (int i = 0; i < list.size(); i++) {
				
				if (list.get(i) instanceof Entity || list.get(i) instanceof EntityPlayer) {
					Entity entity = null;
					if (list.get(i) instanceof Entity) entity = (Entity) list.get(i);
					else if (list.get(i) instanceof EntityPlayer) entity = (EntityPlayer) list.get(i);
					if (worldObj.getBlockState(this.getPos()) == BlockList.moving_sidewalk.getStateFromMeta(0)) entity.addVelocity(0, 0, 0.1);
					if (worldObj.getBlockState(this.getPos()) == BlockList.moving_sidewalk.getStateFromMeta(1)) entity.addVelocity(-0.1, 0, 0);
					if (worldObj.getBlockState(this.getPos()) == BlockList.moving_sidewalk.getStateFromMeta(2)) entity.addVelocity(0, 0, -0.1);
					if (worldObj.getBlockState(this.getPos()) == BlockList.moving_sidewalk.getStateFromMeta(3)) entity.addVelocity(0.1, 0, 0);
						 }
			}
		}
		
	}
	//Find a "random" item over the block
	public static EntityItem CheckForItemEntities(World worldIn, double p_145897_1_, double p_145897_3_, double p_145897_5_)
	{
	List<?> list = worldIn.getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(p_145897_1_, p_145897_3_, p_145897_5_, p_145897_1_ + 1.0D, p_145897_3_ + 0.5D, p_145897_5_ + 1.0D), IEntitySelector.selectAnything);
	return list.size() > 0 ? (EntityItem)list.get(0) : null;
	}

}