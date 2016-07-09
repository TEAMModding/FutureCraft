#version 120

varying vec4 color;
varying float distance;

void main()
{
    gl_FragColor = vec4(0.2, 0.2, 0.2, clamp((20 - distance) / 10, 0, 1));
} 
