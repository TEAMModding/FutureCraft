package com.team.futurecraft.event;

import java.util.Iterator;
import java.util.List;

import com.team.futurecraft.entity.ChunkEntity;
import com.team.futurecraft.item.ItemSpaceSuit;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.world.WorldProvider;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import com.team.futurecraft.world.WorldProviderPlanet;

/**
 * The core event handler for Futurecraft. Currently only handles
 * low gravity physics.
 * 
 * @author Joseph
 *
 */
public class CoreEventHandler {
@SubscribeEvent
	public void onUpdateEvent(LivingUpdateEvent event) {
		handleChunkEntityBB(event);
		EntityLivingBase entity = event.entityLiving;
		EntityPlayer player = null;
		WorldProvider provider = entity.worldObj.provider;
		
		if (entity instanceof EntityPlayer) {
			player = (EntityPlayer)entity;
			if (player.capabilities.isFlying) return;
		}
		
		if (provider instanceof WorldProviderPlanet) {
			if (player != null)
				if (player.getInventory()[0] != null &&
					player.getInventory()[1] != null &&
					player.getInventory()[2] != null &&
					player.getInventory()[3] != null) {
					if (player.getInventory()[0].getItem() instanceof ItemSpaceSuit &&
							player.getInventory()[1].getItem() instanceof ItemSpaceSuit &&
							player.getInventory()[2].getItem() instanceof ItemSpaceSuit &&
							player.getInventory()[3].getItem() instanceof ItemSpaceSuit) {
						//System.out.println("wearing a space suit");
					}
					else if (!player.capabilities.isCreativeMode) {
						player.attackEntityFrom(DamageSource.generic, 1000);
					}
				}
				else if (!player.capabilities.isCreativeMode) {
					player.attackEntityFrom(DamageSource.generic, 1000);
				}
			WorldProviderPlanet planet = (WorldProviderPlanet)provider;
			
			if (entity.stepHeight != 1f) entity.stepHeight = 1f;
			
			if (!entity.onGround) entity.addVelocity(0, 0.1D - (planet.getGravity() * 0.1D), 0);
		}
		else if (entity.stepHeight != 0.6f) {
			entity.stepHeight = 0.6f;
		}
	}
	
	@SubscribeEvent
	public void onFallEvent(LivingFallEvent event) {
		//System.out.println("falling");
		WorldProvider provider = event.entityLiving.worldObj.provider;
		if (provider instanceof WorldProviderPlanet) {
			System.out.println(event.damageMultiplier);
			event.distance *= (float) ((WorldProviderPlanet)provider).getGravity() * 0.8;
		}
	}
	
	@SuppressWarnings("rawtypes")
	private void handleChunkEntityBB(LivingUpdateEvent event) {
		//System.out.println("handling bounding boxes");
		Entity entity = event.entity;
		AxisAlignedBB entityBB = entity.getEntityBoundingBox();
		
		if (entityBB != null) {
			//System.out.println("bounding box is not null");
			List chunkEntities = entity.worldObj.getEntitiesWithinAABB(ChunkEntity.class, entityBB);
		
			Iterator iterator = chunkEntities.iterator();
			
			while (iterator.hasNext()) {
				ChunkEntity chunk = (ChunkEntity)iterator.next();
				chunk.handleEntity(event.entity);
			}
		}
	}
}
