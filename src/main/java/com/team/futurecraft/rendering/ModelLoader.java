package com.team.futurecraft.rendering;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.util.Vec3;

public class ModelLoader {
	public static void renderObj(String file) {
		Tessellator tessellator = Tessellator.getInstance();
	    WorldRenderer worldrenderer = tessellator.getWorldRenderer();
		Path path = Paths.get(file);
		ArrayList<Vec3> vertices = new ArrayList<Vec3>();
		ArrayList<Vec3> uvs = new ArrayList<Vec3>();
		
		if (Files.exists(path)) 
		{
			try {
				BufferedReader in = Files.newBufferedReader(path);
				String line = null;
				
				while ((line = in.readLine()) != null) 
				{
					if (line.startsWith("v ")) {
						String[] values = line.split(" ");
						float x = Float.valueOf(values[1]);
						float y = Float.valueOf(values[2]);
						float z = Float.valueOf(values[3]);
						vertices.add(new Vec3(x, y, z));
					}
					else if (line.startsWith("vt ")) {
						String[] values = line.split(" ");
						float u = Float.valueOf(values[1]);
						float v = Float.valueOf(values[2]);
						uvs.add(new Vec3(u, 0, v));
					}
					else if (line.startsWith("f ")) {
						String[] triangles = line.split(" ");
						for (int i = 1; i < 4; i++) {
							String[] index = triangles[i].split("/");
							
							Vec3 indexVertex = vertices.get(Integer.valueOf(index[0]) - 1);
							Vec3 indexUV = uvs.get(Integer.valueOf(index[1]) - 1);
							worldrenderer.addVertexWithUV(indexVertex.xCoord, indexVertex.yCoord, indexVertex.zCoord, indexUV.xCoord, indexUV.zCoord);
						}
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
