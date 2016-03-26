package com.team.futurecraft.rendering;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

import com.team.futurecraft.Mat4f;
import com.team.futurecraft.Vec3f;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

public class Shader {
	public int id;
	
	private static HashMap<String, Integer> shaders = new HashMap<String, Integer>();
	private static HashMap<Integer, Integer> BoundVsh = new HashMap<Integer, Integer>();
	private static HashMap<Integer, Integer> BoundFsh = new HashMap<Integer, Integer>();
	
	public Shader(int id) {
		this.id = id;
	}
	
	public void bind() {
		glUseProgram(id);
	}
	
	public void unBind() {
		glUseProgram(0);
	}
	
	public void uniformMat4(Mat4f mat, String name) {
		int uniform = glGetUniformLocation(this.id, name);
        glUniformMatrix4(uniform, false, mat.getBuffer());
	}
	
	public void uniformFloat(float value, String name) {
		int uniform = glGetUniformLocation(this.id, name);
		glUniform1f(uniform, value);
	}
	
	public void uniformVec3f(Vec3f vec, String name) {
		int uniform = glGetUniformLocation(this.id, name);
		glUniform3f(uniform, (float)vec.x, (float)vec.y, (float)vec.z);
	}
	
	public static Shader loadShader(String name) {
		
		if (shaders.containsKey(name)) {
			return new Shader(shaders.get(name));
		}
		else {
			System.out.println("loading shader: " + name + " from file");
			
			int id = glCreateProgram();
			loadProgram(name + ".vsh", name + ".fsh", id);
			shaders.put(name, id);
			
			return new Shader(id);
		}
	}
	
	public static void reloadShaders() {
		Set<Entry<String, Integer>> entrySet = shaders.entrySet();
		Iterator<Entry<String, Integer>> iterator = entrySet.iterator();
		
		while (iterator.hasNext()) {
			Entry<String, Integer> entry = iterator.next();
			String key = entry.getKey();
			int value = entry.getValue();
			
			glDetachShader(value, BoundVsh.get(value));
			glDetachShader(value, BoundFsh.get(value));
			
			loadProgram(key + ".vsh", key + ".fsh", value);
		}
	}
	
	private static void loadProgram(String vshPath, String fshPath, int id) {
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
		
		BoundVsh.put(id, vertexShader);
		BoundFsh.put(id, fragmentShader);
		
		//create shader program
		glAttachShader(id, vertexShader);
		glAttachShader(id, fragmentShader);
		glLinkProgram(id);
		glDeleteShader(vertexShader);
		glDeleteShader(fragmentShader);
	}
	
	private static String loadShaderFile(String path) {
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
