package com.team.futurecraft.space.planets;

import com.team.futurecraft.biome.BiomePlanet;
import com.team.futurecraft.space.CelestialObject;
import com.team.futurecraft.space.OrbitalParameters;
import com.team.futurecraft.space.PhysicalParameters;
import com.team.futurecraft.space.Planet;
import com.team.futurecraft.space.PlanetType;

import net.minecraft.util.Vec3;

public class Earth extends Planet {

	public Earth(CelestialObject parent) {
		super(parent);
		
		this.orbit = new OrbitalParameters(63082497600L, 27.322f, 0.00470989f, 0.055f, 5.15f, 125.08f, 138.15f, 135.27f);
		this.physical = new PhysicalParameters(1, 12756.28f, 0, 1, -79.5f, 23.439f, 180f);
	}

	public BiomePlanet getBiome() {
		return null;
	}
	
	@Override
	public PlanetType getWorldType() {
		return null;
	}

	@Override
	public Vec3 getAtmosphericColor() {
		return new Vec3(0.3, 0.6, 1);
	}

	@Override
	public float getAtmosphericDensity() {
		return 0.7f;
	}

	@Override
	public String getName() {
		return "Earth";
	}

	@Override
	public String getTexture() {
		return "earth";
	}
	
	@Override
	public String getCloudTexture() {
		return "earth_clouds";
	}
	
	@Override
	public String getNightTexture() {
		return "earth_lights";
	}
	
	@Override
	public String getOceanTexture() {
		return "earth_ocean";
	}

	@Override
	public String getColormap() {
		return null;
	}
}
