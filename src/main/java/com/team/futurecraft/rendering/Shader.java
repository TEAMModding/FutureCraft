package com.team.futurecraft.rendering;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.team.futurecraft.Mat4f;
import com.team.futurecraft.Vec3f;
import com.team.futurecraft.Vec4f;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

public class Shader {
	public int id;
	
	public Shader(String filename) {
		int vertexShader;
		vertexShader = glCreateShader(GL_VERTEX_SHADER);
		
		glShaderSource(vertexShader, read(filename + ".vsh"));
		glCompileShader(vertexShader);
		check(vertexShader);
		
		int fragmentShader;
		fragmentShader = glCreateShader(GL_FRAGMENT_SHADER);
		
		glShaderSource(fragmentShader, read(filename + ".fsh"));
		glCompileShader(fragmentShader);
		check(fragmentShader);
		
		id = glCreateProgram();
		glAttachShader(id, vertexShader);
		glAttachShader(id, fragmentShader);
		glLinkProgram(id);
		
		glDeleteShader(vertexShader);
		glDeleteShader(fragmentShader);
	}
	
	public void bind() {
		glUseProgram(this.id);
	}
	
	public void unBind() {
		glUseProgram(0);
	}
	
	public void uniformVec4f(String name, Vec4f value) {
		int vertexColorLocation = glGetUniformLocation(this.id, name);
		glUniform4f(vertexColorLocation, value.x, value.y, value.z, value.w);
	}
	
	public void uniformVec3f(String name, Vec3f value) {
		int vertexColorLocation = glGetUniformLocation(this.id, name);
		glUniform3f(vertexColorLocation, value.x, value.y, value.z);
	}
	
	public void uniformFloat(String name, float value) {
		int vertexColorLocation = glGetUniformLocation(this.id, name);
		glUniform1f(vertexColorLocation, value);
	}
	
	public void uniformInt(String name, int value) {
		int vertexColorLocation = glGetUniformLocation(this.id, name);
		glUniform1i(vertexColorLocation, value);
	}
	
	public void uniformMat4(String name, Mat4f value) {
		int vertexColorLocation = glGetUniformLocation(this.id, name);
		glUniformMatrix4(vertexColorLocation, false, value.getBuffer());
	}
	
	private static void check(int shader) {
		int success;
		success = glGetShaderi(shader, GL_COMPILE_STATUS);
		if (success != GL_TRUE) {
			throw new RuntimeException(glGetShaderInfoLog(shader, 10000));
		}
	}
	
	private static String read(String path) {
Minecraft mc = Minecraft.getMinecraft();
		
		String shader = "";
		try {
			InputStream stream = mc.getResourceManager().getResource(new ResourceLocation("futurecraft", "shaders/" + path)).getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
			String line = null;
			while ((line = reader.readLine()) != null) {
				shader += line + "\n";
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return shader;
	}
}
