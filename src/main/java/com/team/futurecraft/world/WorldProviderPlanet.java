package com.team.futurecraft.world;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import com.team.futurecraft.FutureCraft;
import com.team.futurecraft.SpaceRegistry;
import com.team.futurecraft.rendering.PlanetSkyRenderer;
import com.team.futurecraft.space.Planet;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ResourceLocation;
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
	public Planet planet;
	private int[] heightmap;
	
	public WorldProviderPlanet() {
		
	}
	
	public int getTurfColor(BlockPos pos) {
		return heightmap[pos.getY()];
	}
	
    @Override
	public Vec3 getFogColor(float p_76562_1_, float p_76562_2_) {
    	float time = (float) (System.nanoTime() * 0.000000001 * 0.0003f)  + FutureCraft.timeOffset;
    	Vec3 planetToLight = new Vec3(0, 0, 0).subtract(this.planet.getPosition(time)).normalize();
    	float value = (float) this.planet.getDirection(time).dotProduct(planetToLight) * 4.0f;
    	if (value < -1) value = -1;
    	
    	float r = (float) this.planet.getAtmosphericColor().xCoord;
    	float g = (float) this.planet.getAtmosphericColor().yCoord;
    	float b = (float) this.planet.getAtmosphericColor().zCoord;
    	
    	Vec3 color = new Vec3(Math.min(r * value, r), Math.min(g * value, g), Math.min(b * value, b));
    	
    	if (this.planet.getAtmosphericDensity() > 0)
    		return color;
    	else
    		return new Vec3(-1.0, -1.0, -1.0);
	}
    
    @SideOnly(Side.CLIENT)
    public float getSunBrightness(float par1)
    {
    	float time = (float) (System.nanoTime() * 0.000000001 * 0.0003f)  + FutureCraft.timeOffset;
    	Vec3 planetToLight = new Vec3(0, 0, 0).subtract(this.planet.getPosition(time)).normalize();
    	float value = (float) this.planet.getDirection(time).dotProduct(planetToLight) * 4.0f;
    	if (value < 0) value = 0;
    	if (value > 1) value = 1;
    	//System.out.println(value);
    	
        return value;
    }
    
    public long getWorldTime()
    {
    	return 5000;
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
		this.worldChunkMgr = new PlanetChunkManager(this.planet.getBiome(), 0.0F);
        this.setSkyRenderer(new PlanetSkyRenderer(this.planet));
	}
    
    public String getSaveFolder() {
    	return this.planet.getName();
    }
    
    public IChunkProvider createChunkGenerator() {
    	return new ChunkProviderPlanet(this.worldObj, this.worldObj.getSeed(), this.planet.getWorldType());
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
        return 100;
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