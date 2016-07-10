#version 120

uniform sampler2D screenTexture;

void main()
{
	vec3 color = texture2D(screenTexture, gl_TexCoord[0].st).rgb;
    gl_FragColor = vec4(color, 1.0f);
}  

