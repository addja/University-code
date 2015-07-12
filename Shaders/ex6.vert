// simple vertex shader

varying vec3 v;
varying vec3 N;

void main()
{
	gl_Position    = gl_ModelViewProjectionMatrix * gl_Vertex;
	gl_FrontColor  = gl_Color;
	gl_TexCoord[0] = gl_MultiTexCoord0;

	v = vec3(gl_ModelViewMatrix * gl_Vertex);
	N = gl_NormalMatrix * gl_Normal;	
}