#version 120

uniform sampler2D texture;
uniform vec3 lightDirection;

varying vec3 fragNormal;

void main()
{
	vec3 texColor = texture2D(texture, gl_TexCoord[0].st).rgb;

	float diff = max(dot(fragNormal, lightDirection), 0.0);

    gl_FragColor = vec4(texColor * diff, 1.0);
} 
