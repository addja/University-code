// simple vertex shader
uniform float time;
uniform float freq;
uniform float amp;

void main()
{
	vec4 v = gl_Vertex + vec4(gl_Normal,0)*amp * sin(freq*time);
	gl_Position    = gl_ModelViewProjectionMatrix * v;
	gl_FrontColor  = vec4((gl_NormalMatrix*gl_Normal).z);
}
