package com.team.futurecraft.rendering;

import static org.lwjgl.opengl.GL11.*;

public class Mesh {
	private float[] vertices;
	private float[] colors;
	private float[] normals;
	private float[] uvs;
	
	public Mesh(float[] vertices) {
		this.vertices = vertices;
	}
	
	public Mesh(float[] vertices, float[] colors, float[] uvs, float[] normals) {
		this.vertices = vertices;
		this.colors = colors;
		this.uvs = uvs;
		this.normals = normals;
	}
	
	public void draw() {
		glBegin(GL_TRIANGLES);
		
		boolean hasColors = colors != null;
		boolean hasNormals = normals != null;
		boolean hasUVs = uvs != null;
		
		int j = 0;
		for (int i = 0; i < vertices.length; i += 3) {
			if (hasColors) glColor3f(colors[i], colors[i + 1], colors[i + 2]);
			if (hasNormals) glNormal3f(normals[i], normals[i + 1], normals[i + 2]);
			if (hasUVs) glTexCoord2f(uvs[j], uvs[j + 1]);
			glVertex3f(vertices[i], vertices[i + 1], vertices[i + 2]);
			
			j += 2;
		}
		glEnd();
	}
}
