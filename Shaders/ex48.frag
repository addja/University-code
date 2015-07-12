// simple fragment shader

// 'time' contains seconds since the program was linked.
uniform float time;
varying vec3 norm;
varying vec4 obj;
uniform sampler2D sampler;

vec4 shading(vec3 N, vec3 V, vec4 diffuse) {
	const vec3 lightPos = vec3(0.0,0.0,2.0);
	vec3 L = normalize( lightPos - V );
	V = -normalize(V);
	vec3 R = reflect(-L,N);
	float NdotL = max( 0.0, dot( N,L ) );
	float RdotV = max( 0.0, dot( R,V ) );
	float Ispec = pow( RdotV, 20.0 );
	return diffuse * NdotL + Ispec;
}

void main()
{
	float s = dot(0.3*vec4(0.0,1.0,-1.0,0.0),obj);
	float t = dot(0.3*vec4(-2.0,-1.0,1.0,0.0),obj);
	float c = (texture2D(sampler,vec2(s,t))).x;
	vec4 white = vec4(1.0,1.0,1.0,1.0);
	vec4 redish = vec4(0.5,0.2,0.2,1.0);
	vec4 diff;
	if (c < 0.5) diff = mix(white,redish,2.0*c);
	else diff = mix(redish,white,-1.0+2.0*c);
	gl_FragColor = shading(normalize(gl_NormalMatrix*norm),normalize((gl_ModelViewMatrix*obj).xyz),diff);
}
