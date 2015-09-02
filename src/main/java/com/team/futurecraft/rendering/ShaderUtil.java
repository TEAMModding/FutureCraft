package com.team.futurecraft.rendering;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


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
	
	public static String loadShaderFile(String path) {
		String shader = "";
		try {
			BufferedReader reader = Files.newBufferedReader(Paths.get(path));
			String line = null;
			while ((line = reader.readLine()) != null) {
				shader += line + "\n";
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return shader;
	}
}
