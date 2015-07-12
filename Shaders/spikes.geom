// simple geometry shader

// these lines enable the geometry shader support.
#version 120
#extension GL_EXT_geometry_shader4 : enable

uniform float disp;
varying out vec3 gNormal;

void main( void )
{
	// Calculate normal
	vec3 v1 = gl_PositionIn[1].xyz - gl_PositionIn[0].xyz;
	vec3 v2 = gl_PositionIn[2].xyz - gl_PositionIn[0].xyz;
	vec3 N = normalize(cross(v1,v2)); 

	vec4 c = (gl_PositionIn[0]+gl_PositionIn[1]+gl_PositionIn[2])/3;
	vec4 pos = c + vec4(disp*N,0);
	
	// definition of vertices is cylic
	for (int i = 0; i < 3; i++) {		
		vec3 a = gl_PositionIn[i].xyz - pos.xyz;
		vec3 b = gl_PositionIn[(i+1)%3].xyz - pos.xyz;
		gNormal = normalize(cross(a,b));

		gl_FrontColor  = vec4(gNormal.xyz,0.0);  //gl_FrontColorIn[i];
        gl_Position    = gl_ProjectionMatrix * gl_PositionIn[i];
        EmitVertex();
        gl_FrontColor  = vec4(gNormal.xyz,0.0);//gl_FrontColorIn[(i+1)%3];
        gl_Position    = gl_ProjectionMatrix * gl_PositionIn[(i+1)%3];
        EmitVertex();
        gl_FrontColor  = vec4(gNormal.xyz,0.0); // vec4(1.0);
        gl_Position    = gl_ProjectionMatrix * pos;
        EmitVertex();
        EndPrimitive();
	}
}
