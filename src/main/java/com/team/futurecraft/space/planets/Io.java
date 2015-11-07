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

public class Io extends Planet {

	public Io(CelestialObject parent) {
		super(parent);
		
		this.orbit = new OrbitalParameters(62344209600L, 1.769f, 0.421594364f, 0.004f, 0.04f, 312.981f, -215.246f, 8.989f);
		this.physical = new PhysicalParameters(0.18328f, 3643.2f, 0, 1.7691f, 0, 0.04f, 312.981f);
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
		return "Io";
	}

	@Override
	public String getTexture() {
		return "io";
	}
	
	@Override
	public String getNightTexture() {
		return "io_lights";
	}
	
	public float getNightMultiplier() {
		return 5.0f;
	}
	
	@Override
	public String getColormap() {
		return null;
	}
}

