// simple vertex shader
varying vec3 eyevert;

void main()
{
	gl_Position    = gl_ModelViewProjectionMatrix * gl_Vertex;
	gl_FrontColor  = gl_Color;
	eyevert = (gl_ModelViewMatrix * gl_Vertex).xyz;
	gl_TexCoord[0] = gl_MultiTexCoord0;
}
