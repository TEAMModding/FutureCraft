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

public class Europa extends Planet {

	public Europa(CelestialObject parent) {
		super(parent);
		
		this.orbit = new OrbitalParameters(62344209600L, 3.552f, 0.670891031f, 0.01f, 0.470f, 101.087f, 54.425f, 20.865f);
		this.physical = new PhysicalParameters(0.13418f, 6243.2f, 0, 3.5518f, 0, 0.47f, 101.087f);
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
		return "Europa";
	}

	@Override
	public String getTexture() {
		return "europa";
	}
	
	@Override
	public String getColormap() {
		return null;
	}
}
