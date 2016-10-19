#version 120

varying vec2 TexCoords;

void main()
{
    gl_Position = gl_Vertex; 
    gl_TexCoord[0] = gl_MultiTexCoord0;
}  