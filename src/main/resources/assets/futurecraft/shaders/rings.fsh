#version 120

uniform sampler2D texture;
uniform float innerRadius;
uniform float outerRadius;

varying vec3 vLightDirection;
varying vec3 vNormal;
varying vec3 vEyeDirection;

void main(){
    vec4 textureColor = texture2D(texture, gl_TexCoord[0].st);
    
    vec3 n = normalize( vNormal);
    vec3 l = normalize( vLightDirection);
    
    float cosTheta = clamp(dot( n,l ), 0, 1);
    vec3 E = normalize(vEyeDirection);
    vec3 R = reflect(-l,n);
    float cosAlpha = clamp( dot( E,R ), 0,1 ); 
    
    gl_FragColor = vec4(textureColor.rgb, 0.5);
}