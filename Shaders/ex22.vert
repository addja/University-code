// simple vertex shader
uniform float scale;
uniform float time;

float triangleWave(float x) {
	return abs(mod(x, 2.0) - 1.0);
}

void main()
{
	vec3 t = vec3(triangleWave(time/1.618),triangleWave(time),0.0);
	vec3 t1 = scale*(vec3(-1.0,-1.0,0.0)+vec3(2.0,2.0,0.0)*t);
	mat4 T = mat4(
		1.0,0.0,0.0,0.0,
		0.0,1.0,0.0,0.0,
		0.0,0.0,1.0,0.0,
		t1,1.0
	);
	vec4 v = T*gl_ModelViewProjectionMatrix*gl_Vertex;
	v /= vec4(scale,scale,scale,1);
	gl_Position    = v;
	gl_FrontColor  = vec4(0.3, 0.3, 0.9, 1.0)*(gl_NormalMatrix * gl_Normal).z;
	gl_TexCoord[0] = gl_MultiTexCoord0;
}
