// simple vertex shader
uniform float time;
varying vec3 eyevert;

void main()
{
	float a = time * 0.1;
	mat4 some = mat4(
		cos(a),0.0,-sin(a),0.0,
		0.0,1.0,0.0,0.0,
		sin(a),0.0,cos(a),0.0,
		0.0,0.0,0.0,1.0
	);
	gl_Position    = gl_ModelViewProjectionMatrix * some * gl_Vertex;
	gl_FrontColor  = gl_Color;
	gl_TexCoord[0] = gl_MultiTexCoord0;
	eyevert = (gl_ModelViewMatrix *some * gl_Vertex).xyz;
}
