package com.team.futurecraft.world;

import net.minecraft.world.chunk.IChunkProvider;

/**
 * Holds earth's data and doesn't specify any world generation (of course).
 * It's only used for the planet registry.
 * 
 * @author Joseph
 *
 */
public class WorldProviderEarth extends WorldProviderPlanet
{
	@Override
	public void registerWorldChunkManager()
	{
		
	}
	
	public WorldProviderEarth()
    {
    	this.setDimension(1);
    }

	@Override
	public String getSaveFolder() 
	{
		return null;
	}

	@Override
	public IChunkProvider createChunkGenerator() {
		return null;
	}

	@Override
	public String getPlanetName() {
		return "Earth";
	}

	@Override
	public double getGravity() {
		return 1;
	}

	@Override
	public String getImage() {
		return "textures/environment/earth.png";
	}

}
