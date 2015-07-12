varying vec3 N;   	// normal (en object space)
varying vec3 P; 	// posicio del vertex (en object space)
uniform float time;
uniform bool rotate;

// V, N, P, lightPos han d'estar al mateix espai de coordenades
// V és el vector unitari cap a l'observador
// N és la normal
// P és la posició 
// lightPos és la posició de la llum
// lightColor és el color de la llum
vec4 light(vec3 V, vec3 N, vec3 P, vec3 lightPos, vec3 lightColor)
{
	const float shininess = 100.0;
	const float Kd = 0.5;
	N = normalize(N);
	vec3 L = normalize(lightPos - P);
	vec3 R = reflect(-L, N);
	float NdotL = max(0.0, dot(N,L));
	float RdotV = max(0.0, dot(R,V));
	float spec =  pow( RdotV, shininess);
	return vec4(Kd*lightColor*NdotL + vec3(spec),0);
}

void main()
{
	mat4 lights = mat4(
		0,10,0,1,
		0,-10,0,1,
		10,0,0,1,
		-10,0,0,1
	);

	mat4 lightCol = mat4(
		0,1,0,0,
		1,1,0,0,
		0,0,1,0,
		1,0,0,0
	);

	mat3 rotation = mat3(
		cos(time),sin(time),0,
		-sin(time),cos(time),0,
		0,0,1
	);

	vec4 aux = vec4(0,0,0,0);
	vec4 c;

	vec3 V = normalize(gl_ModelViewMatrixInverse[3].xyz - P);
	for (int i = 0; i < 4; i++) {
		if (rotate) {
			vec3 var = vec3(gl_ModelViewMatrixInverse*vec4(rotation*vec3(lights[i]),1));
			c = light(V, N, P, var, vec3(lightCol[i]));
		}
		else {
			c = light(V, N, P, vec3(gl_ModelViewMatrixInverse*lights[i]), vec3(lightCol[i]));
		}
		aux += c;
	}
	gl_FragColor = aux;
}
