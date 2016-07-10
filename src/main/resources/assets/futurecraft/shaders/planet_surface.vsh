#version 120

uniform mat4 model;
uniform mat4 view;
uniform mat4 projection;

varying vec3 fragNormal;

void main()
{
    gl_Position = projection * view * model * gl_Vertex;
    fragNormal = (model * vec4(gl_Normal, 1.0)).xyz;
    gl_TexCoord[0] = gl_MultiTexCoord0;
}
