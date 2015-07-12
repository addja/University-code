// simple vertex shader
uniform float n;

void main()
{
	vec4 lightpos = gl_ModelViewMatrixInverse*gl_LightSource[0].position;
	float d = distance(lightpos,gl_Vertex);
	float w = clamp(1.0/pow(d,n),0.0,1.0);
	vec4 v2 = (1.0-w)*gl_Vertex+w*lightpos;
	gl_Position = gl_ModelViewProjectionMatrix * v2;
	gl_FrontColor = vec4((gl_NormalMatrix * gl_Normal).z);
}
