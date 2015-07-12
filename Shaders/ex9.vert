// simple vertex shader
uniform float speed;
uniform float time;

void main()
{
	gl_Position    = gl_ModelViewProjectionMatrix * gl_Vertex;
	gl_FrontColor = vec4((gl_NormalMatrix * gl_Normal).z);
	gl_TexCoord[0].s = gl_MultiTexCoord0.s+speed*time;
	gl_TexCoord[0].t = gl_MultiTexCoord0.t;
}
