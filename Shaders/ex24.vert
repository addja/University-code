// simple vertex shader
uniform float time;

void main()
{
	float a = 0.4*gl_Vertex.y*sin(time);
	mat3 rotation = mat3(
		cos(a),0.0,-sin(a),
		0.0,1.0,0.0,
		sin(a),0.0,cos(a)
	);
	vec3 v = rotation* gl_Vertex.xyz;
	gl_Position    = gl_ModelViewProjectionMatrix *vec4(v,1.0);
	gl_FrontColor  = gl_Color;
	gl_TexCoord[0] = gl_MultiTexCoord0;
}
