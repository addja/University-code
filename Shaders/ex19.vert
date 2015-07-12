// simple vertex shader
uniform float time;

void main()
{
	float a = -time * gl_MultiTexCoord0.s;
	mat4 some = mat4(
		cos(a),0.0,-sin(a),0.0,
		0.0,1.0,0.0,0.0,
		sin(a),0.0,cos(a),0.0,
		0.0,0.0,0.0,1.0
	);
	gl_Position    = gl_ModelViewProjectionMatrix * some * gl_Vertex;
	gl_FrontColor  = vec4(0,0,1,0);
	gl_TexCoord[0] = gl_MultiTexCoord0;
}
