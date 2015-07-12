// simple fragment shader

// 'time' contains seconds since the program was linked.
uniform float time;
varying vec4 v;
varying vec3 N;

uniform bool world;

vec4 light(vec3 N, vec3 V, vec3 L) {
	N=normalize(N); V=normalize(V); L=normalize(L);
	vec3 R = normalize( 2.0*dot(N,L)*N-L );
	float NdotL = max( 0.0, dot( N,L ) );
	float RdotV = max( 0.0, dot( R,V ) );
	float Idiff = NdotL;
	float Ispec = 0.0;

	if (NdotL>0.0) Ispec=pow( RdotV, gl_FrontMaterial.shininess );
	return gl_FrontMaterial.emission +
	gl_FrontMaterial.ambient * gl_LightModel.ambient +
	gl_FrontMaterial.ambient * gl_LightSource[0].ambient +
	gl_FrontMaterial.diffuse * gl_LightSource[0].diffuse * Idiff+
	gl_FrontMaterial.specular * gl_LightSource[0].specular * Ispec;
}

void main()
{
	vec3 L, V, newN;
	if (world) {
		L = vec3(gl_ModelViewMatrixInverse*gl_LightSource[0].position - v);
		V = vec3(gl_ModelViewMatrixInverse*vec4(0,0,0,1)-v);
		newN = N;
	}
	else {
		V = vec3(-gl_ModelViewMatrix * v);
		L = vec3(gl_LightSource[0].position - gl_ModelViewMatrix*v);
		newN = gl_NormalMatrix * N;
	}
	gl_FragColor = light(newN,V,L);
}
