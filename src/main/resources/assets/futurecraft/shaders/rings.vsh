#version 120

varying vec3 vLightDirection;
varying vec3 vNormal;
varying vec3 vEyeDirection;

uniform mat4 model;
uniform mat4 view;
uniform mat4 projection;

void main(){
    mat4 MVP = projection * view * model;

    gl_Position = MVP * gl_Vertex;
    gl_TexCoord[0] = gl_MultiTexCoord0;
    
    vec3 mPos = (model * gl_Vertex).xyz;
    vec3 vPos = (view * model * gl_Vertex).xyz;
    
    vEyeDirection = vec3(0,0,0) - vPos;
    
    vec3 vLightPos = (view * vec4(0, 0, 0, 1)).xyz;
    vLightDirection = vLightPos + vEyeDirection;
    
    vNormal = (view * model * vec4(gl_Normal, 0)).xyz;
}