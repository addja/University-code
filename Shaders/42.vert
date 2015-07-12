// simple vertex shader
uniform float d;
uniform float time;

attribute vec3 attrTangent;
attribute vec3 attrBitangent;

const float PI = 3.141592;

void main()
{
	float fase = time + 2.0*PI*(gl_MultiTexCoord0.s + gl_MultiTexCoord0.t);
	vec3 C = vec3(d*cos(fase),d*sin(fase),0.0);
	vec3 offset = mat3(attrTangent,attrBitangent,gl_Normal) * C; 
	vec4 V = gl_Vertex + vec4(offset,0.0);
	gl_Position    = gl_ModelViewProjectionMatrix * V;
	gl_FrontColor  = gl_Color;
	gl_TexCoord[0] = gl_MultiTexCoord0;
}
