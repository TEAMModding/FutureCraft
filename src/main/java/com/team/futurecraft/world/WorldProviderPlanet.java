package com.team.futurecraft.world;

import com.team.futurecraft.SpaceRegistry;
import com.team.futurecraft.rendering.PlanetSkyRenderer;
import com.team.futurecraft.space.Planet;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.util.Vec3;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.chunk.IChunkProvider;

/**
 * This represents the dimension for a certain planet.
 * Simply takes data from the Planet object and generates accordingly.
 * 
 * @author Joseph
 *
 */
public class WorldProviderPlanet extends WorldProvider {
	private Planet planet;
	
	
	
    @Override
	public Vec3 getFogColor(float p_76562_1_, float p_76562_2_) {
    	if (this.planet.getAtmosphericDensity() > 0)
    		return this.planet.getAtmosphericColor();
    	else
    		return new Vec3(0.5, 0.5, 0.5);
	}
    
    public boolean hasAtmosphere() {
    	if (this.planet.getAtmosphericDensity() == 0.0f) return false;
    	return true;
    }

	@Override
	public void setDimension(int dim) {
    	super.setDimension(dim);
		this.planet = SpaceRegistry.getPlanetForDimension(dim);
		System.out.println("loaded dimension for: " + planet.getName());
	}

	public void registerWorldChunkManager() {
		this.worldChunkMgr = new PlanetChunkManager(this.planet.getWorldType().getBiome(), 0.0F);
        this.setSkyRenderer(new PlanetSkyRenderer(this.planet));
	}
    
    public String getSaveFolder() {
    	return this.planet.getName();
    }
    
    public IChunkProvider createChunkGenerator() {
    	return new ChunkProviderPlanet(this.worldObj, this.worldObj.getSeed(), this.planet.getWorldType().getStoneBlock());
    }
    
    /**
     * True if the player can respawn in this dimension (true = overworld, false = nether).
     */
    public boolean canRespawnHere() {
        return false;
    }

    /**
     * the y level at which clouds are rendered.
     */
    @SideOnly(Side.CLIENT)
    public float getCloudHeight() {
        return 8.0F;
    }
    
    public boolean isSurfaceWorld() {
        return false;
    }

    public int getAverageGroundLevel() {
        return 50;
    }

    /**
     * Returns the dimension's name, e.g. "The End", "Nether", or "Overworld".
     */
    public String getDimensionName() {
    	return this.planet.getName();
    }
    
    /**
     * gets the gravity for this planet 1.0 is earth gravity.
     * warning: values below 0.2 will never let the player fall
     * 
     * @return the gravity for this planet
     */
    public double getGravity() {
    	return this.planet.getGravity();
    }
    
    public String getImage() {
    	return this.planet.getTexture();
    }
    
    @Override
	public String getInternalNameSuffix() {
		return null;
	}
}