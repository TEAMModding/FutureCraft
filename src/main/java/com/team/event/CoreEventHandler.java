package com.team.event;

import com.team.world.WorldProviderPlanet;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.WorldProvider;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

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
	}
}
