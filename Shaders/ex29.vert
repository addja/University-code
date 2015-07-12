// simple vertex shader
varying vec4 eyecoord;
varying vec4 norm;

void main()
{
	gl_Position    = gl_ModelViewProjectionMatrix * gl_Vertex;
	eyecoord = (gl_ModelViewMatrix * gl_Vertex);
	norm = vec4(gl_NormalMatrix * gl_Normal, 0);
	gl_FrontColor  = gl_Color;
	gl_TexCoord[0] = gl_MultiTexCoord0;
}
