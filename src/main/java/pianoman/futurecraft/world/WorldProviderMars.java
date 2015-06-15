package pianoman.futurecraft.world;

import net.minecraft.world.chunk.IChunkProvider;
import pianoman.futurecraft.BlockList;
import pianoman.futurecraft.biome.BiomeList;

/**
 * The class that specifies mars as a planet into the registry.
 * 
 * @author Joseph
 *
 */
public class WorldProviderMars extends WorldProviderPlanet
{
	/**
     * creates a new world chunk manager for WorldProvider
     */
    public void registerWorldChunkManager()
    {
        this.worldChunkMgr = new PlanetChunkManager(BiomeList.mars, 0.0F);
    }
    
    public WorldProviderMars()
    {
    	this.setDimension(-11);
    }
    
    /**
     * Returns a new chunk provider which generates chunks for this world
     */
    public IChunkProvider createChunkGenerator()
    {
        return new ChunkProviderPlanet(this.worldObj, this.worldObj.getSeed(), BlockList.desert_stone);
    }
    
    public double getGravity()
    {
    	return 0.38D;
    }
    
    public String getPlanetName()
    {
        return "Mars";
    }

	@Override
	public String getSaveFolder() 
	{
		return "mars";
	}
	
	@Override
	public String getImage() 
	{
		return "textures/blocks/mars_sand.png";
	}
}
