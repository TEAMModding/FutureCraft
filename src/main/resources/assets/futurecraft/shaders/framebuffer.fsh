#version 120

uniform sampler2D screenTexture;

void main()
{
    gl_FragColor = vec4(texture2D(screenTexture, gl_TexCoord[0].st).rgb, 1.0f);
}  

