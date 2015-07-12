// simple geometry shader

// these lines enable the geometry shader support.
#version 120
#extension GL_EXT_geometry_shader4 : enable

varying in vec3 vNormal[3];
const float speed = 1.2;
uniform float time;

void main( void )
{
	vec3 n = (vNormal[0] + vNormal[1] + vNormal[2])/3.0;
	vec3 t = speed*time*n;

	for( int i = 0 ; i < gl_VerticesIn ; i++ )
	{
		gl_FrontColor  = gl_FrontColorIn[i];
		vec4 v = vec4(gl_PositionIn[i].xyz+t,gl_PositionIn[i].w);
		gl_Position = gl_ModelViewProjectionMatrix*v;
		EmitVertex();
	}
}
