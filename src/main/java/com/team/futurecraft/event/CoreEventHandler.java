package com.team.futurecraft.event;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.world.WorldProvider;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import com.team.futurecraft.BlockList;
import com.team.futurecraft.world.WorldProviderPlanet;

/**
 * The core event handler for Futurecraft. Currently only handles
 * low gravity physics.
 * 
 * @author Joseph
 *
 */
public class CoreEventHandler
{
	@SubscribeEvent
	public void onUpdateEvent(LivingUpdateEvent event)
	{
		EntityLivingBase entity = event.entityLiving;
		EntityPlayer player;
		WorldProvider provider = entity.worldObj.provider;
		
		if (entity instanceof EntityPlayer)
		{
			player = (EntityPlayer)entity;
			if (player.capabilities.isFlying)
				return;
		}
		
		if (provider instanceof WorldProviderPlanet)
		{
			WorldProviderPlanet planet = (WorldProviderPlanet)provider;
			
			if (!entity.onGround)
				entity.addVelocity(0, 0.1D - (planet.getGravity() * 0.1D), 0);
		}
		
		Entity anyEntity = event.entity;
		
		if (anyEntity.worldObj.getBlockState(new BlockPos(anyEntity.posX, anyEntity.posY - 0.1, anyEntity.posZ)) == BlockList.moving_sidewalk.getStateFromMeta(0)) {
			anyEntity.addVelocity(0, 0, 0.1);
		}
		if (anyEntity.worldObj.getBlockState(new BlockPos(anyEntity.posX, anyEntity.posY - 0.1, anyEntity.posZ)) == BlockList.moving_sidewalk.getStateFromMeta(1)) {
			anyEntity.addVelocity(-0.1, 0, 0);
		}
		if (anyEntity.worldObj.getBlockState(new BlockPos(anyEntity.posX, anyEntity.posY - 0.1, anyEntity.posZ)) == BlockList.moving_sidewalk.getStateFromMeta(2)) {
			anyEntity.addVelocity(0, 0, -0.1);
		}
		if (anyEntity.worldObj.getBlockState(new BlockPos(anyEntity.posX, anyEntity.posY - 0.1, anyEntity.posZ)) == BlockList.moving_sidewalk.getStateFromMeta(3)) {
			anyEntity.addVelocity(0.1, 0, 0);
		}
		
		if (anyEntity.worldObj.getBlockState(new BlockPos(anyEntity.posX, anyEntity.posY - 0.1, anyEntity.posZ)).getBlock() == BlockList.moving_sidewalk)
			System.out.println(" " + anyEntity.worldObj.getBlockState(new BlockPos(anyEntity.posX, anyEntity.posY - 0.1, anyEntity.posZ)).getBlock().getMetaFromState(anyEntity.worldObj.getBlockState(new BlockPos(anyEntity.posX, anyEntity.posY - 0.1, anyEntity.posZ))));
	}
	
	
	@SubscribeEvent
	public void onUpdate(Entity entity) {
		
	}
}
