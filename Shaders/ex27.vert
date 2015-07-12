// simple vertex shader
uniform float offset;
varying float num;

void main()
{
	vec4 v = -gl_ModelViewMatrixInverse*gl_LightSource[0].position;
	float a = v.x;
	float b = v.y;
	float c = v.z;
	num = gl_Vertex.x*a+gl_Vertex.y*b+gl_Vertex.z*c+offset;
	gl_Position    = gl_ModelViewProjectionMatrix * gl_Vertex;
	gl_FrontColor  = gl_Color;
}
