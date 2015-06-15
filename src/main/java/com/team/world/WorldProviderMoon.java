package com.team.world;

import com.team.BlockList;
import com.team.SkyRenderer;
import com.team.biome.BiomeList;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.world.chunk.IChunkProvider;

/**
 * The class that specifies the moon as a planet into the registry.
 * 
 * @author Joseph
 *
 */
public class WorldProviderMoon extends WorldProviderPlanet
{
	/**
     * creates a new world chunk manager for WorldProvider
     */
    public void registerWorldChunkManager()
    {
        this.worldChunkMgr = new PlanetChunkManager(BiomeList.moon, 0.0F);
        this.setSkyRenderer(new SkyRenderer());
    }
    
    public WorldProviderMoon()
    {
    	this.setDimension(-10);
    }
    
    @SideOnly(Side.CLIENT)
    public float[] calcSunriseSunsetColors(float p_76560_1_, float p_76560_2_)
    {
        return null;
    }
    
    /**
     * Returns a new chunk provider which generates chunks for this world
     */
    public IChunkProvider createChunkGenerator()
    {
        return new ChunkProviderPlanet(this.worldObj, this.worldObj.getSeed(), BlockList.selena_stone);
    }
    
    public double getGravity()
    {
    	return 0.3D;
    }
    
    public String getPlanetName()
    {
        return "The Moon";
    }

	@Override
	public String getSaveFolder() 
	{
		return "moon";
	}

	@Override
	public String getImage() 
	{
		return "textures/blocks/moon_dirt.png";
	}
}
