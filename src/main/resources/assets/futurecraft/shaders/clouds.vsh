#version 120

varying vec3 vLightDirection;
varying vec3 vNormal;
varying vec3 vEyeDirection;

uniform mat4 model;
uniform mat4 view;
uniform mat4 realModel;
uniform mat4 realView;
uniform mat4 projection;
uniform vec3 lightPos;

void main(){
	mat4 MVP = projection * view * model;

    gl_Position = MVP * gl_Vertex;
    gl_TexCoord[0] = gl_MultiTexCoord0;
    
    vec3 mPos = (realModel * gl_Vertex).xyz;
    vec3 vPos = (realView * realModel * gl_Vertex).xyz;
    
    vEyeDirection = vec3(0,0,0) - vPos;
    
    vec3 vLightPos = (realView * vec4(lightPos, 1)).xyz;
	vLightDirection = vLightPos + vEyeDirection;
	
	vNormal = (realView * realModel * vec4(gl_Normal, 0)).xyz;
}