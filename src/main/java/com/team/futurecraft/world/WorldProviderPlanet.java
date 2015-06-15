package com.team.futurecraft.world;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.chunk.IChunkProvider;

/**
 * This is the Base class for all planets.
 * Any planet's WorldProvider class that extends this one is to be
 * used as the starting point for a new planet. Here you specify gravity, the chunkManager (for biomes),
 * and the ChunkProvider (for generation). 
 * see: WorldProviderMoon and WorldProviderMars.
 * 
 * @author Joseph
 *
 */
public abstract class WorldProviderPlanet extends WorldProvider
{
    
    public abstract void registerWorldChunkManager();
    
    public abstract String getSaveFolder();
    
    public abstract IChunkProvider createChunkGenerator();
    /**
     * True if the player can respawn in this dimension (true = overworld, false = nether).
     */
    public boolean canRespawnHere()
    {
        return false;
    }

    /**
     * the y level at which clouds are rendered.
     */
    @SideOnly(Side.CLIENT)
    public float getCloudHeight()
    {
        return 8.0F;
    }
    
    public boolean isSurfaceWorld()
    {
        return false;
    }

    public int getAverageGroundLevel()
    {
        return 50;
    }

    /**
     * Returns the dimension's name, e.g. "The End", "Nether", or "Overworld".
     */
    public String getDimensionName()
    {
    	return getPlanetName();
    }
    
    public abstract String getPlanetName();
    
    /**
     * gets the gravity for this planet 1.0 is earth gravity.
     * warning: values below 0.2 will never let the player fall
     * 
     * @return the gravity for this planet
     */
    public abstract double getGravity();
    
    public abstract String getImage();
    
    @Override
	public String getInternalNameSuffix() {
		// TODO Auto-generated method stub
		return null;
	}
}