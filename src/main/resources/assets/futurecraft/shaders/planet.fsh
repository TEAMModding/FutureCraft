#version 120

uniform sampler2D texture[3];
uniform vec3 atmosphereColor;
uniform float atmosphereDensity;
uniform float nightMultiplier;

varying vec3 vLightDirection;
varying vec3 vNormal;
varying vec3 vEyeDirection;

void main() {
    vec3 textureColor = texture2D(texture[0], gl_TexCoord[0].st).rgb;
	vec3 isWater = texture2D(texture[1], gl_TexCoord[0].st).rgb;
	vec3 nightColor = texture2D(texture[2], gl_TexCoord[0].st).rgb;
    
    vec3 n = normalize( vNormal);
    vec3 l = normalize( vLightDirection);
    
    float cosTheta = clamp(dot( n,l ), 0, 1);
	float negCosTheta = clamp(dot( n, -l), 0, 1);
    vec3 lightColor = vec3(1.0, 1.0, 1.0);
    
    vec3 E = normalize(vEyeDirection);
	vec3 R = reflect(-l,n);
    float cosAtmo = clamp( dot( E,n ), 0,1 );
	float cosAlpha = clamp( dot( E,R ), 0,1 );

	vec3 waterColor = isWater * vec3(0, 0.1, 0.5) * (lightColor * cosTheta);
	vec3 specular = pow(cosAlpha, 5) * isWater * 0.5 * cosTheta;
    vec3 diffuse = ((lightColor * cosTheta) * textureColor) * ( 1 - isWater);
	vec3 night = nightColor * negCosTheta * nightMultiplier;
    
    gl_FragColor = vec4(textureColor, 1.0);
}