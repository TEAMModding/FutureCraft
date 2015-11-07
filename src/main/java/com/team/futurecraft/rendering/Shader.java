package com.team.futurecraft.rendering;

import static org.lwjgl.opengl.GL11.GL_TRUE;
import static org.lwjgl.opengl.GL20.GL_COMPILE_STATUS;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL20.glAttachShader;
import static org.lwjgl.opengl.GL20.glCompileShader;
import static org.lwjgl.opengl.GL20.glCreateProgram;
import static org.lwjgl.opengl.GL20.glCreateShader;
import static org.lwjgl.opengl.GL20.glDeleteShader;
import static org.lwjgl.opengl.GL20.glGetShaderInfoLog;
import static org.lwjgl.opengl.GL20.glGetShaderi;
import static org.lwjgl.opengl.GL20.glLinkProgram;
import static org.lwjgl.opengl.GL20.glShaderSource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.Buffer;
import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.BiConsumer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL20;

import com.team.futurecraft.Mat4;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;

public class Shader {
	public int id;
	
	private static HashMap<String, Integer> shaders = new HashMap<String, Integer>();
	
	public Shader(int id) {
		this.id = id;
	}
	
	public void bind() {
		GL20.glUseProgram(id);
	}
	
	public void unBind() {
		GL20.glUseProgram(0);
	}
	
	public void uniformMat4(Mat4 mat, String name) {
		int uniform = GL20.glGetUniformLocation(this.id, name);
        GL20.glUniformMatrix4(uniform, false, mat.getBuffer());
	}
	
	public void uniformFloat(float value, String name) {
		int uniform = GL20.glGetUniformLocation(this.id, name);
		GL20.glUniform1f(uniform, value);
	}
	
	public void uniformVec3(Vec3 vec, String name) {
		int uniform = GL20.glGetUniformLocation(this.id, name);
		GL20.glUniform3f(uniform, (float)vec.xCoord, (float)vec.yCoord, (float)vec.zCoord);
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
	
	class MapConsumer implements BiConsumer<String, Integer> {
		@Override
		public void accept(String t, Integer u) {
			
		}
	}
}