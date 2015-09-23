package com.team.futurecraft.rendering;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.lwjgl.opengl.GL20;

import com.team.futurecraft.Mat4;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;


public class ShaderUtil 
{	
	public static int loadShader(String vshPath, String fshPath) {
		String vertexSource = loadShaderFile(vshPath);
		String fragmentSource = loadShaderFile(fshPath);
		
		//compile shaders
		int vertexShader = glCreateShader(GL_VERTEX_SHADER);
		glShaderSource(vertexShader, vertexSource);
		glCompileShader(vertexShader);
		
		int fragmentShader = glCreateShader(GL_FRAGMENT_SHADER);
		glShaderSource(fragmentShader, fragmentSource);
		glCompileShader(fragmentShader);
		
		//check shaders
		int status = glGetShaderi(vertexShader, GL_COMPILE_STATUS);
		if (status != GL_TRUE) {
			throw new RuntimeException(glGetShaderInfoLog(vertexShader, 1000));
		}
		int status2 = glGetShaderi(fragmentShader, GL_COMPILE_STATUS);
		if (status2 != GL_TRUE) {
			throw new RuntimeException(glGetShaderInfoLog(fragmentShader, 1000));
		}
				
		//create shader program
		int shaderProgram = glCreateProgram();
		glAttachShader(shaderProgram, vertexShader);
		glAttachShader(shaderProgram, fragmentShader);
		glLinkProgram(shaderProgram);
		glDeleteShader(vertexShader);
		glDeleteShader(fragmentShader);
		
		return shaderProgram;
	}
	
	public static void uniformMat4(Mat4 mat, String name, int shader) {
		int uniform = GL20.glGetUniformLocation(shader, name);
        GL20.glUniformMatrix4(uniform, false, mat.getBuffer());
	}
	
	public static void uniformFloat(float value, String name, int shader) {
		int uniform = GL20.glGetUniformLocation(shader, name);
		GL20.glUniform1f(uniform, value);
	}
	
	public static void uniformVec3(Vec3 vec, String name, int shader) {
		int uniform = GL20.glGetUniformLocation(shader, name);
		GL20.glUniform3f(uniform, (float)vec.xCoord, (float)vec.yCoord, (float)vec.zCoord);
	}
	
	public static String loadShaderFile(String path) {
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
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return shader;
	}
}
