// simple vertex shader
uniform float time;
uniform float freq;
uniform float amp;
uniform sampler2D sampler;

void main()
{
	gl_TexCoord[0] = gl_MultiTexCoord0;
	vec4 fase = 2.0*3.1415*texture2D(sampler, gl_TexCoord[0].st);
	vec4 v = gl_Vertex + vec4(gl_Normal,0)*amp * sin(freq*time+fase);
	gl_Position    = gl_ModelViewProjectionMatrix * v;
	gl_FrontColor  = vec4((gl_NormalMatrix*gl_Normal).z);
}
