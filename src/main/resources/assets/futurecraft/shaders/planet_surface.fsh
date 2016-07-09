#version 120

uniform sampler2D texture;

void main()
{
	vec4 texColor = texture2D(texture, gl_TexCoord[0].st);
    gl_FragColor = texColor;
} 
