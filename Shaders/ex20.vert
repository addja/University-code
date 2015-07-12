// simple vertex shader
uniform float time;
uniform bool eyespace;

void main()
{
	float amp;
	if (eyespace) amp = (gl_ModelViewMatrix*gl_Vertex).y;
	else amp = gl_Vertex.y;
	vec4 v = gl_Vertex + vec4(gl_Normal,0)*amp* sin(time/(2.0*3.1415));
	gl_Position    = gl_ModelViewProjectionMatrix * v;
	gl_FrontColor  = gl_Color;
	gl_TexCoord[0] = gl_MultiTexCoord0;
}
