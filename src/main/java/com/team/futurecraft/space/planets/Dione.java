package com.team.futurecraft.space.planets;

import com.team.futurecraft.biome.BiomeList;
import com.team.futurecraft.biome.BiomePlanet;
import com.team.futurecraft.space.CelestialObject;
import com.team.futurecraft.space.OrbitalParameters;
import com.team.futurecraft.space.PhysicalParameters;
import com.team.futurecraft.space.Planet;
import com.team.futurecraft.space.PlanetType;
import com.team.futurecraft.space.PlanetTypeFrozen;

import net.minecraft.util.Vec3;

public class Dione extends Planet {

	public Dione(CelestialObject parent) {
		super(parent);
		
		this.orbit = new OrbitalParameters(63050918400L, 2.7369f, 0.377394954f, 0f, 0.002f, 57.741f, 173.999f, 109.204f);
		this.physical = new PhysicalParameters(0.023954f, 1118f, 0f, 2.7369f, 0f, 0.002f, 57.741f);
	}
	
	public BiomePlanet getBiome() {
		return BiomeList.FROZEN_EUROPA;
	}

	@Override
	public PlanetType getWorldType() {
		return new PlanetTypeFrozen();
	}

	@Override
	public Vec3 getAtmosphericColor() {
		return new Vec3(1, 1, 1);
	}

	@Override
	public float getAtmosphericDensity() {
		return 0.0f;
	}

	@Override
	public String getName() {
		return "Dione";
	}

	@Override
	public String getTexture() {
		return "dione";
	}
	
	@Override
	public String getColormap() {
		return null;
	}
}
