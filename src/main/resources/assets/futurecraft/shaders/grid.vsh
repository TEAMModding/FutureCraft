#version 120

uniform mat4 model;
uniform mat4 view;
uniform mat4 projection;

varying vec4 color;
varying float distance;

void main()
{
    gl_Position = projection * view * model * gl_Vertex;
    gl_TexCoord[0] = gl_MultiTexCoord0;
    color = gl_Color;

    distance = length(view * model * gl_Vertex);
}
