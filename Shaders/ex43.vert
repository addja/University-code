// simple vertex shader

void main()
{
	vec4 p = gl_ModelViewProjectionMatrix * gl_Vertex;
	gl_Position    = vec4(p.x,p.y,-p.z,p.w);
	gl_FrontColor  = gl_Color;
	gl_TexCoord[0] = gl_MultiTexCoord0;
}
