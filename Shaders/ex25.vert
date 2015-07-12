// simple vertex shader
uniform float time;
uniform float amplitude;

void main()
{
	float y = amplitude*sin(2.0*3.1415*gl_Vertex.x+3.0*time);
	vec4 v = vec4(gl_Vertex.x,y,gl_Vertex.z,gl_Vertex.w);
	gl_Position    = gl_ModelViewProjectionMatrix * v;
	gl_FrontColor  = vec4(1.0,0.0,0.0,0.0);
	gl_TexCoord[0] = gl_MultiTexCoord0;
}
