// simple vertex shader

uniform float offset;

void main()
{
	mat4 R = mat4(
		1.0,0.0,0.0,0.0,
		0.0,1.0,0.0,0.0,
		0.0,0.0,-1.0,0.0,
		0.0,0.0,0.0,1.0
	);
	mat4 T = mat4(
		1.0,0.0,0.0,0.0,
		0.0,1.0,0.0,0.0,
		0.0,0.0,1.0,0.0,
		0.0,0.0,2.0*offset,1.0
	);

	gl_Position    = gl_ModelViewProjectionMatrix *R*T* gl_Vertex;
	gl_FrontColor  = gl_Color * (-1.0*gl_NormalMatrix * gl_Normal)[2];
}
