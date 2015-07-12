// simple vertex shader
uniform vec2 Min, Max;

void main()
{
	float s = 2.0*(-0.5+(gl_MultiTexCoord0.s-Min.x)/(Max.y-Min.y));
	float t = 2.0*(-0.5+(gl_MultiTexCoord0.t-Min.y)/(Max.y-Min.y));
	gl_Position    = vec4(s,t,0.0,1.0);
	gl_FrontColor  = gl_Color;
	gl_TexCoord[0] = gl_MultiTexCoord0;
}
