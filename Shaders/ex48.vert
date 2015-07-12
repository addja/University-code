// simple vertex shader
varying vec3 norm;
varying vec4 obj;

void main()
{
	gl_Position    = gl_ModelViewProjectionMatrix * gl_Vertex;
	gl_FrontColor  = gl_Color;
	gl_TexCoord[0] = gl_MultiTexCoord0;
	obj = gl_Vertex;
	norm = gl_Normal;
}
