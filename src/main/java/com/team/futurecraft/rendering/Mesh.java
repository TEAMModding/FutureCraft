package com.team.futurecraft.rendering;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import java.nio.FloatBuffer;

public class Mesh {
	private int VAO;
	private int VBO;
	
	private int length;
	
	public Mesh(float[] vertices) {
		VAO = glGenVertexArrays();
		glBindVertexArray(VAO);
		
		VBO = glGenBuffers();
		
		length = vertices.length / 3;
		
		FloatBuffer vertexBuffer = GLBuffers.StaticBuffer(vertices);
		
		glBindBuffer(GL_ARRAY_BUFFER, VBO);
		glBufferData(GL_ARRAY_BUFFER, vertexBuffer, GL_STATIC_DRAW);
		
		glVertexAttribPointer(0, 3, GL_FLOAT, false, 3 * 4, 0);
		glEnableVertexAttribArray(0);
		
		glBindVertexArray(0);
	}
	
	public void bind() {
		glBindVertexArray(VAO);
	}
	
	public void unBind() {
		glBindVertexArray(0);
	}
	
	public void draw() {
		glDrawArrays(GL_TRIANGLES, 0, length);
	}
}
