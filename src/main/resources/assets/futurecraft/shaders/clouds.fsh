#version 120

uniform sampler2D texture;
uniform vec3 atmosphereColor;
uniform float atmosphereDensity;
uniform float nightMultiplier;

varying vec3 vLightDirection;
varying vec3 vNormal;
varying vec3 vEyeDirection;

void main(){
    vec3 textureColor = texture2D(texture, gl_TexCoord[0].st).rgb;
    
    float greyscale = (textureColor.r + textureColor.g + textureColor.b) / 2;
    
    vec3 n = normalize( vNormal);
    vec3 l = normalize( vLightDirection);
    
    float cosTheta = clamp(dot( n,l ), 0, 1);
	float negCosTheta = clamp(dot( n, -l), 0, 1);
    vec3 lightColor = vec3(1.0, 1.0, 1.0);
    
    vec3 E = normalize(vEyeDirection);
	vec3 R = reflect(-l,n);
    float cosAtmo = clamp( dot( E,n ), 0,1 );
	float cosAlpha = clamp( dot( E,R ), 0,1 );
	
	vec3 diffuse = (lightColor * cosTheta) * vec3(1, 1, 1) * greyscale;
    
    if (atmosphereDensity > 0) {
    	gl_FragColor = vec4(mix(atmosphereColor * cosTheta * atmosphereDensity, diffuse, cosAtmo), greyscale);
    }
    else {
    	gl_FragColor = vec4(diffuse, greyscale);
    }
}