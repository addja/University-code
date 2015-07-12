// simple vertex shader

varying float var;

void main()
{
	gl_Position    = gl_ModelViewProjectionMatrix * gl_Vertex;
	gl_FrontColor  = gl_Color;
	gl_TexCoord[0] = gl_MultiTexCoord0;
	var = (gl_NormalMatrix * gl_Normal)[2];
}
