package com.team.futurecraft.space;

import java.io.InputStream;
import java.math.BigDecimal;

import org.apache.commons.io.IOUtils;

import com.google.common.base.Charsets;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.team.futurecraft.FutureCraft;
import com.team.futurecraft.MetricUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IResource;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;

public class SpaceJSONLoader {
	public static CelestialObject starSystem;
	
	public static void load() {
		JsonParser jsonparser = new JsonParser();
        InputStream inputstream = null;
        
        try {
        	IResource resource = Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation("futurecraft", "data/solarSystem.json"));
        	inputstream = resource.getInputStream();
        	
        	JsonObject jsonobject = jsonparser.parse(IOUtils.toString(inputstream, Charsets.UTF_8)).getAsJsonObject();
        	
        	CelestialObject obj = process(jsonobject, null);
        	starSystem = obj;
        	starSystem.init();
        	System.out.println(obj);
        }
        catch (Exception exception2)
        {
        	System.err.println("failed to load solar system");
        	exception2.printStackTrace();
        }
	}
	
	private static String getString(JsonObject obj, String name, String backup) {
		if (obj.has(name)) {
			try {
				return obj.get(name).getAsString();
			}
			catch (Exception e) {}
		}
		return backup;
	}
	
	private static float getFloat(JsonObject obj, String name, float backup) {
		if (obj.has(name)) {
			try {
				return obj.get(name).getAsFloat();
			}
			catch (Exception e) {}
		}
		return backup;
	}
	
	private static BigDecimal getDistance(JsonObject obj, String name, String backup) {
		String st = getString(obj, name, backup);
		BigDecimal dec = MetricUtil.multiDistanceToMeters(st);
		if (dec != null) {
			return dec;
		}
		else {
			return new BigDecimal(0);
		}
	}
	
	private static BigDecimal getTime(JsonObject obj, String name, String backup) {
		String st = getString(obj, name, backup);
		BigDecimal dec = MetricUtil.multiTimeToSeconds(st);
		if (dec != null) {
			return dec;
		}
		else {
			return new BigDecimal(0);
		}
	}
	
	private static CelestialObject process(JsonObject obj, CelestialObject parent) {
		String type = getString(obj, "type", "barycenter");
		CelestialObject spaceObject = null;
		
		if (type.equals("star")) {
			spaceObject = new Star(parent);
		}
		if (type.equals("planet")) {
			spaceObject = new Planet(parent);
		}
		
		String name = getString(obj, "name", "Unnamed");
		float gravity = getFloat(obj, "gravity", 0.0f);
		BigDecimal diameter = getDistance(obj, "diameter", "0");
		float oblateness = getFloat(obj, "oblateness", 0.0f);
		BigDecimal rotationPeriod = getTime(obj, "rotationPeriod", "0");
		float rotationOffset = getFloat(obj, "rotationOffset", 0.0f);
		float obliquity = getFloat(obj, "obliquity", 0.0f);
		float eqAscNode = getFloat(obj, "eqAscNode", 0.0f);
		
		spaceObject.name = name;
		spaceObject.physical = new PhysicalParameters(gravity, diameter.floatValue(), oblateness, rotationPeriod.floatValue(), rotationOffset, obliquity, eqAscNode);
		
		if (obj.has("orbit")) {
			if (obj.get("orbit").isJsonObject()) {
				JsonObject orbit = obj.get("orbit").getAsJsonObject();
				BigDecimal period = getTime(orbit, "period", "0");
				BigDecimal semimajorAxis = getDistance(orbit, "semimajorAxis", "0");
				float eccentricity = getFloat(orbit, "eccentricity", 0.0f);
				float inclination = getFloat(orbit, "inclination", 0.0f);
				float ascendingNode = getFloat(orbit, "ascendingNode", 0.0f);
				float argOfPericenter = getFloat(orbit, "argOfPericenter", 0.0f);
				float meanAnomaly = getFloat(orbit, "meanAnomaly", 0.0f);
				
				spaceObject.orbit = new OrbitalParameters(63082497600L, period.floatValue(), semimajorAxis.floatValue(), eccentricity, inclination, ascendingNode, argOfPericenter, meanAnomaly);
			}
		}
		else {
			spaceObject.orbit = new OrbitalParameters(63082497600L, 0f, 0f, 0f, 0f, 0f, 0f, 0f);
		}
		
		if (obj.has("children")) {
			JsonElement chobj = obj.get("children");
			if (chobj.isJsonArray()) {
				JsonArray charr = chobj.getAsJsonArray();
				
				for (int i = 0; i < charr.size(); i++) {
					JsonElement iterEl = charr.get(i);
					if (iterEl.isJsonObject()) {
						JsonObject iterObj = iterEl.getAsJsonObject();
						CelestialObject cestobj = process(iterObj, spaceObject);
						spaceObject.add(cestobj);
						System.out.println(cestobj);
					}
				}
			}
		}
		
		return spaceObject;
	}
}
