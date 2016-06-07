#version 120

uniform mat4 model;
uniform mat4 view;
uniform mat4 projection;

varying vec4 color;

void main()
{
	mat4 newview = view;
	newview[3][0] = 0;
	newview[3][1] = 0;
	newview[3][2] = 0;

    gl_Position = projection * newview * model * gl_Vertex;
    color = gl_Color;
    gl_TexCoord[0] = gl_MultiTexCoord0;
}