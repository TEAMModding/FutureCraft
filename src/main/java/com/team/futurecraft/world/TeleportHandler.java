package com.team.futurecraft.world;

import net.minecraft.entity.Entity;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;

/**
 * Handles teleporting to planets.
 * Simply a blank slate so that we dont have to use minecraft's Teleporter
 * or else it would check for nether portals and such.
 * 
 * @author Joseph
 */
public class TeleportHandler extends Teleporter {

	public TeleportHandler(WorldServer world) {
		super(world);
	}

	@Override
	public void placeInPortal(Entity entity, float rotationYaw) {}

	@Override
	public boolean placeInExistingPortal(Entity entity, float rotationYaw) {
		return true;
	}

	@Override
	public boolean makePortal(Entity entity) {
		return true;
	}

	@Override
	public void removeStalePortalLocations(long time) {}
}
