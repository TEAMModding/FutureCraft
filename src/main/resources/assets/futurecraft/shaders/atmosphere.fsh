#version 120

uniform vec3 atmosphereColor;
uniform float atmosphereDensity;

varying vec3 vLightDirection;
varying vec3 vNormal;
varying vec3 vEyeDirection;

void main(){
	vec3 n = normalize( vNormal);
    vec3 l = normalize( vLightDirection);
	float cosTheta = clamp(dot( -n,l ), 0, 1);

	vec3 E = normalize(vEyeDirection);
	vec3 R = reflect(-l,n);
    float cosAtmo = clamp( dot( E,n ) * 9 - 1.2, 0,1 );

    gl_FragColor = vec4(atmosphereColor * 6 * atmosphereDensity * cosAtmo * cosTheta, 1);
}