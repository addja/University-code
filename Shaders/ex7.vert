// simple vertex shader

varying vec4 v;
varying vec3 N;

void main()
{
	gl_Position    = gl_ModelViewProjectionMatrix * gl_Vertex;
	gl_FrontColor  = gl_Color;
	gl_TexCoord[0] = gl_MultiTexCoord0;

	v = gl_Vertex;
	N = gl_Normal;	
}