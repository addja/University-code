// simple vertex shader
uniform int tiles;

void main()
{
	gl_Position    = gl_ModelViewProjectionMatrix * gl_Vertex;
	gl_FrontColor = vec4((gl_NormalMatrix * gl_Normal).z);
	gl_TexCoord[0] = gl_MultiTexCoord0*tiles;
}
