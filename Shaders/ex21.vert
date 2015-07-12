// simple vertex shader
uniform float time;
uniform float speed;

void main()
{
	float a = time * speed;
	mat4 some = mat4(
		cos(a),0.0,-sin(a),0.0,
		0.0,1.0,0.0,0.0,
		sin(a),0.0,cos(a),0.0,
		0.0,0.0,0.0,1.0
	);
	gl_Position    = gl_ModelViewProjectionMatrix * some * gl_Vertex;
	gl_FrontColor  = gl_Color;
	gl_TexCoord[0] = gl_MultiTexCoord0;
}
