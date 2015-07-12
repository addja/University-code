// simple fragment shader

// 'time' contains seconds since the program was linked.
uniform float time;
varying vec3 v, N;

void main()
{
	// Phong vars
	vec4 Ma = gl_LightModel.ambient;
	vec4 Ia = gl_LightSource[0].ambient;
	vec4 Id = gl_LightSource[0].diffuse;
	vec4 Is = gl_LightSource[0].specular;

	vec4 Ke = gl_FrontMaterial.emission;
	vec4 Ka = gl_FrontMaterial.ambient;
	vec4 Kd = gl_FrontMaterial.diffuse;
	vec4 Ks = gl_FrontMaterial.specular;
	float s = gl_FrontMaterial.shininess;

	vec3 L = normalize(gl_LightSource[0].position.xyz - v);
	vec3 V = normalize(-v);
	vec3 R = normalize(2.0*dot(N,L)*N-L);

	float var = Ks*Is*pow(max(0.0,dot(R,V)),s);
	if (var > 0.0) gl_FragColor  = Ke+Ka*(Ma+Ia)+Kd*Id*max(0.0,dot(N,L))+var;
	else gl_FragColor  = Ke+Ka*(Ma+Ia)+Kd*Id*max(0.0,dot(N,L));
}
